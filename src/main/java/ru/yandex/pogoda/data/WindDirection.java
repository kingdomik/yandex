package ru.yandex.pogoda.data;

import ru.yandex.pogoda.wi.locale.Text;

public enum WindDirection {

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
		return Text.valueOf(WindDirection.class.getSimpleName().toUpperCase() + "_" + name()).getValue();
	}
	
	public static WindDirection get(String name) {
		return valueOf(name.toUpperCase());
	};
	
}
