package ru.yandex.pogoda.wi.lang;

import static ru.yandex.pogoda.common.Messages.ERR_NO_LANG_WEARTHER;
import ru.yandex.common.FrameworkException;
import ru.yandex.pogoda.ws.Forecast;

public enum Language {
	
	RU,
	UK,
	BE;
	
	public static Language get(String name) {
		return valueOf(name.toUpperCase());
	}
	
	public String getWeatherType(Forecast.Fact ws) {
		if (this.equals(RU)) {
			return ws.getWeatherType();
		}
		if (this.equals(UK)) {
			return ws.getWeatherTypeUa();
		}
		if (this.equals(BE)) {
			return ws.getWeatherTypeBy();
		}
		throw new FrameworkException(ERR_NO_LANG_WEARTHER, this);
	}
	
	public String getWeatherType(Forecast.Day.DayPart ws) {
		if (this.equals(RU)) {
			return ws.getWeatherType();
		}
		if (this.equals(UK)) {
			return ws.getWeatherTypeUa();
		}
		if (this.equals(BE)) {
			return ws.getWeatherTypeBy();
		}
		throw new FrameworkException(ERR_NO_LANG_WEARTHER, this);
	}
	
}
