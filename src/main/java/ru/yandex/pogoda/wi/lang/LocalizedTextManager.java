package ru.yandex.pogoda.wi.lang;

import static ru.yandex.pogoda.common.Messages.ERR_FAILED_READ_PROPERTIES;
import static ru.yandex.pogoda.common.Messages.ERR_LANGUAGE_NOT_FOUND;
import static ru.yandex.pogoda.common.Messages.ERR_PROPERTY_UNDEFINED;
import static ru.yandex.pogoda.common.Messages.LOG_SET_LANGUAGE;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import ru.yandex.common.FrameworkException;
import ru.yandex.common.Loggers;
import ru.yandex.common.Validate;

/**
 * language manager class
 * 
 */
public class LocalizedTextManager {

	private static Properties propDefault = getProperties(System.getProperty("language.default", "ru"));
	private static Properties propCurrent;
	
	private static Properties getProperties(String lang) {
		Validate.notNull(lang, "lang");
		Properties properties = new Properties();
		InputStream is = LocalizedTextManager.class.getResourceAsStream(lang.toLowerCase() + ".properties");
		if (null == is) {
			throw new FrameworkException(ERR_LANGUAGE_NOT_FOUND, lang);
		}
		try {
			try {
				properties.load(new InputStreamReader(is, "UTF-8"));
			} finally {
				is.close();
			}
		} catch (IOException e) {
			throw new FrameworkException(ERR_FAILED_READ_PROPERTIES, lang);
		}

		// Check property file defines all enum values
		for (LocalizedText l : LocalizedText.values()) {
			if (null == properties.getProperty(l.name())) {
				throw new FrameworkException(ERR_PROPERTY_UNDEFINED, l.name(), lang);
			}
		}

		return properties;
	}

	/**
	 * Set language
	 * 
	 * @param lang
	 *            name of language
	 */
	public static void setLanguage(String lang) {
		Validate.notNull(lang, "lang");
		propCurrent = getProperties(lang);
		Loggers.wi.debug(LOG_SET_LANGUAGE.getValue(lang));
	}

	/**
	 * Get default language
	 * 
	 * @return properties
	 */
	public static Properties getDefault() {
		return propDefault;
	}

	/**
	 * Get current language
	 * 
	 * @return properties
	 */
	public static Properties getCurrent() {
		return null == propCurrent ? propDefault : propCurrent;
	}

}
