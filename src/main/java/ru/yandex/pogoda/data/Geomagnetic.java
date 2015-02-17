package ru.yandex.pogoda.data;

import ru.yandex.common.Validate;
import ru.yandex.pogoda.wi.lang.LocalizedText;

/**
 * Enumerates geomagnetic states
 *
 */
public enum Geomagnetic {

	CALM,
	WEAK,
	STRONG;

	/**
	 * Returns localized name of geomagnetic state 
	 * @return name of geomagnetic state
	 */
	public String getValue() {
		return LocalizedText.valueOf(Geomagnetic.class.getSimpleName().toUpperCase() + "_" + name()).getValue();
	}
	
	/**
	 * Return geomagnetic state by index delivered by web service
	 * @param sid - geomagnetic state id 
	 * @return geomagnetic state
	 */
	public static Geomagnetic get(String sid) {
		int id = Integer.parseInt(sid);
		Validate.biggerOrEqual(id, 1, "geomagnetic id");
		Validate.smallerOrEqual(id, values().length, "geomagnetic id");
		return values()[id - 1];
	};
	
}
