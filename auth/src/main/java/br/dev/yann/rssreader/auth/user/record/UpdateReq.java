package br.dev.yann.rssreader.auth.user.record;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.dev.yann.rssreader.auth.user.UserController;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
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
	@Size(min = 3, max = 255) @NotBlank
	@Schema(example = "name", requiredMode = RequiredMode.NOT_REQUIRED) 
	String name,
	
	@Hidden
	/**Flag which is {@code true} if field {@link #name} has been set and {@code false} if not */
	boolean hasName,
	
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#username username} */
	@Size(min = 3, max = 40) @NotBlank
	@Schema(example = "username", requiredMode = RequiredMode.NOT_REQUIRED) 
	@Pattern(regexp = "^[A-Z0-9_\\.]+$") 
	String username,
	
	@Hidden
	/**Flag which is {@code true} if field {@link #username} has been set and {@code false} if not */
	boolean hasUsername,
	
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#password password} */	
	@Size(min = 3, max = 255) @NotBlank
	@Schema(example = "password", requiredMode = RequiredMode.NOT_REQUIRED)  
	String password,	
	
	@Hidden
	/**Flag which is {@code true} if field {@link #hasPassword} has been set and {@code false} if not */
	boolean hasPassword){		
	
	/**
	 * Constructs a {@code UpdateReq} with name, username and password as parameters 
	 * @param name the name updates {@link #name} if not null 
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
