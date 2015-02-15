package ru.yandex.pogoda.wi;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Image;
import ru.yandex.qatools.htmlelements.element.TextBlock;

public class ClimateForecastBlock  extends AbstractPage {
	
	public static final int GRAPH_COUNT = 6;
	
	public static class Diagram extends HtmlElement {
		
		@Name("Заголовок")
		@FindBy(tagName="h3")
		public TextBlock txtTitle;
				
		@Name("График")
		@FindBy(tagName="img")
		public Image imgGraph;
		
		@Name("Месяцы")
		@FindBy(className="climate-image__item")
		public List<TextBlock> lstMonths;
		
	}

	@Name("Диаграммы")
	@FindBy(className="climate-image")
	public List<Diagram> lstGraphs;
	
	public ClimateForecastBlock(WebDriver driver) {
	    super(driver);
    }
	
}
