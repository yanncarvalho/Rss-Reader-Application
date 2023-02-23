package br.dev.yann.rssreader.auth.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")	
class UserRepositoryTest {

	@Autowired
	private UserRepository repository;
	
	@BeforeEach
	void init() {
	   repository.deleteAll();
	}
	
	@Test
	void test(){
		var userName = "user";
		var userUsername = "user";
		var userPassword = "user";
		var userRoleUser = new User(userName, userUsername, userPassword);
		repository.save(userRoleUser);
		
		var adminName = "admin";
		var adminUsername = "admin";
		var adminPassword = "admin";
		var userRoleAdmin = new User(adminName, adminUsername, adminPassword);
		userRoleAdmin.setRole(UserRole.ADMIN);
		repository.save(userRoleAdmin);
		

	}
}
