package ru.yandex.pogoda.common;


import java.util.Collection;

public class TestException extends RuntimeException {

	private static final long serialVersionUID = -1199259648444871312L;

	private String message;

	public TestException(Throwable t, IMessage msg, Object... params) {
		// Message is immutable field but we can't pass it to super constructor
		// so pass dummy text and override getMessage()
		super("", t);
		params = convert(params);
		message = msg.getValue(params);
	}

	public TestException(IMessage message, Object... params) {
		this(null, message, params);
	}

	@Override
	public String getMessage() {
		return message;
	}

	private static Object[] convert(Object[] params) {
		Object[] result = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			Object param = params[i];
			if (null == param) {
				result[i] = null;
			} else if (param.getClass().isArray()) {
				result[i] = Utils.join((Object[]) param);
			} else if (param instanceof Collection) {
				result[i] = Utils.join((Collection<?>) param);
			} else {
				result[i] = param;
			}
		}
		return result;
	}

}
