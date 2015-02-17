package ru.yandex.common;


import java.util.Collection;

/**
 * Framework exception
 *
 */
public class FrameworkException extends RuntimeException {

	private static final long serialVersionUID = -1199259648444871312L;

	private String message;

	/**
	 * Create framework exception
	 * @param cause - caused exception object
	 * @param message - exception message, can be string format if parameters are required
	 * @param params - message parameters
	 */
	public FrameworkException(Throwable cause, IMessage message, Object... params) {
		super("", cause);
		this.message = message.getValue(convert(params));
	}

	/**
	 * Create framework exception
	 * @param message - exception message, can be string format if parameters are required
	 * @param params - message parameters
	 */
	public FrameworkException(IMessage message, Object... params) {
		this(null, message, params);
	}

	/**
	 * Return exception message
	 */
	@Override
	public String getMessage() {
		return message;
	}

	@SuppressWarnings("unchecked")
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
