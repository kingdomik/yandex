package ru.yandex.common;

import static ru.yandex.pogoda.Messages.ERR_ARGUMENT_BIGGER;
import static ru.yandex.pogoda.Messages.ERR_ARGUMENT_BIGGER_OR_EQUAL;
import static ru.yandex.pogoda.Messages.ERR_ARGUMENT_IS_EMPTY;
import static ru.yandex.pogoda.Messages.ERR_ARGUMENT_IS_NULL;
import static ru.yandex.pogoda.Messages.ERR_ARGUMENT_SMALLER;
import static ru.yandex.pogoda.Messages.ERR_ARGUMENT_SMALLER_OR_EQUAL;

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
	
	public static <T extends Comparable<T>> T biggerOrEqual(T argValue, T value, String argName) {
		if (argValue.compareTo(value) == -1) {
			throw new IllegalArgumentException(
			        ERR_ARGUMENT_BIGGER_OR_EQUAL.getValue(argName, value, argValue));
		}
		return argValue;
	}

	public static <T extends Comparable<T>> T smallerOrEqual(T argValue, T value, String argName) {
		if (argValue.compareTo(value) == 1) {
			throw new IllegalArgumentException(
			        ERR_ARGUMENT_SMALLER_OR_EQUAL.getValue(argName, value, argValue));
		}
		return argValue;
	}

	public static <T extends Comparable<T>> T bigger(T argValue, T value, String argName) {
		if (argValue.compareTo(value) != 1) {
			throw new IllegalArgumentException(
			        ERR_ARGUMENT_BIGGER.getValue(argName, value, argValue));
		}
		return argValue;
	}

	public static <T extends Comparable<T>> T smaller(T argValue, T value, String argName) {
		if (argValue.compareTo(value) != -1) {
			throw new IllegalArgumentException(
			        ERR_ARGUMENT_SMALLER.getValue(argName, value, argValue));
		}
		return argValue;
	}

}
