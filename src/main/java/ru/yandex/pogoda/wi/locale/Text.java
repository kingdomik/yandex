package ru.yandex.pogoda.wi.locale;

import ru.yandex.pogoda.common.Utils;

/**
 * Text used by tests
 * 
 * Each enum value must have corresponding entries localize property files  
 *
 */
public enum Text {

	LOCATION,
	LOCAL_TIME,
	TEMPERATURE,
	TEMPERATURE_CELSIUS,
	TEMPERATURE_DAY,
	TEMPERATURE_NIGHT,
	TEMPERATURE_YESTERDAY,
	HUMIDITY,
	SUNRISE_SUNSET,
	WIND,
	WIND_SPEED,
	PRESSURE,
	OBSERVATION_TIME,
	
	DETAILED_TEMPERATURE,
	DETAILED_HUMIDITY,
	DETAILED_SUNRISE,
	DETAILED_SUNSET,

	GEOMAGNETIC,	
	SOLAR_MINOR,
	
	TODAY,
	TOMORROW,
	
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY,
	SUNDAY,	
	
	WIND_N,
	WIND_W,
	WIND_S,
	WIND_E,
	WIND_NW,
	WIND_SW,
	WIND_NE,
	WIND_SE;
	
	public String getValue(Object... params) {
		return Utils.format(LocaleManager.getCurrent().getProperty(name()), params);
	}
	
	
	public String getName() {
		// Use one of available locales comfortable for QA team as property names 
		return LocaleManager.getDefault().getProperty(name());
	}
}
