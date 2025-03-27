package com.demoproject.utilities;

import java.util.List;

import org.testng.annotations.DataProvider;


public class DataProviders {
	
	public static final String filePath = System.getProperty("user.dir")+"/src/test/resources/testdata/TestData.xlsx";
	
	@DataProvider(name="validLoginData")
	public static Object[][] validLoginData(){
		return getSheetData("ValidLoginData");
	}
	
	@DataProvider(name="inValidLoginData")
	public static Object[][] InvalidLoginData(){
		return getSheetData("InValidLoginData");
	}
	
	@DataProvider(name="M2FE_AccountCreation")
	public static Object[][] M2FE_AccountCreation(){
		return getSheetData("M2FE_AccountCreation");
	}
	
	private static Object[][] getSheetData(String sheetName){
		List<String[]> sheetData = ExcelReaderUtility.getSheetData(filePath, sheetName);
		
		Object[][] data = new Object[sheetData.size()][sheetData.get(0).length];
		
		for (int i=0; i<sheetData.size(); i++) {
			data[i] = sheetData.get(i);
		}
		return data;
	}

}
