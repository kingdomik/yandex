package ru.yandex.common.matchers;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.SubstringMatcher;

public class StringTrimmedEqualTo extends SubstringMatcher {
    public StringTrimmedEqualTo(String substring) {
        super(substring);
    }

    @Override
    protected boolean evalSubstringOf(String s) {
        return s.equals(substring.trim());
    }

    @Override
    protected String relationship() {
        return "trimmed equal to";
    }

    @Factory
    public static Matcher<String> equalTrimmedTo(String string) {
        return new StringTrimmedEqualTo(string);
    }

}
