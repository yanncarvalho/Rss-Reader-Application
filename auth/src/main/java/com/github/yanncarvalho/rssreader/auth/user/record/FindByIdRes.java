package com.github.yanncarvalho.rssreader.auth.user.record;

import java.util.UUID;

import com.github.yanncarvalho.rssreader.auth.user.UserController;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * {@link UserController#findById Find by id} request record.
 * 
 * @param id {@link #id}.
 * @param username {@link #username}.
 * @param name {@link #name}.
 * 
 * @author Yann Carvalho
 */
public record FindByIdRes(
		
		 @Schema(example = "id") 
		/**Representantion of {@link com.github.yanncarvalho.rssreader.auth.user.User#id id}. */
		UUID id,
		
		 @Schema(example = "username") 
		/**
		 * Representantion of {@link com.github.yanncarvalho.rssreader.auth.user.User#username username}. 
		 */
		String username,
		
		 @Schema(example = "name") 
		/**Representantion of {@link com.github.yanncarvalho.rssreader.auth.user.User#name name}. */
	    String name) {}