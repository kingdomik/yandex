package ru.yandex.pogoda.data;

import ru.yandex.pogoda.wi.locale.Text;

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
		return Text.valueOf(name()).getValue();
	}

}
