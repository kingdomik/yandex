package ru.yandex.pogoda.tests;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static ru.yandex.pogoda.wi.lang.LocalizedText.MSG_SEARCHED;
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

public class LanguageTest {
	
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
	}
	
}
