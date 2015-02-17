package ru.yandex.pogoda.data;

import ru.yandex.pogoda.wi.lang.LocalizedText;

/**
 * Enumerate wind directions
 *
 */
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

	/**
	 * Return localized wind direction name
	 * @return name of direction
	 */
	public String getValue() {
		return LocalizedText.valueOf(WindDirection.class.getSimpleName().toUpperCase() + "_" + name()).getValue();
	}
	
	/**
	 * Return wind direction by abbreviation
	 * @param name - wind abbreviation 
	 * @return wind direction
	 */
	public static WindDirection get(String name) {
		return valueOf(name.toUpperCase());
	};
	
}
