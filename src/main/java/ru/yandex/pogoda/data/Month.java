package ru.yandex.pogoda.data;

import ru.yandex.pogoda.common.Validate;
import ru.yandex.pogoda.wi.lang.LocalizedText;

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

	public String getValue() {
		return LocalizedText.valueOf(name()).getValue();
	}
	
	public String getShortValue() {
		return LocalizedText.valueOf("SHORT_" + name()).getValue();
	}
	
	public static Month get(int index) {
		Validate.biggerOrEqual(index, 0, "month index");
		Validate.smallerOrEqual(index, 11, "month index");
		return values()[index];
	}

}
