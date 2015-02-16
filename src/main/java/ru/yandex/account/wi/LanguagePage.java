package ru.yandex.account.wi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.common.wi.Page;
import ru.yandex.pogoda.wi.lang.Language;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Select;

public class LanguagePage extends Page {

	@Name("Язык")
	@FindBy(name = "intl")
	private Select selLanguage;
		
	@Name("Результаты")
	@FindBy(css = "input[type=submit]")
	private Button btnSubmit;
			
	public LanguagePage(WebDriver driver) {
		super(driver);
	}
	
	public SettingsPage setLanguage(Language lang) {
		selLanguage.selectByValue(lang.name().toLowerCase());
		btnSubmit.click();
		return new SettingsPage(getDriver());
	}

}
