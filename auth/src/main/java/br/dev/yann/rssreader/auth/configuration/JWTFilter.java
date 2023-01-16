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

@Component
public class JWTFilter extends OncePerRequestFilter {
	
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
	
    @Autowired
    private UserService service;
    
    @Autowired
    private JWTToken tokenService;
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
       
        try {
               var tokenJWT = retrieveToken(request);
               var subject = tokenService.getClaims(tokenJWT).getSubject();
               var id = UUID.fromString(subject);
               var user = service.findById(id);
               request.setAttribute("userUUID", id);
               var authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
               SecurityContextHolder.getContext().setAuthentication(authentication);    
        	   filterChain.doFilter(request, response);
         } catch (Exception err) {
        	 resolver.resolveException(request, response, null, err);
        }
    }

    private String retrieveToken(HttpServletRequest request) {
      var authorizationHeader = request.getHeader("Authorization");
      if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) 
        	throw new NullPointerException("Bearer token authentication was not found");
       
       return authorizationHeader.replace("Bearer ", "");
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    	return Arrays.asList(SecurityConfigurations.permitAllPaths)
    			.contains(request.getRequestURI());
    }
}