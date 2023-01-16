package br.dev.yann.rssreader.auth.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
	
	 
	 Optional<User> findByUsername(String username);
	 
	 boolean existsUserByUsername(String username); 


}