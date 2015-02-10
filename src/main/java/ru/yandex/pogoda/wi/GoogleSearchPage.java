package ru.yandex.pogoda.wi;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import org.openqa.selenium.By;

public class GoogleSearchPage {
	public GoogleResultsPage search(String query) {
		$(By.name("q")).setValue(query).pressEnter();
		return page(GoogleResultsPage.class);
	}
}