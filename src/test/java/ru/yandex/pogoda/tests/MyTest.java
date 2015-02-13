package ru.yandex.pogoda.tests;

import static ru.yandex.qatools.htmlelements.matchers.common.HasTextMatcher.*;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static ru.yandex.pogoda.wi.locale.Text.*;
import static ru.yandex.pogoda.wi.locale.Text.TEMPERATURE;

import java.text.SimpleDateFormat;
import static ru.yandex.qatools.htmlelements.matchers.TypifiedElementMatchers.*;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.yandex.pogoda.data.City;
import ru.yandex.pogoda.wi.ForecastPage;
import ru.yandex.pogoda.ws.Forecast;
//import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MyTest {
	
	Forecast forecast = City.SAINT_PETERSBURG.getForecast();
	WebDriver driver = new FirefoxDriver();
	
	@Test
	public void test() {		
//		ForecastPage page = open("https://pogoda.yandex.ru", ForecastPage.class);
		driver.get("https://pogoda.yandex.ru");
		ForecastPage page = new ForecastPage(driver);
		
		SimpleDateFormat df = new SimpleDateFormat(OBSERVATION_TIME.getValue());
	     		
		assertThat(
			page.txtObservationTime,
			hasText(df.format(forecast.getFact().getObservationTime().toGregorianCalendar().getTime()))
		);
		assertThat(
			page.txtTemperature, 
			hasText(TEMPERATURE.getValue(forecast.getFact().getTemperature().getValue()))
		);
		assertThat(
			page.txtHumidity,
			hasText(HUMIDITY.getValue(forecast.getFact().getHumidity()))
		);
		
//		mp.inpLocation.setValue("test");
//		mp.btnSearch.click();;
//		GoogleSearchPage searchPage = open("http://google.com", GoogleSearchPage.class);
//		GoogleResultsPage resultsPage = searchPage.search("selenide");
//		resultsPage.results().shouldHave(size(8));
//		resultsPage.results().get(0)
//		        .shouldHave(text("Selenide: Concise UI Tests in Java"));
	}

}