package io.github.yanncarvalho.rssreader.auth.user.exception;

import static io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.EXCEPTION_SINGLE_USER_ADMIN;

import java.io.Serial;

import io.github.yanncarvalho.rssreader.auth.user.UserRole;

/**
 * Throw if there is only one user with {@link UserRole#ADMIN Admin} role
 * and it has been tried to change its role or delete it.
 *
 * @author Yann Carvalho
 */
public class UserSingleAdminException  extends RuntimeException{

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code OnlyAdminException} with  
	 * {@link io.github.yanncarvalho.rssreader.auth.configuration.DefaultValue#EXCEPTION_SINGLE_USER_ADMIN default message}.
	 */
	public UserSingleAdminException() {
		super(EXCEPTION_SINGLE_USER_ADMIN);
	}

	/**
	 * Constructs a {@code OnlyAdminException} with the specified message.
	 * @param msg the detail message.
	 */
	public UserSingleAdminException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code OnlyAdminException} with the specified message and root
	 * cause.
	 * @param msg the detail message.
	 * @param cause root cause
	 */
	public UserSingleAdminException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
