package ru.yandex.pogoda.wi.locale;

import ru.yandex.pogoda.common.Utils;

/**
 * Text used by tests
 * 
 * Each enum value must have corresponding entries localize property files  
 *
 */
public enum Text {

	MSG_NOT_FOUND,
	MSG_SEARCHED,
	
	TAB_BRIEF,
	TAB_DETAILED,
	TAB_CLIMATE,
	
	LOCATION,
	LOCAL_TIME,
	TEMPERATURE,
	TEMPERATURE_CELSIUS,
	TEMPERATURE_DAY,
	TEMPERATURE_NIGHT,
	TEMPERATURE_YESTERDAY,
	WATER_TEMPERATURE,
	HUMIDITY,
	SUNRISE_SUNSET,
	WIND,
	WIND_SPEED,
	WIND_CALM,
	PRESSURE,
	OBSERVATION_TIME,
	
	DETAILED_TEMPERATURE,
	DETAILED_HUMIDITY,
	DETAILED_SUNRISE,
	DETAILED_SUNSET,

	GEOMAGNETIC,	
	GEOMAGNETIC_SOLAR_MINOR,
	
	TODAY,
	TOMORROW,
	
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY,
	SUNDAY,	
	
	JANUARY,
	FEBRUARY,
	MARCH,
	APRIL,
	MAY,
	JUNE,
	JULY,
	AUGUST,
	SEPTEMBER,
	OCTOBER,
	NOVEMBER,
	DECEMBER,

	WINDDIRECTION_N,
	WINDDIRECTION_W,
	WINDDIRECTION_S,
	WINDDIRECTION_E,
	WINDDIRECTION_NW,
	WINDDIRECTION_SW,
	WINDDIRECTION_NE,
	WINDDIRECTION_SE,
	
	DIAGRAM_HUMIDITY,
	DIAGRAM_RAINFALL_DAYAS,
	DIAGRAM_RAINFALL,
	DIAGRAM_SUN_DAYS,
	DIAGRAM_DAY_TEMPERATURE,
	DIAGRAM_NIGHT_TEMPERATURE;
	
	public String getValue(Object... params) {
		return Utils.format(LocaleManager.getCurrent().getProperty(name()), params);
	}
	
	
	public String getName() {
		// Use one of available locales comfortable for QA team as property names 
		return LocaleManager.getDefault().getProperty(name());
	}
}
