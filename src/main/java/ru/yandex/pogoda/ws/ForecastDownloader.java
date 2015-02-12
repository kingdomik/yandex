package ru.yandex.pogoda.ws;

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

import ru.yandex.pogoda.common.TestException;

public class ForecastDownloader {

	private static final String FORECAST_URL = "http://export.yandex.ru/weather-ng/forecasts/%d.xml";

	public static Forecast download(int cityId) {
		String url = String.format(FORECAST_URL, cityId);
		try (InputStream is = new URL(url).openStream()) {
			JAXBContext jaxbContext = JAXBContext.newInstance(Forecast.class);
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			return (Forecast) unMarshaller.unmarshal(is);
		} catch (MalformedURLException e) {
			throw new TestException(ERR_INVALID_URL, url);
		} catch (IOException e) {
			throw new TestException(e, ERR_FAILED_READ_URL, url);
		} catch (JAXBException e) {
			throw new TestException(e, ERR_FAILED_PARSE_XML, url);
		}
	}

}