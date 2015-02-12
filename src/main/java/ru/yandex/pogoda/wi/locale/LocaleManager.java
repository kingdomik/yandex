package ru.yandex.pogoda.wi.locale;

import static ru.yandex.pogoda.common.Messages.ERR_FAILED_READ_PROPERTIES;
import static ru.yandex.pogoda.common.Messages.ERR_LOCALE_NOT_FOUND;
import static ru.yandex.pogoda.common.Messages.ERR_PROPERTY_UNDEFINED;
import static ru.yandex.pogoda.common.Messages.LOG_SET_LOCALE;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import ru.yandex.pogoda.common.FrameworkException;
import ru.yandex.pogoda.common.Loggers;
import ru.yandex.pogoda.common.Validate;

/**
 * Locale manager class
 * 
 */
public class LocaleManager {

	private static Properties propDefault = getProperties(System.getProperty("locale.default", "ru"));
	private static Properties propCurrent;

	private static Properties getProperties(String locale) {
		Validate.notNull(locale, "locale");
		Properties properties = new Properties();
		InputStream is = LocaleManager.class.getResourceAsStream(locale + ".properties");
		if (null == is) {
			throw new FrameworkException(ERR_LOCALE_NOT_FOUND, locale);
		}
		try {
			try {
				properties.load(new InputStreamReader(is, "UTF-8"));
			} finally {
				is.close();
			}
		} catch (IOException e) {
			throw new FrameworkException(ERR_FAILED_READ_PROPERTIES, locale);
		}

		// Check property file defines all enum values
		for (Text l : Text.values()) {
			if (null == properties.getProperty(l.name())) {
				throw new FrameworkException(ERR_PROPERTY_UNDEFINED, l.name(), locale);
			}
		}

		return properties;
	}

	/**
	 * Set locale
	 * 
	 * @param locale
	 *            name of locale
	 */
	public static void setLocale(String locale) {
		Validate.notNull(locale, "locale");
		propCurrent = getProperties(locale);
		Loggers.wi.debug(LOG_SET_LOCALE.getValue(locale));
	}

	/**
	 * Get default locale
	 * 
	 * @return properties
	 */
	public static Properties getDefault() {
		return propDefault;
	}

	/**
	 * Get curent locale
	 * 
	 * @return properties
	 */
	public static Properties getCurrent() {
		return null == propCurrent ? propDefault : propCurrent;
	}

}
