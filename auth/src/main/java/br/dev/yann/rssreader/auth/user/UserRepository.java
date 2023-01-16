package br.dev.yann.rssreader.auth.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {
	
	 
	 Optional<User> findByUsername(String username);
	 
	 boolean existsByUsername(String username); 
	 
	 @Query("SELECT COUNT(user) = 1 FROM Users user WHERE user.role = ADMIN AND user.id = :id  "
	 	  + "AND (SELECT COUNT(userAdmin) = 1 FROM Users userAdmin WHERE userAdmin.role = ADMIN)")
	 boolean existsOneUserAdminById(UUID id);

}