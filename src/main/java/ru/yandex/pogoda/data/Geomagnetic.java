package ru.yandex.pogoda.data;

import ru.yandex.pogoda.wi.locale.Text;

public enum Geomagnetic {

	SOLAR_MINOR;

	Geomagnetic() {
	}
	
	public String getValue() {
		return Text.valueOf(name()).getValue();
	}
	
	public static Geomagnetic get(String name) {
		return valueOf(name.toUpperCase().replace('-', '_'));
	};
	
}
