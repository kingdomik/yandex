package ru.yandex.account.wi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.common.wi.Page;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.TextInput;

/**
 * Wraps region settings page
 *
 */
public class RegionSettingsPage extends Page {
	
	
	/**
	 * URL to language settings page 
	 */
	public static final String URL = "http://tune.yandex.ru/region/";

	@Name("Регион по умолчанию")
	@FindBy(name = "regions_auto")
	private CheckBox chkAutoRegion;

	@Name("Регион")
	@FindBy(css = "input[name=region]")
	private TextInput inpRegion;
		
	@Name("Сохранить")
	@FindBy(css = "input[type=submit]")
	private Button btnSubmit;
			
	public RegionSettingsPage(WebDriver driver) {
		super(driver);
	}
	
}
