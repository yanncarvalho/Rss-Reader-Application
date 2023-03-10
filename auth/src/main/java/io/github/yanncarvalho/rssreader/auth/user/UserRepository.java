package io.github.yanncarvalho.rssreader.auth.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {

	 Optional<User> findByUsername(String username);

	 boolean existsByUsername(String username);

	/**
	 * Check if user with a specific id is the only user with role {@link UserRole#ADMIN Admin}
	 * @param id the {@link User#id id} to search for the user.
	 * @return {@code true} if the user is the only with role {@link UserRole#ADMIN Admin},
	 * 		   {@code false} if not
	 */
	 @Query("SELECT COUNT(user) = 1 FROM Users user WHERE user.role = ADMIN AND user.id = :id  "
	 	  + "AND (SELECT COUNT(userAdmin) = 1 FROM Users userAdmin WHERE userAdmin.role = ADMIN)")
	 boolean existsSoleUserAdminById(UUID id);

}
