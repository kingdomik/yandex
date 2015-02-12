package ru.yandex.pogoda.wi.controls.property;

import static ru.yandex.pogoda.common.Messages.*;

import org.testng.Assert;

import ru.yandex.pogoda.common.Validate;
import ru.yandex.pogoda.wi.controls.WIElement;

import com.thoughtworks.selenium.Wait;

/**
 * Read-only web element
 * 
 * @param <T>
 *            property type
 */
public abstract class WIProperty<T> extends WIElement {

	public static final String WI_PROPERTY_TYPE = "property";

	public WIProperty(IWIRoad road, String locator, String name) {
		super(road, locator, name, WI_PROPERTY_TYPE);
	}

	/**
	 * Read property value
	 * 
	 * @return value
	 */
	public T getValue() {
		return convert(getBrowser().getText(getGlobalID()));
	}

	protected abstract T convert(String text);

	/**
	 * Assert property has specified value
	 * @param value expected value
	 */
	public void assertValue(T value) {
		Validate.notNull(value, "value");
		Assert.assertEquals(getValue(), value, this + " value");
	}

	/**
	 * Wait property has specified value
	 * @param timeout time interval
	 * @param value expected value
	 */
	public void waitValue(TimeSpan timeout, final T value) {
		Validate.notNull(timeout, "timeout");
		Validate.notNull(value, "value");
		new Wait() {
			@Override
			public boolean until() {
				return value.equals(getValue());
			}
		}.wait(TEST_EXPECTED_VALUE.getValue(this, value), timeout.toMilliseconds());
	}

	/**
	 * Wait property doesn't have specified value 
	 * @param timeout time interval
	 * @param value	expected value
	 */
	public void waitNoValue(TimeSpan timeout, final T value) {
		Validate.notNull(timeout, "timeout");
		Validate.notNull(value, "value");
		new Wait() {
			@Override
			public boolean until() {
				return !value.equals(getValue());
			}
		}.wait(TEST_UNEXPECTED_VALUE.getValue(this, value), timeout.toMilliseconds());
	}

}
