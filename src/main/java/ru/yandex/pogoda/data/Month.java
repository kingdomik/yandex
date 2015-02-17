package ru.yandex.pogoda.data;

import ru.yandex.common.Validate;
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

	public String getGenitiveValue() {
		return LocalizedText.valueOf("GENITIVE_" + name()).getValue();
	}
	
	public String getShortValue() {
		return LocalizedText.valueOf("SHORT_" + name()).getValue();
	}
	
	public static Month getById(int index) {
		Validate.biggerOrEqual(index, 1, "month index");
		Validate.smallerOrEqual(index, 12, "month index");
		return values()[index - 1];
	}

}
