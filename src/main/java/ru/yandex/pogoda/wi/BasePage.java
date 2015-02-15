package ru.yandex.pogoda.wi;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.pogoda.common.Utils;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public class BasePage {
	
	@Name("Запрос")
	@FindBy(name = "request")
	public TextInput inpRequest;
	
	@Name("Найти")
	@FindBy(css = "button[type=submit]")
	public Button btnSubmit;
	
	private WebDriver driver;
	
	BasePage(WebDriver driver) {
		this.driver = driver;		
		 HtmlElementLoader.populatePageObject(this, getDriver());
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public URL getUrl() {
		return Utils.getUrl(getDriver().getCurrentUrl());
	}

}