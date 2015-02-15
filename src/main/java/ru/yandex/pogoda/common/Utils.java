package ru.yandex.pogoda.common;

import static ru.yandex.pogoda.common.Messages.ERR_FAILED_FORMAT_STRING;
import static ru.yandex.pogoda.common.Messages.ERR_FAILED_PARSE_DATE;
import static ru.yandex.pogoda.common.Messages.ERR_INVALID_URL;

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

import javax.xml.datatype.XMLGregorianCalendar;

public class Utils {

	public static final String DEFAULT_ARRAY_DELIMITER = ",";

	public static URL getUrl(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new FrameworkException(ERR_INVALID_URL, url);
		}
	}
	
	public static String formatDate(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
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
		return new DecimalFormat(format).format(value);
	}

	public static String byteToStringWithSign(byte value) {
		// Trick: Need long minus
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

	public static String join(Collection<?> values) {
		return join(DEFAULT_ARRAY_DELIMITER, values);
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
