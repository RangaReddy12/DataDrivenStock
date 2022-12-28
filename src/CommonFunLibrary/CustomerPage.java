package CommonFunLibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomerPage {
	WebDriver driver;
	WebDriverWait wait;
	public CustomerPage(WebDriver driver)
	{
		this.driver=driver;
	}
	@FindBy(xpath="(//a[text()='Customers'])[2]")
	WebElement clickCustomerlink;
	@FindBy(xpath="(//span[@data-caption='Add'])[1]")
	WebElement clickAddIcon;
	@FindBy(name="x_Customer_Number")
	WebElement CustomerNumber;
	@FindBy(name="x_Customer_Name")
	WebElement CustomerName;
	@FindBy(name="x_Address")
	WebElement Address;
	@FindBy(name="x_City")
	WebElement City;
	@FindBy(name="x_Country")
	WebElement Country;
	@FindBy(name="x_Contact_Person")
	WebElement ContactPerson;
	@FindBy(name="x_Phone_Number")
	WebElement PhoneNumber;
	@FindBy(name="x__Email")
	WebElement Email;
	@FindBy(name="x_Mobile_Number")
	WebElement MobileNumber;
	@FindBy(name="x_Notes")
	WebElement Notes;
	@FindBy(name="btnAction")
	WebElement ClickAddBtn;
	@FindBy(xpath="//button[contains(text(),'OK!')]")
	WebElement clickConfirmOkBtn;
	@FindBy(xpath="(//button[text()='OK'])[6]")
	WebElement clickAlertOkBtn;
	@FindBy(xpath="//span[@data-caption='Search']")
	WebElement clickSearchPanelBtn;
	@FindBy(name="psearch")
	WebElement SearchTextbox;
	@FindBy(name="btnsubmit")
	WebElement SearchBtn;
	@FindBy(xpath="//table[@id='tbl_a_customerslist']")
	WebElement CustomerTable;
	public String verifyCustomer(String customername,String Address,String City,String Country,
		String ContactPerson,String PhoneNumber,String Email,String MobileNumber,String Note)
	{
		String res1="";
		wait= new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(clickCustomerlink));
		this.clickCustomerlink.click();
		wait.until(ExpectedConditions.visibilityOf(clickAddIcon));
		this.clickAddIcon.click();
		wait.until(ExpectedConditions.visibilityOf(CustomerNumber));
		String custNumber=CustomerNumber.getAttribute("value");
		this.CustomerName.sendKeys(customername);
		this.Address.sendKeys(Address);
		this.City.sendKeys(City);
		this.Country.sendKeys(Country);
		this.ContactPerson.sendKeys(ContactPerson);
		this.PhoneNumber.sendKeys(PhoneNumber);
		this.Email.sendKeys(Email);
		this.MobileNumber.sendKeys(MobileNumber);
		this.Notes.sendKeys(Note);
		this.ClickAddBtn.sendKeys(Keys.ENTER);
		wait.until(ExpectedConditions.elementToBeClickable(clickConfirmOkBtn));
		this.clickConfirmOkBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(clickAlertOkBtn));
		this.clickAlertOkBtn.click();
		if(!SearchTextbox.isDisplayed())
		//click on search panel
		this.clickSearchPanelBtn.click();
		this.SearchTextbox.clear();
		this.SearchTextbox.sendKeys(custNumber);
		this.SearchBtn.click();
		wait.until(ExpectedConditions.visibilityOf(CustomerTable));
		String expected=driver.findElement(By.xpath("//table[@id='tbl_a_customerslist']/tbody/tr[1]/td[5]/div/span/span")).getText();
	if(custNumber.equals(expected))
	{
		res1="pass";
	}
	else
	{
		res1="Fail";
	}
	return res1;
	}
}
