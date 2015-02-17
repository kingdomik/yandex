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
 * SearchPage describes common
 * @author dkishenk
 *
 */
public class ForecastPage extends Page {
	
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
	
	public CityForecastPage goForecast(String text) {
		inpRequest.sendKeys(text);
		btnSubmit.click();
		return new CityForecastPage(getDriver());
	}

	public SearchResultsPage search(String text) {
		inpRequest.sendKeys(text);
		btnSubmit.click();
		return new SearchResultsPage(getDriver());
	}

	public void detectLanguage() {
		LocalizedTextManager.setLanguage(getLanguage().name().toLowerCase());
	}
	
	public Language getLanguage() {
		return Language.get(elmHtml.getAttribute("lang"));
	}
	
}
