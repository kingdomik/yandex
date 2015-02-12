package ru.yandex.pogoda.wi.controls.property;

import org.testng.Assert;

import ru.yandex.pogoda.common.Validate;
import ru.yandex.pogoda.wi.controls.WIElement;

import com.codeborne.selenide.SelenideElement;

/**
 * Read-only web element
 * 
 * @param <T>
 *            property type
 */
public abstract class WIProperty<T> extends WIElement {

	public static final String WI_PROPERTY_TYPE = "property";

	public WIProperty(SelenideElement element, String name) {
		super(element, name, WI_PROPERTY_TYPE);
	}

	/**
	 * Read property value
	 * 
	 * @return value
	 */
	public T getValue() {
		return convert(getElement().getText());
	}

	/**
	 * Abstract method to convert web data to functional data
	 * 
	 * @param text
	 * @return
	 */
	protected abstract T convert(String text);

	/**
	 * Assert property has specified value
	 * @param value expected value
	 */
	public void assertValue(T value) {
		Validate.notNull(value, "value");
		Assert.assertEquals(getValue(), value, this + " value");
	}

}
