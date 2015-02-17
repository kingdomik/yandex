package ru.yandex.pogoda.tests;

import static org.hamcrest.Matchers.allOf;
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
import static ru.yandex.pogoda.wi.lang.LocalizedText.TEMPERATURE;
import static ru.yandex.pogoda.wi.lang.LocalizedText.TEMPERATURE_CELSIUS;
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
import ru.yandex.pogoda.data.Climate;
import ru.yandex.pogoda.data.DayOfWeek;
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
		
		assertElement(
			page.txtLocation, 
			hasText(LOCATION.getValue(city.getGenitive(lang))));
		assertThat(page.txtLocalTime.getWrappedElement(), isDisplayed());
		assertThat(
			LocalTime.parse(page.txtLocalTime.getText(), DateTimeFormatter.ofPattern("HH:mm")), 
			equalWithDelltaTo(LocalTime.now(city.getTimezone()), ChronoUnit.MINUTES, 1));
		assertElement(
			page.txtTemperature, 
			hasText(TEMPERATURE_CELSIUS.getValue(Utils.temperature(wsFact.getTemperature().getValue()))));
		assertElement(page.txtCondition, hasText(lang.getWeatherType(wsFact)));
		assertElement(
			page.imgCondition, 
			hasCss("background-image", String.format(ICON_URL, wsFact.getImageV3().getValue())));
		assertElement(
			page.txtYesterdayTemperature, 
			hasText(TEMPERATURE_YESTERDAY.getValue(Utils.temperature(wsForecast.getYesterday().getTemperature().getValue()))));
		
		if (wsFact.getWaterTemperature() == null) {
			assertThat(
				page.txtWaterTemperature.getWrappedElement(),
				not(isDisplayed()));
		} else {
			assertElement(
				page.txtWaterTemperature, 
				hasText(WATER_TEMPERATURE.getValue(Utils.temperature(wsFact.getWaterTemperature()))));
		}
		
		assertElement(
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
			
			assertElement(
				day.txtDayOfWeek, 
				hasText(
					i == 0 
					? (dayShift == 0 
						? LocalizedText.TODAY.getValue()
						: LocalizedText.TOMORROW.getValue())
					: DayOfWeek.get(dow).getValue()));
			assertElement(
				day.txtDayOfMonth, 
				hasText(i == 0 | dow == 1 
					? BRIEF_DATE.getValue(date.getDayOfMonth(), Month.get(date.getMonthValue()).getGenitiveValue())  
					: "" + date.getDayOfMonth()));
			assertElement(
				day.imgCondition, 
				hasCss("background-image", String.format(ICON_URL, wsDayPart.getImageV3().getValue())));
			assertElement(
				day.txtCondition, 
				// For unknown reason extra space can be added to web service weather type, e.g.
				// <weather_type_ua>хмарно з проясненнями , невеликий сніг</weather_type_ua>
				// so compare ignoring white spaces as workaround
				hasText(equalToIgnoringWhiteSpace(lang.getWeatherType(wsDayPart))));
			assertElement(
				day.txtDayTemperature, 
				hasText((
					i == 0 
						? BRIEF_TEMPERATURE_DAY 
						: TEMPERATURE
					).getValue(getTemperature(wsDayPart, true))));
			assertElement(
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
		DetailedForecastBlock pagDetailed = page.goDetailedForecastBlock();
		
		assertThat(
			wsDays.size(), 
			equalTo(ForecastPage.DAYS_COUNT));
		assertThat(
			pagDetailed.daysDates.size(), 
			equalTo(ForecastPage.DAYS_COUNT));
		assertThat(
			pagDetailed.daysForecast.size(), 
			equalTo(ForecastPage.DAYS_COUNT));
		
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

			assertElement(
				wiDateDay.txtDayOfWeek, 
				hasText(DayOfWeek.get(date.getDayOfWeek().getValue()).getValue()));
			assertElement(
				wiDateDay.txtDayOfMonth, 
				hasText(DETAILED_DATE.getValue(date.getDayOfMonth(), Month.get(date.getMonthValue()).getGenitiveValue())));
			assertElement(
				wiForecatsDay.txtSunrise, 
				hasText(DETAILED_SUNRISE.getValue(wsDay.getSunrise())));
			assertElement(
				wiForecatsDay.txtSunset, 
				hasText(DETAILED_SUNSET.getValue(wsDay.getSunset())));
			assertElement(
				wiForecatsDay.imgMoon, 
				hasClass("icon_moon_" + wsDay.getMoonPhase().getValue()));
			
			Forecast.Day.Biomet wsBiomet = wsDay.getBiomet();
			if (wsBiomet == null) {
				assertThat(
					wiForecatsDay.txtGeomagnetic.getText().trim(), 
					isEmptyString());
			} else {
				assertElement(
					wiForecatsDay.txtGeomagnetic, 
					hasText(GEOMAGNETIC.getValue(Geomagnetic.get(wsBiomet.getMessage().get(1).getCode()).getValue())));
			}
			
			// FIXME check part size
			for(int j = 0; j < 4; j++) {
				Forecast.Day.DayPart wsDayPart = wsDay.getDayPart().get(j);
				DayPartBlock wiDayPart = wiForecatsDay.dayParts.get(j);
				
				assertElement(
					wiDayPart.txtTemperature, 
					hasText(getDetailedTemperature(wsDayPart)));
				assertElement(
					wiDayPart.imgCondition, 
					hasCss("background-image", String.format(ICON_URL, wsDayPart.getImageV3().getValue())));
				assertElement(
					wiDayPart.txtPressure, 
					hasText("" + wsDayPart.getPressure().getValue()));
				assertElement(
					wiDayPart.txtHumidity, 
					hasText(DETAILED_HUMIDITY.getValue(wsDayPart.getHumidity())));
				assertElement(
					wiDayPart.txtWindSpeed, 
					hasText(Utils.formatFloat(Float.parseFloat(wsDayPart.getWindSpeed()), WIND_SPEED.getValue())));
				assertElement(
					wiDayPart.txtWindDirection, 
					hasText(WindDirection.get(wsDayPart.getWindDirection()).getValue()));
				assertElement(
					wiDayPart.imgWindDirection, 
					hasClass("icon_wind_" + wsDayPart.getWindDirection()));
			}
			
			date = date.plusDays(1);
		}
				
	}
	
	@Test
	public void testClimate() {
		if (city.getClimates().length == 0) {
			assertThat(
				"tab Climate",
				page.tabClimate.getWrappedElement(), 
				not(isDisplayed()));
		} else {
			ClimateForecastBlock pagClimate = page.goClimateForecastBlock();
			
			assertThat(
				"climate diagram count", 
				pagClimate.lstGraphs.size(), 
				equalTo(city.getClimates().length));

			for (int i = 0; i < city.getClimates().length; i ++) {
				Climate diagram = city.getClimates()[i];
				ClimateForecastBlock.Diagram blkDiagram = pagClimate.lstGraphs.get(i);
				assertThat(
					blkDiagram.txtTitle.getText(), 
					equalTo(diagram.getTitle()));
				assertThat(
					blkDiagram.imgGraph.getSource(), 
					equalTo(diagram.getUrl(wsForecast.getGeoid()).toString()));
				
				for(int j = 0; j < Month.values().length; j++) {
					assertThat(
						blkDiagram.lstMonths.get(j).getText(), 
						equalTo(Month.values()[j].getShortValue()));
				}
			}
		}
	}
	
}