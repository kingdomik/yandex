package ru.yandex.pogoda;

import ru.yandex.common.IMessage;
import ru.yandex.common.Utils;

/**
 * Message templates
 *  
 */
public enum Messages implements IMessage {

	ERR_ARGUMENT_IS_NULL("Argument %s must not be null"),
	ERR_ARGUMENT_IS_EMPTY("Argument %s must not be empty"),
	ERR_ARGUMENT_NO_SIZE("Argument %s has no size"),
	ERR_ARGUMENT_SMALLER_OR_EQUAL("Argument %s must be cmaller or equal to %d but was %d"),
	ERR_ARGUMENT_BIGGER_OR_EQUAL("Argument %s must be bigger or equal to %d but was %d"),
	ERR_ARGUMENT_SMALLER("Argument %s must be smaller than %d but was %d"),
	ERR_ARGUMENT_BIGGER("Argument %s must be bigger than %d but was %d"),

	ERR_FAILED_PARSE_XML("Failed parse XML %s"),
	ERR_FAILED_READ_URL("Failed read URL %s"),
	ERR_FAILED_FORMAT_STRING("Failed format string \"%s\"  %s"),
	ERR_FAILED_PARSE_DATE("Failed format date \"%s\" (%s) %s"),
	ERR_INVALID_URL("Invalid URL %s"),
	
	ERR_FAILED_READ_PROPERTIES("Failed read properties %s"),
	ERR_PROPERTY_UNDEFINED("Property not found %s (%s)"),
	ERR_LANGUAGE_NOT_FOUND("Language not found '%s'"),
	ERR_LANGUAGE_UNDEFINED("Language is undefined"),
	
	ERR_NO_LANG_WEARTHER("Failed get weather for language %s");
	
	private String message;

	Messages(String message) {
		this.message = message;
	}

	@Override
	public String getValue(Object... params) {
		return Utils.format(message, params);
	}

}
