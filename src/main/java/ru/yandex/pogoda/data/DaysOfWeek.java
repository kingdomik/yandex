package ru.yandex.pogoda.data;

import ru.yandex.pogoda.common.Validate;
import ru.yandex.pogoda.wi.locale.Text;

public enum DaysOfWeek {

	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY,
	SUNDAY;	

	DaysOfWeek() {
	}
	
	public String getValue() {
		return Text.valueOf(name()).getValue();
	}
	
	public static DaysOfWeek get(int id) {
		Validate.biggerOrEqual(id, 1, "day of week id");
		Validate.smallerOrEqual(id, 7, "day of week id");
		return values()[id - 1];
	};
	
}
