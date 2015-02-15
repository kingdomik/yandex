package ru.yandex.pogoda.common;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class Matchers {

	public static Matcher<LocalTime> equalWithDelltaTo(final LocalTime expected, 
			final ChronoUnit unit, final int delta) {
		 return new TypeSafeDiagnosingMatcher<LocalTime>() {
		      @Override
		      public void describeTo(final Description description) {
		         description
		         	.appendText("date should be ")
		         	.appendValue(expected)
		         	.appendText(" with delta " + delta + " " + unit);
		      }
		 
		      @Override
		      protected boolean matchesSafely(final LocalTime item, final Description mismatchDescription) {
		         mismatchDescription.appendText(" was ").appendValue(item);
		         return unit.between(item, expected) <= delta;
		      }
		   };
	}
	
}
