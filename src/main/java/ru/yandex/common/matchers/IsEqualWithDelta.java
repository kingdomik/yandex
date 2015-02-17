package ru.yandex.common.matchers;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsEqualWithDelta extends TypeSafeDiagnosingMatcher<LocalTime> {
	
    private final LocalTime time;
    private final ChronoUnit unit;
    private final int delta;

    public IsEqualWithDelta(LocalTime time, ChronoUnit unit, int delta) {
        this.time = time;
        this.unit = unit;
        this.delta = delta;
    }

    @Override
    public void describeTo(final Description description) {
       description
       	.appendText("date should be ")
       	.appendValue(time)
       	.appendText(" with delta " + delta + " " + unit);
    }

    @Override
    protected boolean matchesSafely(final LocalTime item, final Description mismatchDescription) {
       mismatchDescription.appendText(" was ").appendValue(item);
       return unit.between(item, time) <= delta;
    }

}
