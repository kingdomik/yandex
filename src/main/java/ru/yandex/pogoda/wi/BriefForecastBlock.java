package ru.yandex.pogoda.wi;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Image;
import ru.yandex.qatools.htmlelements.element.TextBlock;

public class BriefForecastBlock extends SearchPage {
	
	public static class DayOfWeekForecats extends HtmlElement {
		
		@Name("День недели")
		@FindBy(className="forecast-brief__item-dayname")
		public TextBlock txtDayOfWeek;
				
		@Name("Число месяца")
		@FindBy(className="forecast-brief__item-day")
		public TextBlock txtDayOfMonth;
		
		@Name("Иконка погоды")
		@FindBy(tagName="i")
		public Image imgCondition;
		
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
	
	public BriefForecastBlock(WebDriver driver) {
	    super(driver);
    }
	
}
