package io.github.yanncarvalho.rssreader.cloudgateway.configuration;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * Handle Exception in the application, extends {@link DefaultErrorAttributes}.	
 *
 * @author Yann Carvalho
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionFilter extends DefaultErrorAttributes{

	private static final String GENERIC_ERROR = "Cannot proceed.";
	private static final String NOT_FOUND_URL = "URL not found.";
	public static final String INVALID_REQUEST = "Request is not valid.";
	private static final String NO_MESSAGE_AVAILABLE = "No message available"; 
	
	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		var error = super.getErrorAttributes(request, options);
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
		public ErrorRes(int status, Object message, String error) {
			this(status, Objects.toString(message, INVALID_REQUEST), List.of(error));
		}
		public Map<String, Object> toMap() {
			return Map.of("status", status, "message", message, "errors", errors);
		}
	}
}
