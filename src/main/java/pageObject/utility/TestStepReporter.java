package pageObject.utility;

import org.testng.Reporter;

/**
 * Test Step Reporter for reporting Test steps based on Reporter.log of Test Ng.
 */
public class TestStepReporter {

	/**
	 * Reports the detailed Test step with step summary and details
	 *
	 * @param stepText the Test step summary text
	 * @param stepOutput the step output text
	 */
	public static void report(String stepText, String stepDetails) {
		Reporter.log("<b>" + stepText + "</b>" + "&nbsp" + stepDetails + "<p>", true);
	}

	/**
	 * Reports the detailed Test step with step summary and details, - separates the output with new lines
	 *
	 * @param stepText the Test step summary text
	 * @param stepOutput the step output text
	 */
	public static void reportln(String stepText, String stepDetails) {
		Reporter.log("<p>" + "<b>" + stepText + "</b>" + "&nbsp" + stepDetails + "<p>", true);
	}
	
	/**
	 * Reports the general Test step with step summary
	 *
	 * @param stepText the Test step text
	 */
	public static void report(String stepText) {
		Reporter.log("<b>" + stepText + "</b>" + "<p>", true);
	}
	
	/**
	 * Reports the general Test step with step summary - separates the output with new lines
	 *
	 * @param stepText the Test step text
	 */
	public static void reportln(String stepText) {
		Reporter.log("<p>" + "<b>" + stepText + "</b>" + "&nbsp" + "<p>", true);
	}

}
