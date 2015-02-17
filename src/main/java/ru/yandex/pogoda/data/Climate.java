package ru.yandex.pogoda.data;

import java.net.URL;

import ru.yandex.common.Utils;
import ru.yandex.pogoda.wi.lang.LocalizedText;

public enum Climate {

	HUMIDITY("f"),
	RAINFALL_DAYS("n"),
	RAINFALL("r"),
	SUN_DAYS("s"),
	DAY_TEMPERATURE("tmax"),
	NIGHT_TEMPERATURE("tmin");

	private String type;
	
	Climate(String type) {
		this.type = type;
	}
	
	public String getTitle() {
		return LocalizedText.valueOf(Climate.class.getSimpleName().toUpperCase() + "_" + name()).getValue();
	}
	
	public URL getUrl(String geoid) {
		return Utils.getUrl(Utils.format("https://yastatic.net/weather/i/climate-v2/%s/%s.png", geoid, type));
	}

}
