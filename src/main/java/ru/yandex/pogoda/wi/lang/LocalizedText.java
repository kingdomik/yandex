package ru.yandex.pogoda.wi.lang;

import ru.yandex.common.Utils;

/**
 * Enumerate all international messages
 * <p>
 * Each value must have corresponding entry localized for all supported languages
 *
 */
public enum LocalizedText {
	
	GENITIVE_MOSCOW,
	GENITIVE_SAINT_PETERSBURG,
	GENITIVE_LOS_ANGELES,
	
	MSG_NOT_FOUND,
	MSG_SEARCHED,
	
	TAB_BRIEF,
	TAB_DETAILED,
	TAB_CLIMATE,
	
	LOCATION,
	LOCAL_TIME,
	TEMPERATURE,
	TEMPERATURE_CELSIUS,
	TEMPERATURE_YESTERDAY,
	WATER_TEMPERATURE,
	HUMIDITY,
	SUNRISE_SUNSET,
	WIND,
	WIND_SPEED,
	WIND_CALM,
	PRESSURE,
	OBSERVATION_TIME,
	
	BRIEF_DATE,
	BRIEF_TEMPERATURE_DAY,
	BRIEF_TEMPERATURE_NIGHT,
	
	DETAILED_DATE,
	DETAILED_TEMPERATURE,
	DETAILED_HUMIDITY,
	DETAILED_SUNRISE,
	DETAILED_SUNSET,

	GEOMAGNETIC,	
	GEOMAGNETIC_CALM,
	GEOMAGNETIC_WEAK,
	GEOMAGNETIC_STRONG,
	
	TODAY,
	TOMORROW,
	
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY,
	SUNDAY,	
	
	SHORT_JANUARY,
	SHORT_FEBRUARY,
	SHORT_MARCH,
	SHORT_APRIL,
	SHORT_MAY,
	SHORT_JUNE,
	SHORT_JULY,
	SHORT_AUGUST,
	SHORT_SEPTEMBER,
	SHORT_OCTOBER,
	SHORT_NOVEMBER,
	SHORT_DECEMBER,

	GENITIVE_JANUARY,
	GENITIVE_FEBRUARY,
	GENITIVE_MARCH,
	GENITIVE_APRIL,
	GENITIVE_MAY,
	GENITIVE_JUNE,
	GENITIVE_JULY,
	GENITIVE_AUGUST,
	GENITIVE_SEPTEMBER,
	GENITIVE_OCTOBER,
	GENITIVE_NOVEMBER,
	GENITIVE_DECEMBER,

	WINDDIRECTION_N,
	WINDDIRECTION_W,
	WINDDIRECTION_S,
	WINDDIRECTION_E,
	WINDDIRECTION_NW,
	WINDDIRECTION_SW,
	WINDDIRECTION_NE,
	WINDDIRECTION_SE,
	
	DIAGRAM_HUMIDITY,
	DIAGRAM_RAINFALL_DAYS,
	DIAGRAM_RAINFALL,
	DIAGRAM_SUN_DAYS,
	DIAGRAM_DAY_TEMPERATURE,
	DIAGRAM_NIGHT_TEMPERATURE;
	
	/**
	 * Return text value formatted using specified parameters
	 * @param params - parameters required for formating
	 * @return formatted text
	 */
	public String getValue(Object... params) {
		return Utils.format(LocalizedTextManager.getCurrent().getProperty(name()), params);
	}
	
	/**
	 * Return localized text name. Use default language bundle for text names
	 * @return text name
	 */
	public String getName() {
		return LocalizedTextManager.getDefault().getProperty(name());
	}
	
}
