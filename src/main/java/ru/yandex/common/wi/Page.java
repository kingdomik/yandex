package ru.yandex.common.wi;

import java.net.URL;

import org.openqa.selenium.WebDriver;

import ru.yandex.common.Utils;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public class Page {
	
	private WebDriver driver;
	
	public Page(WebDriver driver) {
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