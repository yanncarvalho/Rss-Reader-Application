package br.dev.yann.rssreader.auth.user.exception;

import static br.dev.yann.rssreader.auth.configuration.DefaultValue.EXCEPTION_USER_USERNAME_NOT_UNIQUE;

import java.io.Serial;

/**
 * Throw if the specified username is already used.
 *
 * @author Yann Carvalho
 */
public class UsernameNotUniqueException extends RuntimeException{
	
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code UserUsernameNotUnique} with {@link br.dev.yann.rssreader.auth.configuration.DefaultValue#EXCEPTION_USER_USERNAME_NOT_UNIQUE default message}.
	 */
	public UsernameNotUniqueException() {
		super(EXCEPTION_USER_USERNAME_NOT_UNIQUE);
	}

	/**
	 * Constructs a {@code UserUsernameNotUnique} with the specified message.
	 * @param msg the detail message.
	 */
	public UsernameNotUniqueException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code UserUsernameNotUnique} with the specified message and root
	 * cause.
	 * @param msg the detail message.
	 * @param cause root cause
	 */
	public UsernameNotUniqueException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
