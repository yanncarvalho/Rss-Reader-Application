package com.github.yanncarvalho.rssreader.auth.configuration;

import com.github.yanncarvalho.rssreader.auth.user.UserRole;

/**
 * Centralization of the values used in the entire application
 * 
 * @author Yann Carvalho 
 */
public class DefaultValue {

	/**
	 * Empty Constructor 
	 */
	private DefaultValue (){}
	
	// APPLICATION VALUES
	public static final String ATTRIBUTE_UUID = "userUUID";
	public static final int SIZE_FIELD_MIN = 3;
	public static final int SIZE_FIELD_MAX = 255;
	public static final int SIZE_USER_USERNAME_MAX = 40;
	public static final String APP_LICENSE =
		"https://github.com/yanncarvalho/rss-reader-application/blob/main/LICENSE";
	
	// BEAN VALIDATION VALUES
	public static final String START_WITH_LETTERS_PATTERN  = "([A-Za-z]{3}).*";
	public static final String START_WITH_LETTERS_PATTERN_ERROR_MSG = "must start with 3 letters.";
	public static final String USERNAME_PATTERN = "^(\\w|\\-|\\.)*$";
	public static final String USERNAME_PATTERN_ERROR_MSG = 
			"only allows underscore, hyphen, point, and alphanumeric characters except non-roman and no accented.";
	public static final String NAME_PATTERN = "^(\\w|\\-|\\*|\\s)*$";
	public static final String NAME_PATTERN_ERROR_MSG = 
			"only allows underscore, hyphen, point, whitespace, and alphanumeric characters except non-roman and no accented.";
	public static final String UUID_PATTERN = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";
	public static final String UUID_PATTERN_ERROR_MSG = "UUID informed is not valid.";

	// EXCEPTION FILTERS VALUES
	public static final String INCORRET_CREDENTIALS = "Incorrect credentials.";
	public static final String GENERIC_ERROR = "Cannot proceed.";
	public static final String USER_NOT_FOUND = "User not found.";
	public static final String INVALID_REQUEST = "Request is not valid.";
	public static final String USER_SINGLE_ADMIN = "User is the only admin of the system.";
	public static final String USERNAME_NOT_UNIQUE = "Username already exists.";
	public static final String DATA_NULL = "Data are null.";
	public static final String NOT_FOUND = "Not found.";
	public static final String NOT_FOUND_URL = "URL not found.";
	public static final String ACESS_FORBIDDEN = "Access forbidden.";
	public static final String INTERNAL_SERVER_ERROR =
			"An internal server error occurred, contact system administrator.";
	
	// EXCEPTION VALUES
	public static final String EXCEPTION_SINGLE_USER_ADMIN = 
			"Unable to delete or change role of single user with %s role".formatted(UserRole.ADMIN);
	public static final String EXCEPTION_USER_USERNAME_NOT_UNIQUE = 
			"The username is already used by another user.";

	// JWT VALUES
	public static final String JWT_BAD = "Bad JWT.";
	public static final String JWT_ENCODE_PROBLEM = "Not possible to encode JWT.";
	public static final String JWT_ENCODE_EXCEPTION = "Error generating JWT.";
	public static final String JWT_BAD_EXCEPTION = "Error validating JWT.";
	public static final String JWT_BEARER = "Bearer";
	public static final String JWT_BEARER_NOT_FOUND = "Bearer token authentication was not found.";

	// USER VALUES
	public static final String USER_ID_NOT_FOUND = "The id informed was not found.";
	public static final String USER_UPDATED = "User updated successfully!";
	public static final String USER_DELETED = "User deleted successfully!";
	public static final String USER_CREATED = "User created successfully!";
	
	// SWAGGER VALUES
	public static final String SWAGGER_FIND_USERS = "Get all Users as Admin";
    public static final String SWAGGER_FIND_USER_AS_ADMIN = "Get a user by id as Admin.";
	public static final String SWAGGER_UPDATE_USER_AS_ADMIN = "Update user as Admin.";
	public static final String SWAGGER_DELETE_USER_AS_ADMIN ="Delete user by id as Admin.";
	public static final String SWAGGER_UPDATE_USER = "Update a user by id.";
	public static final String SWAGGER_DELETE_USER = "Delete a user by id.";
	public static final String SWAGGER_FIND_USER = "Get a user by id.";
	public static final String SWAGGER_LOGIN = "Login.";
	public static final String SWAGGER_SAVE = "Save new user.";
	public static final String SWAGGER_USERNAME_DESCRIPTION = 
			"Before save the username is converted to uppercase.";
	public static final String SWAGGER_NAME_DESCRIPTION = 
			"Before save the name is cleaned by removing excess whitespace.";
	public static final String SWAGGER_SECURITY_DESCRIPTION = 
			"Provide the JWT token. JWT token can be obtained from the Login API.";
	
	//LOGGER
	public static final String LOGGER_ADMIN_FIND_USER(Object id) { return String.format("Admin required information about user %s.", id); }
	public static final String LOGGER_ADMIN_FIND_ALL_USERS = "Admin required information about all users.";
	public static final String LOGGER_ADMIN_UPDATE(Object id) { return String.format("User %s updated by admin.", id); }
	public static final String LOGGER_ADMIN_DELETE(Object id) { return String.format("User %s deleted by admin.", id); }
	public static final String LOGGER_USER_UPDATE(Object id) { return String.format("User %s updated by user.", id); }
	public static final String LOGGER_USER_DELETE(Object id) { return String.format("User %s deleted by user.", id); }
	public static final String LOGGER_USER_FIND(Object id) { return String.format("Returned info about user %s.", id); }
	public static final String LOGGER_LOGIN(Object id) { return String.format("User %s login.", id); }
	public static final String LOGGER_CREATE_USER(Object id) { return String.format("User %s created.", id); }
	public static final String LOGGER_EXCEPTION_DEFAULT(Object userAddress, Object exceptionMsg) { return String.format("User with IP %s gets an exception: %s.", userAddress, exceptionMsg); }	

}

