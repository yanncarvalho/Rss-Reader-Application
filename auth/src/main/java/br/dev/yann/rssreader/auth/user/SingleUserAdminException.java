package br.dev.yann.rssreader.auth.user;

import java.io.Serial;

/**
 * Throw if there is only one user with {@link UserRole#ADMIN Admin} role 
 * and it has been tried to change its role or delete it.
 *
 * @author Yann Carvalho
 */
public class SingleUserAdminException  extends RuntimeException{
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a {@code OnlyAdminException} with default message.
	 */
	public SingleUserAdminException() {
		super("Unable to delete or change role of single user with %s role".formatted(UserRole.ADMIN));
	}
	
	/**
	 * Constructs a {@code OnlyAdminException} with the specified message.
	 * @param msg the detail message.
	 */
	public SingleUserAdminException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code OnlyAdminException} with the specified message and root
	 * cause.
	 * @param msg the detail message.
	 * @param cause root cause
	 */
	public SingleUserAdminException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
