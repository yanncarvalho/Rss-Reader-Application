package br.dev.yann.rssreader.auth.user.record;

import br.dev.yann.rssreader.auth.user.UserController;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *{@link UserController#update update} response record.
 *
 * @param name {@link #name}.
 * @param username {@link #username}.
 * @param password {@link #password}.
 * 
 * @author Yann Carvalho
 */
public record UpdateRes(
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#name name} */
	@Schema(example = "name") 
	String name,
	
	@Schema(example = "username") 
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#username username} */
	String username,
	
	@Schema(example = "password") 
	/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#password password} */
	String password) {}