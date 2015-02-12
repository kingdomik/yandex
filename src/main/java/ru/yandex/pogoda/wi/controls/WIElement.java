package ru.yandex.pogoda.wi.controls;

import static ru.odnoklassniki.tests.ui.api.Messages.LOG_ELEMENT_INVISIBLE;
import static ru.odnoklassniki.tests.ui.api.Messages.LOG_ELEMENT_VISIBLE;
import static ru.odnoklassniki.tests.ui.api.Messages.LOG_ELEMENT_WAIT_INVISIBLE;
import static ru.odnoklassniki.tests.ui.api.Messages.LOG_ELEMENT_WAIT_VISIBLE;
import static ru.odnoklassniki.tests.ui.api.Messages.TEST_EXPECTED_INVISIBLE;
import static ru.odnoklassniki.tests.ui.api.Messages.TEST_EXPECTED_VISIBLE;

import ru.yandex.pogoda.common.Validate;

import org.testng.Assert;

import ru.odnoklassniki.tests.common.Loggers;
import ru.odnoklassniki.tests.common.Requirements;
import ru.odnoklassniki.tests.common.Utils;
import ru.odnoklassniki.tests.common.Wait;
import ru.odnoklassniki.tests.ui.api.WIBrowser;
import ru.odnoklassniki.tests.ui.api.common.IWIRoad;
import ru.odnoklassniki.tests.ui.api.common.WIDefaultRoad;

import com.thoughtworks.selenium.SeleniumException;

/**
 * Base class fro all web elements
 *
 */
public class WIElement {

	private String locator;
	private String name;
	private String type;

	public WIElement(String locator, String name, String type) {
//		Validate.noNull(Elements(iterable)
		Validate.notNull(name, "name");
		Validate.notNull(type, "type");
		this.locator = locator;
		this.name = name;
		this.type = type;
	}

	/**
	 * Returns web element type name
	 * @return type name
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns web element name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getType() + " \"" + getName() + "\"";
	}

	/**
	 * Checks web element visibility
	 * @return visibility
	 */
	public boolean isVisible() {
		// Don't use "return isElementPresent && isVisaible" since
		// isVisible throws an exception if element isn't present
		// and compiler/vm can execute both conditions
		if (getBrowser().isElementPresent(getGlobalID())) {
			// For slow connection element can disappear after
			// isElementPresent call so isVisible will throw
			// SeleniumException
			try {
				return getBrowser().isVisible(getGlobalID());
			} catch (SeleniumException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Assert web element is visible
	 */
	public void assertVisible() {
		if (!isVisible()) {
			Assert.fail(TEST_EXPECTED_VISIBLE.getValue(this));
		}
	}

	/**
	 * Assert web element is invisible
	 */
	public void assertInvisible() {
		if (isVisible()) {
			Assert.fail(TEST_EXPECTED_INVISIBLE.getValue(this));
		}
	}

	/**
	 * Wait web element is visible
	 */
	public void waitVisible() {
		Loggers.ui.info(LOG_ELEMENT_WAIT_VISIBLE.getValue(this));
		new Wait() {
			@Override
			public boolean until() {
				return isVisible();
			}
		}.wait(TEST_EXPECTED_VISIBLE.getValue(this));
		Loggers.ui.info(LOG_ELEMENT_VISIBLE.getValue(this));
	}

	/**
	 * Wait web element is invisible
	 */
	public void waitInvisible() {
		Loggers.ui.info(LOG_ELEMENT_WAIT_INVISIBLE.getValue(this));
		new Wait() {
			@Override
			public boolean until() {
				return !isVisible();
			}
		}.wait(TEST_EXPECTED_INVISIBLE.getValue(this));
		Loggers.ui.info(LOG_ELEMENT_INVISIBLE.getValue(this));
	}

}
