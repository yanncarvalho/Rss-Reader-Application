package br.dev.yann.rssreader.auth.user.record;

import br.dev.yann.rssreader.auth.user.UserController;

/**
 * {@link UserController#findById Find by id} request record.
 *
 * @param username {@link #username}.
 * @param name {@link #name}.
 * 
 * @author Yann Carvalho
 */
public record FindByIdReq(
		/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#username username}. */
		String username,
		
		/**Representantion of {@link br.dev.yann.rssreader.auth.user.User#name name}. */
	    String name) {}