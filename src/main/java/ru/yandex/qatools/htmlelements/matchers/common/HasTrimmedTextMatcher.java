package ru.yandex.qatools.htmlelements.matchers.common;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.WebElement;

import static org.hamcrest.Matchers.is;

public class HasTrimmedTextMatcher extends TypeSafeMatcher<WebElement> {
    private final Matcher<String> textMatcher;

    HasTrimmedTextMatcher(Matcher<String> textMatcher) {
        this.textMatcher = textMatcher;
    }

    @Override
    public boolean matchesSafely(WebElement item) {
        return textMatcher.matches(item.getText().trim());
    }

    public void describeTo(Description description) {
        description.appendText("element text ").appendDescriptionOf(textMatcher);
    }

    @Override
    protected void describeMismatchSafely(WebElement item, Description mismatchDescription) {
        mismatchDescription.
                appendText("trimmed text of element ").
                appendValue(item).
                appendText(" was ").
                appendValue(item.getText());
    }

    @Factory
    public static Matcher<WebElement> hasTrimmedText(final Matcher<String> textMatcher) {
        return new HasTextMatcher(textMatcher);
    }

    @Factory
    public static Matcher<WebElement> hasTrimmedText(final String text) {
        return new HasTextMatcher(is(text));
    }
}
