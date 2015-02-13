package ru.yandex.pogoda.tests;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static ru.yandex.pogoda.wi.locale.Text.HUMIDITY;
import static ru.yandex.pogoda.wi.locale.Text.OBSERVATION_TIME;
import static ru.yandex.pogoda.wi.locale.Text.TEMPERATURE;
import static ru.yandex.qatools.htmlelements.matchers.common.HasTextMatcher.hasText;
import static ru.yandex.qatools.htmlelements.matchers.common.IsElementDisplayedMatcher.isDisplayed;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.yandex.pogoda.common.Utils;
import ru.yandex.pogoda.data.City;
import ru.yandex.pogoda.data.DaysOfWeek;
import ru.yandex.pogoda.wi.BriefForecastBlock;
import ru.yandex.pogoda.wi.BriefForecastBlock.DayOfWeekForecats;
import ru.yandex.pogoda.wi.ForecastPage;
import ru.yandex.pogoda.wi.locale.Text;
import ru.yandex.pogoda.ws.Forecast;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

@RunWith(value = Parameterized.class)
public class cityTest {
	
	@Parameters(name = "{0}")
	public static Iterable<Object[]> data1() {
		return Arrays.asList(new Object[][] { 
			{ City.MOSCOW }, 
//			{ City.SAINT_PETERSBURG }, 
//			{ City.SUNNYVALE }, 
		});
	}
	
	WebDriver driver = new FirefoxDriver();
	ForecastPage page;
	Forecast wsForecast;
	City city; 
	
//	DateTimeFormat df = DateTimeFormat.forPattern("dd-MMM-yy");
	
	public cityTest(City city) {
		this.city = city;
	}
	
	@Before
	public void setup() {
		driver.get(city.getUrl().toString());
		page = new ForecastPage(driver);
		wsForecast = city.getForecast();
	}
	
	@After
	public void cleanup() {
		driver.quit();
	}
	
	private void assertValue(TypifiedElement element, String value) {
		assertThat(
			element.getWrappedElement(),
			allOf(isDisplayed(), hasText(value))
		);
	}
	
//	@Test
	public void testFact() {		
		Forecast.Fact wsFact = wsForecast.getFact();
		
		assertValue(page.txtObservationTime, Utils.formatDate(wsFact.getObservationTime().toGregorianCalendar().getTime(), OBSERVATION_TIME.getValue()));
		assertValue(page.txtTemperature, TEMPERATURE.getValue(Utils.byteToStringWithSign(wsFact.getTemperature().getValue())));
		assertValue(page.txtHumidity, HUMIDITY.getValue(wsFact.getHumidity()));
	}
	
	@Test
	public void testBrief() {
		BriefForecastBlock p = new BriefForecastBlock(driver);
		
		List<Forecast.Day> wsDays = wsForecast.getDay();
		
		// Web service delivers data for 10 days where 1 today and 9 next days
		assertThat(wsDays.size(), equalTo(BriefForecastBlock.DAYS_COUNT + 1));

		LocalDate date = LocalDate.now();
		
		for(int i = 0; i < BriefForecastBlock.DAYS_COUNT; i++) {
			// Skip today
			date = date.plusDays(1);
			Forecast.Day wsDay = wsDays.get(i + 1);
			
			int dayOfWeek = date.getDayOfWeek().getValue();
			int dayOfMonth = date.getDayOfMonth();
			
			// Web service is trusted data source but self-check would be useful anyway  
			LocalDate wsDayDate = wsDay.getDate().toGregorianCalendar().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			assertThat(wsDayDate, equalTo(date));
			
			DayOfWeekForecats day = p.days.get(i);
			
			assertValue(day.txtDayOfWeek, i == 0 ? Text.TOMORROW.getValue() : DaysOfWeek.get(dayOfWeek).getValue());
			assertValue(day.txtDayOfMonth, Utils.formatDate(date, i == 0 | dayOfWeek == 0 ? "dd month"  : "dd"));
		}
//		for(Forecast.Day wsDay : wsDays) {
////			assertThat
//			
//		}
		
//		for (DayOfWeekForecats day : p.days) { 
//			if (day.isGap()) {
//				System.out.println("gap");
//			} else {
//				assertValue()
//				assertThat()
//				System.out.println(day.txtDayOfWeek.getText() + " " + day.txtDayOfMonth.getText() + " " + day.txtDayTemperature.getText() + " " + day.txtNightTemperature.getText() + " " + day.txtState.getText());
//			}
//		}
	}

}