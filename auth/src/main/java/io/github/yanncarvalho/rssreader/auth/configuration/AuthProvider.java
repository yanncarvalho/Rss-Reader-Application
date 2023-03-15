package io.github.yanncarvalho.rssreader.auth.configuration;

import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.INCORRET_CREDENTIALS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import io.github.yanncarvalho.rssreader.auth.user.User;
import io.github.yanncarvalho.rssreader.auth.user.UserService;

/**
 * Authentication provider, this class implements
 * {@link AuthenticationProvider}.
 * 
 * @author Yann Carvalho
 */
@Component
public class AuthProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	/**
	 * @throws BadCredentialsException if password is not valid.
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		try {
			var user = (User) userService.loadUserByUsername(username);
			
			if (!user.authenticatePassword(password)) {
				throw new Exception();
			}
			return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
		} catch(Exception e) {
			throw new BadCredentialsException(INCORRET_CREDENTIALS);
		}		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
