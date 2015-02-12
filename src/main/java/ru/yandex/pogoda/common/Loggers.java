package ru.yandex.pogoda.common;

import org.slf4j.Logger;

/**
 * Collection of loggers
 * 
 */
public class Loggers {

	/**
	 * Framework logger. Used for any framework activities
	 */
	public final static Logger framework = LogFactory.getLogger("framework");

	/**
	 * Web interface logger. Used for human readable actions
	 */
	public final static Logger wi = LogFactory.getLogger("wi");


}
