package ru.yandex.pogoda.data;

import static ru.yandex.pogoda.Messages.ERR_FAILED_PARSE_XML;
import static ru.yandex.pogoda.Messages.ERR_FAILED_READ_URL;
import static ru.yandex.pogoda.Messages.ERR_INVALID_URL;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ru.yandex.common.FrameworkException;
import ru.yandex.common.Utils;
import ru.yandex.pogoda.wi.CityForecastPage;
import ru.yandex.pogoda.wi.lang.Language;
import ru.yandex.pogoda.wi.lang.LocalizedText;
import ru.yandex.pogoda.ws.Forecast;

/**
 * Enumerate cities required for testing
 *
 */
public enum City {

	SAINT_PETERSBURG(
		26063, 
		"Europe/Moscow", 
		Diagram.RAINFALL, 
		Diagram.DAY_TEMPERATURE, 
		Diagram.NIGHT_TEMPERATURE),
		
	MOSCOW(
		27612, 
		"Europe/Moscow", 
		Diagram.HUMIDITY, 
		Diagram.RAINFALL_DAYS,
		Diagram.RAINFALL,
		Diagram.SUN_DAYS,
		Diagram.DAY_TEMPERATURE,
		Diagram.NIGHT_TEMPERATURE),
		
	MURMANSK(
			22113, 
			"Europe/Moscow", 
			Diagram.HUMIDITY, 
			Diagram.RAINFALL,
			Diagram.DAY_TEMPERATURE,
			Diagram.NIGHT_TEMPERATURE),
				
	LOS_ANGELES(
		72295, 
		"America/Los_Angeles"),
	
	// Cities are being used to test part search results so full data isn't required
	ST_MORITZ(6790, null),
	SANKT_ANTON_AM_ARLBERG(11110, null),
	ST_GALLEN(0, null),
	SANKT_POLTEN(0, null);
	
	private int id;
	private ZoneId timeZone;
	private Diagram[] diagrams;
	private Forecast data;
	
	/**
	 * Create city
	 * 
	 * @param id - city id according XML {@link http://pogoda.yandex.ru/static/cities.xml} 
	 * @param timeZone - time zone name
	 * @param diagrams - list of available climate diagrams
	 */
	City(int id, String timeZone, Diagram... diagrams) {
		this.id = id;
		this.timeZone = timeZone == null ? null : ZoneId.of(timeZone);
		this.diagrams = diagrams;
	}
	
	/**
	 * Return city by browser URL
	 * @param url - browser URL
	 * @return city
	 */
	public static City getByUrl(String url) {
		return City.valueOf(Utils.getUrl(url).getPath().substring(1).toUpperCase().replace('-', '_'));
	}
	

	/**
	 * Return city name declared in {@link http://pogoda.yandex.ru/static/cities.xml}
	 * @return city name
	 */
	public String getName() {
		return getForecast().getCity();
	}
	
	/**
	 * Return city id
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Return city time zone
	 * @return time zone
	 */
	public ZoneId getTimezone() {
		return timeZone;
	}
	
	/**
	 * Return city name genitive  
	 * @param lang - language to be used
	 * @return city name genitive 
	 */
	public String getGenitive(Language lang) {
		return LocalizedText.valueOf("GENITIVE_" + name()).getValue();
	}
	
	/**
	 * Return available city climate diagrams
	 * @return array of diagrams
	 */
	public Diagram[] getDiagrams() {
		return diagrams;
	}
	
	/**
	 * Return city forecast information delivered by yandex web service
	 * <p>
	 * Data will be requested once first time and cached for all test session
	 * @return forecast object
	 * @see Forecast
	 */
	public Forecast getForecast() {
		if (data == null) {
			String url = String.format("http://export.yandex.ru/weather-ng/forecasts/%d.xml", getId());
		      Properties systemSettings = System.getProperties();
		      systemSettings.put("proxySet", "true");
		      systemSettings.put("http.proxyHost", "www-proxy.us.oracle.com");
		      systemSettings.put("http.proxyPort", "80");
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

	/**
	 * Return URL to city forecast page
	 * @return city URL
	 */
	public URL getUrl() {
		return Utils.getUrl(CityForecastPage.URL + "/" + name().toLowerCase().replace('_', '-'));
	}

}
