package ru.yandex.pogoda.wi;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Image;
import ru.yandex.qatools.htmlelements.element.TextBlock;

public class BriefForecastBlock extends AbstractPage {
	
	public static class DayOfWeekForecats extends HtmlElement {
		
		@Name("День недели")
		@FindBy(className="forecast-brief__item-dayname")
		public TextBlock txtDayOfWeek;
				
		@Name("Число месяца")
		@FindBy(className="forecast-brief__item-day")
		public TextBlock txtDayOfMonth;
		
		@Name("Иконка погоды")
		@FindBy(tagName="i")
		public Image imgDayStateIcon;
		
		@Name("Cостояние погоды")
		@FindBy(className="forecast-brief__item-comment")
		public TextBlock txtDayState;
		
		@Name("Температура днем")
		@FindBy(className="forecast-brief__item-temp-day")
		public TextBlock txtDayTemperature;
		
		@Name("Температура ночью")
		@FindBy(className="forecast-brief__item-temp-night")
		public TextBlock txtNightTemperature;

		public boolean isGap() {
			return $(this).has(cssClass("forecast-brief__item_gap"));
		}

	}

	@Name("Прогно по дням")
	@FindBy(css=".forecast-brief__item")
	public List<DayOfWeekForecats> days;
	
	public BriefForecastBlock(WebDriver driver) {
	    super(driver);
    }
	
}
