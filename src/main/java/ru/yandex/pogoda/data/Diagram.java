package ru.yandex.pogoda.data;

import java.net.URL;

import ru.yandex.common.Utils;
import ru.yandex.pogoda.wi.lang.LocalizedText;

/**
 * Enumerate climate diagrams
 *
 */
public enum Diagram {

	HUMIDITY("f"),
	RAINFALL_DAYS("n"),
	RAINFALL("r"),
	SUN_DAYS("s"),
	DAY_TEMPERATURE("tmax"),
	NIGHT_TEMPERATURE("tmin");

	private String type;
	
	Diagram(String type) {
		this.type = type;
	}

	/**
	 * Returns localized diagram title
	 * @return title of diagram
	 */
	public String getTitle() {
		return LocalizedText.valueOf(Diagram.class.getSimpleName().toUpperCase() + "_" + name()).getValue();
	}
	
	/**
	 * Return URL to diagram image based on "geoid" value delivered by web service
	 * @param geoid - 
	 * @return
	 */
	public URL getUrl(String geoid) {
		return Utils.getUrl(Utils.format("https://yastatic.net/weather/i/climate-v2/%s/%s.png", geoid, type));
	}

}
