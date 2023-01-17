package br.dev.yann.rssreader.auth.user.record;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.dev.yann.rssreader.auth.user.UserController;
import br.dev.yann.rssreader.auth.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *{@link UserController#updateUserAsAdmin UpdateAsAdmin} request record.
 *
 * @param id {@link #id}.
 * @param name {@link #name}.
 * @param hasName {@link #hasName}.
 * @param username {@link #username}.
 * @param hasUsername {@link #hasUsername}.
 * @param role {@link #role}.
 * @param hasRole {@link #hasRole}.
 * 
 * @author Yann Carvalho
 */
public record UpdateAsAdminReq(
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#id id} */
	@NotNull
	UUID id,
	
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#name name} */
	@Size(min = 3, max = 255) @NotBlank
	String name,
	
	/**Flag which is {@code true} if field {@link #name} has been set and {@code false} if not */
	boolean hasName,
	
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#username username} */
	@Size(min = 3, max = 255) @NotBlank
	@Pattern(regexp = "^[A-Z0-9_\\.]+$") 
	String username,
	
	/**Flag which is {@code true} if field {@link #username} has been set and {@code false} if not */
	boolean hasUsername,
		
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#role role} */
	@NotNull
	UserRole role,
	
	/**flag which is {@code true} if field {@link #role} has been set and {@code false} if not */
	boolean hasRole) {

	/**
	 * Constructs a {@code UpdateAsAdminReq} with id, name, username and role as parameters
	 * @param id the id updates {@link #id} 
	 * @param name the name updates {@link #name} if not null 
	 * @param username the username is converted to uppercase and updates {@link #username} if not null 
	 * @param role the role updates {@link #role} if not null 
	 */
	@JsonCreator
	public UpdateAsAdminReq(UUID id, String name, String username, UserRole role) {
		this(id,
			name == null ? "STANDARD_VALUE" : name, 
			name != null, 
			username == null ? "STANDARD_VALUE" : username.toUpperCase(), 
			username != null, 
			role == null ? UserRole.USER : role, 
			role != null);
	}
	
	/** @return {@link #name} if {@link #hasName} is {@code true} if not returns {code null}*/
	public String name() {
		return hasName ? name : null ; 
	}
	
	/** @return {@link #username} if {@link #hasUsername} is {@code true} if not returns {code null}*/
	public String username() {
		return hasUsername ? username : null ; 
	}

	/** @return {@link #role} if {@link #hasRole} is {@code true} if not returns {code null}*/
	public UserRole role() {
		return hasRole ? role : null ; 
	}
}
