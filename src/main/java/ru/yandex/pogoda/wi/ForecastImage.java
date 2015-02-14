package ru.yandex.pogoda.wi;

import org.openqa.selenium.WebElement;

import ru.yandex.qatools.htmlelements.element.Image;

public class ForecastImage extends Image {

	public ForecastImage(WebElement wrappedElement) {
	    super(wrappedElement);
    }
	
	@Override
    public String getSource() {
		return getWrappedElement().getCssValue("background-image");
    }	

}
