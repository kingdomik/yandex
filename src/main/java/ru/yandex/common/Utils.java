package ru.yandex.common;

import static ru.yandex.pogoda.Messages.ERR_FAILED_FORMAT_STRING;
import static ru.yandex.pogoda.Messages.ERR_FAILED_PARSE_DATE;
import static ru.yandex.pogoda.Messages.ERR_INVALID_URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.XMLGregorianCalendar;

/*
 * Set of helpful functions
 */
public class Utils {

	public static final String DEFAULT_ARRAY_DELIMITER = ",";

	/**
	 * Create URL object and add URL to exception message if failed. 
	 * @param url - URL string
	 * @return URL object
	 */
	public static URL getUrl(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new FrameworkException(ERR_INVALID_URL, url);
		}
	}
	
	public static String formatXmlDate(String date, String format) {
		return new SimpleDateFormat(format).format(DatatypeConverter.parseDate(date).getTime());
	}

	public static String formatDate(LocalDate date, String format) {
		return date.format(DateTimeFormatter.ofPattern(format));
	}

	public static String formatDate(LocalTime time, String format) {
		return time.format(DateTimeFormatter.ofPattern(format));
	}

	public static String formatDate(XMLGregorianCalendar date, String format) {
		return new SimpleDateFormat(format).format(date.toGregorianCalendar().getTime());
	}

	public static String formatFloat(float value, String format) {
		// FIXME Use page locale
		return new DecimalFormat(format).format(value).replace('.', ',');
	}

	public static String temperature(String text) {
		return temperature(Byte.parseByte(text));
	}
	
	public static String temperature(byte value) {
		// WARNING: Need long minus
		return value == 0 ? "" + value : (value > 0 ? "+" + value : "−" + (-value));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> String join( T... values) {
		return joinWithDelimiter(DEFAULT_ARRAY_DELIMITER, values);
	}

	@SafeVarargs
	public static <T> String joinWithDelimiter(String delimiter, T... values) {
		if (values.length == 0) {
			return "";
		}
		StringBuilder s = new StringBuilder();
		for (int i = 0;;) {
			s.append(values[i]);
			if (++i == values.length) {
				return s.toString();
			}
			s.append(delimiter);
		}
	}

	public static String join(String delimiter, Collection<?> values) {
		return joinWithDelimiter(delimiter, values.toArray());
	}

	public static String format(String format, Object... params) {
		try {
			return String.format(format, params);
		} catch (Exception e) {
			throw new FrameworkException(ERR_FAILED_FORMAT_STRING, format, e);
		}
	}

	public static Date parseDate(String format, String text) {
		DateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(text);
		} catch (ParseException e) {
			throw new FrameworkException(ERR_FAILED_PARSE_DATE, text, format, e);
		}
	}

}
