package ru.yandex.account.wi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.common.wi.Page;
import ru.yandex.pogoda.wi.lang.Language;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.TextBlock;

/**
 * Wraps settings page
 *
 */
public class SettingsPage extends Page {

	@Name("Язык")
	@FindBy(css = "a.b-lang-switcher__lang")
	private TextBlock txtLanguage;
		
	public SettingsPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Returns current language 
	 * @return language
	 */
	public Language getLanguage() {
		return Language.get(txtLanguage.getText());
	}

}
