package ru.yandex.pogoda.data;

import java.net.URL;

import ru.yandex.pogoda.common.Utils;
import ru.yandex.pogoda.wi.locale.Text;

public enum Diagram {

	HUMIDITY("f"),
	RAINFALL_DAYAS("n"),
	RAINFALL("r"),
	SUN_DAYS("s"),
	DAY_TEMPERATURE("tmax"),
	NIGHT_TEMPERATURE("tmin");

	private String type;
	
	Diagram(String type) {
		this.type = type;
	}
	
	public String getTitle() {
		return Text.valueOf(Diagram.class.getSimpleName().toUpperCase() + "_" + name()).getValue();
	}
	
	public URL getUrl(String geoid) {
		return Utils.getUrl(Utils.format("https://yastatic.net/weather/i/climate-v2/%s/%s.png", geoid, type));
	}

}
