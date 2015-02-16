package ru.yandex.pogoda.wi;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.pogoda.data.City;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.element.TextBlock;

public class SearchResultsPage extends SearchPage {

	public static final int DAYS_COUNT = 10;

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
	
//	public City getCity() {
//		String city = getUrl().getPath();
//		city = city.substring(1).toUpperCase().replace('-', '_');
//		return City.getByUrl(url)valueOf(city);
//	}
	
//	public ForecastPage search(String text) {
//		inpRequest.sendKeys(text);
//		btnSubmit.click();
//		return new ForecastPage(getDriver());
//	}

}
