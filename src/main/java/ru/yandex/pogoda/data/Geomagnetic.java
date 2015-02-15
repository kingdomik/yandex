package ru.yandex.pogoda.data;

import ru.yandex.pogoda.wi.locale.Text;

public enum Geomagnetic {

	SOLAR_MINOR;

	public String getValue() {
		return Text.valueOf(Geomagnetic.class.getSimpleName().toUpperCase() + "_" + name()).getValue();
	}
	
	public static Geomagnetic get(String name) {
		return valueOf(name.toUpperCase().replace('-', '_'));
	};
	
}
