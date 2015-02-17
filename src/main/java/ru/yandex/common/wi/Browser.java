package ru.yandex.common.wi;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.yandex.account.wi.LanguagePage;
import ru.yandex.pogoda.wi.CityForecastPage;
import ru.yandex.pogoda.wi.lang.Language;
import ru.yandex.pogoda.wi.lang.LocalizedTextManager;

public class Browser {
	
	private WebDriver driver = new FirefoxDriver(); 
	
	public Browser() {
		
	}
	
	public void close() {
		driver.quit();		
	}

	public CityForecastPage goForecast(URL url) {
		return goForecast(url.toString());
	}
	
	public CityForecastPage goForecast(String url) {
		driver.get(url);
		return getForecastPage();
	}
	
	public CityForecastPage getForecastPage() {
		return new CityForecastPage(driver);
	}
	
	public LanguagePage goLanguage() {
		driver.get(LanguagePage.URL);
		return new LanguagePage(driver);
	}

	public void setLanguage(Language lang) {
		CityForecastPage page;
		if (!driver.getCurrentUrl().startsWith(CityForecastPage.URL)) {
			page = goForecast(CityForecastPage.URL);
		} else {
			page = getForecastPage();
		}
		if (!lang.equals(page.getLanguage())) {
			goLanguage().setLanguage(lang);
			LocalizedTextManager.setLanguage(lang.name());
		}
	}
	
}
