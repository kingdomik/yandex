package ru.yandex.pogoda.wi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.common.wi.Page;
import ru.yandex.pogoda.wi.lang.Language;
import ru.yandex.pogoda.wi.lang.LocalizedTextManager;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextInput;

/**
 * Class wraps common elements for forecast service
 *
 */
public class ForecastPage extends Page {
	
	/**
	 * URL to main forecast page 
	 */
	public static final String URL = "https://pogoda.yandex.ru";
	
	@FindBy(tagName = "html")
	private HtmlElement elmHtml;
	
	@Name("Запрос")
	@FindBy(name = "request")
	public TextInput inpRequest;
	
	@Name("Найти")
	@FindBy(css = "button[type=submit]")
	public Button btnSubmit;
	
	ForecastPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Go to city forecast page
	 * @param text - city name exact or part but expected redirection to city page
	 * @return wrapper for city forecast page 
	 */
	public CityForecastPage goForecast(String text) {
		inpRequest.sendKeys(text);
		btnSubmit.click();
		return new CityForecastPage(getDriver());
	}

	/**
	 * Search city by name
	 * @param text - city name exact or part. Expected multiple results  otherwise use goForecast()
	 * @return wrapper for search results
	 * @see SearchResultsPage
	 */
	public SearchResultsPage search(String text) {
		inpRequest.sendKeys(text);
		btnSubmit.click();
		return new SearchResultsPage(getDriver());
	}

	/**
	 * Auto detect current page language and switch all localized text values accordingly
	 * @see LocalizedTextManager
	 */
	public void detectLanguage() {
		LocalizedTextManager.setLanguage(getLanguage().name().toLowerCase());
	}
	
	/**
	 * Auto detect current page language
	 * @return page language
	 */
	public Language getLanguage() {
		return Language.get(elmHtml.getAttribute("lang"));
	}
	
}
