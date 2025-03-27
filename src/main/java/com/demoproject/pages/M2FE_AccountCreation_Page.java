package com.demoproject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.demoproject.actiondriver.ActionDriver;
import com.demoproject.base.BaseClass;

public class M2FE_AccountCreation_Page {
	
	private ActionDriver actionDriver;
	
	// Define locators using By class
	private By accountPageText = By.xpath("//h3[text()='Information About You']");
	private By email_txt = By.xpath("//input[@name='Email']");
	private By firstName_txt = By.xpath("//input[@name='First_Name']");
	private By lastName_txt = By.xpath("//input[@name='Last_Name']");
	private By phone_txt = By.xpath("//input[@name='Phone']");
	private By practitionerType_DD = By.xpath("//select[@name='Practitioner_Type']");
	private By license_txt = By.xpath("//input[@name='License_Number']");
	private By supplementType_DD = By.xpath("//select[@name='Personal_Use']");
	private By address1_txt = By.xpath("//input[@name='Street_Address_1']");
	private By city_txt = By.xpath("//input[@name='City']");
	private By AddressState_DD = By.xpath("//select[@name='State']");
	private By zip_txt = By.xpath("//input[@name='Zip']");
	private By referralDD = By.xpath("//select[@name='Referral_Code']");
//	private By tc_CB = By.cssSelector("#prac-acc-agree-tc");
	private By tc_CB = By.xpath("//*[@id='uniform-prac-acc-agree-tc']");
	private By popup_text = By.xpath("//strong[contains(text(),'ATRIUM PROFESSIONAL BRANDS ')]");
	private By applyNow_btn = By.xpath("//input[@type='submit']");
	private By agreePopup_btn = By.xpath("//a[contains(text(), 'I Agree')]");
	private By consent_btn = By.xpath("//button[contains(text(),'Consent')]");
	private By setPasswordLoading_text = By.xpath("//p[contains(text(),'Thank you for')]");
	private By setPasswordPage_text = By.xpath("//h2[contains(text(),'SET A NEW PASSWORD')]");
	
	private By password_txt = By.xpath("//input[@placeholder='Password*']");
	private By confirmPassword_txt = By.xpath("//input[@placeholder='Confirm Password*']");
	private By setPassword_btn = By.xpath("//input[@value='Set Password']/parent::li");
	
	private By myAccount_link = By.xpath("//ul[@class='header links my-account']");
	
	
	public M2FE_AccountCreation_Page(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	public String getURL() {
		actionDriver.launchSite("https://mcstaging.pureencapsulationspro.com/practitioner-account");
		return actionDriver.getCurrentURL();
	}
	
	// Method to perform login
	public void enterAccountDetails(String email, String firstName, String lastName, String phone, String practitioner_DD, String license,
			String supplement_DD, String address1, String city, String state_DD, String zip, String referral_DD) {
		actionDriver.enterText(email_txt, email);
		actionDriver.enterText(firstName_txt, firstName);
		actionDriver.enterText(lastName_txt, lastName);
		actionDriver.enterText(phone_txt, phone);
		actionDriver.selectByVisibleText(practitionerType_DD, practitioner_DD);
		actionDriver.enterText(license_txt, license);
		actionDriver.selectByVisibleText(supplementType_DD, supplement_DD);
		actionDriver.enterText(address1_txt, address1);
		actionDriver.enterText(city_txt, city);
		actionDriver.selectByVisibleText(AddressState_DD, state_DD);
		actionDriver.enterText(zip_txt, zip);
		actionDriver.selectByVisibleText(referralDD, referral_DD);		
	}
	
	public void acceptTermAndConditions() {
		actionDriver.click(consent_btn);
		actionDriver.waitFor(1);
		actionDriver.click(tc_CB);
		actionDriver.isDisplayed(popup_text);
		actionDriver.waitFor(1);
		actionDriver.click(agreePopup_btn);
	}
	
	public void applyNow() {
		actionDriver.click(applyNow_btn);
//		actionDriver.waitFor(5);
	}
	
	public boolean verifySetPassword() {
		actionDriver.isDisplayed(setPasswordLoading_text);
		return actionDriver.isDisplayed(setPasswordPage_text);
	}
	
	public void setPassword(String password) {
//		actionDriver.waitForPageLoad(60);
		
		actionDriver.enterText(password_txt, password);
		actionDriver.enterText(confirmPassword_txt, password);
		actionDriver.click(setPassword_btn);
	}
	
	public boolean verifyMyAccountLink() {
		return actionDriver.isDisplayed(myAccount_link);
	}
	
	public boolean verifyPageTitle(String title) {
		return actionDriver.validatePageTitle(title);
	}
	
	public boolean verifyAccountPageText() {
		return actionDriver.isDisplayed(accountPageText);
	}
	
	// Method to check if error message is displayed
//	public boolean isErrorMessageDisplayed(By by) {
//		return actionDriver.isDisplayed(error_msg);
//	}
//	
//	// Method to get text from error message
//	public String getErrormessage() {
//		String errorMessage = actionDriver.getText(error_msg);
//		return errorMessage;
//	}
//	
//	// Method to compare error message
//	public boolean validateErrorMessage(String expectedError) {
//		return actionDriver.compareText(error_msg, expectedError);
//	}
		

}
