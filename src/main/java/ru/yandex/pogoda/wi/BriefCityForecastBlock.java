package ru.yandex.pogoda.wi;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Image;
import ru.yandex.qatools.htmlelements.element.TextBlock;

/**
 * Wraps brief city forecast block
 *
 */
public class BriefCityForecastBlock extends ForecastPage {
	
	/**
	 * Hour separates day into two parts. If city local time less than hour 
	 * then page shows brief forecast starting from today otherwise from tomorrow
	 */
	public static final int TODAYA_TOMMOW_HOUR = 9;

	/**
	 * Wraps day brief forecast block 
	 *
	 */
	public static class DayOfWeekForecats extends HtmlElement {
		
		@Name("День недели")
		@FindBy(className="forecast-brief__item-dayname")
		public TextBlock txtDayOfWeek;
				
		@Name("Число месяца")
		@FindBy(className="forecast-brief__item-day")
		public TextBlock txtDayOfMonth;
		
		@Name("Иконка погоды")
		@FindBy(tagName="i")
		public Image imgWeather;
		
		@Name("Cостояние погоды")
		@FindBy(className="forecast-brief__item-comment")
		public TextBlock txtCondition;
		
		@Name("Температура днем")
		@FindBy(className="forecast-brief__item-temp-day")
		public TextBlock txtDayTemperature;
		
		@Name("Температура ночью")
		@FindBy(className="forecast-brief__item-temp-night")
		public TextBlock txtNightTemperature;

	}

	@Name("Прогно по дням")
	@FindBy(css=".forecast-brief__item:not(.forecast-brief__item_gap)")
	public List<DayOfWeekForecats> days;
	
	BriefCityForecastBlock(WebDriver driver) {
	    super(driver);
    }
	
}
