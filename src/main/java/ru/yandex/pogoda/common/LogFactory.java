package ru.yandex.pogoda.common;

import static ru.yandex.pogoda.common.Messages.ERR_FAILED_READ_LOG_CONFIG;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 * LogFactory loads log4j settings and provide helpful functions to create
 * loggers
 * 
 */
public class LogFactory {

	static {
		loadConfig();
	}

	private static void loadConfig() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(LogFactory.class.getResourceAsStream("log4j.properties.xml"));
			DOMConfigurator.configure(doc.getDocumentElement());
		} catch (Exception e) {
			throw new FrameworkException(ERR_FAILED_READ_LOG_CONFIG, e);
		}
	}

	/**
	 * Create logger by class name
	 * 
	 * @param clazz
	 *            class of object
	 * @return logger object
	 */
	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	/**
	 * Create logger with specified name
	 * 
	 * @param name
	 *            string logger name
	 * @return logger object
	 */
	public static Logger getLogger(String name) {
		return LoggerFactory.getLogger(name);
	}
}
