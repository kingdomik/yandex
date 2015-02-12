package ru.yandex.pogoda.common;

/**
 * Messages interface
 */
public interface IMessage {

	/**
	 * Get formatted message using parameters
	 * 
	 * @param params
	 *            variable array of objects and/or primitives
	 * @return formatted string
	 */
	String getValue(Object... params);

}
