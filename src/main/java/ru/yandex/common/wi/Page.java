package ru.yandex.common.wi;

import java.net.URL;

import org.openqa.selenium.WebDriver;

import ru.yandex.common.Utils;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

/**
 * Base class for all pages
 *
 */
public class Page {
	
	private WebDriver driver;

	/**
	 * Create page wrapper
	 * @param driver - browser driver 
	 */
	public Page(WebDriver driver) {
		this.driver = driver;		
		 HtmlElementLoader.populatePageObject(this, getDriver());
	}
	
	/**
	 * Return browser driver
	 * @return driver
	 */
	public WebDriver getDriver() {
		return driver;
	}
	
	/**
	 * Return current browser URL 
	 * @return
	 */
	public URL getUrl() {
		return Utils.getUrl(getDriver().getCurrentUrl());
	}
	
}