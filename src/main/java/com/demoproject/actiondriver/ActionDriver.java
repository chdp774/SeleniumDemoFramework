package com.demoproject.actiondriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.demoproject.base.BaseClass;
import com.demoproject.utilities.ExtentManager;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
		logger.info("WebDriver instance is created.");
	}
	
	// Static wait
	public void waitFor(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void launchSite(String url) {
		try {
			driver.get(url);
		} catch (Exception e) {
			System.out.println("Failed to navigate to the URL: " + e.getMessage());
		}
	}

	// Method to click an element
	public void click(By by) {
		String elementDescription = getElementDescription(by);
		try {
			waitForElementToBeClickable(by);
			applyBorder(by, "green");
			driver.findElement(by).click();
			ExtentManager.logStep("Clicked on element --> " + elementDescription);
			logger.info("Clicked on element --> " + elementDescription);
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to click element: " + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to click element: ",
					elementDescription + "_Uable to click" + e.getMessage());
			Assert.fail();
		}
	}

	// Method to enter text into an input field --> to avoid duplication updated the
	// method
	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by, "green");
//			driver.findElement(by).clear();
//			driver.findElement(by).sendKeys(value);
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(value);
			ExtentManager.logStep("Text entered on --> " + getElementDescription(by) + " --> " + value);
			logger.info("Text entered on " + getElementDescription(by) + " --> " + value);
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to enter the value: " + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), "Element isn't present", "Element is not present");
			Assert.fail();
		}
	}

	// Method to get text from input field
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by, "green");
			return driver.findElement(by).getText();
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to get the text: " + e.getMessage());
			return "";
		}
	}

	// Method to compare Two text -- changed the return type
	public boolean compareText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			String actualText = driver.findElement(by).getText();
			if (actualText.equals(expectedText)) {
				applyBorder(by, "green");
				logger.info("Text are Matching: " + actualText + " equals " + expectedText);
				ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Compare text",
						"Text verified successfully! " + actualText + " equals " + expectedText);
				return true;
			} else {
				applyBorder(by, "red");
				logger.error("Text are not Matching: " + actualText + " not equals " + expectedText);
				ExtentManager.logFailure(BaseClass.getDriver(), "Text Comparsion Failed!",
						"Text comparison Failed! " + actualText + " not equals " + expectedText);
				return false;
			}
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to compare text: " + e.getMessage());
			return false;
		}
	}

	// Method to check if an element is displayed
	/*
	 * public boolean isDisplayed(By by) { try { boolean isDisplayed =
	 * driver.findElement(by).isDisplayed(); if(isDisplayed) {
	 * System.out.println("Element is visible"); return isDisplayed; }else { return
	 * isDisplayed; } } catch (Exception e) {
	 * System.out.println("Element is not visible: "+e.getMessage()); return false;
	 * } }
	 */

	// Simplified the method and removed redundant conditions
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by, "green");
			logger.info("Element is displayed: " + getElementDescription(by));
			ExtentManager.logStep("Verifying element: " + getElementDescription(by));
			ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Element verification successful! ",
					"Element is displayed: " + getElementDescription(by));
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Element is not displayed: " + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), "Element is not displayed: ",
					"Element is not displayed: " + getElementDescription(by) + e.getMessage());
			Assert.fail();
			return false;
		}
	}

	// Wait for the page to load
	public void waitForPageLoad(int timeOutInSec) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(WebDriver -> ((JavascriptExecutor) WebDriver)
					.executeScript("return document.readyState").equals("complete"));
			logger.info("Page loaded successfully");
		} catch (Exception e) {
			logger.error("Page did not loaded within: " + timeOutInSec + "seconds");
		}
	}

	// Scroll to element using JS
	public void scrollToElement(By by) {
		try {
			applyBorder(by, "green");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguments[0],scrollIntoView(true);", element);
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to locate element: " + e.getMessage());
		}
	}

	// Wait for element to be clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("Element is not clickable: " + e.getMessage());
		}
	}

	// Wait for element to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.error("Element is not visible: " + e.getMessage());
		}
	}

	// Method to get description of an element using By locator
	private String getElementDescription(By locator) {
		// Check for null driver or locator to avoid NullPointerExeception
		if (driver == null) {
			return "driver is NULL";
		}
		if (locator == null) {
			return "locator is NULL";
		}
		try {
			// Find element using locator
			WebElement element = driver.findElement(locator);

			// Get element attribute
			String name = element.getDomAttribute("name");
			String id = element.getDomAttribute("id");
			String text = element.getText();
			String className = element.getDomAttribute("class");
			String placeHolder = element.getDomAttribute("placeholder");

			// Return description based on element attribute
			if (isNotEmpty(name)) {
				return "Element with name " + name;
			}
			if (isNotEmpty(id)) {
				return "Element with name " + name;
			}
			if (isNotEmpty(text)) {
				return "Element with name " + truncate(text, 50);
			}
			if (isNotEmpty(className)) {
				return "Element with name " + className;
			}
			if (isNotEmpty(placeHolder)) {
				return "Element with name " + placeHolder;
			}
		} catch (Exception e) {
			logger.error("Unable to describe the element " + e.getMessage());
		}
		return "Unable to describe the element";
	}

	// Utility method to check a string is Null or Empty
	private boolean isNotEmpty(String value) {
		return value != null && !value.isEmpty();
	}

	// Utility method to truncate the String
	private String truncate(String value, int maxLen) {
		if (value == null || value.length() <= maxLen) {
			return value;
		}
		return value.substring(0, maxLen) + "...";
	}

	// Utility method to Border the element
	
//	public void applyBorder(By by, String color) {
//	    try {
//	        WebElement element = driver.findElement(by);
//	        JavascriptExecutor js = (JavascriptExecutor) driver;
//	        
//	        // Blink border 3 times
//	        for (int i = 0; i < 3; i++) {
//	            js.executeScript("arguments[0].style.border='3px solid " + color + "'", element);
//	            Thread.sleep(300); // Wait 300ms
//	            js.executeScript("arguments[0].style.border=''", element);
//	            Thread.sleep(300); // Wait 300ms
//	        }
//
//	        // Apply final border
//	        js.executeScript("arguments[0].style.border='3px solid " + color + "'", element);
//
//	        // Wait for 2 seconds and remove border
//	        Thread.sleep(2000);
//	        js.executeScript("arguments[0].style.border=''", element);
//
////	        logger.info("✔ Applied blinking border with color " + color + " to element: " + getElementDescription(by));
//	    } catch (Exception e) {
//	        logger.warn("❌ Failed to apply blinking border to element: " + getElementDescription(by) + 
//	                    " | Exception: " + e.getMessage());
//	    }
//	}
	public void applyBorder(By by, String color) {
		try {
			// Locate element
			WebElement element = driver.findElement(by);
			// Apply border
			String script = "arguments[0].style.border='3px solid " + color + "'";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(script, element);
//			js.executeScript("arguments[0].style.border=''", element);
			// logger.info("Appiled the border with color " + color + " to element " + getElementDescription(by));
		} catch (Exception e) {
			// logger.warn("Failed to apply the border to an element: " + getElementDescription(by));
		}
	}

	// ===================== Select Methods =====================

	// Method to select a dropdown by visible text
	public void selectByVisibleText(By by, String value) {
		try {
			WebElement element = driver.findElement(by);
			new Select(element).selectByVisibleText(value);
			applyBorder(by, "green");
			logger.info("Selected dropdown value: " + value);
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to select dropdown value: " + value, e);
		}
	}

	// Method to select a dropdown by value
	public void selectByValue(By by, String value) {
		try {
			WebElement element = driver.findElement(by);
			new Select(element).selectByValue(value);
			applyBorder(by, "green");
			logger.info("Selected dropdown value by actual value: " + value);
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to select dropdown by value: " + value, e);
		}
	}

	// Method to select a dropdown by index
	public void selectByIndex(By by, int index) {
		try {
			WebElement element = driver.findElement(by);
			new Select(element).selectByIndex(index);
			applyBorder(by, "green");
			logger.info("Selected dropdown value by index: " + index);
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to select dropdown by index: " + index, e);
		}
	}

	// Method to get all options from a dropdown
	public List<String> getDropdownOptions(By by) {
		List<String> optionsList = new ArrayList<>();
		try {
			WebElement dropdownElement = driver.findElement(by);
			Select select = new Select(dropdownElement);
			for (WebElement option : select.getOptions()) {
				optionsList.add(option.getText());
			}
			applyBorder(by, "green");
			logger.info("Retrieved dropdown options for " + getElementDescription(by));
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to get dropdown options: " + e.getMessage());
		}
		return optionsList;
	}

	// ===================== JavaScript Utility Methods =====================

	// Method to click using JavaScript
	public void clickUsingJS(By by) {
		try {
			WebElement element = driver.findElement(by);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			applyBorder(by, "green");
			logger.info("Clicked element using JavaScript: " + getElementDescription(by));
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to click using JavaScript", e);
		}
	}

	// Method to scroll to the bottom of the page
	public void scrollToBottom() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
		logger.info("Scrolled to the bottom of the page.");
	}

	// Method to highlight an element using JavaScript
	public void highlightElementJS(By by) {
		try {
			WebElement element = driver.findElement(by);
			((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid yellow'", element);
			logger.info("Highlighted element using JavaScript: " + getElementDescription(by));
		} catch (Exception e) {
			logger.error("Unable to highlight element using JavaScript", e);
		}
	}

	// ===================== Window and Frame Handling =====================

	// Method to switch between browser windows
	public void switchToWindow(String windowTitle) {
		try {
			Set<String> windows = driver.getWindowHandles();
			for (String window : windows) {
				driver.switchTo().window(window);
				if (driver.getTitle().equals(windowTitle)) {
					logger.info("Switched to window: " + windowTitle);
					return;
				}
			}
			logger.warn("Window with title " + windowTitle + " not found.");
		} catch (Exception e) {
			logger.error("Unable to switch window", e);
		}
	}

	// Method to switch to an iframe
	public void switchToFrame(By by) {
		try {
			driver.switchTo().frame(driver.findElement(by));
			logger.info("Switched to iframe: " + getElementDescription(by));
		} catch (Exception e) {
			logger.error("Unable to switch to iframe", e);
		}
	}

	// Method to switch back to the default content
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
		logger.info("Switched back to default content.");
	}

	// ===================== Alert Handling =====================

	// Method to accept an alert popup
	public void acceptAlert() {
		try {
			driver.switchTo().alert().accept();
			logger.info("Alert accepted.");
		} catch (Exception e) {
			logger.error("No alert found to accept", e);
		}
	}

	// Method to dismiss an alert popup
	public void dismissAlert() {
		try {
			driver.switchTo().alert().dismiss();
			logger.info("Alert dismissed.");
		} catch (Exception e) {
			logger.error("No alert found to dismiss", e);
		}
	}

	// Method to get alert text
	public String getAlertText() {
		try {
			return driver.switchTo().alert().getText();
		} catch (Exception e) {
			logger.error("No alert text found", e);
			return "";
		}
	}

	// ===================== Browser Actions =====================

	public void refreshPage() {
		try {
			driver.navigate().refresh();
			ExtentManager.logStep("Page refreshed successfully.");
			logger.info("Page refreshed successfully.");
		} catch (Exception e) {
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to refresh page", "refresh_page_failed");
			logger.error("Unable to refresh page: " + e.getMessage());
		}
	}

	public String getCurrentURL() {
		try {
			String url = driver.getCurrentUrl();
			ExtentManager.logStep("Current URL fetched: " + "<span style='color:orange; font-weight:600;'>" + url + "</span>");
			logger.info("Current URL fetched: " + url);
			return url;
		} catch (Exception e) {
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to fetch current URL", "get_current_url_failed");
			logger.error("Unable to fetch current URL: " + e.getMessage());
			return null;
		}
	}

	public void maximizeWindow() {
		try {
			driver.manage().window().maximize();
			ExtentManager.logStep("Browser window maximized.");
			logger.info("Browser window maximized.");
		} catch (Exception e) {
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to maximize window", "maximize_window_failed");
			logger.error("Unable to maximize window: " + e.getMessage());
		}
	}

	// ===================== Advanced WebElement Actions =====================

	public void moveToElement(By by) {
		String elementDescription = getElementDescription(by);
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(by)).perform();
			ExtentManager.logStep("Moved to element: " + elementDescription);
			logger.info("Moved to element --> " + elementDescription);
		} catch (Exception e) {
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to move to element",
					elementDescription + "_move_failed");
			logger.error("Unable to move to element: " + e.getMessage());
		}
	}

	public void dragAndDrop(By source, By target) {
		String sourceDescription = getElementDescription(source);
		String targetDescription = getElementDescription(target);
		try {
			Actions actions = new Actions(driver);
			actions.dragAndDrop(driver.findElement(source), driver.findElement(target)).perform();
			ExtentManager.logStep("Dragged element: " + sourceDescription + " and dropped on " + targetDescription);
			logger.info("Dragged element: " + sourceDescription + " and dropped on " + targetDescription);
		} catch (Exception e) {
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to drag and drop",
					sourceDescription + "_drag_failed");
			logger.error("Unable to drag and drop: " + e.getMessage());
		}
	}

	public void doubleClick(By by) {
		String elementDescription = getElementDescription(by);
		try {
			Actions actions = new Actions(driver);
			actions.doubleClick(driver.findElement(by)).perform();
			ExtentManager.logStep("Double-clicked on element: " + elementDescription);
			logger.info("Double-clicked on element --> " + elementDescription);
		} catch (Exception e) {
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to double-click element",
					elementDescription + "_doubleclick_failed");
			logger.error("Unable to double-click element: " + e.getMessage());
		}
	}

	public void rightClick(By by) {
		String elementDescription = getElementDescription(by);
		try {
			Actions actions = new Actions(driver);
			actions.contextClick(driver.findElement(by)).perform();
			ExtentManager.logStep("Right-clicked on element: " + elementDescription);
			logger.info("Right-clicked on element --> " + elementDescription);
		} catch (Exception e) {
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to right-click element",
					elementDescription + "_rightclick_failed");
			logger.error("Unable to right-click element: " + e.getMessage());
		}
	}

	public void sendKeysWithActions(By by, String value) {
		String elementDescription = getElementDescription(by);
		try {
			Actions actions = new Actions(driver);
			actions.sendKeys(driver.findElement(by), value).perform();
			ExtentManager.logStep("Sent keys to element: " + elementDescription + " | Value: " + value);
			logger.info("Sent keys to element --> " + elementDescription + " | Value: " + value);
		} catch (Exception e) {
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to send keys",
					elementDescription + "_sendkeys_failed");
			logger.error("Unable to send keys to element: " + e.getMessage());
		}
	}

	public void clearText(By by) {
		String elementDescription = getElementDescription(by);
		try {
			driver.findElement(by).clear();
			ExtentManager.logStep("Cleared text in element: " + elementDescription);
			logger.info("Cleared text in element --> " + elementDescription);
		} catch (Exception e) {
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to clear text",
					elementDescription + "_clear_failed");
			logger.error("Unable to clear text in element: " + e.getMessage());
		}
	}

	// Method to upload a file
	public void uploadFile(By by, String filePath) {
		try {
			driver.findElement(by).sendKeys(filePath);
			applyBorder(by, "green");
			logger.info("Uploaded file: " + filePath);
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to upload file: " + e.getMessage());
		}
	}
	
	// Method to get page title
	public boolean validatePageTitle(String expectedTitle) {
	    try {
	        String actualTitle = driver.getTitle();
	        
	        // Logging the actual and expected titles
	        logger.info("Verifying Page Title. Expected: " + expectedTitle + ", Actual: " + actualTitle);
	        ExtentManager.logStep("Verifying Page Title. Expected: " + expectedTitle + ", Actual: " + actualTitle);
	        ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Verifying Page Title",
	                "Expected: " + expectedTitle + ", Actual: " + actualTitle);
	        
	        if (actualTitle.equalsIgnoreCase(expectedTitle)) {
	            logger.info("Page title is correct: " + actualTitle);
	            ExtentManager.logStep("Page title is correct: " + actualTitle);
	            return true;
	        } else {
	            logger.warn("Page title mismatch! Expected: " + expectedTitle + ", but found: " + actualTitle);
	            ExtentManager.logFailure(BaseClass.getDriver(), "Page title mismatch",
	                    "Expected: " + expectedTitle + ", but found: " + actualTitle);
	            return false;
	        }
	    } catch (Exception e) {
	        logger.error("Exception in verifying page title: " + e.getMessage());
	        ExtentManager.logFailure(BaseClass.getDriver(), "Exception in verifying page title",
	                "Exception: " + e.getMessage());
	        return false;
	    }
	}
	
	

}
