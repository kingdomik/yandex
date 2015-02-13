package ru.yandex.pogoda.data;

import java.net.URL;

import ru.yandex.pogoda.common.Utils;
import ru.yandex.pogoda.ws.Forecast;
import ru.yandex.pogoda.ws.ForecastDownloader;

public enum City {

	SAINT_PETERSBURG(26063),
	MOSCOW(27612),
	SUNNYVALE(1);
	
	private int id;
	private Forecast data;
	
	City(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
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
