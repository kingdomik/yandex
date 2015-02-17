package ru.yandex.pogoda.wi;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Image;
import ru.yandex.qatools.htmlelements.element.TextBlock;

public class DetailedCityForecastBlock extends ForecastPage {

	public static class DateDayBlock extends HtmlElement {
		
		@Name("День недели")
		@FindBy(className="forecast-detailed__weekday")
		public TextBlock txtDayOfWeek;
				
		@Name("Число месяца")
		@FindBy(className="forecast-detailed__day-number")
		public TextBlock txtDayOfMonth;
		
	}

	public static class ForecastDayBlock extends HtmlElement {
		
		@Name("Восход")
		@FindBy(className="forecast-detailed__sunrise")
		public TextBlock txtSunrise;
		
		@Name("Закат")
		@FindBy(className="forecast-detailed__sunset")
		public TextBlock txtSunset;
		
		@Name("Фаза луны")
		@FindBy(css=".forecast-detailed__moon i")
		public Image imgMoon;

		@Name("Магнитное поле")
		@FindBy(className="forecast-detailed__geomagnetic-field")
		public TextBlock txtGeomagnetic;		

		@Name("Часть дня")
		@FindBy(className="weather-table__row")
		public List<DayPartBlock> dayParts;		

	}
	
	public static class DayPartBlock extends HtmlElement {
	
		@Name("Температура")
		@FindBy(css=".weather-table__body-cell_type_daypart .weather-table__daypart")
		public TextBlock txtName;
		
		@Name("Температура")
		@FindBy(css=".weather-table__body-cell_type_daypart .weather-table__temp")
		public TextBlock txtTemperature;
		
		@Name("Иконка погоды")
		@FindBy(tagName="i")
		public Image imgWeather;
		
		@Name("Cостояние погоды")
		@FindBy(className="table__body-cell_type_condition")
		public TextBlock txtCondition;
		
		@Name("Давление")
		@FindBy(className="weather-table__body-cell_type_air-pressure")
		public TextBlock txtPressure;
		
		@Name("Влажность")
		@FindBy(className="weather-table__body-cell_type_humidity")
		public TextBlock txtHumidity;
		
		@Name("Направление ветра")
		@FindBy(css=".weather-table__body-cell_type_wind abbr")
		public TextBlock txtWindDirection;
			
		@Name("Исконка напрвления ветра")
		@FindBy(css=".weather-table__body-cell_type_wind i")
		public Image imgWindDirection;
			
		@Name("Сила ветра")
		@FindBy(className="weather-table__wind")
		public TextBlock txtWindSpeed;
			
	}

	@Name("Прогно по дням")
	@FindBy(css="dt.forecast-detailed__day")
	public List<DateDayBlock> daysDates;
	
	@Name("Прогно по дням")
	@FindBy(css="dd.forecast-detailed__day-info")
	public List<ForecastDayBlock> daysForecast;
	
	public DetailedCityForecastBlock(WebDriver driver) {
	    super(driver);
    }

}
