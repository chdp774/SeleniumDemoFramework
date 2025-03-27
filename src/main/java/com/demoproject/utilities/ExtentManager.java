package com.demoproject.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap = new HashMap<>();
//	private static final String REPORT_PATH = "test-output/ExtentReports/";
	private static final String HISTORY_FILE = "test-output/execution_history.json";
	
	// Initialize the Extent report
	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
			String reportPath = System.getProperty("user.dir")+"/src/test/resources/ExtentReport/ExtentReport" + "_" + timeStamp + ".html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setReportName("Automation Test Report");
			spark.config().setDocumentTitle("Nestle Automation Report");
			spark.config().setTheme(Theme.DARK);
			
			extent = new ExtentReports();
			extent.attachReporter(spark);
			// Adding system information
			extent.setSystemInfo("Operating System", System.getProperty("os.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("User name", System.getProperty("user.name"));
			
			// Save execution history when the report is generated
	        saveExecutionHistory(reportPath);
		}
		return extent;
	}
	
	private static void saveExecutionHistory(String latestReport) {
        JSONArray historyArray = new JSONArray();
        File file = new File(HISTORY_FILE);

        try {
            if (file.exists()) {
                historyArray = new JSONArray(new String(java.nio.file.Files.readAllBytes(file.toPath())));
            }
            
            JSONObject reportEntry = new JSONObject();
            reportEntry.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            reportEntry.put("reportPath", latestReport);
            
            historyArray.put(reportEntry);

            if (historyArray.length() > 5) {  // Keep only the last 5 executions
                historyArray.remove(0);
            }

            try (FileWriter writer = new FileWriter(HISTORY_FILE)) {
                writer.write(historyArray.toString(4));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	// Start the test
	public synchronized static ExtentTest startTest(String testName) {
		ExtentTest extentTest = getReporter().createTest(testName);
		test.set(extentTest);
		return extentTest;
	}
	
	// End the test
	public synchronized static void endTest() {
		getReporter().flush();
	}
	
	// Get the current thread's test
	public synchronized static ExtentTest getTest() {
		return test.get();
	}
	
	// Method to get the name of the current test
	public static String getTestName() {
		ExtentTest currentTest = getTest();
		if(currentTest != null) {
			return currentTest.getModel().getName();
		}
		else {
			return "No test is currently active for this thread";
		}
	}
	
	// Log a step
	public static void logStep(String logMessage) {
		getTest().info(logMessage);
	}
	
	// Log a step validation with Screenshot
	public static void logStepWithScreenshot(WebDriver driver, String logMessage, String screenShotMessage) {
		String colorMessage = String.format("<span style='color:green;'>%s</span>", logMessage);
		getTest().pass(colorMessage);
		//Screenshot message
		attachScreenshot(driver, screenShotMessage);
	}
	
	// Log a Failure
	public static void logFailure(WebDriver driver, String logMessage, String screenShotMessage) {
		String colorMessage = String.format("<span style='color:red;'>%s</span>", logMessage);
		getTest().fail(colorMessage);
		//Screenshot message
		attachScreenshot(driver, screenShotMessage);
	}
		
	// Log a Skip
	public static void logSkip(String logMessage) {
		String colorMessage = String.format("<span style='color:orange;'>%s</span>", logMessage);
		getTest().skip(colorMessage);
	}
	
	// Take screenshot with data and time in a file
	public synchronized static String takeScreenshot(WebDriver driver, String screenshotName) {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		
		// Format date and time for file name
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		
		String destPath = System.getProperty("user.dir")+"/src/test/resources/screenshots/" + screenshotName + "_" + timeStamp + ".png";
		
		File finalPath = new File(destPath);
		try {
			FileUtils.copyFile(src, finalPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Convert screenshot to Base64 for embedding in the report
		String base64Format = convertToBase64(src) ;
		return base64Format;
	}
	
	// Convert screenshot to base64 format
	public static String convertToBase64(File screenshotFile) {
		String base64Format = "";
		
		//Read file content into a byte Array
		try {
			byte[] fileContent = FileUtils.readFileToByteArray(screenshotFile);
			base64Format = Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return base64Format;
	}
	
	// Attache screenshot to report using Base64
	public synchronized static void attachScreenshot(WebDriver driver, String message) {
		try {
			String screenshotBase64 = takeScreenshot(driver, getTestName());
			getTest().info(message, com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
		} catch (Exception e) {
			getTest().fail("Failed to attach screenshot: " +message);
			e.printStackTrace();
		}
	}
	
	// Register WebDriver for current Thread
	public static void registerDriver(WebDriver driver) {
		driverMap.put(Thread.currentThread().getId(), driver);
	}
}
