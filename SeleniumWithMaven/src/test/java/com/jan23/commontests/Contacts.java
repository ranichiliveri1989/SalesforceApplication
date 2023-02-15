package com.jan23.commontests;

import java.text.SimpleDateFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Contacts extends BaseTest{
	
	@BeforeMethod
	public void lunch() throws InterruptedException
	{
		login();
	}
	
	@AfterMethod
	public void close()
	{
		driver.close();
	}
	
	public static void openContacts() throws InterruptedException
	{
		click("//*[@id=\"Contact_Tab\"]/a");
		waiting();
		popup();
		String pageType=getText("//h1[@class=\"pageType\"]");
		String pageDesc=getText("//h2[@class=\"pageDescription\"]");
		Assert.assertEquals(pageType, "Contacts");
		Assert.assertEquals(pageDesc, "Home");
		
	}
	public static void createNewViewAssertion() throws InterruptedException
	{
		openContacts();
		click("//span[@class=\"fFooter\"]/a[2]");
		waiting();
		String expected_NewViewcontactPage="Create New View";
		String actual_NewViewcontactPageString=getText("//h2[@class=\"pageDescription\"]");
		Assert.assertEquals(expected_NewViewcontactPage, actual_NewViewcontactPageString);
		waiting();
	}
	@Test
	public static void createNewContact() throws InterruptedException {
		openContacts();
		click("//td[@class=\"pbButton\"]/input");
		waiting();
		String expected_NewcontactPage="New Contact";
		String actual_NewContactPage=getText("//h2[@class=\"pageDescription\"]");
		Assert.assertEquals(expected_NewcontactPage, actual_NewContactPage);
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());
		String lastName="konyala"+timeStamp;
		String accountName="myNewAccount1";
		sendKeys("//input[@name=\"name_lastcon2\"]",lastName);
		sendKeys("//input[@name=\"con4\"]",accountName);
		waiting();
		scrolldown();
		waiting();
		click("//td[@class=\"pbButtonb\"]/input[1]");
		String actual_NewContact=getText("//h2[@class=\"topName\"]");
		Assert.assertEquals(actual_NewContact, lastName);
	
	}
	@Test
	public static void newViewConactPage() throws InterruptedException
	{
		
		createNewViewAssertion();
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());
		String viewName="konyala"+timeStamp;
		String viewUniqueName="rani"+timeStamp;
		sendKeys("//*[@id=\"fname\"]",viewName);
		sendKeys("//*[@id=\"devname\"]",viewUniqueName);
		waiting();
		click("//*[@id=\"editPage\"]/div[1]/table/tbody/tr/td[2]/input[1]");
		waiting();
		Select recent=new Select(driver.findElement(By.xpath("//*[@name=\"fcf\"]")));
		String a=recent.getFirstSelectedOption().getText();
		Assert.assertEquals(a,viewName);
		
		
	}
	@Test
	public static void recentContacts() throws InterruptedException
	{
		openContacts();
		waiting();
		Select recent=new Select(driver.findElement(By.xpath("//select[@id=\"hotlist_mode\"]")));
		recent.selectByVisibleText("Recently Created");
		waiting();
		List<WebElement> table_data = driver.findElements(By.xpath("//*[@id=\"bodyCell\"]/div[3]/div[1]/div/div[2]/table/tbody/tr"));
	     System.out.println(table_data.size());
		Assert.assertTrue(table_data.size() > 0);

		
	}
	@Test
	public static void myContactsView() throws InterruptedException
	{
		openContacts();
		waiting();
		Select recent=new Select(driver.findElement(By.xpath("//select[@id=\"fcf\"]")));
		recent.selectByVisibleText("My Contacts");
		waiting();
		List<WebElement> table_data = driver.findElements(By.xpath("//*[@id=\"bodyCell\"]/div[3]/div[1]/div/div[2]/table/tbody/tr"));
	     System.out.println(table_data.size());
		Assert.assertTrue(table_data.size() > 0);
		
	}
	@Test
	public static void viewContacts() throws InterruptedException
	{
		openContacts();
		Select recent=new Select(driver.findElement(By.xpath("//select[@id=\"fcf\"]")));
		recent.selectByVisibleText("My Contacts");
		waiting();
		String Expectedcontact=getText("//table/tbody/tr[2]/th/a");
		
		click("//table/tbody/tr[2]/th/a");
		waiting();
		String actualcontact=getText("//h2[@class=\"topName\"]");
		Assert.assertEquals(Expectedcontact, actualcontact);
		
	}
	@Test
	public static void errorMessageInNewView() throws InterruptedException
	{
		createNewViewAssertion();
		
		String viewUniqueName="EFGH";
		
		sendKeys("//*[@id=\"devname\"]",viewUniqueName);
		waiting();
		click("//*[@id=\"editPage\"]/div[1]/table/tbody/tr/td[2]/input[1]");
		waiting();
		String actual_errorMsg=getText("//div[@class=\"requiredInput\"]/div[@class=\"errorMsg\"]");
		String expected_errorMsg="Error: You must enter a value";
		Assert.assertEquals(expected_errorMsg,actual_errorMsg);
		
	}
	@Test
	public static void checkCancelButton() throws InterruptedException
	{
		createNewViewAssertion();
		String viewName="ABCD";
		String viewUniqueName="EFGH";
		sendKeys("//*[@id=\"fname\"]",viewName);
		sendKeys("//*[@id=\"devname\"]",viewUniqueName);
		waiting();
		click("//*[@id=\"editPage\"]/div[1]/table/tbody/tr/td[2]/input[2]");
		waiting();
		String actualValue=getText("//*[@id=\"bodyCell\"]/div[3]/div[1]/div/div[2]/table/tbody/tr[2]/th/a");
		Assert.assertNotEquals(viewName,actualValue);
	}
	@Test
	public static void checksaveAndButton() throws InterruptedException
	{
		openContacts();
		click("//td[@class=\"pbButton\"]/input");
		waiting();
		String expected_NewcontactPage="New Contact";
		String actual_NewContactPage=getText("//h2[@class=\"pageDescription\"]");
		Assert.assertEquals(expected_NewcontactPage, actual_NewContactPage);
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());
		String lastName="Indian"+timeStamp;
		String accountName="GlobalMedia"+timeStamp;
		sendKeys("//input[@name=\"name_lastcon2\"]",lastName);
		sendKeys("//input[@name=\"con4\"]",accountName);
		click("(//input[@name=\"save\"])[1]");
		String actual_NewContact=getText("//div[@class=\"errorMsg\"]");
		String actual_main_error = getText("//div[@class=\"pbError\"]");
		String exp_newContact_err = "Error: No matches found.";
		String exp_main_error = "Invalid Data.";
		Assert.assertEquals(actual_NewContact, exp_newContact_err);
		Assert.assertTrue(actual_main_error.contains(exp_main_error));
	}
	
}
