package ru.yandex.common.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ContainsIgnoreCase extends TypeSafeMatcher<String> {

    private final String string;

    public ContainsIgnoreCase(String string) {
        this.string = string;
    }

    @Override
    public boolean matchesSafely(String item) {
        return string.toLowerCase().indexOf(item.toLowerCase()) != -1;
    }
    
    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
      mismatchDescription.appendText("was  ").appendText(item.trim());
    }
    
    public void describeTo(Description description) {
        description.appendText("contain (ignore case) ")
                .appendValue(string)
                .appendText(" ");
    }

}
