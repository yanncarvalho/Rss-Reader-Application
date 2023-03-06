package com.github.yanncarvalho.rssreader.auth.user.record;

import com.github.yanncarvalho.rssreader.auth.user.UserController;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * {@link UserController#findById Find by id} request record.
 *
 * @param username {@link #username}.
 * @param name {@link #name}.
 * 
 * @author Yann Carvalho
 */
public record FindByIdReq(
		 @Schema(example = "username") 
		/**Representantion of {@link com.github.yanncarvalho.rssreader.auth.user.User#username username}. */
		String username,
		
		 @Schema(example = "name") 
		/**Representantion of {@link com.github.yanncarvalho.rssreader.auth.user.User#name name}. */
	    String name) {}