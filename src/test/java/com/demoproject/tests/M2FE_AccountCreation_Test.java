package com.demoproject.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.demoproject.base.BaseClass;
import com.demoproject.pages.HomePage;
import com.demoproject.pages.M2FE_AccountCreation_Page;
import com.demoproject.utilities.DataProviders;
import com.demoproject.utilities.ExtentManager;

public class M2FE_AccountCreation_Test extends BaseClass{
	
	private M2FE_AccountCreation_Page account;
	
	@BeforeMethod
	public void setupPages() {
		account = new M2FE_AccountCreation_Page(getDriver());
	}
	
	@Test(dataProvider="M2FE_AccountCreation", dataProviderClass=DataProviders.class)
	public void test_Account_Creation(String email, String fname, String lname, String phone, String practitioner_DD, String license, String supplement_DD, 
			String address1, String city, String state_DD, String zip, String refferal_DD) {
		ExtentManager.logStep("Launched the Account creation page" + "( " + account.getURL() + "");
		Assert.assertTrue(account.verifyAccountPageText(), "Element is not present");
		account.enterAccountDetails(email, fname, lname, phone, practitioner_DD, license, supplement_DD, address1, city, state_DD, zip, refferal_DD);
		account.acceptTermAndConditions();
//		account.applyNow();
//		Assert.assertTrue(account.verifySetPassword());
//		account.setPassword("Welcome123$");
//		Assert.assertTrue(account.verifyMyAccountLink());
	}
	
	
	public void accountCreationTest() {
		ExtentManager.logStep("Launched the Account creation page" + "( " + account.getURL() + "");
//		Assert.assertTrue(account.verifyPageTitle("Practitioner Account"), "Page title is not matched");
		Assert.assertTrue(account.verifyAccountPageText(), "Element is not present");
		
		account.enterAccountDetails("prasad12389ss4g81@gmail.com", "prasad", "automation", "9000899899", "Dentistry", "123df", "For personal or family use", 
				"99 UNIVERSITY AVE", "NEW CASTLE", "Delaware", "19270", "Catalog Mail");
		account.acceptTermAndConditions();
		account.applyNow();
		Assert.assertTrue(account.verifySetPassword());
		account.setPassword("Welcome123$");
		Assert.assertTrue(account.verifyMyAccountLink());
	}
}
