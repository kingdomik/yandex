package ru.yandex.pogoda.wi.controls;

import java.util.Date;

import org.openqa.selenium.WebElement;

public class TimeTextBlock extends TypedTextBlock<	Date>{

	public TimeTextBlock(WebElement wrappedElement) {
	    super(wrappedElement);
    }

	@Override
    public Date getValue() {
	    return null;
    }

}
