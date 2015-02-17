package ru.yandex.pogoda.tests;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static ru.yandex.common.matchers.Matchers.equalWithDelltaTo;
import static ru.yandex.pogoda.wi.lang.LocalizedText.BRIEF_DATE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.BRIEF_TEMPERATURE_DAY;
import static ru.yandex.pogoda.wi.lang.LocalizedText.BRIEF_TEMPERATURE_NIGHT;
import static ru.yandex.pogoda.wi.lang.LocalizedText.DETAILED_DATE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.DETAILED_HUMIDITY;
import static ru.yandex.pogoda.wi.lang.LocalizedText.DETAILED_SUNRISE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.DETAILED_SUNSET;
import static ru.yandex.pogoda.wi.lang.LocalizedText.DETAILED_TEMPERATURE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.GEOMAGNETIC;
import static ru.yandex.pogoda.wi.lang.LocalizedText.HUMIDITY;
import static ru.yandex.pogoda.wi.lang.LocalizedText.LOCATION;
import static ru.yandex.pogoda.wi.lang.LocalizedText.OBSERVATION_TIME;
import static ru.yandex.pogoda.wi.lang.LocalizedText.PRESSURE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.SUNRISE_SUNSET;
import static ru.yandex.pogoda.wi.lang.LocalizedText.TAB_BRIEF;
import static ru.yandex.pogoda.wi.lang.LocalizedText.TAB_CLIMATE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.TAB_DETAILED;
import static ru.yandex.pogoda.wi.lang.LocalizedText.TEMPERATURE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.TEMPERATURE_CELSIUS;
import static ru.yandex.pogoda.wi.lang.LocalizedText.TEMPERATURE_YESTERDAY;
import static ru.yandex.pogoda.wi.lang.LocalizedText.WATER_TEMPERATURE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.WIND;
import static ru.yandex.pogoda.wi.lang.LocalizedText.WIND_CALM;
import static ru.yandex.pogoda.wi.lang.LocalizedText.WIND_SPEED;
import static ru.yandex.qatools.htmlelements.matchers.common.HasAttributeMatcher.hasAttribute;
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
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebElement;

import ru.yandex.common.Utils;
import ru.yandex.common.wi.Browser;
import ru.yandex.pogoda.data.City;
import ru.yandex.pogoda.data.DayOfWeek;
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
			
	@Parameters(name = "{0}.{1}")
	public static Iterable<Object[]> data1() {
		return Arrays.asList(new Object[][] { 
//			{ Language.RU, City.MOSCOW }, 
//			{ Language.RU, City.LOS_ANGELES }, 
//			{ Language.RU, City.SAINT_PETERSBURG }, 
//			{ Language.UK, City.MOSCOW }, 
//			{ Language.UK, City.LOS_ANGELES }, 
			{ Language.UK, City.SAINT_PETERSBURG }, 
		});
	}
	
	static Browser browser;
	
	ForecastPage page;
	Forecast wsForecast;
	List<Forecast.Day> wsDays;
	City city; 
	Language lang;
	
	@BeforeClass
	public static void openBrowser() {
		browser = new Browser();
	}
	
	@AfterClass
	public static void closeBrowser() {
		browser.close();
	}
	
	public ForecastTest(Language lang, City city) {
		this.lang = lang;
		this.city = city;
	}
	
	@Before
	public void setup() {
		page = browser.goForecast(city.getUrl().toString());
		if (!page.getLanguage().equals(lang)) {
			browser.setLanguage(lang);
			page = browser.goForecast(city.getUrl().toString());
		}
		wsForecast = city.getForecast();
		wsDays = wsForecast.getDay();
	}
	
	void assertDisplayed(String message, TypifiedElement element, Matcher<WebElement> matcher) {
		assertThat(
			message,
			element.getWrappedElement(),
			allOf(isDisplayed(), matcher)
		);
	}
	
	void assertDisplayed(TypifiedElement element, Matcher<WebElement> matcher) {
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
	
//	@Test
	public void testFact() {
		Forecast.Fact wsFact = wsForecast.getFact();
		
		assertDisplayed(
			page.txtLocation, 
			hasText(LOCATION.getValue(city.getGenitive(lang))));
		assertThat(
			page.txtLocalTime.getWrappedElement(),
			isDisplayed());
		assertThat(
			LocalTime.parse(page.txtLocalTime.getText(), DateTimeFormatter.ofPattern("HH:mm")), 
			// Local page time can has minor difference with test time
			equalWithDelltaTo(LocalTime.now(city.getTimezone()), ChronoUnit.MINUTES, 1));
		assertDisplayed(
			page.txtTemperature, 
			hasText(TEMPERATURE_CELSIUS.getValue(Utils.temperature(wsFact.getTemperature().getValue()))));
		assertDisplayed(
			page.txtWaether, 
			hasText(lang.getWeatherType(wsFact)));
		assertDisplayed(
			page.imgWaether, 
			hasCss("background-image", String.format(ICON_URL, wsFact.getImageV3().getValue())));
		assertDisplayed(
			page.txtYesterdayTemperature, 
			hasText(TEMPERATURE_YESTERDAY.getValue(Utils.temperature(wsForecast.getYesterday().getTemperature().getValue()))));
		
		if (wsFact.getWaterTemperature() == null) {
			assertThat(
				page.txtWaterTemperature.getWrappedElement(),
				not(isDisplayed()));
		} else {
			assertDisplayed(
				page.txtWaterTemperature, 
				hasText(WATER_TEMPERATURE.getValue(Utils.temperature(wsFact.getWaterTemperature()))));
		}
		
		assertDisplayed(
			page.txtSunriseSunset, 
			hasText(SUNRISE_SUNSET.getValue(wsForecast.getDay().get(0).getSunrise(), wsForecast.getDay().get(0).getSunset())));
		
		String wind;
		WindDirection windDirection = WindDirection.get(wsFact.getWindDirection());
		if (WindDirection.CALM.equals(windDirection)) {
			wind = WIND_CALM.getValue();
		} else {
			wind = WIND.getValue(
					Utils.formatFloat(Float.parseFloat(wsFact.getWindSpeed()), WIND_SPEED.getValue()),
					windDirection.getValue());
			assertDisplayed(
				page.imgWindDirection, 
				hasClass("icon_wind_" + wsFact.getWindDirection()));
		}
		assertDisplayed(
			page.txtWind, 
			hasText(wind));
		assertDisplayed(
			page.txtHumidity, 
			hasText(HUMIDITY.getValue(wsFact.getHumidity())));
		assertDisplayed(
			page.txtPressure, 
			hasText(PRESSURE.getValue(wsFact.getPressure().getValue())));
		assertDisplayed(
			page.txtObservationTime, 
			hasText(Utils.formatXmlDate(wsFact.getObservationTime(), OBSERVATION_TIME.getValue())));
	}
	
//	@Test
	public void testBrief() {
		assertDisplayed(
				page.tabBrief, 
				hasText(TAB_BRIEF.getValue()));
			
		LocalDate date = LocalDate.now(city.getTimezone());
		LocalTime time = LocalTime.now(city.getTimezone());
		
		int dayShift = 0;
		if (time.getHour() >= ForecastPage.TODAYA_TOMMOW_SWITCH) {
			date = date.plusDays(1);
			dayShift = 1;
		}
		
		BriefForecastBlock pagBrief = page.goBriefForecastBlock();
		
		assertThat(
				wsDays.size(), 
				equalTo(ForecastPage.DAYS_COUNT));

		for(int i = 0; i < ForecastPage.DAYS_COUNT - 1; i++) {
			Forecast.Day wsDay = wsDays.get(i + dayShift);
			DayOfWeekForecats day = pagBrief.days.get(i);
			
			int dow = date.getDayOfWeek().getValue();
			Forecast.Day.DayPart wsDayPart = wsDay.getDayPart().get(1); 
			Forecast.Day.DayPart wsNightPart = wsDay.getDayPart().get(3);
			
			// Web service is trusted data source but self-check would be useful anyway  
			assertThat(wsDay.getDate().toString(), equalTo(date.toString()));
			assertThat(wsDayPart.getType(), equalTo("day"));
			assertThat(wsNightPart.getType(), equalTo("night"));
			
			assertDisplayed(
				day.txtDayOfWeek, 
				hasText(
					i == 0 
					? (dayShift == 0 
						? LocalizedText.TODAY.getValue()
						: LocalizedText.TOMORROW.getValue())
					: DayOfWeek.get(dow).getValue()));
			assertDisplayed(
				day.txtDayOfMonth, 
				hasText(i == 0 | dow == 1 
					? BRIEF_DATE.getValue(date.getDayOfMonth(), Month.get(date.getMonthValue()).getGenitiveValue())  
					: "" + date.getDayOfMonth()));
			assertDisplayed(
				day.imgCondition, 
				hasCss("background-image", String.format(ICON_URL, wsDayPart.getImageV3().getValue())));
			assertDisplayed(
				day.txtCondition, 
				// For unknown reason extra space can be added to web service weather type, e.g.
				// <weather_type_ua>хмарно з проясненнями , невеликий сніг</weather_type_ua>
				// so compare ignoring white spaces as workaround
				hasText(equalToIgnoringWhiteSpace(lang.getWeatherType(wsDayPart))));
			assertDisplayed(
				day.txtDayTemperature, 
				hasText((
					i == 0 
						? BRIEF_TEMPERATURE_DAY 
						: TEMPERATURE
					).getValue(getTemperature(wsDayPart, true))));
			assertDisplayed(
				day.txtNightTemperature, 
				hasText((
					i == 0 
						? BRIEF_TEMPERATURE_NIGHT 
						: TEMPERATURE
					).getValue(getTemperature(wsNightPart, false))));
			
			date = date.plusDays(1);			
		}
		
	}

	@Test
	public void testDetailed() {
		assertDisplayed(
			page.tabDetailed, 
			hasText(TAB_DETAILED.getValue()));
		
		DetailedForecastBlock pagDetailed = page.goDetailedForecastBlock();
		
		assertThat(
			"web service days count",
			wsDays.size(), 
			equalTo(ForecastPage.DAYS_COUNT));
		assertThat(
			"page dates count",
			pagDetailed.daysDates.size(), 
			equalTo(ForecastPage.DAYS_COUNT));
		assertThat(
			"page forecast count",
			pagDetailed.daysForecast.size(), 
			equalTo(ForecastPage.DAYS_COUNT));
		
		LocalDate date = LocalDate.now(city.getTimezone());
		
		for(int i = 0; i < ForecastPage.DAYS_COUNT; i++) {
			String msgDay = "day " + date;
			Forecast.Day wsDay = wsDays.get(i);
			DateDayBlock blkDateDay = pagDetailed.daysDates.get(i);
			ForecastDayBlock wiForecatsDay = pagDetailed.daysForecast.get(i);
			
			// Web service is trusted data source but self-check would be useful anyway  
			assertThat(wsDay.getDate(), equalTo(date.toString()));
			assertThat(wsDay.getDayPart().get(0).getType(), equalTo("morning"));
			assertThat(wsDay.getDayPart().get(1).getType(), equalTo("day"));
			assertThat(wsDay.getDayPart().get(2).getType(), equalTo("evening"));
			assertThat(wsDay.getDayPart().get(3).getType(), equalTo("night"));

			assertDisplayed(
				msgDay,
				blkDateDay.txtDayOfWeek, 
				hasText("-" + DayOfWeek.get(date.getDayOfWeek().getValue()).getValue()));
			assertDisplayed(
				msgDay,
				blkDateDay.txtDayOfMonth, 
				hasText(DETAILED_DATE.getValue(date.getDayOfMonth(), Month.get(date.getMonthValue()).getGenitiveValue())));
			assertDisplayed(
				msgDay,
				wiForecatsDay.txtSunrise, 
				hasText(DETAILED_SUNRISE.getValue(wsDay.getSunrise())));
			assertDisplayed(
				msgDay,
				wiForecatsDay.txtSunset, 
				hasText(DETAILED_SUNSET.getValue(wsDay.getSunset())));
			assertDisplayed(
				msgDay,
				wiForecatsDay.imgMoon, 
				// Moon phase image url building algorithm is unknown so check css only  
				// url(//yastatic.net/weather-frontend/_/R2XdVySiSf7fRCJcl8jHy_oxG3A.svg)
				hasClass("icon_moon_" + wsDay.getMoonPhase().getValue()));
			
			Forecast.Day.Biomet wsBiomet = wsDay.getBiomet();
			if (wsBiomet == null) {
				assertThat(
					msgDay,
					wiForecatsDay.txtGeomagnetic.getText().trim(), 
					isEmptyString());
			} else {
				assertDisplayed(
					msgDay,
					wiForecatsDay.txtGeomagnetic, 
					hasText(GEOMAGNETIC.getValue(Geomagnetic.get(wsBiomet.getMessage().get(1).getCode()).getValue())));
			}
			
			assertThat(
				"number of day parts for " + date,
				wsDay.getDayPart().size(), 
				greaterThanOrEqualTo(4));
			
			for(int j = 0; j < 4; j++) {
				String msgDayParrt = msgDay + " day part " + j; 
				Forecast.Day.DayPart wsDayPart = wsDay.getDayPart().get(j);
				DayPartBlock wiDayPart = wiForecatsDay.dayParts.get(j);
				
				assertDisplayed(
					msgDayParrt,
					wiDayPart.txtTemperature, 
					hasText(getDetailedTemperature(wsDayPart)));
				assertDisplayed(
					msgDayParrt,
					wiDayPart.imgCondition, 
					hasCss("background-image", String.format(ICON_URL, wsDayPart.getImageV3().getValue())));
				assertDisplayed(
					msgDayParrt,
					wiDayPart.txtPressure, 
					hasText("" + wsDayPart.getPressure().getValue()));
				assertDisplayed(
					msgDayParrt,
					wiDayPart.txtHumidity, 
					hasText(DETAILED_HUMIDITY.getValue(wsDayPart.getHumidity())));
				assertDisplayed(
					msgDayParrt,
					wiDayPart.txtWindSpeed, 
					hasText(Utils.formatFloat(Float.parseFloat(wsDayPart.getWindSpeed()), WIND_SPEED.getValue())));
				assertDisplayed(
					msgDayParrt,
					wiDayPart.txtWindDirection, 
					hasText(WindDirection.get(wsDayPart.getWindDirection()).getValue()));
				assertDisplayed(
					msgDayParrt,
					wiDayPart.imgWindDirection, 
					hasClass("icon_wind_" + wsDayPart.getWindDirection()));
			}
			
			date = date.plusDays(1);
		}
				
	}
	
//	@Test
	public void testClimate() {
		if (city.getClimates().length == 0) {
			assertThat(
				page.tabClimate.getWrappedElement(), 
				not(isDisplayed()));
		} else {
			assertDisplayed(
				page.tabClimate, 
				hasText(TAB_CLIMATE.getValue()));
			
			ClimateForecastBlock pagClimate = page.goClimateForecastBlock();
			
			assertThat(
				"climate diagram count", 
				pagClimate.lstGraphs.size(), 
				equalTo(city.getClimates().length));

			for (int i = 0; i < city.getClimates().length; i ++) {
				Diagram diagram = city.getClimates()[i];
				String msgDiagram = "diagram " + diagram.getTitle(); 
				ClimateForecastBlock.Diagram blkDiagram = pagClimate.lstGraphs.get(i);
				assertThat(
					msgDiagram,
					blkDiagram.txtTitle.getWrappedElement(), 
					hasText(diagram.getTitle()));
				assertThat(
					msgDiagram,
					blkDiagram.imgGraph.getWrappedElement(),
					hasAttribute("src", diagram.getUrl(wsForecast.getGeoid()).toString()));
				
				for(int j = 0; j < Month.values().length; j++) {
					assertThat(
						msgDiagram,
						blkDiagram.lstMonths.get(j).getText(), 
						equalTo(Month.values()[j].getShortValue()));
				}
			}
		}
	}
	
}