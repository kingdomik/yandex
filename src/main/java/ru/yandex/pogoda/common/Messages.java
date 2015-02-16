package ru.yandex.pogoda.common;

/**
 * Messages collection used for framework 
 */
public enum Messages implements IMessage {

	ERR_ARGUMENT_IS_NULL("Argument %s must not be null"),
	ERR_ARGUMENT_IS_EMPTY("Argument %s must not be empty"),
	ERR_ARGUMENT_NO_SIZE("Argument %s has no size"),
	ERR_ARGUMENT_IS_SMALL("Argument %s must not bigger than %s"),
	ERR_ARGUMENT_IS_BIG("Argument %s must not less than %s"),

	ERR_FAILED_PARSE_XML("Failed parse XML %s"),
	ERR_FAILED_READ_URL("Failed read URL %s"),
	ERR_FAILED_FORMAT_STRING("Failed format string \"%s\"  %s"),
	ERR_FAILED_PARSE_DATE("Failed format date \"%s\" (%s) %s"),
	ERR_INVALID_URL("Invalid URL %s"),
	
	ERR_FAILED_READ_PROPERTIES("Failed read properties %s"),
	ERR_PROPERTY_UNDEFINED("Property not found %s (%s)"),
	ERR_LANGUAGE_NOT_FOUND("Language not found '%s'"),
	ERR_LANGUAGE_UNDEFINED("Language is undefined"),
	
	ERR_FAILED_READ_LOG_CONFIG("Failed read log config %s"),
	
	TEST_VALUE("%s value"),
	TEST_EXPECTED_VISIBLE("Expected visible %s"), 
	TEST_EXPECTED_INVISIBLE("Expected invisible %s"), 
	
	TEST_EXPECTED_VALUE("Expected %s has value \"%s\""),
	TEST_UNEXPECTED_VALUE("Unexpected %s has value \"%s\""),

	TEST_CHECKED("Expected %s checked"),
	TEST_UNCHECKED("Expected %s unchecked"),
	
	LOG_SET_LANGUAGE("Set language %s");
	
	
	private String message;

	Messages(String message) {
		this.message = message;
	}

	@Override
	public String getValue(Object... params) {
		return Utils.format(message, params);
	}

}
