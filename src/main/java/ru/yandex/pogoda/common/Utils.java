package ru.yandex.pogoda.common;

import static ru.yandex.pogoda.common.Messages.ERR_FAILED_FORMAT_STRING;
import static ru.yandex.pogoda.common.Messages.ERR_FAILED_PARSE_DATE;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class Utils {

	public static final String DEFAULT_ARRAY_DELIMITER = ",";

	public static URI getURI(String url, IMessage errorMessage) {
		try {
			return new URI(url);
		} catch (URISyntaxException e) {
			throw new FrameworkException(errorMessage, url);
		}
	}

	@SuppressWarnings({ "unchecked", "varargs" })
	public static <T> String join(T... values) {
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
