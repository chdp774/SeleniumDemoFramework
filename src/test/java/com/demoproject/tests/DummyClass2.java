package com.demoproject.tests;

import org.testng.annotations.Test;

import com.demoproject.base.BaseClass;
import com.demoproject.utilities.ExtentManager;

public class DummyClass2 extends BaseClass{

	@Test
	public void dummyTest() {
		//ExtentManager.startTest("Dummy2 Test");	--> This has been implemented in TestListener 
		String title = getDriver().getTitle();
		ExtentManager.logStep("Verifing the title");
		assert title.contains("OrangeHRM"):"Test Failed - Title is not matching";
		
		System.out.println("Test Passed - Title is Matching");
		ExtentManager.logStep("Validation Successfull");
	}
}
