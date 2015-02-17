package ru.yandex.pogoda.data;

import ru.yandex.common.Validate;
import ru.yandex.pogoda.wi.lang.LocalizedText;

/**
 * Enumerate days of week
 *
 */
public enum DayOfWeek {

	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY,
	SUNDAY;	

	/**
	 * Returns localized name of week day 
	 * @return name of day
	 */
	public String getValue() {
		return LocalizedText.valueOf(name()).getValue();
	}
	
	/**
	 * Returns day of week by index
	 * @param id - day index (1..7) 
	 * @return day of week
	 */
	public static DayOfWeek get(int id) {
		Validate.biggerOrEqual(id, 1, "day of week id");
		Validate.smallerOrEqual(id, 7, "day of week id");
		return values()[id - 1];
	};
	
}
