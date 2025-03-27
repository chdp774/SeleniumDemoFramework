package com.demoproject.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.demoproject.base.BaseClass;
import com.demoproject.utilities.ExtentManager;

public class TestListener implements ITestListener{
	
	// Triggered when test started
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		
		// Start logging in extent report
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test started: " + testName);
	}
	
	// Triggered when test succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String greenTestPassed = "<span style='color:green; font-size:16px; font-weight:600;'> Test Passed </span>";
		ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully!", "Test End: " + testName + " - " + greenTestPassed );
		
	}

	// Triggered when test Failed
	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String failureMessage = result.getThrowable().getMessage();
		ExtentManager.logStep(failureMessage);
		String redTestFailed = "<span style='color:red; font-size:16px; font-weight:600;'> Test Passed </span>";
		ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed!", "Test End: " +testName + " - " + redTestFailed);
	}

	// Triggered with test Skipped
	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logSkip("Test Skipped " +testName);
	}

	// Triggered when suite start
	@Override
	public void onStart(ITestContext context) {
		// Initialize the extent reports
		ExtentManager.getReporter();
	}
	
	// Triggered when the suite ends
	@Override
	public void onFinish(ITestContext context) {
		// Flush the extent report
		ExtentManager.endTest();
	}
	
}
