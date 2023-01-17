package br.dev.yann.rssreader.auth.configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import br.dev.yann.rssreader.auth.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT validation filter, this class extends {@link OncePerRequestFilter}.
 * 
 * @author Yann Carvalho
 */
@Component
public class JWTFilter extends OncePerRequestFilter {
	
    /**
     * Handler Exception Resolver
     * @see HandlerExceptionResolver
     */
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
	
    /**
     * User Service
     * @see UserService
     */
    @Autowired
    private UserService userService;
    
    /**
     * JWT service to encode and decode.
     * @see JWTService
     */
    @Autowired
    private JWTService tokenService;
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
       
        try {
               var tokenJWT = retrieveToken(request);
               var subject = tokenService.decode(tokenJWT).getSubject();
               var id = UUID.fromString(subject);
               var user = userService.findById(id);
               request.setAttribute("userUUID", id);
               var authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
               SecurityContextHolder.getContext().setAuthentication(authentication);    
        	   filterChain.doFilter(request, response);
         } catch (Exception err) {
        	 resolver.resolveException(request, response, null, err);
        }
    }

	/**
	 * Check if the access token has been sent.
	 * 
	 * @param request request sent.
	 * @return Access token.
	 * @throws NullPointerException if access token not found.
	 */
    private String retrieveToken(HttpServletRequest request) {
      var authorizationHeader = request.getHeader("Authorization");
      if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) 
        	throw new NullPointerException("Bearer token authentication was not found");
       
       return authorizationHeader.replace("Bearer ", "");
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    	return Arrays.asList(SecurityConfiguration.AUTH_WHITELIST)
    			.contains(request.getRequestURI());
    }
}