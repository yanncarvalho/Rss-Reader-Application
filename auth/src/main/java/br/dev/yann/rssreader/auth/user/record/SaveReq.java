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

import java.util.Objects;

import br.dev.yann.rssreader.auth.user.UserController;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *{@link UserController#save Save} request record.
 *
 * @param username {@link #username}.
 * @param password {@link #password}.
 * @param name {@link #name}.
 * 
 * @author Yann Carvalho
 */
public record SaveReq(
	 /**Representantion of {@link br.dev.yann.rssreader.auth.user.User#username username} */
	 @Schema(example = "username", description = SWAGGER_USERNAME_DESCRIPTION) 
	 @Size(min = SIZE_FIELD_MIN, max = SIZE_USER_USERNAME_MAX)
	 @Pattern(regexp = BEAN_REGEX_START_WITH_LETTERS, message = BEAN_REGEX_START_WITH_LETTERS_MESSAGE)
	 @Pattern(regexp = BEAN_REGEX_USERNAME, message = BEAN_REGEX_USERNAME_MESSAGE) 
	 String username,
	 
	 /**Representantion of {@link br.dev.yann.rssreader.auth.user.User#password password} */
	 @Schema(example = "password") 
	 @Size(min = SIZE_FIELD_MIN, max = SIZE_FIELD_MAX) 
	 String password,
	 
	 /**Representantion of {@link br.dev.yann.rssreader.auth.user.User#name name} */
	 @Schema(example = "name", description =  SWAGGER_NAME_DESCRIPTION) 
	 @Size(min = SIZE_FIELD_MIN, max = SIZE_FIELD_MAX) 
	 @Pattern(regexp = BEAN_REGEX_START_WITH_LETTERS, message = BEAN_REGEX_START_WITH_LETTERS_MESSAGE)
	 @Pattern(regexp = BEAN_REGEX_NAME, message = BEAN_REGEX_NAME_MESSAGE) 
	 String name) {
	
	/**
	 * Constructs a {@code SaveReq} with username, password and name as parameters
	 * @param username the username is converted to uppercase and updates {@link #username} 
	 * @param password the password updates {@link #password} 
	 * @param name the name is cleaned by removing excess whitespace and updates {@link #name}
	 */
	 public SaveReq(String username, String password, String name) {
	     this.username =  Objects.toString(username, "").toUpperCase();
	     this.password = password;
	     this.name = Objects.toString(name, "").replaceAll("\\s+", " ").trim();
	  }

}

