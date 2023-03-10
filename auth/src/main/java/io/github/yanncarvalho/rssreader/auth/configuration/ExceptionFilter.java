package io.github.yanncarvalho.rssreader.auth.configuration;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.ACESS_FORBIDDEN;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.DATA_NULL;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.GENERIC_ERROR;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.INCORRET_CREDENTIALS;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.INTERNAL_SERVER_ERROR;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.INVALID_REQUEST;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.JWT_BAD;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.JWT_ENCODE_PROBLEM;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.LOGGER_EXCEPTION_DEFAULT;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.NOT_FOUND;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.NOT_FOUND_URL;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.USERNAME_NOT_UNIQUE;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.USER_NOT_FOUND;
import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.USER_SINGLE_ADMIN;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.yanncarvalho.rssreader.auth.user.exception.UserSingleAdminException;
import io.github.yanncarvalho.rssreader.auth.user.exception.UsernameNotUniqueException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

/**
 * Handle Exception in the application, extends {@link Http403ForbiddenEntryPoint}.
 *
 * @author Yann Carvalho
 */
@RestControllerAdvice
public class ExceptionFilter extends Http403ForbiddenEntryPoint {
	
    private static Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

	/**
	 * The most generic handler. It is call when {@link Exception} is thrown.
	 *
	 * @param ex error thrown.
	 * @param request information for HTTP servlets.
	 * 
	 * @return message: {@link DefaultValue#INTERNAL_SERVER_ERROR}, <br/>
	 *         errors: {@link Exception#getLocalizedMessage} wrapped in a {@link List}, <br/>
	 *         and http status: {@link HttpStatus#INTERNAL_SERVER_ERROR}.
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorRes processException(Exception ex, HttpServletRequest request) {
		logger.error(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), ex));
		return new ErrorRes(HttpStatus.INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
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
	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorRes processUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
		logger.error(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), ex));
		return new ErrorRes(HttpStatus.UNAUTHORIZED.value(), USER_NOT_FOUND, ex.getLocalizedMessage());
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
	 * It is call when {@link UserSingleAdminException} is thrown.
	 *
	 * @param ex error thrown.
     * @param request information for HTTP servlets.
     * 
	 * @return message: {@link DefaultValue#USER_SINGLE_ADMIN}, <br/>
	 *         errors: {@link UserSingleAdminException#getLocalizedMessage} wrapped in a
	 *         {@link List}, <br/>
	 *         and http status: {@link HttpStatus#CONFLICT}.
	 */
	@ExceptionHandler(UserSingleAdminException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorRes processSingleUserAdminException(UserSingleAdminException ex, HttpServletRequest request) {
		logger.error(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), ex));
    	return new ErrorRes(HttpStatus.CONFLICT.value(), USER_SINGLE_ADMIN, ex.getLocalizedMessage());
	}
	
	/**
	 * It is call when {@link UserSingleAdminException} is thrown.
	 *
	 * @param ex error thrown.
     * @param request information for HTTP servlets.
     * 
	 * @return message: {@link DefaultValue#INCORRET_CREDENTIALS}, <br/>
	 *         errors: {@link UsernameNotUniqueException#getLocalizedMessage} wrapped in a
	 *         {@link List}, <br/>
	 *         and http status: {@link HttpStatus#CONFLICT}.
	 */
	@ExceptionHandler(UsernameNotUniqueException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorRes processUsernameNotUniqueException(UsernameNotUniqueException ex, HttpServletRequest request) {
		logger.warn(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), ex.getLocalizedMessage()));
		return new ErrorRes(HttpStatus.CONFLICT.value(), USERNAME_NOT_UNIQUE, ex.getLocalizedMessage());
	}
	
	/**
	 * It is call when {@link BadJwtException} is thrown.
	 *
	 * @param ex error thrown.
     * @param request information for HTTP servlets.
     * 
	 * @return message: {@link DefaultValue#BAD_JWT}, <br/>
	 *         errors: {@link UserSingleAdminException#getLocalizedMessage} wrapped in a
	 *         {@link List}, <br/>
	 *         and http status: {@link HttpStatus#UNAUTHORIZED}.
	 */
	@ExceptionHandler(BadJwtException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorRes processBadJwtException(BadJwtException ex, HttpServletRequest request) {
		logger.warn(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), ex.getLocalizedMessage()));
		return new ErrorRes( HttpStatus.UNAUTHORIZED.value(), JWT_BAD, ex.getLocalizedMessage());
	}

	/**
	 * It is call when {@link JwtEncodingException} is thrown.
	 *
	 * @param ex error thrown.
     * @param request information for HTTP servlets.
     * 
	 * @return message: {@link DefaultValue#JWT_ENCODE_PROBLEM}, <br/>
	 *         errors: {@link JwtEncodingException#getLocalizedMessage} wrapped in a
	 *         {@link List}, <br/>
	 *         and http status: {@link HttpStatus#INTERNAL_SERVER_ERROR}.
	 */
	@ExceptionHandler(JwtEncodingException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorRes procesJwtEncodingException(JwtEncodingException ex, HttpServletRequest request) {
		logger.warn(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), ex.getLocalizedMessage()));
		return new ErrorRes(HttpStatus.INTERNAL_SERVER_ERROR.value(), JWT_ENCODE_PROBLEM, ex.getLocalizedMessage());
	}

	/**
	 * It is call when {@link BadCredentialsException} is thrown.
	 *
	 * @param ex error thrown.
     * @param request information for HTTP servlets.
     * 
	 * @return message: {@link DefaultValue#INCORRET_CREDENTIALS}, <br/>
	 *         errors: {@link BadCredentialsException#getLocalizedMessage} wrapped in a
	 *         {@link List}, <br/>
	 *         and http status: {@link HttpStatus#UNAUTHORIZED}.
	 */
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorRes processBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
		logger.warn(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), ex.getLocalizedMessage()));
		return new ErrorRes(HttpStatus.UNAUTHORIZED.value(), INCORRET_CREDENTIALS, ex.getLocalizedMessage());
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
	 * It is call when request fail in {@link SecurityConfig}.
	 *
	 * @return message: {@link DefaultValue#NOT_FOUND} ||
	 *         {@link DefaultValue#ACESS_FORBIDDEN} <br/>
	 *         errors: {@link DefaultValue#NOT_FOUND_URL} ||
	 *         {@link AuthenticationException#getLocalizedMessage} wrapped in a {@link List},
	 *         <br/>
	 *         and http status: {@link HttpServletResponse#getStatus}.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse res, 
			             AuthenticationException authException) throws IOException {
		res.setContentType("application/json;charset=UTF-8");
		if (res.getStatus() == HttpStatus.NOT_FOUND.value()) {
			res.getWriter().write(new ObjectMapper()
				.writeValueAsString(new ErrorRes(404, NOT_FOUND, NOT_FOUND_URL)));
			logger.warn(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), NOT_FOUND_URL));
			return;
		}
		logger.warn(LOGGER_EXCEPTION_DEFAULT(request.getRemoteAddr(), authException.getLocalizedMessage()));
		res.getWriter().write(new ObjectMapper()
				.writeValueAsString(
						new ErrorRes(res.getStatus(), ACESS_FORBIDDEN, authException.getLocalizedMessage())
						));
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
	}
}
