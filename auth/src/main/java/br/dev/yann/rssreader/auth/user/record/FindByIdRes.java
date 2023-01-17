package br.dev.yann.rssreader.auth.user.record;

import java.util.UUID;

import br.dev.yann.rssreader.auth.user.UserRole;
import br.dev.yann.rssreader.auth.user.User;
import br.dev.yann.rssreader.auth.user.UserController;

/**
 * {@link UserController#findById Find by id} response record.
 *
 * @param id {@link #id}.
 * @param username {@link #username}.
 * @param name {@link #name}.
 * @param role {@link #role}.
 * 
 * @author Yann Carvalho
 */
public record FindByIdRes(
		/**Representantion of {@link User#id id} */
		UUID id,
		
		/**Representantion of {@link User#username username} */
	    String username,
	    
		/**Representantion of {@link User#name name} */
	    String name,
	    
		/**Representantion of {@link User#role role} */
	    UserRole role) {
	
		/**
		 * Constructs a {@code LoginReq} with {@link User user} as parameter
		 * @param user the {@link User user} 
		 */
		public FindByIdRes(User user) {
			this(user.getId(), user.getUsername(), user.getName(), user.getRole());
		}
}
