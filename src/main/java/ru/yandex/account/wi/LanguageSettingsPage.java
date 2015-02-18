package ru.yandex.account.wi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.common.wi.Page;
import ru.yandex.pogoda.wi.lang.Language;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Select;

/**
 * Wraps language settings page
 *
 */
public class LanguageSettingsPage extends Page {
	
	
	/**
	 * URL to language settings page 
	 */
	public static final String URL = "http://tune.yandex.ru/lang";

	@Name("Язык")
	@FindBy(name = "intl")
	private Select selLanguage;
		
	@Name("Сохранить")
	@FindBy(css = "input[type=submit]")
	private Button btnSubmit;
			
	public LanguageSettingsPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Set current language
	 * @param lang - language
	 * @return settings page wrapper
	 * @see SettingsPage
	 */
	public SettingsPage setLanguage(Language lang) {
		selLanguage.selectByValue(lang.name().toLowerCase());
		btnSubmit.click();
		return new SettingsPage(getDriver());
	}

}
