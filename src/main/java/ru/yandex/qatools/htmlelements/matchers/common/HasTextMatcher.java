package ru.yandex.qatools.htmlelements.matchers.common;

import static org.hamcrest.Matchers.is;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import ru.yandex.qatools.htmlelements.element.TextBlock;

public class HasTextMatcher extends TypeSafeMatcher<TextBlock> {
    private final Matcher<String> textMatcher;

    public HasTextMatcher(Matcher<String> textMatcher) {
        this.textMatcher = textMatcher;
    }

    @Override
    protected boolean matchesSafely(TextBlock textBlock) {
        return textMatcher.matches(textBlock.getText());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("text block value ").appendDescriptionOf(textMatcher);
    }

    @Override
    protected void describeMismatchSafely(TextBlock textInput, Description mismatchDescription) {
        mismatchDescription.appendText("text block ").
                appendValue(textInput).
                appendText("value was ").
                appendValue(textInput.getText());
    }

    /**
     * Creates matcher that matches text entered in {@link TextInput} with specified matcher.
     *
     * @param textMatcher Matcher to match entered text with.
     */
    @Factory
    public static Matcher<TextBlock> hasEnteredText(final Matcher<String> textMatcher) {
        return new HasTextMatcher(textMatcher);
    }

    /**
     * Creates matcher that checks if text entered in {@link TextInput} equals to given text.
     *
     * @param text Expected entered text.
     */
    @Factory
    public static Matcher<TextBlock> hasText(final String text) {
        return new HasTextMatcher(is(text));
    }
}
