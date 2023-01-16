package br.dev.yann.rssreader.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
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
		
		String providedUsername = authentication.getPrincipal().toString();
		var user =  (User) userDetailsService.loadUserByUsername(providedUsername);
		
		String providedPassword = authentication.getCredentials().toString();
		
		

		if(user.authenticationPassword(providedPassword)) {
		
			throw new RuntimeException("Incorrect Credentials");
		}


		Authentication authenticationResult = 
				new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		 return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
