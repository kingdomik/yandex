package ru.yandex.qatools.htmlelements.matchers.common;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.WebElement;

public class HasClassMatcher extends TypeSafeMatcher<WebElement> {
    private final String className;

    public HasClassMatcher(String className) {
        this.className = className;
    }

    @Override
    public boolean matchesSafely(WebElement item) {
    	return Arrays.asList(item.getAttribute("class").split(" ")).contains(className);
    }

    public void describeTo(Description description) {
        description.appendText("element has class ").
                appendValue(className);
    }

    @Override
    protected void describeMismatchSafely(WebElement item, Description mismatchDescription) {
        mismatchDescription.appendText("element ").
        		appendValue(item).
        		appendText(" doesn't have class ").
                appendValue(className).
                appendText(" but have ").
                appendValue(item.getAttribute("class"));
    }

    @Factory
    public static Matcher<WebElement> hasClass(final String className) {
        return new HasClassMatcher(className);
    }

}
