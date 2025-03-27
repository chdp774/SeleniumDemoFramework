package com.demoproject.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.demoproject.actiondriver.ActionDriver;
import com.demoproject.utilities.ExecutionHistoryManager;
import com.demoproject.utilities.ExtentManager;
import com.demoproject.utilities.LoggerManager;

import com.aventstack.extentreports.Status;

public class BaseClass {

	protected static Properties prop;
//	protected static WebDriver driver;
//	private static ActionDriver actionDriver;

	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();

	@BeforeSuite
	public void loadConfig() throws IOException {
		// Load the configuration file
		prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/resources/config.properties");
		prop.load(fis);
		logger.info("Config.properties file loaded");

		// Start the Extent report test
		// ExtentManager.getReporter(); --> This has been implemented in TestListener
	}

	@BeforeMethod
	public synchronized void setup() {
		logger.info("Setting up WebDriver for: " + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(2);
		logger.info("WebDriver initialized and Browser Maximized");

		// Initialize the ActionDriver only once
		/*
		 * if(actionDriver == null) { actionDriver = new ActionDriver(driver);
		 * logger.info("ActionDriver instance is created." +
		 * Thread.currentThread().getId()); }
		 */

		// Initialize ActionDriver for the current thread
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("ActionDriver initialized for thread" + Thread.currentThread().getId());
	}

	// Initialize the WebDriver based on browser defined in Config.properties file
	private synchronized void launchBrowser() {

		String browser = prop.getProperty("browser");
		if (browser.equalsIgnoreCase("chrome")) {

			// Create chrome options
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless"); // Run chrome in headless mode
			options.addArguments("--disable-gpu"); // Disable GPU for headless mode
			// options.addArguments("--window-size=1920,1080"); // Set window size
			options.addArguments("--disable-notifications"); // Disable browser notifications
			options.addArguments("--no-sandbox"); // Required for some CI environments like Jenkins
			options.addArguments("--disable-dev-shm-usage"); // Resolve issues in resource-limited environments

			// driver = new ChromeDriver();
			driver.set(new ChromeDriver(options)); // New changes as per Thread
			ExtentManager.registerDriver(getDriver());
			logger.info("ChromeDriver instance is created.");
		} 
		else if (browser.equalsIgnoreCase("firefox")) {

			// Create FirefoxOptions
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--headless"); 				// Run Firefox in headless mode
			options.addArguments("--disable-gpu"); 				// Disable GPU rendering (useful for headless mode)
			options.addArguments("--width=1920"); 				// Set browser width
			options.addArguments("--height=1080"); 				// Set browser height
			options.addArguments("--disable-notifications"); 	// Disable browser notifications
			options.addArguments("--no-sandbox"); 				// Needed for CI/CD environments
			options.addArguments("--disable-dev-shm-usage"); 	// Prevent crashes in low-resource environments

			// driver = new FirefoxDriver();
			driver.set(new FirefoxDriver(options)); // New changes as per Thread
			ExtentManager.registerDriver(getDriver());
			logger.info("FirefoxDriver instance is created.");
		} 
		else if (browser.equalsIgnoreCase("edge")) {
			
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--headless"); 				// Run Edge in headless mode
			options.addArguments("--disable-gpu"); 				// Disable GPU acceleration
			options.addArguments("--window-size=1920,1080"); 	// Set window size
			options.addArguments("--disable-notifications"); 	// Disable pop-up notifications
			options.addArguments("--no-sandbox"); 				// Needed for CI/CD
			options.addArguments("--disable-dev-shm-usage"); 	// Prevent resource-limited crashes

			// driver = new EdgeDriver();
			driver.set(new EdgeDriver(options)); // New changes as per Thread
			ExtentManager.registerDriver(getDriver());
			logger.info("EdgeDriver instance is created.");
		} 
		else {
			throw new IllegalArgumentException("Browser Not Supported: " + browser);
		}

	}

	/*
	 * Configured browser setting such as implicit wait, maximize the browser and
	 * navigate to URL
	 */

	private void configureBrowser() {
		// Implicit Wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// Maximize the browser
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();

		// Navigate to URL
		
//		String url = prop.getProperty("url");
//		try {
//			getDriver().get(url);
//		} catch (Exception e) {
//			System.out.println("Failed to navigate to the URL: " + e.getMessage());
//		}
		
	}

	@AfterMethod
	public synchronized void tearDown() {
		if (getDriver() != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				System.out.println("Unable to quit the browser: " + e.getMessage());
			}
		}
		logger.info("WebDriver Instance is closed.");
		// driver = null;
		// actionDriver = null;
		driver.remove();
		actionDriver.remove();

		// ExtentManager.endTest(); --> This has been implemented in TestListener
	}

	/*
	 * //Driver getter method public WebDriver getDriver() { return driver; }
	 * 
	 * //Driver setter method public void setDriver(WebDriver driver) { this.driver
	 * = driver; }
	 */

	// Getter method of WebDriver
	public static WebDriver getDriver() {
		if (driver.get() == null) {
			System.out.println("WebDriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}
		return driver.get();
	}

	// Getter method of ActionDriver
	public static ActionDriver getActionDriver() {
		if (actionDriver.get() == null) {
			System.out.println("ActionDriver is not initialized");
			throw new IllegalStateException("ActionDriver is not initialized");
		}
		return actionDriver.get();
	}

	// Getter method for prop
	public static Properties getProp() {
		return prop;
	}

	// Driver setter method
	public void setDriver(ThreadLocal<WebDriver> driver) {
		this.driver = driver;
	}

	// Static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}
	
	 @AfterSuite
	 public void tearDownreport() {
		 Map<Status, Long> stats = ExtentManager.getReporter().getStats().getParent();

	        int passed = stats.getOrDefault(Status.PASS, 0L).intValue();
	        int failed = stats.getOrDefault(Status.FAIL, 0L).intValue();
	        int skipped = stats.getOrDefault(Status.SKIP, 0L).intValue();

	        ExecutionHistoryManager.saveExecutionHistory(passed, failed, skipped);
	    }
}
