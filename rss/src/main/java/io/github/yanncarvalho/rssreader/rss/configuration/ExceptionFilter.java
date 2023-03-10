package io.github.yanncarvalho.rssreader.rss.configuration;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.DATA_NULL;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.GENERIC_ERROR;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.INVALID_REQUEST;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.LOGGER_EXCEPTION_DEFAULT;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.NOT_FOUND_URL;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.NO_MESSAGE_AVAILABLE;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

/**
 * Handle Exception in the application, extends {@link DefaultErrorAttributes}.	
 *
 * @author Yann Carvalho
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionFilter  extends DefaultErrorAttributes{
	
	private static Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		var error = super.getErrorAttributes(webRequest, options);
		var message = Objects.toString(error.get("message"), "");
		var errorMsg = error.get("error");
		
		Object objStatus = error.get("status");
		
		Integer status = (objStatus instanceof Integer) 
										? (Integer) objStatus 
										: HttpStatus.INTERNAL_SERVER_ERROR.value(); 
		if(status.equals(HttpStatus.NOT_FOUND.value())) {
			message = NOT_FOUND_URL; 
		}
		
	
		if(!status.equals(HttpStatus.NO_CONTENT.value()) &&
			(message.isBlank() || message.equals(NO_MESSAGE_AVAILABLE))) {
			message = GENERIC_ERROR; 
		}
			
		return new ErrorRes(status, errorMsg, message).toMap(); 
	  }
		
	
	/**
	 * The most generic handler. It is call when {@link HttpMessageNotReadableException} is thrown.
	 *
	 * @param ex error thrown.
	 * @param request information for HTTP servlets.
	 * 
	 * @return message: {@link DefaultValue#GENERIC_ERROR}, <br/>
	 *         errors: {@link Exception#getLocalizedMessage} wrapped in a {@link List}, <br/>
	 *         and http status: {@link HttpStatus#BAD_REQUEST}.
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorRes processException(HttpMessageNotReadableException ex, HttpServletRequest request) {
		logger.warn(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(),  ex));
		return new ErrorRes(HttpStatus.BAD_REQUEST.value(), GENERIC_ERROR,  ex.getLocalizedMessage().replaceAll("\\s\\(.*\\)", "."));
	}
	
	/**
	 * It is call when {@link ResponseStatusException} is thrown.
	 *
	 * @param ex error thrown.
     * @param request information for HTTP servlets.
     * 
	 * @return message: {@link DefaultValue#GENERIC_ERROR}, <br/>
	 *         errors: {@link ResponseStatusException#getReason} wrapped in a
	 *         {@link List}, <br/>
	 *         and {@link ResponseStatusException#getResponseHeaders http status}.
	 */
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorRes> processResponseStatusException(ResponseStatusException ex,  HttpServletRequest request) {
		logger.warn(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), ex));
		return ResponseEntity.status(ex.getStatusCode())
				     .body(new ErrorRes(ex.getStatusCode().value(), GENERIC_ERROR, ex.getReason()));
	}
	
	/**
	 * It is call when {@link UsernameNotFoundException} is thrown.
	 *
	 * @param ex error thrown.
     * @param request information for HTTP servlets.
	 *
	 * @return message: {@link DefaultValue#USER_NOT_FOUND}, <br/>
	 *         errors: {@link UsernameNotFoundException#getLocalizedMessage} wrapped in a
	 *         {@link List}, <br/>
	 *         and http status: {@link HttpStatus#UNAUTHORIZED}.
	 */
	@ExceptionHandler(FeignException.class)
	public String processFeignExceptionException(FeignException ex, HttpServletResponse response) {
		response.setStatus(ex.status());
		return ex.contentUTF8(); 
	}

	/**
	 * It is call when {@link MethodArgumentNotValidException} is thrown.
	 *
	 * @param ex error thrown.
     * @param request information for HTTP servlets.
     * 
	 * @return message: {@link DefaultValue#INVALID_REQUEST}, <br/>
	 *         errors: {@link MethodArgumentNotValidException#getBindingResult}
	 *         wrapped in a {@link List}, of String <br/>
	 *         and http status: {@link HttpStatus#BAD_REQUEST}.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorRes processMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
		var errors = ex.getBindingResult().getAllErrors().stream()
				.map(error -> ((FieldError) error).getField() + " " + error.getDefaultMessage())
				.sorted(Comparator.naturalOrder()).toList();
		logger.warn(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), errors));
		return new ErrorRes(HttpStatus.BAD_REQUEST.value(), INVALID_REQUEST, errors);
	}
	
	/**
	 * It is call when {@link MethodArgumentNotValidException} is thrown.
	 *
	 * @param ex error thrown.
     * @param request information for HTTP servlets.
     * 
	 * @return message: {@link DefaultValue#INVALID_REQUEST}, <br/>
	 *         errors: {@link ConstraintViolationException#getLocalizedMessage}
	 *         wrapped in a {@link List}, of String <br/>
	 *         and http status: {@link HttpStatus#BAD_REQUEST}.
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorRes processConstraintViolationException(ConstraintViolationException ex,  HttpServletRequest request) {
		var error =  ex.getLocalizedMessage().replaceFirst("^.*\\.","");
		logger.warn(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), ex.getLocalizedMessage()));
		return new ErrorRes(HttpStatus.BAD_REQUEST.value(), INVALID_REQUEST, error);
	}
	
	/**
	 * It is call when {@link IllegalArgumentException} is thrown.
	 *
	 * @param ex error thrown.
     * @param request information for HTTP servlets.
     * 
	 * @return message: {@link DefaultValue#INVALID_REQUEST}, <br/>
	 *         errors: {@link IllegalArgumentException#getLocalizedMessage}
	 *         wrapped in a {@link List}, of String <br/>
	 *         and http status: {@link HttpStatus#BAD_REQUEST}.
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorRes processIllegalArgumentException(IllegalArgumentException ex,  HttpServletRequest request) {
	    logger.warn(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), ex.getLocalizedMessage()));
		return new ErrorRes(HttpStatus.BAD_REQUEST.value(), INVALID_REQUEST, ex.getLocalizedMessage());
	}
	
	/**
	 * It is call when {@link NullPointerException} is thrown.
	 *
	 * @param ex error thrown.
	 * @param request information for HTTP servlets.
	 *
	 * @return message: {@link DefaultValue#DATA_NULL}, <br/>
	 *         errors: {@link NullPointerException#getLocalizedMessage} wrapped in a
	 *         {@link List}, <br/>
	 *         and http status: {@link HttpStatus#UNAUTHORIZED}.
	 */
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorRes procesNullPointerException(NullPointerException ex, HttpServletRequest request) {
		logger.warn(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), ex.getLocalizedMessage()));
		return new ErrorRes(HttpStatus.UNAUTHORIZED.value(), DATA_NULL, ex.getLocalizedMessage());
	}

	/**
	 * Error response record.
	 *
	 * @param message {@link #message}.
	 * @param error   {@link #error}.
	 * @param status   {@link #status}.
	 */
	private record ErrorRes(
		/** error status. */
		int status,
		
		/** custom message. */
		String message,

		/** error message. */
		List<String> errors) {

		/**
		 * Constructs a {@code ErrorRes} with message and error as parameters.
		 * 
		 * @param message {@link #message}.
		 * @param error   {@link #error} wrapped in a {@link List}.
		 */
		public ErrorRes(int status, String message, String error) {
			this(status, message, List.of(error));
		}
		public ErrorRes(int status, Object message, Object error) {
			this(status, Objects.toString(message, INVALID_REQUEST), List.of(Objects.toString(error, INVALID_REQUEST)));
		}
		public Map<String, Object> toMap() {
			return Map.of("status", status, "message", message, "errors", errors);
		}
	}
}
