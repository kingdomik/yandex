package ru.yandex.pogoda.wi;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Image;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.element.TextBlock;

/**
 * Class wraps city forecast elements
 *
 */
public class CityForecastPage extends ForecastPage {

	/**
	 * String template to build weather icon URL
	 */
	public static final String WEATHER_ICON_URL = "url(\"https://yastatic.net/weather/i/icons/svg/%s.svg\")";
	
	/**
	 * Total count of days available on the page for brief and detailed views 
	 */
	public static final int DAYS_COUNT = 10;

	/**
	 * Wraps day part block after current day part 
	 *
	 */
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
	public TextBlock txtLocalTime;

	@Name("Текущая температура")
	@FindBy(className = "current-weather__thermometer_type_now")
	public TextBlock txtTemperature;

	@Name("Вчерашняя температура")
	@FindBy(className = "current-weather__yesterday")
	public TextBlock txtYesterdayTemperature;

	@Name("Иконка текущей погоды")
	@FindBy(css = ".current-weather__col_type_now i")
	public Image imgWeather;

	@Name("Текущеая погода")
	@FindBy(className = "current-weather__comment")
	public TextBlock txtWeather;

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
	
	@Name("Температцра воды")
	@FindBy(className = "current-weather__water")
	public Button txtWaterTemperature;

	@FindBy(css = "[role=tab]:nth-child(1)")
	public Link tabBrief;
	
	@FindBy(css = "[role=tab]:nth-child(2)")
	public Link tabDetailed;
	
	@FindBy(css = "[role=tab]:nth-child(3)")
	public Link tabClimate;

	@FindBy(css = ".navigation-city__info button")
	public Button btnOthwerCity;

	public CityForecastPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Switch to brief forecast view
	 * @return brief forecast wrapper
	 * @see BriefCityForecastBlock
	 */
	public BriefCityForecastBlock setBriefView() {
		tabBrief.click();
		return new BriefCityForecastBlock(getDriver());
	}
	
	/**
	 * Switch to detailed forecast view
	 * @return detailed forecast wrapper
	 * @see DetailedCityForecastBlock
	 */
	public DetailedCityForecastBlock setDetailedView() {
		tabDetailed.click();
		return new DetailedCityForecastBlock(getDriver());
	}
	
	/**
	 * Switch to climate forecast view
	 * @return climate forecast wrapper
	 * @see ClimateCityForecastBlock
	 */
	public ClimateCityForecastBlock setClimateView() {
		tabClimate.click();
		return new ClimateCityForecastBlock(getDriver());
	}
	
}
