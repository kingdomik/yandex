package ru.yandex.pogoda.tests;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

import org.junit.Test;

import ru.yandex.pogoda.wi.GoogleResultsPage;
import ru.yandex.pogoda.wi.GoogleSearchPage;

public class MyTest {

	@Test
	public void test() {		
		open("https://pogoda.yandex.ru");
		GoogleSearchPage searchPage = open("http://google.com", GoogleSearchPage.class);
		GoogleResultsPage resultsPage = searchPage.search("selenide");
		resultsPage.results().shouldHave(size(8));
		resultsPage.results().get(0)
		        .shouldHave(text("Selenide: Concise UI Tests in Java"));
	}

}