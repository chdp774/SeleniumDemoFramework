package com.demoproject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.demoproject.actiondriver.ActionDriver;
import com.demoproject.base.BaseClass;

public class LoginPage {
	
	private ActionDriver actionDriver;
	
	// Define locators using By class
	private By userName_txt = By.name("username");
	private By password_txt = By.xpath("//input[@type='password']");
	private By login_btn = By.xpath("//button[@type='submit']");
	private By error_msg = By.xpath("//div[@role='alert']//p");
	
	// Initialize the ActionDriver object by passed WebDriver Instance
	/*
	public LoginPage(WebDriver driver) {
		this.actionDriver = new ActionDriver(driver);
	}
	*/
	
	public LoginPage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	// Method to perform login
	public void login(String userName, String password) {
		actionDriver.enterText(userName_txt, userName);
		actionDriver.enterText(password_txt, password);
		actionDriver.click(login_btn);
	}
	
	// Method to check if error message is displayed
	public boolean isErrorMessageDisplayed(By by) {
		return actionDriver.isDisplayed(error_msg);
	}
	
	// Method to get text from error message
	public String getErrormessage() {
		String errorMessage = actionDriver.getText(error_msg);
		return errorMessage;
	}
	
	// Method to compare error message
	public boolean validateErrorMessage(String expectedError) {
		return actionDriver.compareText(error_msg, expectedError);
	}
		

}
