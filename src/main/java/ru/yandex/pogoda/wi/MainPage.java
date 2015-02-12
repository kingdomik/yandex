package ru.yandex.pogoda.wi;

import org.openqa.selenium.support.FindBy;

import ru.yandex.pogoda.wi.controls.property.WITextProperty;

import com.codeborne.selenide.ElementsContainer;
import com.codeborne.selenide.SelenideElement;

public class MainPage {

	public static class CurrentWeather extends ElementsContainer {
		
		@FindBy(css=".current-weather__today")
		public SelenideElement propNow;
		
		@FindBy(css=".current-weather__local-time")
		public SelenideElement propLocalTime;

		public WITextProperty propLocalTime2 = new WITextProperty();
		
		@FindBy(xpath="//*[text()='Восход: ']/following::text()[1]")
		public SelenideElement propRiseTime;
		
		@FindBy(xpath="//*[text()='Закат: ']/following::text()[1]")
		public SelenideElement propSunsetTime;
		
		@FindBy(xpath="//*[text()='Ветер: ']/following::text()[1]")
		public SelenideElement propWindSpeed;
		
		@FindBy(xpath="//*[text()='Ветер: ']/following::abbr[1]")
		public SelenideElement propWindDirection;
		
		@FindBy(xpath="//*[text()='Влажность: ']/following::abbr[1]")
		public SelenideElement propHumidity;
		
		@FindBy(xpath="//*[text()='Lfdktybt: ']/following::abbr[1]")
		public SelenideElement propPressure;
		
		@FindBy(xpath="//*[starts-with(.,'Данные на ')]")
		public SelenideElement propActualTime;
		
		@FindBy(xpath="//*[starts-with(.,'Вчера в это время ')]")
		public SelenideElement propYesterdayTemperature;
		
		@FindBy(className="current-weather__comment")
		public SelenideElement propWeatherTitle;
		
	}
	
	@FindBy(name="request")
	public SelenideElement inpLocation;

	@FindBy(css="button[type='Найти']")
	public SelenideElement btnSearch;
	
	@FindBy(css="button[title='Войти']")
	public SelenideElement btnLogin;
	
	@FindBy(css="button containsspan[title='другой город']")
	public SelenideElement btnSelectCity;
	
	@FindBy(css="h1.title")
	public SelenideElement propCity;
	
	
	@FindBy(xpath="//*[@class='radio-button__text' and string(.)='кратко']")
	public SelenideElement btnViewBrief;
	
	@FindBy(xpath="//*[@class='radio-button__text' and string(.)='подробно']")
	public SelenideElement btnViewDetails;
	
	@FindBy(xpath="//*[@class='radio-button__text' and string(.)='климат']")
	public SelenideElement btnViewClimate;
	
	
	public MainPage() {
	}
	
}
