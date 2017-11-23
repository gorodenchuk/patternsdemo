package pageObject.utility;//package pageObject.utility;
//
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import pageObject.webDriver.WebDriverFactory;
import ru.yandex.qatools.allure.annotations.Attachment;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TestExecutionListener extends TestListenerAdapter {

	private static final String SCREENSHOT_FOLDER = "target/screenshots";
	private static final String SCREENSHOT_FORMAT = ".png";
	private static final Logger LOG = LogManager.getLogger(TestExecutionListener.class);

	/**
	 * Prints the Test results to report.
	 *
	 * @param result
	 *            the result
	 */
	private void printTestResults(ITestResult result) {
		if (result.getParameters().length != 0) {
			String params = null;
			for (Object parameter : result.getParameters()) {
				params += parameter.toString() + ",";
			}

			TestStepReporter.reportln(
					"Test Method had the following parameters : ", params);
		}

		String status = null;
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			status = "Pass";
			LOG.info("--- TEST PASS!!!!! ---");
			break;
		case ITestResult.FAILURE:
			status = "Failed";
			LOG.info("--- TEST FAILED!!!!! ---");
			break;
		case ITestResult.SKIP:
			status = "Skipped";
			LOG.info("--- TEST SKIPPED!!!!! ---");
			break;
		}

		TestStepReporter.reportln("Test Status after execution: ", status);
		//takeScreenshot(result);
	}

	public void onTestSkipped(ITestResult arg0) {
		printTestResults(arg0);
	}

	public void onTestSuccess(ITestResult arg0) {
		printTestResults(arg0);
	}

	public void onTestFailure(ITestResult arg0) {
		printTestResults(arg0);
		takeScreenshot(arg0);
		createWebBrowserScreenShot(arg0);
//		makeScreenshot("Failure screenshot");
	}


	public void takeScreenshot(ITestResult result) {
		//creating screenshot folder for Test
		String folder = SCREENSHOT_FOLDER + "/" + result.getName();
		File dir = new File(folder);
			if (!dir.exists()) {
				dir.mkdir();
			}

			try {
				// Pause because sometimes webdriver takes previous page screenshot
				Thread.sleep(3000);
				// Taking webDriver screenshot
				File screenFile = ((TakesScreenshot) WebDriverFactory.getSetDriver()).getScreenshotAs(OutputType.FILE);
				// Setting screenshot file name 'testMethodName_01_12_14_14_11_09.png'
				SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
				String fileName = result.getName() + "_" + formater.format(Calendar.getInstance().getTime()) + SCREENSHOT_FORMAT;
				// Put screen file to appropriate folder
				FileUtils.copyFile(screenFile, new File(dir.getAbsoluteFile() + "/" + fileName));

				File directory = new File(".");
				String cannonicalScreenshotsPath = directory.getCanonicalPath();
				TestStepReporter.report("<a href=\"file:///" + cannonicalScreenshotsPath + "/" + folder + "/" + fileName + "\"" + " target='_blank' >"
						+ "<p><br/><img src=\"file:///" + cannonicalScreenshotsPath + "/" + folder + "/" + fileName
						+ "\" width=\"600\" height=\"400\" alt=\"\"/>" + "<br/></p></a>");
				LOG.info("Taking screenShot with file name:" + " " + fileName);
			} catch (WebDriverException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}

	@Attachment(value = "Browser screenshot after Test failed", type = "image/png")
	private byte[] createWebBrowserScreenShot(ITestResult result) {
		String currentTestName = TestExecutionListener.getTestName(result);
		BufferedImage image = null;
		LOG.debug("Writing out web browser screenshot on {} Test failure",
				currentTestName);
		WebDriver driver = WebDriverFactory.getSetDriver();
		try {
			// we add this, because sometimes webdriver takes screenshots little
			// bit earlier then needed
			Thread.sleep(1500);
		} catch (InterruptedException e1) {
			LOG.error(e1.getMessage());
		}
		File imageFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			LOG.error(
					"Cannot read browser screenshot created file and convert it to Buffered Image object for Test: "
							+ currentTestName, e);
		}
//		saveScreenshotFileToLog(image, currentTestName,
//				ScreenShot.BrowserScreen.name(), WebBrowser.getSetWebBrowser());

		return TestExecutionListener.getByteArrayFromImage(image,
				"png");
	}


	static String getTestName(ITestResult result){

		return result.getMethod().getMethodName();
	}
	static byte[] getByteArrayFromImage(BufferedImage image, String format) {
		//saveFileToLog(image, testName, screenshotType);
		LOG.debug("Converting screenshot to byte array for report attachment");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, format, baos);
			LOG.info("Successfully wrote screenshot to byte array output stream");
		} catch (IOException e) {
			LOG.error("Cannot write screenshot to ByteArrayOutputStream");
		}
		byte[] imageBytes = baos.toByteArray();

		if (imageBytes.length == 0) {
			String errorMessage = "Converted byte array for screenshot is empty.";
			LOG.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}
		LOG.info("Converted image screenshot to byte array. Byte array size is: "
				+ imageBytes.length);
		return imageBytes;
	}

	}
//	@Attachment(value = "ScreenshotAttachment", type = "image/png")
//	public byte[] createAttachment() {
//		return captureScreenShot();
//	}
//
//	private byte[] captureScreenShot() {
//		try {
//			BufferedImage image  = new AShot().shootingStrategy(new ViewportPastingStrategy(5)).takeScreenshot(driver).getImage();
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ImageIO.write(image, "png", baos);
//			baos.flush();
//			byte[] imageInByte = baos.toByteArray();
//			baos.close();
//			return imageInByte;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return "Attachment Content Empty, can't create screenshot".getBytes();
//	}

