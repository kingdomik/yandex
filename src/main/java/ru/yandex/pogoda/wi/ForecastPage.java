package ru.yandex.pogoda.wi;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import ru.yandex.pogoda.data.City;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Image;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.element.TextBlock;

public class ForecastPage extends AbstractPage {

	public static final int DAYS_COUNT = 10;

	public class AfterBlock extends HtmlElement {
		
		@Name("Часть дня")
		@FindBy(className = "current-weather__thermometer-name")
		public TextBlock txtName;
		
		@Name("Мконка состояния погоды")
		@FindBy(tagName = "i")
		public TextBlock imgCondition;
		
		@Name("Температура")
		@FindBy(className = "current-weather__thermometer_type_after")
		public TextBlock txtTemperature;		
		
	}
	
	@Name("Город")
	@FindBy(css = ".navigation-city h1")
	public TextBlock txtLocation;
	
	@Name("Текущее время")
	@FindBy(css = ".current-weather__today span")
	@CacheLookup
	public TextBlock txtLocalTime;

	@Name("Текущая температура")
	@FindBy(className = "current-weather__thermometer_type_now")
	public TextBlock txtTemperature;

	@Name("Вчерашняя температура")
	@FindBy(className = "current-weather__yesterday")
	public TextBlock txtYesterdayTemperature;

	@Name("Иконка текущей погоды")
	@FindBy(css = ".current-weather__col_type_now i")
	public Image imgCondition;

	@Name("Текущеая погода")
	@FindBy(className = "current-weather__comment")
	public TextBlock txtCondition;

	@Name("Дальнейшая погода")
	@FindBy(className = "current-weather__col_type_after")
	public List<AfterBlock> lstAfter;
	
	@Name("Восход/Закат")
	@FindBy(className = "current-weather__info-row")
	public TextBlock txtSunriseSunset;

	@Name("Ветер")
	@FindBy(className = "current-weather__info-row_type_wind")
	public TextBlock txtWind;

	@Name("Направление ветера")
	@FindBy(css = ".current-weather__info-row_type_wind i")
	public Image imgWindDirection;
	
	@Name("Влажность")
	@FindBy(css = ".current-weather__info-row:nth-child(3)")
	public TextBlock txtHumidity;
	
	@Name("Давление")
	@FindBy(css = ".current-weather__info-row:nth-child(4)")
	public TextBlock txtPressure;
	
	@Name("Актуальность данных")
	@FindBy(css = ".current-weather__info-row:nth-child(5)")
	public TextBlock txtObservationTime;
	
	@FindBy(css = "[role=tab]:nth-child(1)")
	public Link tabBrief;
	
	@FindBy(css = "[role=tab]:nth-child(2)")
	public Link tabDetailed;
	
	@FindBy(css = "[role=tab]:nth-child(3)")
	public Link tabClimate;

	@FindBy(css = "navigation-city__info button")
	public Button btnOthwerCity;
		
	public ForecastPage(WebDriver driver) {
		super(driver);
		//FIXME test tab names
	}
	
	public City getCity() {
		String city = getUrl().getPath();
		city = city.substring(1).toUpperCase().replace('-', '_');
		return City.valueOf(city);
	}

}
