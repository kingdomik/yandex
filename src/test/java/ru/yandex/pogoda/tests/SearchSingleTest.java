package ru.yandex.pogoda.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ru.yandex.common.wi.Browser;
import ru.yandex.pogoda.data.City;
import ru.yandex.pogoda.wi.CityForecastPage;

@RunWith(value = Parameterized.class)
public class SearchSingleTest {

	@Parameters(name = "{0}")
	public static Iterable<Object[]> data1() {
		return Arrays.asList(new Object[][] { 
			{ "testExacty", 		"Санкт-Петербург",		City.SAINT_PETERSBURG }, 
			{ "testLowerCase", 		"санкт-петербург",		City.SAINT_PETERSBURG }, 
			{ "testUpperCase", 		"САНКТ-ПЕТЕРБУРГ",		City.SAINT_PETERSBURG }, 
			{ "testPartly", 		"петербург",			City.SAINT_PETERSBURG }, 
			{ "testWrongLanguage",	"cfyrn-gtnth,ehu",		City.SAINT_PETERSBURG }, 
			{ "testLangEnglish",	"Murmansk",				City.MURMANSK },
			// and other languages
		});
	}
	
	static Browser browser;
	
	CityForecastPage pagMain;
	
	String request;
	City city;
	
	public SearchSingleTest(String testName, String request, City city) {
		this.request = request;
		this.city = city;
	}
	
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
		pagMain = browser.goForecast(City.MOSCOW.getUrl());
	}
	
	@Test
	public void testSearch() {
		pagMain = pagMain.goForecast(request);
		assertThat(
			pagMain.getUrl(), 
			equalTo(city.getUrl()));		
	}
	
}
