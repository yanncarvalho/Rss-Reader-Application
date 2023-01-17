package br.dev.yann.rssreader.auth.user.record;

import java.util.Objects;

import br.dev.yann.rssreader.auth.user.UserController;
import jakarta.validation.constraints.NotBlank;

/**
 *{@link UserController#login Login} request record.
 *
 * @param username {@link #username}.
 * @param password {@link #password}.
 * 
 * @author Yann Carvalho
 */
public record LoginReq(
	  /**Representantion of {@link br.dev.yann.rssreader.auth.user.User#username username} */
	  @NotBlank
	  String username,	
	 
	  /**Representantion of {@link br.dev.yann.rssreader.auth.user.User#password password} */
	  @NotBlank
	  String password) {

	/**
	 * Constructs a {@code LoginReq} with username and password as parameters
	 * @param username username is converted to uppercase and updates {@link #username} 
	 * @param password the password updates {@link #password} 
	 */
	  public LoginReq(String username, String password) {
	     this.username = Objects.toString(username, "").toUpperCase();
	     this.password = password;
	  }
}
