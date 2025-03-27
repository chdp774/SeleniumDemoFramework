package com.demoproject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.demoproject.actiondriver.ActionDriver;
import com.demoproject.base.BaseClass;

public class HomePage {
	
	private ActionDriver actionDriver;
	
	// Define locators using By class
	private By admin_tab = By.xpath("//span[text()='Admin']");
	private By orngeHRM_logo = By.xpath("//div[@class='oxd-brand-banner']/img");
	private By userName_btn = By.cssSelector(".oxd-userdropdown-name");
	private By logout_btn = By.xpath("//a[text()='Logout']");
	
	// Initialize the ActionDriver object by passed WebDriver Instance
	/*
	public HomePage(WebDriver driver) {
		this.actionDriver = new ActionDriver(driver);
	}
	*/
	
	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	// Verify admin tab is displayed or not
	public boolean verifyAdminTab() {
		return actionDriver.isDisplayed(admin_tab);
	}
	
	// Verify Logo is displayed or not
	public boolean verifyLogo() {
		return actionDriver.isDisplayed(orngeHRM_logo);
	}
	
	// Method to logout
	public void logout() {
		actionDriver.click(userName_btn);
		actionDriver.click(logout_btn);
	}
}
