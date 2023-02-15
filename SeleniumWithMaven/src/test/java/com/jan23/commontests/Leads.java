package com.jan23.commontests;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Leads extends BaseTest{
	
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
	public static void openLeads() throws InterruptedException
	{
		click("//*[@id=\"Lead_Tab\"]/a");
		waiting();
		popup();
		String pageType=getText("//h1[@class=\"pageType\"]");
		String pageDesc=getText("//h2[@class=\"pageDescription\"]");
		Assert.assertEquals(pageType, "Leads");
		Assert.assertEquals(pageDesc, "Home");
		
	}
	
	@Test
	public static void checkLeadsTab() throws InterruptedException
	{
		openLeads();
	}
	
	@Test
	public static void validateViewDropDownList() throws InterruptedException
	{
		openLeads();
		Select viewdrop=new Select(driver.findElement(By.xpath("//*[@name=\"fcf\"]")));
		List<WebElement> viewdropElements=viewdrop.getOptions();
		List<String> expected_items = Arrays.asList("All Open Leads","My Unread Leads","Recently Viewed Leads","Today's Leads","View - Custom 1","View - Custom 2");
		for (WebElement wp: viewdropElements)
	    
	    {
	    	Assert.assertTrue(expected_items.contains(wp.getText())); 
	    }
		
		
		
	}
	@Test
	public static void goButton() throws InterruptedException
	{
		openLeads();
    	Select leadsDropdown=new Select(driver.findElement(By.xpath("//*[@name=\"fcf\"]")));
    	leadsDropdown.selectByVisibleText("Today's Leads");
    	waiting();
    	Select leadsDropdown1=new Select(driver.findElement(By.xpath("//*[@name=\"fcf\"]")));
    	String updated_dropdown = "My Unread Leads";
    	leadsDropdown1.selectByVisibleText(updated_dropdown);
    	waiting();
    	logout();
    	waiting();
		WebElement uname=driver.findElement(By.xpath("//input[@id='username']"));
		uname.sendKeys("rani.ch@adcd.com");
		WebElement password=driver.findElement(By.xpath("//input[@id='password']"));
		password.sendKeys("rani@12345");
		WebElement login=driver.findElement(By.xpath("//*[@id='Login']"));
		login.click();
		waiting();
		openLeads();
        click("//input[@value=\" Go! \"]");
        waiting();
    	Select leadsDropdown2=new Select(driver.findElement(By.xpath("//*[@name=\"fcf\"]")));
        String selected_dropdown = leadsDropdown2.getFirstSelectedOption().getText();
        Assert.assertEquals(selected_dropdown, updated_dropdown);
	}
    @Test
	public static void todaysLeadsWork() throws InterruptedException
	{
		openLeads();
    	Select leadsDropdown=new Select(driver.findElement(By.xpath("//*[@id=\"fcf\"]")));
    	String updated_dropdown = "Today's Leads";
    	leadsDropdown.selectByVisibleText("Today's Leads");
    	popup();
        String selected_dropdown = leadsDropdown.getFirstSelectedOption().getText();
        Assert.assertEquals(selected_dropdown, updated_dropdown);

	}
    @Test
	public static void newLeads() throws InterruptedException
	{
		openLeads();
		click("//input[@name=\"new\"]");
		waiting();
		String pageType=getText("//h1[@class=\"pageType\"]");
		String pageDesc=getText("//h2[@class=\"pageDescription\"]");
		Assert.assertEquals(pageType, "Lead Edit");
		Assert.assertEquals(pageDesc, "New Lead");
		String name = "ABCD";
		sendKeys("//input[@id=\"name_lastlea2\"]", name);
		sendKeys("//input[@id=\"lea3\"]", name);
		click("(//input[@name=\"save\"])[1]");
		waiting();
		String page_title = getText("//h2[@class=\"topName\"]");
		Assert.assertEquals(page_title, name);
	}


}
