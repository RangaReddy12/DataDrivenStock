package DriverFactory;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.CustomerPage;
import CommonFunLibrary.LoginPage;
import CommonFunLibrary.SupplierPage;
import Utilities.ExcelFileUtil;

public class DataDrivenScript {
	WebDriver driver;
	Properties p;
	FileInputStream f;
	String inputPath="D:\\StockAccounting\\TestInput\\StockAccountingData.xlsx";
	String outputPath="D:\\StockAccounting\\TestOutput\\DataDrivenResults.xlsx";
	ExtentReports report;
	ExtentTest test;
	@BeforeTest
	public void setUp() throws Throwable
	{
		//generate html Report
		report= new ExtentReports("./ExtentReport/Reports/StockAccounting.html");
		p= new Properties();
		f= new FileInputStream("D:\\StockAccounting\\PropertyFile\\OR.properties");
		//load file
		p.load(f);
		if(p.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.get(p.getProperty("Url"));
			driver.manage().window().maximize();
			//Thread.sleep(5000);
		}
		else if(p.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "d:\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.get(p.getProperty("Url"));
			//Thread.sleep(5000);
		}
		else
		{
			Reporter.log("Browser Value Is Not Matching",true);
		}
		Thread.sleep(5000);
		LoginPage login =PageFactory.initElements(driver, LoginPage.class);
		login.verifyLogin("admin", "master");
	}
	@Test
	public void addSupplier()throws Throwable
	{
		//call supplierpage
		SupplierPage addsup= PageFactory.initElements(driver, SupplierPage.class);
		//create object for accessing xl methods
		ExcelFileUtil xl = new ExcelFileUtil(inputPath);
		//count no of cells in first row
		int cc=xl.cellCount("Supplier");
		//count no of rows in a sheet
		int rc =xl.rowCount("Supplier");
		Reporter.log("No of rows are::"+rc+"  "+"No ofcells in firtsrow::"+cc,true);
		for(int i=1;i<=rc;i++)
		{
			test= report.startTest("Supplier Creation");
			//read all cells data
			String supplierName=xl.getCellData("Supplier", i, 0);
			String Address=xl.getCellData("Supplier", i, 1);
			String city=xl.getCellData("Supplier", i, 2);
			String Country =xl.getCellData("Supplier", i, 3);
			String cperson=xl.getCellData("Supplier", i, 4);
			String phoneNumber=xl.getCellData("Supplier", i, 5);
			String Email=xl.getCellData("Supplier", i, 6);
			String mobileNumber =xl.getCellData("Supplier", i, 7);
			String Notes=xl.getCellData("Supplier", i, 8);
			test.log(LogStatus.INFO, supplierName+" "+Address+" "+city+" "+Country+" "+cperson+" "+phoneNumber+"  "+Email+"  "+mobileNumber+"  "+Notes);
			String status =addsup.verifySupplier(supplierName, Address, city, Country, cperson, phoneNumber, Email, mobileNumber, Notes);
			if(status.equalsIgnoreCase("Pass"))
			{
				//write status into result cell
				xl.setCellData("Supplier", i, 9, status, outputPath);
				test.log(LogStatus.PASS, "Supplier Created.");
			}
			else if(status.equalsIgnoreCase("Fail"))
			{
				xl.setCellData("Supplier", i, 9, status, outputPath);	
				test.log(LogStatus.FAIL, "Fail To Create Supplier.");
			}
		}
	}
	@Test
	public void addCustomer()throws Throwable
	{
		//call Customerpage
		CustomerPage addcust= PageFactory.initElements(driver, CustomerPage.class);
		//create object for accessing xl methods
		ExcelFileUtil xs = new ExcelFileUtil(inputPath);
		//count no of cells in first row
		int cc=xs.cellCount("Customer");
		//count no of rows in a sheet
		int rc =xs.rowCount("Customer");
		Reporter.log("No of rows are::"+rc+"  "+"No ofcells in firtsrow::"+cc,true);
		for(int i=1;i<=rc;i++)
		{
			test= report.startTest("Customer Creation");
			//read all cells data
			String customerName=xs.getCellData("Customer", i, 0);
			String Address=xs.getCellData("Customer", i, 1);
			String city=xs.getCellData("Customer", i, 2);
			String Country =xs.getCellData("Customer", i, 3);
			String cperson=xs.getCellData("Customer", i, 4);
			String phoneNumber=xs.getCellData("Customer", i, 5);
			String Email=xs.getCellData("Customer", i, 6);
			String mobileNumber =xs.getCellData("Customer", i, 7);
			String Notes=xs.getCellData("Customer", i, 8);
			test.log(LogStatus.INFO, customerName+" "+Address+" "+city+" "+Country+" "+cperson+" "+phoneNumber+"  "+Email+"  "+mobileNumber+"  "+Notes);
			String status =addcust.verifyCustomer(customerName, Address, city, Country, cperson, phoneNumber, Email, mobileNumber, Notes);
			if(status.equalsIgnoreCase("Pass"))
			{
				//write status into result cell
				xs.setCellData("Customer", i, 9, status, outputPath);
				test.log(LogStatus.PASS, "Customer Created.");
			}
			else if(status.equalsIgnoreCase("Fail"))
			{
				xs.setCellData("Customer", i, 9, status, outputPath);	
				test.log(LogStatus.FAIL, "Fail To Create Customer.");
			}
			
		}
	}
	@AfterTest
	public void tearDown()
	{
		driver.close();
		report.endTest(test);
		report.flush();
	}

}
