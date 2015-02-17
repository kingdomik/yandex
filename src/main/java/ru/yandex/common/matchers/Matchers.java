package ru.yandex.common.matchers;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.hamcrest.Matcher;

public class Matchers {

    public static Matcher<String> containsIgnoreCase(String string) {
        return new ContainsIgnoreCase(string);
    }
	
    public static Matcher<String> equalToTrimmed(String string) {
        return new IsEqualTrimmed(string);
    }
	
	public static Matcher<LocalTime> equalWithDelltaTo(LocalTime time, ChronoUnit unit, int delta) {
		return new IsEqualWithDelta(time, unit, delta);
	}
	
}
