package ru.yandex.pogoda.data;

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
		return values()[id];
	};
	
}
