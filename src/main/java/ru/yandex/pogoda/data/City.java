package ru.yandex.pogoda.data;

import ru.yandex.pogoda.ws.Forecast;
import ru.yandex.pogoda.ws.ForecastDownloader;

public enum City {

	MOSCOW(27612);
	
	private int id;
	private Forecast data;
	
	City(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public Forecast getData() {
		if (data == null) {
			data = ForecastDownloader.download(getId());
		}
		return data;
	}

}
