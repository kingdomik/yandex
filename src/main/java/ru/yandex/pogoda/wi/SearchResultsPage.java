package ru.yandex.pogoda.wi;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.element.TextBlock;

/**
 * Wraps forecast search results page
 *
 */
public class SearchResultsPage extends ForecastPage {

	/**
	 * Wraps search result item block 
	 *
	 */
	public static class Result extends HtmlElement {
		
		@Name("Город")
		@FindBy(tagName = "a")
		public Link lnkCity;
		
		@Name("Температура")
		@FindBy(tagName = "span")
		public TextBlock txtTemperature;		
		
	}
	
	@Name("Сообщение")
	@FindBy(css = "h1.title")
	public TextBlock txtMessage;
		
	@Name("Результаты")
	@FindBy(className= "place-list__item")
	public List<Result> lstResults;
			
	public SearchResultsPage(WebDriver driver) {
		super(driver);
	}
	
}
