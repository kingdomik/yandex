package ru.yandex.pogoda.wi.lang;

import static ru.yandex.pogoda.Messages.ERR_NO_LANG_WEARTHER;
import ru.yandex.common.FrameworkException;
import ru.yandex.pogoda.ws.Forecast;

/**
 * Enumerate available languages supported by forecast service
 * <p>
 * To support new language
 * <li>Add item to enum according yandex service convention, e.g. BE
 * <li>Add bundle with translated messages, e.g. be.properties
 * <li>Extends language sensitive methods
 *
 */
public enum Language {
	
	/**
	 * Russian
	 */
	RU,
	
	/**
	 * Ukrainian
	 */
	UK;
	
	/**
	 * Get language by short name abbreviation, e.g. "ru", "uk"
	 * @param name - language enum abbreviation
	 * @return language
	 */
	public static Language get(String name) {
		return valueOf(name.toUpperCase());
	}
	
	/**
	 * Extract weather type name from web service data depends on language 
	 * @param ws - web service data
	 * @return weather type name
	 * @see Forecast.Fact
	 */
	public String getWeatherType(Forecast.Fact ws) {
		if (this.equals(RU)) {
			return ws.getWeatherType();
		}
		if (this.equals(UK)) {
			return ws.getWeatherTypeUa();
		}
		throw new FrameworkException(ERR_NO_LANG_WEARTHER, this);
	}
	
	/**
	 * Extract weather type name from web service data depends on language 
	 * @param ws - web service data
	 * @return weather type name
	 * @see Forecast.Day.DayPart
	 */
	public String getWeatherType(Forecast.Day.DayPart ws) {
		if (this.equals(RU)) {
			return ws.getWeatherType();
		}
		if (this.equals(UK)) {
			return ws.getWeatherTypeUa();
		}
		throw new FrameworkException(ERR_NO_LANG_WEARTHER, this);
	}
	
}
