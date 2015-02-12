package ru.yandex.pogoda.wi.controls.property;

import org.testng.Assert;

/**
 * Text property web object 
 *
 */
public class WITextProperty extends WIProperty<String> {

	public WITextProperty(String locator, String name) {
		super(locator, name);
	}

	public WITextProperty(IWIRoad road, Text name) {
		super(road, "//*[text()='" + name.getValue() + "']", name.getName());
	}

	@Override
	protected String convert(String text) {
		return text;
	}

	/**
	 * Assert property value is empty
	 */
	public void assertEmpty() {
		Assert.assertEquals(getValue(), "", this + " value");
	}

	public void assertValue(Text value) {
		super.assertValue(value.getValue());
	}

}