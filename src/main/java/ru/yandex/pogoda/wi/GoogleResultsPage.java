package ru.yandex.pogoda.wi;

import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;

public class GoogleResultsPage {
	public ElementsCollection results() {
		return $$("#ires li.g");
	}
}