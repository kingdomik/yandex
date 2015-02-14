package ru.yandex.pogoda.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static ru.yandex.pogoda.wi.locale.Text.*;
import static ru.yandex.qatools.htmlelements.matchers.common.IsElementDisplayedMatcher.*;
import static ru.yandex.qatools.htmlelements.matchers.common.HasCssMatcher.hasCss;
import static ru.yandex.qatools.htmlelements.matchers.common.HasTextMatcher.hasText;
import static ru.yandex.qatools.htmlelements.matchers.common.HasClassMatcher.*;
import static ru.yandex.qatools.htmlelements.matchers.common.IsElementDisplayedMatcher.isDisplayed;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.yandex.pogoda.common.Utils;
import ru.yandex.pogoda.data.City;
import ru.yandex.pogoda.data.DaysOfWeek;
import ru.yandex.pogoda.data.Geomagnetic;
import ru.yandex.pogoda.data.WindDirection;
import ru.yandex.pogoda.wi.BriefForecastBlock;
import ru.yandex.pogoda.wi.BriefForecastBlock.DayOfWeekForecats;
import ru.yandex.pogoda.wi.DetailedForecastBlock;
import ru.yandex.pogoda.wi.DetailedForecastBlock.DateDayBlock;
import ru.yandex.pogoda.wi.DetailedForecastBlock.DayPartBlock;
import ru.yandex.pogoda.wi.DetailedForecastBlock.ForecastDayBlock;
import ru.yandex.pogoda.wi.ForecastPage;
import ru.yandex.pogoda.wi.locale.Text;
import ru.yandex.pogoda.ws.Forecast;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

@RunWith(value = Parameterized.class)
public class CityTest {
	
	static final String ICON_URL = "url(\"https://yastatic.net/weather/i/icons/svg/%s.svg\")";
	
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
	List<Forecast.Day> wsDays;
	City city; 
	
//	DateTimeFormat df = DateTimeFormat.forPattern("dd-MMM-yy");
	
	public CityTest(City city) {
		this.city = city;
	}
	
	@Before
	public void setup() {
		driver.get(city.getUrl().toString());
		page = new ForecastPage(driver);
		wsForecast = city.getForecast();
		wsDays = wsForecast.getDay();
	}
	
	@After
	public void cleanup() {
		driver.quit();
	}
	
	void assertElement(TypifiedElement element, Matcher<WebElement> matcher) {
		assertThat(
			element.getWrappedElement(),
			allOf(isDisplayed(), matcher)
		);
	}
	
	String getTemperature(Forecast.Day.DayPart dayPart, boolean day) {
		return Utils.byteToStringWithSign( 
			dayPart.getTemperatureFrom() == 0 & dayPart.getTemperatureTo() == 0
			? dayPart.getTemperature()
			: (byte)(
				day
					? Math.max(dayPart.getTemperatureFrom(), dayPart.getTemperatureTo())
					: Math.min(dayPart.getTemperatureFrom(), dayPart.getTemperatureTo())
			)
		);
	}
	
	String getDetailedTemperature(Forecast.Day.DayPart dayPart) {
		return  
			dayPart.getTemperatureFrom() == 0 & dayPart.getTemperatureTo() == 0
			? Utils.byteToStringWithSign(dayPart.getTemperature())
			: DETAILED_TEMPERATURE.getValue(
					Utils.byteToStringWithSign(dayPart.getTemperatureFrom()), 
					Utils.byteToStringWithSign(dayPart.getTemperatureTo()));
	}
	
	@Test
	public void testFact() {		
		Forecast.Fact wsFact = wsForecast.getFact();
		
		String observation = Utils.formatDate(wsFact.getObservationTime().toGregorianCalendar().getTime(), OBSERVATION_TIME.getValue());
		String temperature = TEMPERATURE_CELSIUS.getValue(Utils.byteToStringWithSign(wsFact.getTemperature().getValue()));
		String humidity = HUMIDITY.getValue(wsFact.getHumidity());
		
		assertElement(page.txtObservationTime, hasText(observation));
		assertElement(page.txtTemperature, hasText(temperature));
		assertElement(page.txtHumidity, hasText(humidity));
	}
	
	@Test
	public void testBrief() {
		BriefForecastBlock pagBrief = new BriefForecastBlock(driver);
		
		// Web service delivers data for 10 days where 1 today and 9 next days
		assertThat(wsDays.size(), equalTo(ForecastPage.DAYS_COUNT + 1));

		LocalDate date = LocalDate.now();
		int gaps = 0;
		
		for(int i = 0; i < ForecastPage.DAYS_COUNT; i++) {
			// Skip today data
			date = date.plusDays(1);
			Forecast.Day wsDay = wsDays.get(i + 1);
			
			int dow = date.getDayOfWeek().getValue();
			String dayOfWeek = 
				i == 0 
					? Text.TOMORROW.getValue() 
					: DaysOfWeek.get(dow).getValue();
			String dayOfMonth = Utils.formatDate(
				date, 
				i == 0 | dow == 1 
					? "d MMMM"  
					: "d");
			Forecast.Day.DayPart wsDayPart = wsDay.getDayPart().get(1); 
			Forecast.Day.DayPart wsNightPart = wsDay.getDayPart().get(3);
			String dayTemperature = (
				i == 0 
					? TEMPERATURE_DAY 
					: TEMPERATURE
				).getValue(getTemperature(wsDayPart, true));
			String nightTemperature = (
				i == 0 
					? TEMPERATURE_NIGHT 
					: TEMPERATURE
				).getValue(getTemperature(wsNightPart, false));
			String iconUrl = String.format(ICON_URL, wsDayPart.getImageV3().getValue());
			
			// Web service is trusted data source but self-check would be useful anyway  
			assertThat(wsDay.getDate().toString(), equalTo(date.toString()));
			assertThat(wsDayPart.getType(), equalTo("day"));
			assertThat(wsNightPart.getType(), equalTo("night"));
			
			DayOfWeekForecats day = pagBrief.days.get(i + gaps);
			
			if (i > 0 & dow == 1) {
				assertTrue("Expected gap between weeks", day.isGap());
				day = pagBrief.days.get(i + ++gaps);
			} else {
				assertFalse("Unexpected gap before " + date, day.isGap());
			}
			
			assertElement(day.txtDayOfWeek, hasText(dayOfWeek));
			assertElement(day.txtDayOfMonth, hasText(dayOfMonth));
			assertElement(day.imgDayStateIcon, hasCss("background-image", iconUrl));
			assertElement(day.txtDayState, hasText(wsDayPart.getWeatherType()));
			assertElement(day.txtDayTemperature, hasText(dayTemperature));
			assertElement(day.txtNightTemperature, hasText(nightTemperature));
		}
		
	}

	@Test
	public void testDetailed() {
		page.tabDetailed.click();
		DetailedForecastBlock pagDetailed = new DetailedForecastBlock(driver);
		
		// Web service delivers data for 10 days where 1 today and 9 next days
		assertThat(wsDays.size(), equalTo(ForecastPage.DAYS_COUNT + 1));

		// FIXME set DAYS_COUNT to 10
		assertThat(pagDetailed.daysDates.size(), equalTo(ForecastPage.DAYS_COUNT + 1));
		assertThat(pagDetailed.daysForecast.size(), equalTo(ForecastPage.DAYS_COUNT + 1));
		
		LocalDate date = LocalDate.now();
		// FIXME check daysDates size
		for(int i = 0; i < ForecastPage.DAYS_COUNT; i++) {
			Forecast.Day wsDay = wsDays.get(i);
			DateDayBlock wiDateDay = pagDetailed.daysDates.get(i);
			ForecastDayBlock wiForecatsDay = pagDetailed.daysForecast.get(i);
			
			// Web service is trusted data source but self-check would be useful anyway  
			assertThat(wsDay.getDate().toString(), equalTo(date.toString()));
			assertThat(wsDay.getDayPart().get(0).getType(), equalTo("morning"));
			assertThat(wsDay.getDayPart().get(1).getType(), equalTo("day"));
			assertThat(wsDay.getDayPart().get(2).getType(), equalTo("evening"));
			assertThat(wsDay.getDayPart().get(3).getType(), equalTo("night"));

			int dow = date.getDayOfWeek().getValue();
			String dayOfWeek = DaysOfWeek.get(dow).getValue();
			String dayOfMonth = Utils.formatDate(date, "d\nMMMM");
			String sunrise = DETAILED_SUNRISE.getValue(wsDay.getSunrise());
			String sunset = DETAILED_SUNSET.getValue(wsDay.getSunset());
			String moonClass = "icon_moon_" + wsDay.getMoonPhase().getValue();
//			String geomagnetic = GEOMAGNETIC.getValue(Geomagnetic.get(wsDay.getBiomet().getMessage().get(1).getCode()).getValue());

			assertElement(wiDateDay.txtDayOfWeek, hasText(dayOfWeek));
			assertElement(wiDateDay.txtDayOfMonth, hasText(dayOfMonth));

			assertElement(wiForecatsDay.txtSunrise, hasText(sunrise));
			assertElement(wiForecatsDay.txtSunset, hasText(sunset));
			assertElement(wiForecatsDay.imgMoon, hasClass(moonClass));
			
			if (i == 0) {
//				assertElement(wiForecatsDay.txtGeomagnetic, hasText(geomagnetic));
			} else {
//				assertThat(wiForecatsDay.txtGeomagnetic.getText(), isEmptyString());
			}
			// FIXME check part size
			for(int j = 0; j < 4; j++) {
				Forecast.Day.DayPart wsDayPart = wsDay.getDayPart().get(j);
				DayPartBlock wiDayPart = wiForecatsDay.dayParts.get(j);
				String temperature = getDetailedTemperature(wsDayPart);
				String iconUrl = String.format(ICON_URL, wsDayPart.getImageV3().getValue());
				String pressure = "" + wsDayPart.getPressure().getValue();
				String humidity = DETAILED_HUMIDITY.getValue(wsDayPart.getHumidity());
				String windSpeed = Utils.formatFloat(wsDayPart.getWindSpeed(), DETAILED_WIND_SPEED.getValue());
				String windDirection = WindDirection.get(wsDayPart.getWindDirection()).getValue();
				String windDirectionClass = "icon_wind_" + wsDayPart.getWindDirection();
				
				assertElement(wiDayPart.txtTemperature, hasText(temperature));
				assertElement(wiDayPart.imgCondition, hasCss("background-image", iconUrl));
				assertElement(wiDayPart.txtPressure, hasText(pressure));
				assertElement(wiDayPart.txtHumidity, hasText(humidity));
				assertElement(wiDayPart.txtWindSpeed, hasText(windSpeed));
				assertElement(wiDayPart.txtWindDirection, hasText(windDirection));
				assertElement(wiDayPart.imgWindDirection, hasClass(windDirectionClass));
			}
			
			date = date.plusDays(1);
		}
				
	}
	
}