package br.dev.yann.rssreader.auth.user;

import static com.github.yanncarvalho.rssreader.auth.user.UserRole.ADMIN;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.github.yanncarvalho.rssreader.auth.AuthApplication;
import com.github.yanncarvalho.rssreader.auth.user.User;
import com.github.yanncarvalho.rssreader.auth.user.UserRepository;
import com.github.yanncarvalho.rssreader.auth.user.UserRole;

@SpringBootTest
@ContextConfiguration(classes = AuthApplication.class)
@ActiveProfiles("test")	
class UserRepositoryTest {

	@Autowired
	private UserRepository repository;
	
	@BeforeEach
	void init() {
	   repository.deleteAll();
	}
	
	/**
	 * Save an user in database
	 * @param name user name
	 * @param username user username
	 * @param password user password
	 * @param role user role
	 * 
	 * @see UserRole
	 * 
	 * @return the user save in the database
	 */
	private User saveUserDatabase(String name, String username, String password, UserRole role) {
		var user = new User(name, username, password);
		user.setRole(role);
		return repository.saveAndFlush(user);
	}
	
	@Test
	@DisplayName("if user is the single admin then return true")
	void ifUserTheSingleAdminThenReturnTrue() {
		
		//Arrange
		var singleAdmin = saveUserDatabase("singleAdmin", "singleAdmin", "singleAdmin", ADMIN);
		
		//Act
		var isSingleAdmin = repository.existsSoleUserAdminById(singleAdmin.getId());
		
		//Assert
		assertTrue(isSingleAdmin);	
	}
	
	@Test
	@DisplayName("if not user is the single admin then return false")
	void ifNotUserTheSingleAdminThenReturnFalse(){
		
		//Arrange
		saveUserDatabase("firstAdmin", "firstAdmin", "firstAdmin", ADMIN);
		var secondAdmin = saveUserDatabase("secondAdmin", "secondAdmin", "secondAdmin", ADMIN);
		
		//Act
		var isSingleAdmin = repository.existsSoleUserAdminById(secondAdmin.getId());
		
		//Assert
		assertFalse(isSingleAdmin);	
	}
	
}
