package ru.yandex.common.wi;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.yandex.account.wi.LanguagePage;
import ru.yandex.pogoda.wi.ForecastPage;
import ru.yandex.pogoda.wi.lang.Language;
import ru.yandex.pogoda.wi.lang.LocalizedTextManager;

public class Browser {
	
	private WebDriver driver = new FirefoxDriver(); 
	
	public Browser() {
		
	}
	
	public void close() {
		driver.quit();		
	}
	
	public ForecastPage goForecast(String url) {
		driver.get(url);
		return getForecastPage();
	}
	
	public ForecastPage getForecastPage() {
		return new ForecastPage(driver);
	}
	
	public LanguagePage goLanguage() {
		return new LanguagePage(driver);
	}

	public void setLanguage(Language lang) {
		ForecastPage page = goForecast("http://pogoda.yandex.ru");
		if (!lang.equals(page.getLanguage())) {
			driver.get("http://tune.yandex.ru/lang");
			goLanguage().setLanguage(lang);
			page = goForecast("http://pogoda.yandex.ru");
			assertThat("Page language", page.getLanguage(), equalTo(lang));
			LocalizedTextManager.setLanguage(lang.name());
		}
	}
	
}
