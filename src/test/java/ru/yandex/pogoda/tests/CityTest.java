package ru.yandex.pogoda.tests;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;
import static ru.yandex.pogoda.common.Matchers.equalWithDelltaTo;
import static ru.yandex.pogoda.wi.locale.Text.DETAILED_HUMIDITY;
import static ru.yandex.pogoda.wi.locale.Text.DETAILED_SUNRISE;
import static ru.yandex.pogoda.wi.locale.Text.DETAILED_SUNSET;
import static ru.yandex.pogoda.wi.locale.Text.DETAILED_TEMPERATURE;
import static ru.yandex.pogoda.wi.locale.Text.GEOMAGNETIC;
import static ru.yandex.pogoda.wi.locale.Text.HUMIDITY;
import static ru.yandex.pogoda.wi.locale.Text.LOCATION;
import static ru.yandex.pogoda.wi.locale.Text.OBSERVATION_TIME;
import static ru.yandex.pogoda.wi.locale.Text.PRESSURE;
import static ru.yandex.pogoda.wi.locale.Text.SUNRISE_SUNSET;
import static ru.yandex.pogoda.wi.locale.Text.TAB_BRIEF;
import static ru.yandex.pogoda.wi.locale.Text.TAB_CLIMATE;
import static ru.yandex.pogoda.wi.locale.Text.TAB_DETAILED;
import static ru.yandex.pogoda.wi.locale.Text.TEMPERATURE;
import static ru.yandex.pogoda.wi.locale.Text.TEMPERATURE_CELSIUS;
import static ru.yandex.pogoda.wi.locale.Text.TEMPERATURE_DAY;
import static ru.yandex.pogoda.wi.locale.Text.TEMPERATURE_NIGHT;
import static ru.yandex.pogoda.wi.locale.Text.TEMPERATURE_YESTERDAY;
import static ru.yandex.pogoda.wi.locale.Text.WIND;
import static ru.yandex.pogoda.wi.locale.Text.WIND_SPEED;
import static ru.yandex.qatools.htmlelements.matchers.common.HasClassMatcher.hasClass;
import static ru.yandex.qatools.htmlelements.matchers.common.HasCssMatcher.hasCss;
import static ru.yandex.qatools.htmlelements.matchers.common.HasTextMatcher.hasText;
import static ru.yandex.qatools.htmlelements.matchers.common.IsElementDisplayedMatcher.isDisplayed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
import ru.yandex.pogoda.data.Diagram;
import ru.yandex.pogoda.data.Geomagnetic;
import ru.yandex.pogoda.data.Month;
import ru.yandex.pogoda.data.WindDirection;
import ru.yandex.pogoda.wi.BriefForecastBlock;
import ru.yandex.pogoda.wi.BriefForecastBlock.DayOfWeekForecats;
import ru.yandex.pogoda.wi.ClimateForecastBlock;
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
		// FIXME test after day part
		Forecast.Fact wsFact = wsForecast.getFact();
		
		String location = LOCATION.getValue(city.getGenetive());
		String temperature = TEMPERATURE_CELSIUS.getValue(Utils.byteToStringWithSign(wsFact.getTemperature().getValue()));
		String condition = wsFact.getWeatherType();
		String iconUrl = String.format(ICON_URL, wsFact.getImageV3().getValue());
		String yesterday = TEMPERATURE_YESTERDAY.getValue(Utils.byteToStringWithSign(wsForecast.getYesterday().getTemperature().getValue()));
		String sunriseAndSunset = SUNRISE_SUNSET.getValue(wsForecast.getDay().get(0).getSunrise(), wsForecast.getDay().get(0).getSunset());
		String wind = WIND.getValue(
				Utils.formatFloat(wsFact.getWindSpeed(), WIND_SPEED.getValue()),
				WindDirection.get(wsFact.getWindDirection()).getValue());
		String windDirectionClass = "icon_wind_" + wsFact.getWindDirection();
		
		LocalTime localTime = LocalTime.parse(page.txtLocalTime.getText(), DateTimeFormatter.ofPattern("HH:mm"));
		
		assertElement(page.txtLocation, hasText(location));
		assertThat(page.txtLocalTime.getWrappedElement(), isDisplayed());
		assertThat(localTime, equalWithDelltaTo(LocalTime.now(), ChronoUnit.MINUTES, 1));
		assertElement(page.txtTemperature, hasText(temperature));
		assertElement(page.txtCondition, hasText(condition));
		assertElement(page.imgCondition, hasCss("background-image", iconUrl));
		assertElement(page.txtYesterdayTemperature, hasText(yesterday));
		assertElement(page.txtSunriseSunset, hasText(sunriseAndSunset));
		assertElement(page.txtWind, hasText(wind));
		assertElement(page.imgWindDirection, hasClass(windDirectionClass));
		assertElement(page.txtHumidity, hasText(HUMIDITY.getValue(wsFact.getHumidity())));
		assertElement(page.txtPressure, hasText(PRESSURE.getValue(wsFact.getPressure().getValue())));
		assertElement(
				page.txtObservationTime, 
				hasText(Utils.formatDate(wsFact.getObservationTime(), OBSERVATION_TIME.getValue())));
		
		assertElement(page.tabBrief, hasText(TAB_BRIEF.getValue()));
		assertElement(page.tabDetailed, hasText(TAB_DETAILED.getValue()));
		assertElement(page.tabClimate, hasText(TAB_CLIMATE.getValue()));
	}
	
	@Test
	public void testBrief() {
		BriefForecastBlock pagBrief = new BriefForecastBlock(driver);
		
		// Web service delivers data for 10 days where 1 today and 9 next days
		assertThat(wsDays.size(), equalTo(ForecastPage.DAYS_COUNT));

		LocalDate date = LocalDate.now();
		
		for(int i = 0; i < ForecastPage.DAYS_COUNT - 1; i++) {
			// Skip today data
			date = date.plusDays(1);
			Forecast.Day wsDay = wsDays.get(i + 1);
			DayOfWeekForecats day = pagBrief.days.get(i);
			
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
		assertThat(wsDays.size(), equalTo(ForecastPage.DAYS_COUNT));

		// FIXME set DAYS_COUNT to 10
		assertThat(pagDetailed.daysDates.size(), equalTo(ForecastPage.DAYS_COUNT));
		assertThat(pagDetailed.daysForecast.size(), equalTo(ForecastPage.DAYS_COUNT));
		
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

			assertElement(wiDateDay.txtDayOfWeek, hasText(dayOfWeek));
			assertElement(wiDateDay.txtDayOfMonth, hasText(dayOfMonth));

			assertElement(wiForecatsDay.txtSunrise, hasText(sunrise));
			assertElement(wiForecatsDay.txtSunset, hasText(sunset));
			assertElement(wiForecatsDay.imgMoon, hasClass(moonClass));
			
			Forecast.Day.Biomet wsBiomet = wsDay.getBiomet();
			if (wsBiomet == null) {
				assertThat(wiForecatsDay.txtGeomagnetic.getText().trim(), isEmptyString());
			} else {
				assertElement(
						wiForecatsDay.txtGeomagnetic, 
						hasText(GEOMAGNETIC.getValue(Geomagnetic.get(wsBiomet.getMessage().get(1).getCode()).getValue())));
			}
			// FIXME check part size
			for(int j = 0; j < 4; j++) {
				Forecast.Day.DayPart wsDayPart = wsDay.getDayPart().get(j);
				DayPartBlock wiDayPart = wiForecatsDay.dayParts.get(j);
				String temperature = getDetailedTemperature(wsDayPart);
				String iconUrl = String.format(ICON_URL, wsDayPart.getImageV3().getValue());
				String pressure = "" + wsDayPart.getPressure().getValue();
				String humidity = DETAILED_HUMIDITY.getValue(wsDayPart.getHumidity());
				String windSpeed = Utils.formatFloat(wsDayPart.getWindSpeed(), WIND_SPEED.getValue());
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
	
	@Test
	public void testClimate() {
		page.tabClimate.click();
		ClimateForecastBlock pagClimate = new ClimateForecastBlock(driver);
		
		assertThat(pagClimate.lstGraphs.size(), equalTo(ClimateForecastBlock.GRAPH_COUNT));
		
		for(int i = 0; i < ClimateForecastBlock.GRAPH_COUNT; i++) {
			Diagram diagram = Diagram.values()[i];
			ClimateForecastBlock.Diagram blkDiagram = pagClimate.lstGraphs.get(i);
			assertThat(blkDiagram.txtTitle.getText(), equalTo(diagram.getTitle()));
			assertThat(blkDiagram.imgGraph.getSource(), equalTo(diagram.getUrl(wsForecast.getGeoid()).toString()));
			
			for(int j = 0; j < Month.values().length; j++) {
				assertThat(blkDiagram.lstMonths.get(j).getText(), equalTo(Month.values()[j].getValue()));
			}
		}		
	}
	
}
