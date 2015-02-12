package ru.yandex.pogoda.wi.controls.property;

import org.testng.Assert;

import com.codeborne.selenide.SelenideElement;

/**
 * Text property web object 
 *
 */
public class WITextProperty extends WIProperty<String> {

	public WITextProperty(SelenideElement element, String name) {
		super(element, name);
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

}