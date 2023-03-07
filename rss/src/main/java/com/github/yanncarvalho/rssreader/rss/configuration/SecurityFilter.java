package com.github.yanncarvalho.rssreader.rss.configuration;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private AuthClient client;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			var authorizationHeader = request.getHeader("Authorization");
			var authUser =  client.getAuthUser(authorizationHeader);
			UUID userUUID = UUID.fromString(authUser.get("id"));
			request.setAttribute("userUUID", userUUID);
			filterChain.doFilter(request, response);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}


}
