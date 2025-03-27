package com.demoproject.tests;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.demoproject.base.BaseClass;
import com.demoproject.utilities.ExtentManager;

public class DummyClass extends BaseClass{

	@Test
	public void dummyTest() {
		// ExtentManager.startTest("Dummy1 Test");	--> This has been implemented in TestListener 
		String title = getDriver().getTitle();
		ExtentManager.logStep("Verifing the title");
		assert title.contains("OrangeHRM"):"Test Failed - Title is not matching";
		
		System.out.println("Test Passed - Title is Matching");
		throw new SkipException("Skipped the test as part of Testing");
	}
}
