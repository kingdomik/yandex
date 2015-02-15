package ru.yandex.pogoda.data;

import static ru.yandex.pogoda.common.Messages.ERR_FAILED_PARSE_XML;
import static ru.yandex.pogoda.common.Messages.ERR_FAILED_READ_URL;
import static ru.yandex.pogoda.common.Messages.ERR_INVALID_URL;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ru.yandex.pogoda.common.FrameworkException;
import ru.yandex.pogoda.common.Utils;
import ru.yandex.pogoda.ws.Forecast;

public enum City {

	SAINT_PETERSBURG(26063, "Санкт-Петербурге"),
	MOSCOW(27612, "Москве"),
	
	// Results for part search by "санкт"
	ST_MORITZ(6790, "Санкт-Морице"),
	SANKT_ANTON_AM_ARLBERG(11110, "Санкт-Антон-ам-Арльберг"),
	ST_GALLEN(0, "Санкт-Галлене"),
	SANKT_POLTEN(0, "Санкт-Пёльтене");
	
	private int id;
	private String genetive;
	private Forecast data;
	
	City(int id, String genetive) {
		this.id = id;
		this.genetive = genetive;
	}
	
	public static City getByUrl(String url) {
		return City.valueOf(Utils.getUrl(url).getPath().substring(1).toUpperCase().replace('-', '_'));
	}
	
	public int getId() {
		return id;
	}
	
	public String getGenetive() {
		return genetive;
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
