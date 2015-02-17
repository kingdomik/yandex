package ru.yandex.pogoda.data;

import static ru.yandex.pogoda.common.Messages.ERR_FAILED_PARSE_XML;
import static ru.yandex.pogoda.common.Messages.ERR_FAILED_READ_URL;
import static ru.yandex.pogoda.common.Messages.ERR_INVALID_URL;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ru.yandex.common.FrameworkException;
import ru.yandex.common.Utils;
import ru.yandex.pogoda.wi.lang.Language;
import ru.yandex.pogoda.wi.lang.LocalizedText;
import ru.yandex.pogoda.ws.Forecast;

public enum City {

	SAINT_PETERSBURG(
		26063, 
		"Europe/Moscow", 
		Climate.RAINFALL, 
		Climate.DAY_TEMPERATURE, 
		Climate.NIGHT_TEMPERATURE),
		
	MOSCOW(
		27612, 
		"Europe/Moscow", 
		Climate.HUMIDITY, 
		Climate.RAINFALL_DAYS,
		Climate.RAINFALL,
		Climate.SUN_DAYS,
		Climate.DAY_TEMPERATURE,
		Climate.NIGHT_TEMPERATURE),
		
	LOS_ANGELES(
		72295, 
		"America/Los_Angeles"),
	
	// Results for part search by "санкт"
	ST_MORITZ(6790, null),
	SANKT_ANTON_AM_ARLBERG(11110, null),
	ST_GALLEN(0, null),
	SANKT_POLTEN(0, null);
	
	private int id;
	private ZoneId timeZone;
	private Climate[] climates;
	private Forecast data;
	
	City(int id, String timeZone, Climate... climates) {
		this.id = id;
		this.timeZone = timeZone == null ? null : ZoneId.of(timeZone);
		this.climates = climates;
	}
	
	public static City getByUrl(String url) {
		return City.valueOf(Utils.getUrl(url).getPath().substring(1).toUpperCase().replace('-', '_'));
	}
	
	public int getId() {
		return id;
	}
	
	public ZoneId getTimezone() {
		return timeZone;
	}
	
	public String getGenitive(Language lang) {
		return LocalizedText.valueOf("GENITIVE_" + name()).getValue();
	}
	
	public Climate[] getClimates() {
		return climates;
	}
	
	public String getName() {
		return getForecast().getCity();
	}
	
	public Forecast getForecast() {
		if (data == null) {
			String url = String.format("http://export.yandex.ru/weather-ng/forecasts/%d.xml", getId());
//		      Properties systemSettings = System.getProperties();
//		      systemSettings.put("proxySet", "true");
//		      systemSettings.put("http.proxyHost", "www-proxy.us.oracle.com");
//		      systemSettings.put("http.proxyPort", "80");
			try (InputStream is = new URL(url).openStream()) {
				JAXBContext jaxbContext = JAXBContext.newInstance(Forecast.class);
				Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
				return (Forecast) unMarshaller.unmarshal(is);
			} catch (MalformedURLException e) {
				throw new FrameworkException(ERR_INVALID_URL, url);
			} catch (IOException e) {
				throw new FrameworkException(e, ERR_FAILED_READ_URL, url);
			} catch (JAXBException e) {
				throw new FrameworkException(e, ERR_FAILED_PARSE_XML, url);
			}
		}
		return data;
	}
	
	public URL getUrl() {
		return Utils.getUrl("https://pogoda.yandex.ru/" + name().toLowerCase().replace('_', '-'));
	}

}
