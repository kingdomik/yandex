package ru.yandex.pogoda.tests;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static ru.yandex.pogoda.common.Messages.FAIL_CITY_NOT_FOUND;
import static ru.yandex.pogoda.wi.lang.LocalizedText.MSG_SEARCHED;
import static ru.yandex.qatools.htmlelements.matchers.common.HasTextMatcher.hasText;
import static ru.yandex.qatools.htmlelements.matchers.common.IsElementDisplayedMatcher.isDisplayed;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.yandex.common.Utils;
import ru.yandex.common.wi.Browser;
import ru.yandex.pogoda.data.City;
import ru.yandex.pogoda.wi.ForecastPage;
import ru.yandex.pogoda.wi.SearchResultsPage;

public class SearchTest {
	
	static Browser browser;
	
	ForecastPage pagMain;
	SearchResultsPage pagResults;
	
	@BeforeClass
	public static void opneBrowser() {
		browser = new Browser();
	}
	
	@AfterClass
	public static void closeBrowser() {
		browser.close();
	}
	
	@Before
	public void setup() {
		pagMain = browser.goForecast("http://pogoda.yandex.ru");
	}
	
	@Test
	public void testSearchExactly() {
		pagMain = pagMain.goForecast(City.SAINT_PETERSBURG.getName());
		assertThat(pagMain.getUrl(), equalTo(City.SAINT_PETERSBURG.getUrl()));
		
		pagMain = pagMain.goForecast(City.MOSCOW.getName());
		assertThat(pagMain.getUrl(), equalTo(City.MOSCOW.getUrl()));		
	}
	
	@Test
	public void testSearchPartlySingle() {
		pagMain = pagMain.goForecast(City.MOSCOW.getName());
		assertThat(pagMain.getUrl(), equalTo(City.MOSCOW.getUrl()));		

		pagMain = pagMain.goForecast("петербург");
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
			// so web service info is unavailable. Need extra data source to fix
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
				pagMain = browser.getForecastPage();
				assertThat(pagMain.getUrl(), equalTo(City.SAINT_PETERSBURG.getUrl()));
				return;
			}
		}
		
		fail(FAIL_CITY_NOT_FOUND.getValue(City.SAINT_PETERSBURG.getName(), text));
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
				pagMain = browser.getForecastPage();
				assertThat(pagMain.getUrl(), equalTo(City.SAINT_PETERSBURG.getUrl()));
				return;
			}
		}
		
		fail(FAIL_CITY_NOT_FOUND.getValue(cityRussian, cityEnglish));
	}
	
	@Test
	public void testNotfound() {
		pagResults = pagMain.search("dummy");
		assertThat(pagResults.txtMessage.getWrappedElement(), isDisplayed());		
	}
	
}
