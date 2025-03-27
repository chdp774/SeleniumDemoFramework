package com.demoproject.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.demoproject.base.BaseClass;
import com.demoproject.pages.JDE_OrderVerification_Page;
import com.demoproject.utilities.ExtentManager;

public class JDE_OrderVerification_Test extends BaseClass{
private JDE_OrderVerification_Page jde;
	
	@BeforeMethod
	public void setupPages() {
		jde = new JDE_OrderVerification_Page(getDriver());
	}
	
	@Test
	public void orderCreationTest() {
		ExtentManager.logStep("Launched the Account creation page" + "( " + jde.getURL() + " )");
//		Assert.assertTrue(account.verifyPageTitle("Practitioner Account"), "Page title is not matched");
		Assert.assertTrue(jde.verifyLoginPage());
		jde.doLogin("pchippada", "itsFeb17th$$");
		Assert.assertTrue(jde.verifyDashboardPage());
		jde.navigateToWebOrderPage();
		Assert.assertTrue(jde.verifyWebOrdersPage());
		jde.searchOrder("50273762");
	}
}
