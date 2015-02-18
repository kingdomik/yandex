package ru.yandex.account.wi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.common.wi.Page;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.TextBlock;

/**
 * Wraps settings page
 *
 */
public class SettingsPage extends Page {

	@Name("Настройка города")
	@FindBy(css = "a.b-link_type_retpath")
	private TextBlock txtLocation;
	
	@Name("Настройка языка интерфейса")
	@FindBy(css = "a.b-lang-switcher__lang")
	private TextBlock txtLanguage;
		
	public SettingsPage(WebDriver driver) {
		super(driver);
	}

}
