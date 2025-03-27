package com.demoproject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.demoproject.actiondriver.ActionDriver;
import com.demoproject.base.BaseClass;

public class JDE_OrderVerification_Page {
	private ActionDriver actionDriver;
	
	public JDE_OrderVerification_Page(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	//itsFeb17th$$
	
	private By userName_txt = By.cssSelector("#User");
	private By password_text = By.cssSelector("#Password");
	private By login_Btn = By.cssSelector("input[type='submit']");
	private By dashboardPage_verification = By.cssSelector("#topnavOracleBranding");
	
	private By fastpathIcon = By.cssSelector("#drop_mainmenuParent");
	private By atriumInnovations_option = By.xpath("//span[contains(text(), 'Atrium-Innovations')]");
	private By salesOrderManagement_option = By.xpath("//span[contains(text(), 'Sales Order Management')]");
	private By reviewWebOrder_option = By.xpath("//div[@tasklabel='Review Web Order &#45; SW']");
	
	private By frameLocator = By.cssSelector("#e1menuAppIframe");
	private By webOrders_verification = By.cssSelector("#formHeading");
	
	private By orderNumber_text = By.xpath("//table[@class='JSGridQTPClass']//input[@title='Order Number']");
	
	
	
	
	public String getURL() {
		actionDriver.launchSite("https://qa900.na.atriumcorp.local/jde/E1Menu.maf?jdeowpBackButtonProtect=PROTECTED");
		return actionDriver.getCurrentURL();
	}
	
	public void doLogin(String username, String password) {
		actionDriver.enterText(userName_txt, username);
		actionDriver.enterText(password_text, password);
		actionDriver.click(login_Btn);
	}
	
	public void navigateToWebOrderPage() {
		actionDriver.click(fastpathIcon);
		actionDriver.click(atriumInnovations_option);
		actionDriver.click(salesOrderManagement_option);
		actionDriver.click(reviewWebOrder_option);
	}
	
	public void searchOrder(String orderNumber) {
		actionDriver.enterText(orderNumber_text, orderNumber);
	}
	
	public boolean verifyLoginPage() {
		return actionDriver.isDisplayed(userName_txt);
	}
	
	public boolean verifyDashboardPage() {
		return actionDriver.isDisplayed(dashboardPage_verification);
	}
	
	public boolean verifyWebOrdersPage() {
		actionDriver.switchToFrame(frameLocator);
		return actionDriver.isDisplayed(webOrders_verification);
	}
}
