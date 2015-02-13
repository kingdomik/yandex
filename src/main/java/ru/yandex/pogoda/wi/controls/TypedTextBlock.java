package ru.yandex.pogoda.wi.controls;

import org.openqa.selenium.WebElement;

import ru.yandex.qatools.htmlelements.element.TextBlock;

public abstract class TypedTextBlock<T> extends TextBlock {
	
	public TypedTextBlock(WebElement wrappedElement) {
	    super(wrappedElement);
    }

	public abstract T getValue();

}
