package io.github.yanncarvalho.rssreader.rss.configuration;


import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.ATTRIBUTE_UUID;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Security validation filter, this class extends {@link OncePerRequestFilter}.
 *
 * @author Yann Carvalho
 */
@Component
public class SecurityFilter extends OncePerRequestFilter{
		 
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
	
	@Autowired
	private AuthClient client;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			var authorizationHeader = request.getHeader("Authorization");
			var authUser =  client.getAuthUser(authorizationHeader);
			UUID userUUID = UUID.fromString(authUser.get("id"));
			request.setAttribute(ATTRIBUTE_UUID, userUUID);
			filterChain.doFilter(request, response);
	
		} catch(Exception ex) {
		 	 resolver.resolveException(request, response, null, ex);
		}

	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

		return Arrays.stream(SecurityConfig.WHITELIST).anyMatch(
	   		     element -> new AntPathMatcher().match(element, request.getServletPath()));
	}


}
