package br.dev.yann.rssreader.auth.user.record;

import java.util.UUID;

import br.dev.yann.rssreader.auth.user.UserRole;
import br.dev.yann.rssreader.auth.user.User;

public record FindUsersRes(
		UUID id,
	    String username,
	    String name,
	    UserRole role) {
	
		public FindUsersRes(User user) {
			this(user.getId(), user.getUsername(), user.getName(), user.getRole());
		}
}
