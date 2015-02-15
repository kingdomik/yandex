package ru.yandex.pogoda.data;

import java.net.URL;

import ru.yandex.pogoda.common.Utils;
import ru.yandex.pogoda.ws.Forecast;
import ru.yandex.pogoda.ws.ForecastDownloader;

public enum City {

	SAINT_PETERSBURG(26063, "Санкт-Петербурге"),
	MOSCOW(27612, "Москве");
	
	private int id;
	private String genetive;
	private Forecast data;
	
	City(int id, String genetive) {
		this.id = id;
		this.genetive = genetive;
	}
	
	public int getId() {
		return id;
	}
	
	public String getGenetive() {
		return genetive;
	}
	
	public Forecast getForecast() {
		if (data == null) {
			data = ForecastDownloader.download(getId());
		}
		return data;
	}
	
	public URL getUrl() {
		return Utils.getUrl("https://pogoda.yandex.ru/" + name().toLowerCase().replace('_', '-'));
	}

}
