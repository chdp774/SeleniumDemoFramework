package com.demoproject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.demoproject.actiondriver.ActionDriver;
import com.demoproject.base.BaseClass;

public class M2Admin_OrderCreation_Page {
	
	private ActionDriver actionDriver;
	
	private By userName_txt = By.xpath("//input[@id='username']");
	private By password_text = By.xpath("//input[@id='login']");
	private By login_Btn = By.xpath("//span[contains(text(), 'Sign in')]");
	private By dashboardPage_verification = By.xpath("//h1[contains(text(),'Dashboard')]");
	private By customersPage_verification = By.xpath("//h1[contains(text(),'Customers')]");
	private By customerInfoPage_verification = By.xpath("//strong[contains(text(),'Customer Information')]");
	private By selectStorePage_Verification = By.xpath("//span[contains(text(),'Please select a store')]");
	
	private By menu_salesBtn_link = By.xpath("//nav[@class='admin__menu']//span[contains(text(),'Sales')]/parent::a/parent::li");
	private By menu_ordersBtn_link = By.xpath("//div[@class='submenu']//span[contains(text(),'Orders')]/parent::a/parent::li");
	private By menu_customersBtn_link = By.xpath("//nav[@class='admin__menu']//span[contains(text(),'Customers')]/parent::a/parent::li");
	private By menu_allCustomersBtn_link = By.xpath("//div[@class='submenu']//span[contains(text(),'All Customers')]/parent::a/parent::li");
	private By customers_search_txt = By.xpath("//div[@class='data-grid-search-control-wrap']/input");
	private By customers_editBtn = By.xpath("//a[contains(text(),'Edit')]");
	private By orders_createOrder = By.xpath("//button[@title='Create Order']");
	private By pureEncapsulation_US = By.xpath("//input[@id='store_1']");
	private By order_AddProductsBySKU = By.xpath("//span[contains(text(),'Add Products By SKU')]");
	private By order_SKUnumber = By.xpath("//input[@id='sku_0']");
	private By order_SKUqty = By.xpath("//input[@id='sku_qty_0']");
	private By order_addToOrder = By.xpath("//span[contains(text(),'Add to Order')]/parent::button");
	private By shippingMethod = By.xpath("//span[contains(text(),'Get shipping methods and rates')]");
	private By shippingMethod_free = By.xpath("//label[contains(text(),'Free - ')]/parent::li/input");
	private By submitOrderBtn = By.cssSelector("#submit_order_top_button");
	private By orderNum = By.cssSelector(".page-title");
	
	public M2Admin_OrderCreation_Page(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	public String getURL() {
		actionDriver.launchSite("https://mcstaging.pureencapsulationspro.com/atriumgate/admin/");
		return actionDriver.getCurrentURL();
	}
	
	public void doLogin(String username, String password) {
		actionDriver.enterText(userName_txt, username);
		actionDriver.enterText(password_text, password);
		actionDriver.click(login_Btn);
	}
	
	public void navigateToCustomers() {
		actionDriver.waitFor(2);
		actionDriver.click(menu_customersBtn_link);
		actionDriver.click(menu_allCustomersBtn_link);
	}
	
	public void searchCustomer(String customer) {
		actionDriver.clearText(customers_search_txt);
		actionDriver.enterText(customers_search_txt, customer + Keys.ENTER);
		actionDriver.waitFor(10);
		actionDriver.click(customers_editBtn);
	}
	
	public void navigateToOrders() {
		actionDriver.waitFor(5);
		actionDriver.click(orders_createOrder);
		actionDriver.click(pureEncapsulation_US);	
	}
	
	public void addProductsBySKU(String sku, String qty) {
		actionDriver.click(order_AddProductsBySKU);
		actionDriver.enterText(order_SKUnumber, sku);
		actionDriver.enterText(order_SKUqty, qty);
		actionDriver.click(order_addToOrder);
	}
	
	public void addShippingMethod() {
		actionDriver.scrollToElement(shippingMethod);
		actionDriver.waitFor(2);
		actionDriver.click(shippingMethod);
		actionDriver.waitFor(3);
		actionDriver.click(shippingMethod_free);
	}
	
	public void submitOrder() {
		actionDriver.waitFor(5);
		actionDriver.click(submitOrderBtn);
	}
	
	public boolean verifyLoginPage() {
		return actionDriver.isDisplayed(userName_txt);
	}
	
	public boolean verifyDashBoardpage() {
		return actionDriver.isDisplayed(dashboardPage_verification);
	}
	
	public boolean verifyCustomerspage() {
		return actionDriver.isDisplayed(customersPage_verification);
	}
	
	public boolean verifyCustomerInfopage() {
		return actionDriver.isDisplayed(customerInfoPage_verification);
	}
	
	public boolean verifyOrderspage() {
		return actionDriver.isDisplayed(order_AddProductsBySKU);
	}
}
