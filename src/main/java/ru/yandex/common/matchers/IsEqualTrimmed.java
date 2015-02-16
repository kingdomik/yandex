package ru.yandex.common.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEqualTrimmed extends TypeSafeMatcher<String> {

    private final String string;

    public IsEqualTrimmed(String string) {
        this.string = string;
    }

    @Override
    public boolean matchesSafely(String item) {
        return string.trim().equals(item.trim());
    }
    
    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
      mismatchDescription.appendText("was  ").appendText(item.trim());
    }
    
    public void describeTo(Description description) {
        description.appendText("equal to trimmed ")
                .appendValue(string)
                .appendText(" ");
    }

}
