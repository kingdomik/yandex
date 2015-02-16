package ru.yandex.pogoda.tests;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static ru.yandex.pogoda.common.Matchers.equalWithDelltaTo;
import static ru.yandex.pogoda.wi.lang.LocalizedText.DETAILED_HUMIDITY;
import static ru.yandex.pogoda.wi.lang.LocalizedText.DETAILED_SUNRISE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.*;
import static ru.yandex.pogoda.wi.lang.LocalizedText.DETAILED_TEMPERATURE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.GEOMAGNETIC;
import static ru.yandex.pogoda.wi.lang.LocalizedText.HUMIDITY;
import static ru.yandex.pogoda.wi.lang.LocalizedText.LOCATION;
import static ru.yandex.pogoda.wi.lang.LocalizedText.OBSERVATION_TIME;
import static ru.yandex.pogoda.wi.lang.LocalizedText.PRESSURE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.SUNRISE_SUNSET;
import static ru.yandex.pogoda.wi.lang.LocalizedText.TEMPERATURE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.TEMPERATURE_CELSIUS;
import static ru.yandex.pogoda.wi.lang.LocalizedText.BRIEF_TEMPERATURE_DAY;
import static ru.yandex.pogoda.wi.lang.LocalizedText.BRIEF_TEMPERATURE_NIGHT;
import static ru.yandex.pogoda.wi.lang.LocalizedText.TEMPERATURE_YESTERDAY;
import static ru.yandex.pogoda.wi.lang.LocalizedText.WATER_TEMPERATURE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.WIND;
import static ru.yandex.pogoda.wi.lang.LocalizedText.WIND_CALM;
import static ru.yandex.pogoda.wi.lang.LocalizedText.WIND_SPEED;
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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import ru.yandex.common.wi.Browser;
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
import ru.yandex.pogoda.wi.lang.Language;
import ru.yandex.pogoda.wi.lang.LocalizedText;
import ru.yandex.pogoda.ws.Forecast;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

@RunWith(value = Parameterized.class)
public class ForecastTest {
	
	static final String ICON_URL = "url(\"https://yastatic.net/weather/i/icons/svg/%s.svg\")";
			
	@Parameters(name = "{0}")
	public static Iterable<Object[]> data1() {
		return Arrays.asList(new Object[][] { 
//			{ City.MOSCOW }, 
			{ City.LOS_ANGELES, Language.UK }, 
//			{ City.SAINT_PETERSBURG }, 
//			{ City.SUNNYVALE }, 
		});
	}
	
	Browser browser = new Browser();
	ForecastPage page;
	Forecast wsForecast;
	List<Forecast.Day> wsDays;
	City city; 
	Language lang;
	
	public ForecastTest(City city, Language lang) {
		this.city = city;
		this.lang = lang;
	}
	
	@Before
	public void setup() {
		browser.setLanguage(lang);
		page = browser.goForecast(city.getUrl().toString());
		wsForecast = city.getForecast();
		wsDays = wsForecast.getDay();
	}
	
	@After
	public void cleanup() {
		browser.close();
	}
	
	void assertElement(TypifiedElement element, Matcher<WebElement> matcher) {
		assertThat(
			element.getWrappedElement(),
			allOf(isDisplayed(), matcher)
		);
	}
	
	String getTemperature(Forecast.Day.DayPart dayPart, boolean day) {
		return Utils.temperature( 
			dayPart.getTemperatureFrom() == null & dayPart.getTemperatureTo() == null
			? Byte.parseByte(dayPart.getTemperature())
			: (byte)(
					day
					? Math.max(
						Byte.parseByte(dayPart.getTemperatureFrom()), 
						Byte.parseByte(dayPart.getTemperatureTo()))
					: Math.min(
						Byte.parseByte(dayPart.getTemperatureFrom()), 
						Byte.parseByte(dayPart.getTemperatureTo()))
			)
		);
	}
	
	String getDetailedTemperature(Forecast.Day.DayPart dayPart) {
		return  
			dayPart.getTemperatureFrom() == null & dayPart.getTemperatureTo() == null
			? Utils.temperature(dayPart.getTemperature())
			: DETAILED_TEMPERATURE.getValue(
					Utils.temperature(dayPart.getTemperatureFrom()), 
					Utils.temperature(dayPart.getTemperatureTo()));
	}
	
	@Test
	public void testFact() {
		// FIXME test after day part
		Forecast.Fact wsFact = wsForecast.getFact();
		
		String location = LOCATION.getValue(city.getGenetive());
		String temperature = TEMPERATURE_CELSIUS.getValue(Utils.temperature(wsFact.getTemperature().getValue()));
		String condition = wsFact.getWeatherType();
		String iconUrl = String.format(ICON_URL, wsFact.getImageV3().getValue());
		String yesterday = TEMPERATURE_YESTERDAY.getValue(Utils.temperature(wsForecast.getYesterday().getTemperature().getValue()));
		String sunriseAndSunset = SUNRISE_SUNSET.getValue(wsForecast.getDay().get(0).getSunrise(), wsForecast.getDay().get(0).getSunset());
		
		LocalTime localTime = LocalTime.parse(page.txtLocalTime.getText(), DateTimeFormatter.ofPattern("HH:mm"));

		assertElement(page.txtLocation, hasText(location));
		assertThat(page.txtLocalTime.getWrappedElement(), isDisplayed());
		assertThat(localTime, equalWithDelltaTo(LocalTime.now(city.getTimezone()), ChronoUnit.MINUTES, 1));
		assertElement(page.txtTemperature, hasText(temperature));
		assertElement(page.txtCondition, hasText(condition));
		assertElement(page.imgCondition, hasCss("background-image", iconUrl));
		assertElement(page.txtYesterdayTemperature, hasText(yesterday));
		if (wsFact.getWaterTemperature() != null) {
			assertElement(
					page.txtWaterTemperature, 
					hasText(WATER_TEMPERATURE.getValue(Utils.temperature(wsFact.getWaterTemperature()))));
		}
		assertElement(page.txtSunriseSunset, hasText(sunriseAndSunset));
		String wind;
		WindDirection windDirection = WindDirection.get(wsFact.getWindDirection());
		if (WindDirection.CALM.equals(windDirection)) {
			wind = WIND_CALM.getValue();
		} else {
			wind = WIND.getValue(
					Utils.formatFloat(Float.parseFloat(wsFact.getWindSpeed()), WIND_SPEED.getValue()),
					windDirection.getValue());
			String windDirectionClass = "icon_wind_" + wsFact.getWindDirection();
			assertElement(page.imgWindDirection, hasClass(windDirectionClass));
		}
		assertElement(page.txtWind, hasText(wind));
		assertElement(page.txtHumidity, hasText(HUMIDITY.getValue(wsFact.getHumidity())));
		assertElement(page.txtPressure, hasText(PRESSURE.getValue(wsFact.getPressure().getValue())));
		assertElement(
				page.txtObservationTime, 
				hasText(Utils.formatXmlDate(wsFact.getObservationTime(), OBSERVATION_TIME.getValue())));
		
//		assertElement(page.tabBrief, hasText(TAB_BRIEF.getValue()));
//		assertElement(page.tabDetailed, hasText(TAB_DETAILED.getValue()));
//		assertElement(page.tabClimate, hasText(TAB_CLIMATE.getValue()));
	}
	
	@Test
	public void testBrief() {
		BriefForecastBlock pagBrief = page.goBriefForecastBlock();
		
		// Web service delivers data for 10 days where 1 today and 9 next days
		assertThat(wsDays.size(), equalTo(ForecastPage.DAYS_COUNT));

		LocalDate date = LocalDate.now(city.getTimezone());
		LocalTime time = LocalTime.now(city.getTimezone());
		
		int dayShift = 0;
		if (time.getHour() > 9) {
			date = date.plusDays(1);
			dayShift = 1;
		}
		
		for(int i = 0; i < ForecastPage.DAYS_COUNT - 1; i++) {
			Forecast.Day wsDay = wsDays.get(i + dayShift);
			DayOfWeekForecats day = pagBrief.days.get(i);
			
			int dow = date.getDayOfWeek().getValue();
			String dayOfWeek = 
				i == 0 
					? (dayShift == 0 
						? LocalizedText.TODAY.getValue()
						: LocalizedText.TOMORROW.getValue())
					: DaysOfWeek.get(dow).getValue();
			String dayOfMonth = 
				i == 0 | dow == 1 
					? BRIEF_DATE.getValue(date.getDayOfMonth(), Month.get(date.getMonthValue()).getValue())  
					: "" + date.getDayOfMonth();
			Forecast.Day.DayPart wsDayPart = wsDay.getDayPart().get(1); 
			Forecast.Day.DayPart wsNightPart = wsDay.getDayPart().get(3);
			String dayTemperature = (
				i == 0 
					? BRIEF_TEMPERATURE_DAY 
					: TEMPERATURE
				).getValue(getTemperature(wsDayPart, true));
			String nightTemperature = (
				i == 0 
					? BRIEF_TEMPERATURE_NIGHT 
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
			
			date = date.plusDays(1);			
		}
		
	}

	@Test
	public void testDetailed() {
		DetailedForecastBlock pagDetailed = page.goDetailedForecastBlock();
		
		// Web service delivers data for 10 days where 1 today and 9 next days
		assertThat(wsDays.size(), equalTo(ForecastPage.DAYS_COUNT));

		// FIXME set DAYS_COUNT to 10
		assertThat(pagDetailed.daysDates.size(), equalTo(ForecastPage.DAYS_COUNT));
		assertThat(pagDetailed.daysForecast.size(), equalTo(ForecastPage.DAYS_COUNT));
		
		LocalDate date = LocalDate.now(city.getTimezone());
		// FIXME check daysDates size
		for(int i = 0; i < ForecastPage.DAYS_COUNT; i++) {
			Forecast.Day wsDay = wsDays.get(i);
			DateDayBlock wiDateDay = pagDetailed.daysDates.get(i);
			ForecastDayBlock wiForecatsDay = pagDetailed.daysForecast.get(i);
			
			// Web service is trusted data source but self-check would be useful anyway  
			assertThat(wsDay.getDate(), equalTo(date.toString()));
			assertThat(wsDay.getDayPart().get(0).getType(), equalTo("morning"));
			assertThat(wsDay.getDayPart().get(1).getType(), equalTo("day"));
			assertThat(wsDay.getDayPart().get(2).getType(), equalTo("evening"));
			assertThat(wsDay.getDayPart().get(3).getType(), equalTo("night"));

			int dow = date.getDayOfWeek().getValue();
			String dayOfWeek = DaysOfWeek.get(dow).getValue();
			String dayOfMonth = DETAILED_DATE.getValue(date.getDayOfMonth(), Month.get(date.getMonthValue()).getValue());
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
				String windSpeed = Utils.formatFloat(Float.parseFloat(wsDayPart.getWindSpeed()), WIND_SPEED.getValue());
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
		if (city.hasClimate()) {
			ClimateForecastBlock pagClimate = page.goClimateForecastBlock();
			
			assertThat(pagClimate.lstGraphs.size(), equalTo(ClimateForecastBlock.GRAPH_COUNT));
			
			for(int i = 0; i < ClimateForecastBlock.GRAPH_COUNT; i++) {
				Diagram diagram = Diagram.values()[i];
				ClimateForecastBlock.Diagram blkDiagram = pagClimate.lstGraphs.get(i);
				assertThat(blkDiagram.txtTitle.getText(), equalTo(diagram.getTitle()));
				assertThat(blkDiagram.imgGraph.getSource(), equalTo(diagram.getUrl(wsForecast.getGeoid()).toString()));
				
				for(int j = 0; j < Month.values().length; j++) {
					assertThat(blkDiagram.lstMonths.get(j).getText(), equalTo(Month.values()[j].getShortValue()));
				}
			}		
		} else {
			try {
				page.tabClimate.isDisplayed();
				fail("Unexpected Climate tab");
			} catch (NoSuchElementException e) {
				// Do nothing
			}
		}
	}
	
}
