package ru.yandex.pogoda.wi.controls;

import ru.yandex.common.Validate;

import com.codeborne.selenide.SelenideElement;

/**
 * Base class for all functional web interface elements
 *
 */
public class WIElement {

	private SelenideElement element;
	private String name;
	private String type;

	public WIElement(SelenideElement element, String name, String type) {
		Validate.notNull(name, "name");
		Validate.notNull(type, "type");
		this.element = element;
		this.name = name;
		this.type = type;
	}

	/**
	 * Returns web element type name
	 * @return type name
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns web element name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getType() + " \"" + getName() + "\"";
	}

    public SelenideElement getElement() {
    	return element;
    }

}
