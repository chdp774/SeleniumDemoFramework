package com.demoproject.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.demoproject.base.BaseClass;
import com.demoproject.pages.M2Admin_OrderCreation_Page;
import com.demoproject.utilities.ExtentManager;

public class M2Admin_OrderCreation_Test extends BaseClass{
	
	private M2Admin_OrderCreation_Page admin;
	
	@BeforeMethod
	public void setupPages() {
		admin = new M2Admin_OrderCreation_Page(getDriver());
	}
	
	@Test
	public void orderCreationTest() {
		ExtentManager.logStep("Launched the Account creation page" + "( " + admin.getURL() + " )");
//		Assert.assertTrue(account.verifyPageTitle("Practitioner Account"), "Page title is not matched");
		Assert.assertTrue(admin.verifyLoginPage());
		admin.doLogin("pchippada", "itsFeb18th$$");
		Assert.assertTrue(admin.verifyDashBoardpage());
		admin.navigateToCustomers();
		Assert.assertTrue(admin.verifyCustomerspage());
		admin.searchCustomer("85108698");
		Assert.assertTrue(admin.verifyCustomerInfopage());
		admin.navigateToOrders();
		Assert.assertTrue(admin.verifyOrderspage());
		admin.addProductsBySKU("VD16", "6");
		admin.addShippingMethod();
		admin.submitOrder();
	}
	
	
}
