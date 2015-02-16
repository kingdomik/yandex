package ru.yandex.pogoda.tests;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static ru.yandex.pogoda.wi.locale.Text.MSG_SEARCHED;
import static ru.yandex.qatools.htmlelements.matchers.common.HasTextMatcher.hasText;
import static ru.yandex.qatools.htmlelements.matchers.common.IsElementDisplayedMatcher.isDisplayed;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.yandex.pogoda.common.Utils;
import ru.yandex.pogoda.data.City;
import ru.yandex.pogoda.wi.ForecastPage;
import ru.yandex.pogoda.wi.SearchResultsPage;

public class SearchTest {
	
	WebDriver driver;
	ForecastPage pagMain;
	SearchResultsPage pagResults;
	
	@Before
	public void setup() {
		driver = new FirefoxDriver();
		driver.get("http://pogoda.yandex.ru");
		pagMain = new ForecastPage(driver);
	}
	
	@After
	public void cleanup() {
		driver.quit();
	}
	
	@Test
	public void testSearchExactly() {
		pagMain = pagMain.go(City.SAINT_PETERSBURG.getForecast().getCity());
		assertThat(pagMain.getUrl(), equalTo(City.SAINT_PETERSBURG.getUrl()));
		
		pagMain = pagMain.go(City.MOSCOW.getForecast().getCity());
		assertThat(pagMain.getUrl(), equalTo(City.MOSCOW.getUrl()));		
	}
	
	@Test
	public void testSearchPartlySingle() {
		pagMain = pagMain.go(City.MOSCOW.getForecast().getCity());
		assertThat(pagMain.getUrl(), equalTo(City.MOSCOW.getUrl()));		

		pagMain = pagMain.go("петербург");
		assertThat(pagMain.getUrl(), equalTo(City.SAINT_PETERSBURG.getUrl()));
	}
	
	@Test
	public void testSearchPartlyMany() {
		String text = "Санкт";
		pagResults = pagMain.search(text);
		assertThat(pagResults.txtMessage.getWrappedElement(), hasText(MSG_SEARCHED.getValue(text)));
		assertThat(pagResults.lstResults, not(empty()));
		for(SearchResultsPage.Result blkResult : pagResults.lstResults) {
			City city = City.getByUrl(blkResult.lnkCity.getReference());
			// WARNING: Unfortunately code is unknown for some cities
			if (city.getId() > 0) {
				assertThat(
						blkResult.txtTemperature.getText(), 
						equalTo(Utils.temperature(city.getForecast().getFact().getTemperature().getValue())));
			}
			assertThat(blkResult.lnkCity.getText(), startsWith(text));
		}
		for(SearchResultsPage.Result blkResult : pagResults.lstResults) {
			if (City.SAINT_PETERSBURG.getName().equals(blkResult.lnkCity.getText())) {
				blkResult.lnkCity.click();
				pagMain = new ForecastPage(driver);
				assertThat(pagMain.getUrl(), equalTo(City.SAINT_PETERSBURG.getUrl()));
				return;
			}
		}
		fail(String.format("City %s not found for search request %s", City.SAINT_PETERSBURG.getName(), text));
	}
	
	@Test
	public void testSearchOtherLanguage() {
		String cityEnglish = "Saint-Petersburg";
		String cityRussian = City.SAINT_PETERSBURG.getForecast().getCity();
		pagResults = pagMain.search(cityEnglish);
		assertThat(pagResults.txtMessage.getWrappedElement(), hasText(MSG_SEARCHED.getValue(cityEnglish)));
		assertThat(pagResults.lstResults, not(empty()));
		for(SearchResultsPage.Result blkResult : pagResults.lstResults) {
			assertThat(blkResult.lnkCity.getText(), containsString("Saint Petersburg"));
		}
		for(SearchResultsPage.Result blkResult : pagResults.lstResults) {
			if (blkResult.lnkCity.getText().startsWith(cityRussian)) {
				blkResult.lnkCity.click();
				pagMain = new ForecastPage(driver);
				assertThat(pagMain.getUrl(), equalTo(City.SAINT_PETERSBURG.getUrl()));
				return;
			}
		}
		fail(String.format("City %s not found for search request %s", cityRussian, cityEnglish));
	}
	
	@Test
	public void testNotfound() {
		pagResults = pagMain.search("dummy");
		assertThat(pagResults.txtMessage.getWrappedElement(), isDisplayed());		
	}
	
}
