package ru.yandex.pogoda.common;

import static ru.yandex.pogoda.common.Messages.ERR_ARGUMENT_IS_EMPTY;
import static ru.yandex.pogoda.common.Messages.ERR_ARGUMENT_IS_NULL;

import java.util.Collection;

/**
 * This is analog of org.apache.commons.lang3.Validate class 
 * but with embedded error messages so argument name just required
 *
 */
public class Validate {

	public static <T> T notNull(T argValue, String argName) {
		if (null == argValue) {
			throw new IllegalArgumentException(
			        ERR_ARGUMENT_IS_NULL.getValue(argName));
		}
		return argValue;
	}

	public static <T> Collection<T> notEmpty(Collection<T> argValue,
	        String argName) {
		if (0 == argValue.size()) {
			throw new IllegalArgumentException(
			        ERR_ARGUMENT_IS_EMPTY.getValue(argName));
		}
		return argValue;
	}

	public static <T> T[] notEmpty(T[] argValue, String argName) {
		if (0 == argValue.length) {
			throw new IllegalArgumentException(
			        ERR_ARGUMENT_IS_EMPTY.getValue(argName));
		}
		return argValue;
	}

}
