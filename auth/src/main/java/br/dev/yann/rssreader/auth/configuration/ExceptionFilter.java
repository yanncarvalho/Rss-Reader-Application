package br.dev.yann.rssreader.auth.configuration;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import br.dev.yann.rssreader.auth.user.SingleUserAdminException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handle Exception in the application, extends {@link Http403ForbiddenEntryPoint}.
 * 
 * @author Yann Carvalho
 */
@RestControllerAdvice
public class ExceptionFilter extends Http403ForbiddenEntryPoint  {

	/**
	 * The most generic handler. It is call when {@link Exception} is thrown.
	 * @param ex error thrown.
	 * 
	 * @return  message: 'An internal server error occurred, contact system administrator.',
	 * 			errors: {@link Exception#getMessage error message} wrapped in a {@link List}
	 *          and http status: {@link HttpStatus#INTERNAL_SERVER_ERROR}.
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorRes processException(Exception ex)  {
	    return new ErrorRes("An internal server error occurred, contact system administrator.", 
	    		ex.getMessage());
    }

	/**
	 * It is call when {@link ResponseStatusException} is thrown.
	 * @param ex error thrown.
	 * 
	 * @return  message: 'Cannot proceed.',
	 * 			errors: {@link ResponseStatusException#getReason error message} wrapped in a {@link List}
	 *          and {@link ResponseStatusException#getResponseHeaders http status}.
	 */
	@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorRes> processResponseStatusException(ResponseStatusException ex)  {
	   return ResponseEntity
			   .status(ex.getStatusCode())
			   .body(new ErrorRes("Cannot proceed.",ex.getReason()));
    }
	
	/**
	 * It is call when {@link UsernameNotFoundException} is thrown.
	 * @param ex error thrown.
	 * 
	 * @return 	message: 'User not found.',
	 * 		    errors: {@link UsernameNotFoundException#getMessage error message} wrapped in a {@link List}
	 *          and http status: {@link HttpStatus#UNAUTHORIZED}.
	 */
	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorRes processUsernameNotFoundException(UsernameNotFoundException ex)  {
	   return new ErrorRes("User not found.", ex.getMessage());
    }
	
	
	/**
	 * It is call when {@link MethodArgumentNotValidException} is thrown.
	 * @param ex error thrown.
	 * 
	 * @return 	message: 'Request is not valid',
	 * 			errors: {@link MethodArgumentNotValidException#getBindingResult error message} 
	 * 			wrapped in a {@link List}  of String
	 *          and http status:  {@link HttpStatus#BAD_REQUEST}.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorRes processMethodArgumentNotValidException(MethodArgumentNotValidException ex)  {
		var errors = 
				ex.getBindingResult()
						.getAllErrors().stream()
						.map(error -> ((FieldError) error).getField() + " " + error.getDefaultMessage())
						.sorted(Comparator.naturalOrder())
						.toList();
	    	
		return new ErrorRes("Request is not valid.", errors);
    }
	
	/**
	 * It is call when {@link SingleUserAdminException} is thrown.
	 * @param ex error thrown.
	 * 
	 * @return 	message: 'User is the only admin of the system',
	 * 			errors: {@link SingleUserAdminException#getMessage error message} wrapped in a {@link List}
	 *          and http status: {@link HttpStatus#CONFLICT}.
	 */
	@ExceptionHandler(SingleUserAdminException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
    public ErrorRes processSingleUserAdminException(SingleUserAdminException ex)  {
	   return new ErrorRes("User is the only admin of the system.", ex.getMessage());
    }
	
	/**
	 * It is call when {@link BadJwtException} is thrown.
	 * @param ex error thrown.
	 * 
	 * @return 	message: 'Bad JWT',
	 * 			errors: {@link SingleUserAdminException#getMessage error message} wrapped in a {@link List}
	 *          and http status: {@link HttpStatus#UNAUTHORIZED}.
	 */
	@ExceptionHandler(BadJwtException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorRes processBadJwtException(BadJwtException ex)  {
	   return new ErrorRes("Bad JWT.", ex.getMessage());
    }
	
	/**
	 * It is call when {@link JwtEncodingException} is thrown.
	 * @param ex error thrown.
	 * 
	 * @return 	message: 'Not possible to encode JWT',
	 * 			errors: {@link JwtEncodingException#getMessage error message} wrapped in a {@link List}
	 *          and http status: {@link HttpStatus#INTERNAL_SERVER_ERROR}.
	 */
	@ExceptionHandler(JwtEncodingException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorRes procesJwtEncodingException(JwtEncodingException ex)  {
	   return new ErrorRes("Not possible to encode JWT.", ex.getMessage());
    }
	
	/**
	 * It is call when {@link BadCredentialsException} is thrown.
	 * @param ex error thrown.
	 * 
	 * @return 	message: 'Bad Credentials',
	 * 			errors: {@link BadCredentialsException#getMessage error message} wrapped in a {@link List}
	 *          and http status: {@link HttpStatus#UNAUTHORIZED}.
	 */
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorRes processBadCredentialsException(BadCredentialsException ex)  {
	   return new ErrorRes("Bad Credentials.", ex.getMessage());
    }
	
	/**
	 * It is call when {@link NullPointerException} is thrown.
	 * @param ex error thrown.
	 * 
	 * @return 	message: 'Data are null',
	 * 			errors: {@link NullPointerException#getMessage error message} wrapped in a {@link List}
	 *          and http status: {@link HttpStatus#UNAUTHORIZED}.
	 */
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorRes procesNullPointerException(NullPointerException ex)  {
	   return new ErrorRes("Data are null.", ex.getMessage());
    }

	/**
	 * It is call when request fail in {@link SecurityConfig}
	 * @return message: 'Access forbidden' || 'Not found.' , 
	 * 		   errors: {@link AuthenticationException#getMessage error message} || 
	 * 		   'URL not found.' wrapped in a {@link List}
	 *         and http status: {@link HttpServletResponse#getStatus}.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse res,
	    AuthenticationException authException) throws IOException {
		res.setContentType("application/json;charset=UTF-8");
	    if(res.getStatus() == HttpStatus.NOT_FOUND.value()) {
	  	    res.getWriter().write(new ObjectMapper().writeValueAsString(
	  	    	 new ErrorRes("Not found.", "URL not found."))
	  	     );
	  	    return;
	    }
	    res.getWriter().write(new ObjectMapper().writeValueAsString(
	    	 new ErrorRes("Access forbidden.", authException.getMessage())
	     ));
	}
	
	/**
	 * Error response record.
	 *
	 * @param message {@link #message}.
	 * @param error {@link #error}.
	 */
    private record ErrorRes(
    		/** custom message.*/
    		String message, 
    		
    		/**	error message.*/
    		List<String> errors) {
    	
    	/**
    	 * Constructs a {@code ErrorRes} with message and error as parameters
    	 * @param message {@link #message} 
    	 * @param error {@link #error} wrapped in a {@link List}
    	 */
    	public ErrorRes (String message, String error) {
    		this(message, List.of(error));
    	}
    }
}
