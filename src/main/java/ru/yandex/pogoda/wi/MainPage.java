package ru.yandex.pogoda.wi;

import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import ru.yandex.pogoda.wi.controls.property.WITextProperty;

import com.codeborne.selenide.ElementsContainer;
import com.codeborne.selenide.SelenideElement;

public class MainPage {

	public static class CurrentWeather {
		
		@FindBy(css=".current-weather__today")
		public SelenideElement propNow;
		
		@FindBy(css=".current-weather__local-time")
		public SelenideElement propLocalTime;

//		public WITextProperty propLocalTime2 = new WITextProperty(null, null) {
//			@Override
//			public SelenideElement getElement() {
//				return $(By.xpath("//*[text()='%s ']/span"));
//			}			
//		};
		
		@FindBy(xpath="//*[text()='Восход: ']/following::text()[1]")
		public SelenideElement propRiseTime;
		
		@FindBy(xpath="//*[text()='Закат: ']/following::text()[1]")
		public SelenideElement propSunsetTime;
		
		@FindBy(xpath="//*[text()='Ветер: ']/following::text()[1]")
		public SelenideElement propWindSpeed;
		
		@FindBy(xpath="//*[text()='Ветер: ']/following::abbr[1]")
		public SelenideElement propWindDirection;
		
		@FindBy(xpath="//*[text()='Влажность: ']")
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
	
	public CurrentWeather cntCurentWearther = page(CurrentWeather.class);
	
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
