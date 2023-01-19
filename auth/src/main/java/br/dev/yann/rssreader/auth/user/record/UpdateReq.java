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

import com.fasterxml.jackson.annotation.JsonCreator;

import br.dev.yann.rssreader.auth.user.UserController;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *{@link UserController#update update} request record.
 *
 * @param name {@link #name}.
 * @param hasName {@link #hasName}.
 * @param username {@link #username}.
 * @param hasUsername {@link #hasUsername}.
 * @param password {@link #password}.
 * @param hasPassword {@link #hasPassword}.
 * 
 * @author Yann Carvalho
 */
public record UpdateReq(
		
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#name name} */
	@Schema(example = "name", requiredMode = RequiredMode.NOT_REQUIRED, description = SWAGGER_NAME_DESCRIPTION)  
	@Size(min = SIZE_FIELD_MIN, max = SIZE_FIELD_MAX) 
	@Pattern(regexp = BEAN_REGEX_START_WITH_LETTERS, message = BEAN_REGEX_START_WITH_LETTERS_MESSAGE)
	@Pattern(regexp = BEAN_REGEX_NAME, message = BEAN_REGEX_NAME_MESSAGE) 
	String name,
	
	/**Flag which is {@code true} if field {@link #name} has been set and {@code false} if not */
	@Hidden
	boolean hasName,
	
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#username username} */
	@Schema(example = "username", requiredMode = RequiredMode.NOT_REQUIRED, description = SWAGGER_USERNAME_DESCRIPTION) 
	@Size(min = SIZE_FIELD_MIN, max = SIZE_USER_USERNAME_MAX) 
	@Pattern(regexp = BEAN_REGEX_START_WITH_LETTERS, message = BEAN_REGEX_START_WITH_LETTERS_MESSAGE)
	@Pattern(regexp = BEAN_REGEX_USERNAME, message = BEAN_REGEX_USERNAME_MESSAGE) 
	String username,
	
	/**Flag which is {@code true} if field {@link #username} has been set and {@code false} if not */
	@Hidden
	boolean hasUsername,
	
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#password password} */	
	@Size(min = SIZE_FIELD_MIN, max = SIZE_FIELD_MAX) 
	@Schema(example = "password", requiredMode = RequiredMode.NOT_REQUIRED)  
	String password,	
	
	/**Flag which is {@code true} if field {@link #hasPassword} has been set and {@code false} if not */
	@Hidden
	boolean hasPassword){		
	
	/**
	 * Constructs a {@code UpdateReq} with name, username and password as parameters 
	 * @param name the name is cleaned by removing excess whitespace and updates {@link #name} if not null  
	 * @param username the username is converted to uppercase and updates {@link #username} if not null 
	 * @param password the password updates {@link #password} if not password 
	 */
	@JsonCreator
	public UpdateReq( String name, String username, String password) {
		this(name == null ? "STANDARD_VALUE" : name,
			name != null, 
			username == null ? "STANDARD_VALUE" : username.toUpperCase(), 
			username != null, 
			password == null ? "STANDARD_VALUE" : password, 
			password != null);
	}
	
	/** @return {@link #name} if {@link #hasName} is {@code true} if not returns {code null}*/
	public String name() {
		return hasName ? name : null ; 
	}
	
	/** @return {@link #username} if {@link #hasUsername} is {@code true} if not returns {code null}*/
	public String username() {
		return hasUsername ? username : null ; 
	}

	/** @return {@link #password} if {@link #hasPassword} is {@code true} if not returns {code null}*/
	public String password() {
		return hasPassword ? password : null ; 
	}


}
