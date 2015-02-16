package ru.yandex.pogoda.data;

import ru.yandex.pogoda.wi.lang.LocalizedText;

public enum WindDirection {

	CALM,
	N,
	W,
	S,
	E,
	NW,
	SW,
	NE,
	SE;

	WindDirection() {
	}
	
	public String getValue() {
		return LocalizedText.valueOf(WindDirection.class.getSimpleName().toUpperCase() + "_" + name()).getValue();
	}
	
	public static WindDirection get(String name) {
		return valueOf(name.toUpperCase());
	};
	
}
