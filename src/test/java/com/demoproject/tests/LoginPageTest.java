package com.demoproject.tests;

import java.security.cert.Extension;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.demoproject.base.BaseClass;
import com.demoproject.pages.HomePage;
import com.demoproject.pages.LoginPage;
import com.demoproject.utilities.DataProviders;
import com.demoproject.utilities.ExtentManager;

public class LoginPageTest extends BaseClass{
	private HomePage homePage;
	private LoginPage loginPage;
	
	@BeforeMethod
	public void setupPages() {
		homePage = new HomePage(getDriver());
		loginPage = new LoginPage(getDriver());
	}
	
	@Test(dataProvider="validLoginData", dataProviderClass=DataProviders.class)
	public void verifyValidLoginTest(String username, String password) {
		
		//ExtentManager.startTest("Valid Login Test");	--> This has been implemented in TestListener 
		ExtentManager.logStep("Navigating to login page entering username and password");
		loginPage.login(username, password);
		ExtentManager.logStep("Verifing Admin tab is visiable or not");
		Assert.assertTrue(homePage.verifyAdminTab(), "Admin tab should be visiable after successfull login");
		ExtentManager.logStep("Validation Successfull");
		homePage.logout();
		ExtentManager.logStep("Logged out Successfully");
		staticWait(2);
	}
	
	@Test(dataProvider="inValidLoginData", dataProviderClass=DataProviders.class)
	public void invalidLoginTest(String username, String password) {
		//ExtentManager.startTest("In-valid Login Test");	--> This has been implemented in TestListener 
		ExtentManager.logStep("Navigating to login page entering username and password");
		loginPage.login(username, password);
		String exprectedErrorMessage  = "Invalid credentials1";
		Assert.assertTrue(loginPage.validateErrorMessage(exprectedErrorMessage), "Test FAILED: Invaild error message");
		ExtentManager.logStep("Validation Successfull");
		ExtentManager.logStep("Logged out Successfully");
	}
}
