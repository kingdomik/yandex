package ru.yandex.qatools.htmlelements.matchers.common;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.WebElement;

import static org.hamcrest.Matchers.is;

public class HasCssMatcher extends TypeSafeMatcher<WebElement> {
    private final String cssName;
    private final Matcher<String> cssValueMatcher;

    public HasCssMatcher(String cssName, Matcher<String> cssValueMatcher) {
        this.cssName = cssName;
        this.cssValueMatcher = cssValueMatcher;
    }

    @Override
    public boolean matchesSafely(WebElement item) {
        return cssValueMatcher.matches(item.getCssValue(cssName));
    }

    public void describeTo(Description description) {
        description.appendText("css ").
                appendValue(cssName).
                appendText(" ").
                appendDescriptionOf(cssValueMatcher);
    }

    @Override
    protected void describeMismatchSafely(WebElement item, Description mismatchDescription) {
        mismatchDescription.appendText("css ").
                appendValue(cssName).
                appendText(" of element ").
                appendValue(item).
                appendText(" was ").
                appendValue(item.getAttribute(cssName));
    }

    @Factory
    public static Matcher<WebElement> hasCss(final String cssName,
                                                   final Matcher<String> cssValueMatcher) {
        return new HasCssMatcher(cssName, cssValueMatcher);
    }

    @Factory
    public static Matcher<WebElement> hasCss(final String cssName, final String cssValue) {
        return new HasCssMatcher(cssName, is(cssValue));
    }
}
