package br.dev.yann.rssreader.auth.user.record;

import java.util.Objects;

import br.dev.yann.rssreader.auth.user.UserController;
import jakarta.validation.constraints.NotBlank;
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
	 @Size(min = 3, max = 255) @NotBlank
	 @Pattern(regexp = "^[A-Z0-9_\\.]+$") 
	 String username,
	 
	 /**Representantion of {@link br.dev.yann.rssreader.auth.user.User#password password} */
	 @Size(min = 3, max = 255) @NotBlank
	 String password,
	 
	 /**Representantion of {@link br.dev.yann.rssreader.auth.user.User#name name} */
	 @Size(min = 3, max = 255) @NotBlank
	 String name) {
	
	/**
	 * Constructs a {@code SaveReq} with a username, password and name as parameters
	 * @param username username username is converted to uppercase and updates {@link #username} 
	 * @param password the password updates {@link #password} 
	 * @param name the name updates {@link #name} 
	 */
	 public SaveReq(String username, String password, String name) {
	     this.username =  Objects.toString(username, "").toUpperCase();
	     this.password = password;
	     this.name = name;
	  }

}

