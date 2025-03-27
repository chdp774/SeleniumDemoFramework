package com.demoproject.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.demoproject.base.BaseClass;
import com.demoproject.pages.HomePage;
import com.demoproject.pages.LoginPage;
import com.demoproject.utilities.ExtentManager;

public class HomePageTest extends BaseClass{
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	@Test
	public void verifyOrangeHRMLogo() {
		//ExtentManager.startTest("Homepage verify logo Test");	--> This has been implemented in TestListener 
		ExtentManager.logStep("Navigating to login page entering username and password");
		loginPage.login("Admin", "admin123");
		ExtentManager.logStep("Verifing Logo is visiable or not");
		Assert.assertTrue(homePage.verifyLogo(), "Logo is not visiable");
		ExtentManager.logStep("Validation Successfull");
		ExtentManager.logStep("Logged out Successfully");
	}
}
