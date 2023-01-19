package br.dev.yann.rssreader.auth.user.record;

import static br.dev.yann.rssreader.auth.configuration.DefaultValue.BEAN_REGEX_NAME;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.BEAN_REGEX_NAME_MESSAGE;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.BEAN_REGEX_START_WITH_LETTERS;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.BEAN_REGEX_START_WITH_LETTERS_MESSAGE;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.BEAN_REGEX_USERNAME;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.BEAN_REGEX_USERNAME_MESSAGE;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SIZE_FIELD_MAX;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SIZE_FIELD_MIN;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SIZE_USER_USERNAME_MAX;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SWAGGER_NAME_DESCRIPTION;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SWAGGER_USERNAME_DESCRIPTION;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.dev.yann.rssreader.auth.user.UserController;
import br.dev.yann.rssreader.auth.user.UserRole;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
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
	@Schema(example = "name", requiredMode = RequiredMode.NOT_REQUIRED, description = SWAGGER_NAME_DESCRIPTION)  
	@Size(min = SIZE_FIELD_MIN, max = SIZE_FIELD_MAX) 
	@Pattern(regexp = BEAN_REGEX_START_WITH_LETTERS, message = BEAN_REGEX_START_WITH_LETTERS_MESSAGE)
	@Pattern(regexp = BEAN_REGEX_NAME, message = BEAN_REGEX_NAME_MESSAGE) 
	String name,
	
	@Hidden
	/**Flag which is {@code true} if field {@link #name} has been set and {@code false} if not */
	boolean hasName,
	
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#username username} */
	@Schema(example = "username", requiredMode = RequiredMode.NOT_REQUIRED, description = SWAGGER_USERNAME_DESCRIPTION) 
	@Size(min = SIZE_FIELD_MIN, max = SIZE_USER_USERNAME_MAX) 
	@Pattern(regexp = BEAN_REGEX_START_WITH_LETTERS, message = BEAN_REGEX_START_WITH_LETTERS_MESSAGE)
	@Pattern(regexp = BEAN_REGEX_USERNAME, message = BEAN_REGEX_USERNAME_MESSAGE) 
	String username,
	
	@Hidden
	/**Flag which is {@code true} if field {@link #username} has been set and {@code false} if not */
	boolean hasUsername,
		
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#role role} */
	@NotNull
	@Schema(example = "USER", requiredMode = RequiredMode.NOT_REQUIRED) 
	UserRole role,
	
	/**flag which is {@code true} if field {@link #role} has been set and {@code false} if not */
	@Hidden
	boolean hasRole) {

	/**
	 * Constructs a {@code UpdateAsAdminReq} with id, name, username and role as parameters
	 * @param id the id updates {@link #id} 
	 * @param name the name is cleaned by removing excess whitespace and updates {@link #name} if not null 
	 * @param username the username is converted to uppercase and updates {@link #username} if not null 
	 * @param role the role updates {@link #role} if not null 
	 */
	@JsonCreator
	public UpdateAsAdminReq(UUID id, String name, String username, UserRole role) {
		this(id,
			name == null ? "STANDARD_VALUE" : name.replaceAll("\\s+", " ").trim(), 
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
