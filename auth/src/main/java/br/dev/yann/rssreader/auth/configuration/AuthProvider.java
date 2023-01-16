package br.dev.yann.rssreader.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import br.dev.yann.rssreader.auth.user.User;
import br.dev.yann.rssreader.auth.user.UserService;

@Component
public class AuthProvider  implements AuthenticationProvider {

	
	@Autowired
	private UserService userDetailsService; 
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		var user =  (User) userDetailsService.loadUserByUsername(username);
	
		if(user.authenticatePassword(password)) {
			throw new BadCredentialsException("Incorrect credentials");
		}
		return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		 return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
