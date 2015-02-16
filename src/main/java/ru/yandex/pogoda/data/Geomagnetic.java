package ru.yandex.pogoda.data;

import ru.yandex.pogoda.wi.lang.LocalizedText;

public enum Geomagnetic {

	SOLAR_MINOR,
	SOLAR_WEAK;

	public String getValue() {
		return LocalizedText.valueOf(Geomagnetic.class.getSimpleName().toUpperCase() + "_" + name()).getValue();
	}
	
	public static Geomagnetic get(String name) {
		return valueOf(name.toUpperCase().replace('-', '_'));
	};
	
}
