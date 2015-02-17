package ru.yandex.pogoda.tests;

import static ru.yandex.common.matchers.Matchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static ru.yandex.pogoda.wi.lang.LocalizedText.MSG_NOT_FOUND;
import static ru.yandex.pogoda.wi.lang.LocalizedText.MSG_SEARCHED;
import static ru.yandex.qatools.htmlelements.matchers.common.HasTextMatcher.hasText;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.yandex.common.Utils;
import ru.yandex.common.wi.Browser;
import ru.yandex.pogoda.data.City;
import ru.yandex.pogoda.wi.CityForecastPage;
import ru.yandex.pogoda.wi.SearchResultsPage;

public class SearchTest {
	
	static Browser browser;
	
	CityForecastPage pagMain;
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
		pagMain = browser.goForecast(CityForecastPage.URL);
	}
	
	/**
	 * Test search results contain requested string of original language
	 * and one of known results redirects to searched city page    
	 */
	@Test
	public void testSearchPartlyMany() {
		String text = "Санкт";
		pagResults = pagMain.search(text);
		assertThat(
			pagResults.txtMessage.getWrappedElement(), 
			hasText(MSG_SEARCHED.getValue(text)));
		assertThat(
			pagResults.lstResults, 
			not(empty()));
		
		for(SearchResultsPage.Result blkResult : pagResults.lstResults) {
			City city = City.getByUrl(blkResult.lnkCity.getReference());
			// FIXME Unfortunately code is unknown for some cities
			// so web service info is unavailable. Need extra data source to fix
			if (city.getId() > 0) {
				assertThat(
					blkResult.txtTemperature.getText(), 
					equalTo(Utils.temperature(city.getForecast().getFact().getTemperature().getValue())));
			}
			assertThat(
				blkResult.lnkCity.getText(), 
				containsIgnoreCase(text));
		}
		
		for(SearchResultsPage.Result blkResult : pagResults.lstResults) {
			if (City.SAINT_PETERSBURG.getName().equals(blkResult.lnkCity.getText())) {
				blkResult.lnkCity.click();
				pagMain = browser.getForecastPage();
				assertThat(
					pagMain.getUrl(), 
					equalTo(City.SAINT_PETERSBURG.getUrl()));
				return;
			}
		}
		
		fail(String.format("City %s not found for search request %s", City.SAINT_PETERSBURG.getName(), text));
	}

	/**
	 * Test search results contain requested string of other language 
	 * and one of known results redirects to searched city page    
	 */
	@Test
	public void testSearchOtherLanguage() {
		String cityEnglish = "Saint-Petersburg";
		String cityRussian = "Санкт-Петербург";
		pagResults = pagMain.search(cityEnglish);
		assertThat(
			pagResults.txtMessage.getWrappedElement(), 
			hasText(MSG_SEARCHED.getValue(cityEnglish)));
		assertThat(
			pagResults.lstResults, 
			not(empty()));
		
		for(SearchResultsPage.Result blkResult : pagResults.lstResults) {
			assertThat(
				blkResult.lnkCity.getText(), 
				containsString("Saint Petersburg"));
		}
		
		for(SearchResultsPage.Result blkResult : pagResults.lstResults) {
			if (blkResult.lnkCity.getText().startsWith(cityRussian)) {
				blkResult.lnkCity.click();
				pagMain = browser.getForecastPage();
				assertThat(
					pagMain.getUrl(), 
					equalTo(City.SAINT_PETERSBURG.getUrl()));
				return;
			}
		}
		
		fail(String.format("City %s not found for search request %s", cityRussian, cityEnglish));
	}
	
	/**
	 * Test get correct error message for unknown city name
	 */
	@Test
	public void testNotfound() {
		pagResults = pagMain.search("dummy");
		assertThat(
			pagResults.txtMessage.getWrappedElement(), 
			hasText(MSG_NOT_FOUND.getValue()));		
	}
	
}
