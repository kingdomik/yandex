package ru.yandex.pogoda.data;

import ru.yandex.common.Validate;
import ru.yandex.pogoda.wi.lang.LocalizedText;

public enum Geomagnetic {

	CALM,
	WEAK,
	STRONG;

	public String getValue() {
		return LocalizedText.valueOf(Geomagnetic.class.getSimpleName().toUpperCase() + "_" + name()).getValue();
	}
	
	public static Geomagnetic get(String sid) {
		int id = Integer.parseInt(sid);
		Validate.biggerOrEqual(id, 1, "geomagnetic id");
		Validate.smallerOrEqual(id, values().length, "geomagnetic id");
		return values()[id - 1];
	};
	
}
