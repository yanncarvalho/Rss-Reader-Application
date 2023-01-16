package br.dev.yann.rssreader.auth.role;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, UUID> {
		 
	 Optional<Role> findByNameAndFlagActiveTrue(String name);
}
