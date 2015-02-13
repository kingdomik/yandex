package ru.yandex.pogoda.wi;

import java.net.URL;

import org.openqa.selenium.WebDriver;

import ru.yandex.pogoda.common.Utils;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public class AbstractPage {
	
	private WebDriver driver;
	
	AbstractPage(WebDriver driver) {
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