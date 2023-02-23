package br.dev.yann.rssreader.auth.configuration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import  org.springframework.security.core.Authentication;

import br.dev.yann.rssreader.auth.user.User;
import br.dev.yann.rssreader.auth.user.UserService;

@ExtendWith(MockitoExtension.class)
class AuthProviderTest {
	
	@Mock
	private UserService userServiceMock;

	@Mock
	private Authentication authenticationMock;
		
	@Mock
	private User userMock;
	
	@InjectMocks
	private AuthProvider authProviderMock;
		
	/**
	 * Define default values and returns used in all tests
	 */
	@BeforeEach
	void init() {
		when(authenticationMock.getPrincipal()).thenReturn(StringUtils.EMPTY);
		when(authenticationMock.getCredentials()).thenReturn(StringUtils.EMPTY);
		when(userServiceMock.loadUserByUsername(StringUtils.EMPTY)).thenReturn(userMock);
	}
	
	@Test
	@DisplayName("if user password is valid then not throw BadCredentialsException")
	void testIfUserPasswordIsValidThenNotThrowBadCredentialsException() {

        //Arrange
 		when(userMock.authenticatePassword(anyString())).thenReturn(Boolean.TRUE);
		
		//Assert
		assertDoesNotThrow(() -> authProviderMock.authenticate(authenticationMock));
	}
	
	@Test
	@DisplayName("if user password is not valid then throw BadCredentialsException")
	void testIfUserPasswordIsNotValidThenThrowBadCredentialsException() {
	  
		//Arrange
		when(userMock.authenticatePassword(anyString())).thenReturn(Boolean.FALSE);
		
		//Assert
		assertThrows(BadCredentialsException.class, () -> authProviderMock.authenticate(authenticationMock));
	}


}
