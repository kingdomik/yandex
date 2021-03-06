package ru.yandex.common.wi;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.yandex.account.wi.LanguageSettingsPage;
import ru.yandex.account.wi.RegionSettingsPage;
import ru.yandex.pogoda.wi.CityForecastPage;
import ru.yandex.pogoda.wi.lang.Language;
import ru.yandex.pogoda.wi.lang.LocalizedTextManager;

/**
 * Class represents logical layer of browser and provides shortcuts to all other pages  
 *
 */
public class Browser {
	
	private WebDriver driver; 
	
	/**
	 * Create browser
	 */
	public Browser() {
		driver = new FirefoxDriver();
	}
	
	/**
	 * Close browser
	 */
	public void close() {
		driver.quit();		
	}

	/**
	 * Go to city forecast page
	 * @param url - forecast URL
	 * @return city forecast page wrapper 
	 */
	public CityForecastPage goForecast(URL url) {
		return goForecast(url.toString());
	}
	
	/**
	 * Go to city forecast page
	 * @param url - forecast URL
	 * @return city forecast page wrapper 
	 */
	public CityForecastPage goForecast(String url) {
		driver.get(url);
		return getForecastPage();
	}
	
	/**
	 * Return city forecast page
	 * @param url - forecast URL
	 * @return city forecast page wrapper
	 */
	public CityForecastPage getForecastPage() {
		return new CityForecastPage(driver);
	}
	
	/**
	 * Go to region settings page
	 * @return region settings page wrapper
	 */
	public RegionSettingsPage goRegionSettingsPage() {
		driver.get(RegionSettingsPage.URL);
		return new RegionSettingsPage(driver);
	}
	
	/**
	 * Go to language settings page
	 * @return language settings page wrapper
	 */
	public LanguageSettingsPage goLanguageSettingsPage() {
		driver.get(LanguageSettingsPage.URL);
		return new LanguageSettingsPage(driver);
	}

	/**
	 * Set browser language
	 * @param lang - language
	 */
	public void setLanguage(Language lang) {
		CityForecastPage page;
		if (!driver.getCurrentUrl().startsWith(CityForecastPage.URL)) {
			page = goForecast(CityForecastPage.URL);
		} else {
			page = getForecastPage();
		}
		if (!lang.equals(page.getLanguage())) {
			goLanguageSettingsPage().setLanguage(lang);
			LocalizedTextManager.setLanguage(lang.name());
		}
	}
	
}
