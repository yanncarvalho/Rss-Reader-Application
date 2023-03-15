package io.github.yanncarvalho.rssreader.rss.configuration;

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
	public static final String APP_LICENSE =
		"https://github.com/yanncarvalho/rss-reader-application/blob/main/LICENSE";
	public static final String JWT_BEARER = "Bearer";
	public static final String REGEX_HTTP_HTTPS = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
	public static final String REGEX_HTTP_HTTPS_MESSAGE = "- '${validatedValue}' is not a valid URL.";

	// EXCEPTION FILTERS VALUES
	public static final String GENERIC_ERROR = "Cannot proceed.";
	public static final String INVALID_REQUEST = "Request is not valid.";
	public static final String DATA_NULL = "Data are null.";
	public static final String NOT_FOUND = "Not found.";
	public static final String NOT_FOUND_URL = "URL not found.";
	public static final String ACESS_FORBIDDEN = "Access forbidden.";
	public static final String INTERNAL_SERVER_ERROR =
			"An internal server error occurred, contact system administrator.";
	public static final String NO_MESSAGE_AVAILABLE = "No message available";

	// USER VALUES
	public static final String RSS_DELETED_ALL = "All Rss deleted successfully!";
	public static final String RSS_DELETED = "Rss deleted successfully!";

	// SWAGGER VALUES
	public static final String SWAGGER_GET_CONTENT = "Get all user Rss";
	public static final String SWAGGER_FIND_URL = "Find all URLs by user";
	public static final String SWAGGER_HAS_URL = "Checks if a list of URLs has on a user";
	public static final String SWAGGER_INSERT_RSS = "Insert a list of URLs into a user";
	public static final String SWAGGER_DELETE_RSS = "Delete a list of URLs on a user";
	public static final String SWAGGER_DELETE_ALL_RSS = "Delete all URLs for a user";
	public static final String SWAGGER_CONVERT_TO_RSS = "Convert to a list of URLs to Rss";
	public static final String SWAGGER_USERNAME_DESCRIPTION =
			"Before save the username is converted to uppercase.";
	public static final String SWAGGER_NAME_DESCRIPTION =
			"Before save the name is cleaned by removing excess whitespace.";
	public static final String SWAGGER_SECURITY_DESCRIPTION =
			"Provide the JWT token. JWT token can be obtained from the Login API.";

	//LOGGER
	public static final String LOGGER_EXCEPTION_DEFAULT(Object userAddress, Object exceptionMsg) { return String.format("User with IP %s gets an exception: %s.", userAddress, exceptionMsg); }
}

