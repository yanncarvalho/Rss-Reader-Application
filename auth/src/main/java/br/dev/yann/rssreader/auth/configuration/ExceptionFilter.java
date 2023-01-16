package br.dev.yann.rssreader.auth.configuration;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.dev.yann.rssreader.auth.user.SingleUserAdminException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ExceptionFilter extends Http403ForbiddenEntryPoint {
	

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorValidator processException(Exception e) {
	    return new ErrorValidator("An internal server error occurred, contact system administrator", 
	    						  e.getMessage());
    }
	 
	@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorValidator> processResponseStatusException(ResponseStatusException e) {
	   return ResponseEntity
			   .status(e.getStatusCode())
			   .body(new ErrorValidator(e.getReason(), e.getMessage()));
    }
	
	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorValidator processUsernameNotFoundException(UsernameNotFoundException e) {
	   return new ErrorValidator("Username not found", e.getMessage());
    }
	
	@ExceptionHandler(SingleUserAdminException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
    public ErrorValidator processSingleUserAdminException(SingleUserAdminException e) {
	   return new ErrorValidator("User is the only admin of the system", e.getMessage());
    }
	
	@ExceptionHandler(BadJwtException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorValidator processBadJwtException(BadJwtException e) {
	   return new ErrorValidator("Bad JWT", e.getMessage());
    }
	
	@ExceptionHandler(JwtEncodingException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorValidator procesJwtEncodingException(JwtEncodingException e) {
	   return new ErrorValidator("Not possible to Encode JWT", e.getMessage());
    }
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorValidator processBadCredentialsException(BadCredentialsException e) {
	   return new ErrorValidator("Bad Credentials", e.getMessage());
    }
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorValidator procesNullPointerException(NullPointerException e) {
	   return new ErrorValidator("Data is null", e.getMessage());
    }

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse res,
	    AuthenticationException authException) throws IOException {
		res.setContentType("application/json;charset=UTF-8");
	    res.setStatus(HttpStatus.FORBIDDEN.value());
	    res.getWriter().write(new ObjectMapper().writeValueAsString(
	    	 new ErrorValidator("Access forbidden", authException.getMessage())
	     ));
	}
	
	
    private record ErrorValidator(String message, String error) {}
}
