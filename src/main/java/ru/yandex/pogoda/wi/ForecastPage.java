package ru.yandex.pogoda.wi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.TextBlock;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

import com.codeborne.selenide.SelenideElement;

public class ForecastPage {

//	@Name("Local time")
//	@FindBy(css = ".current-weather__today")
//	@CacheLookup
//	public TextBlock propLocalTime;

	@Name("Temperature")
	@FindBy(css = ".current-weather__thermometer_type_now")
	public TextBlock txtTemperature;

//	// public WITextProperty propLocalTime2 = new WITextProperty(null, null) {
//	// @Override
//	// public SelenideElement getElement() {
//	// return $(By.xpath("//*[text()='%s ']/span"));
//	// }
//	// };
//
//	@FindBy(xpath = "//*[text()='Восход: ']/following::text()[1]")
//	public SelenideElement propRiseTime;
//
//	@FindBy(xpath = "//*[text()='Закат: ']/following::text()[1]")
//	public SelenideElement propSunsetTime;
//
//	@FindBy(xpath = "//*[text()='Ветер: ']/following::text()[1]")
//	public SelenideElement propWindSpeed;
//
//	@FindBy(xpath = "//*[text()='Ветер: ']/following::abbr[1]")
//	public SelenideElement propWindDirection;
//
	@Name("Влажность")
	@FindBy(xpath = "//*[text()='Влажность: ']/..")
	public TextBlock txtHumidity;

//	@FindBy(xpath = "//*[text()='Lfdktybt: ']/following::abbr[1]")
//	public SelenideElement propPressure;
//
	@FindBy(xpath = "//*[starts-with(.,'Данные на ')]")
	@CacheLookup
	public TextBlock txtObservationTime;

//	@FindBy(xpath = "//*[starts-with(.,'Вчера в это время ')]")
//	public SelenideElement propYesterdayTemperature;
//
//	@FindBy(className = "current-weather__comment")
//	public SelenideElement propWeatherTitle;
//
//	@FindBy(name = "request")
//	public SelenideElement inpLocation;
//
//	@FindBy(css = "button[type='Найти']")
//	public SelenideElement btnSearch;
//
//	@FindBy(css = "button[title='Войти']")
//	public SelenideElement btnLogin;
//
//	@FindBy(css = "button containsspan[title='другой город']")
//	public SelenideElement btnSelectCity;
//
//	@FindBy(css = "h1.title")
//	public SelenideElement propCity;
//
//	@FindBy(xpath = "//*[@class='radio-button__text' and string(.)='кратко']")
//	public SelenideElement btnViewBrief;
//
//	@FindBy(xpath = "//*[@class='radio-button__text' and string(.)='подробно']")
//	public SelenideElement btnViewDetails;
//
//	@FindBy(xpath = "//*[@class='radio-button__text' and string(.)='климат']")
//	public SelenideElement btnViewClimate;

	public ForecastPage(WebDriver driver) {
		 HtmlElementLoader.populatePageObject(this, driver);
	}

}
