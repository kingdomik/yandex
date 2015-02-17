package ru.yandex.pogoda.data;

import ru.yandex.common.Validate;
import ru.yandex.pogoda.wi.lang.LocalizedText;

/**
 * Enumerate months
 *
 */
public enum Month {

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
	DECEMBER;

	/**
	 * Return localized month name genitive required to build date
	 * @return month name genitive
	 */
	public String getGenitiveValue() {
		return LocalizedText.valueOf("GENITIVE_" + name()).getValue();
	}
	
	/**
	 * Return short month name required for climate diagram X axis
	 * @return
	 */
	public String getShortValue() {
		return LocalizedText.valueOf("SHORT_" + name()).getValue();
	}
	
	/**
	 * Return month by index (1..12)
	 * @param index - index of month
	 * @return month
	 */
	public static Month get(int index) {
		Validate.biggerOrEqual(index, 1, "month index");
		Validate.smallerOrEqual(index, 12, "month index");
		return values()[index - 1];
	}

}
