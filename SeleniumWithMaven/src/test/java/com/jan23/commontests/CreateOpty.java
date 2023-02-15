package com.jan23.commontests;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreateOpty extends BaseTest {
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
	public static void verifyReports(String report_type) {
		String page_title = getText("//h1[@class=\"noSecondHeader pageType\"]");
    	Assert.assertEquals(page_title, report_type);
		String status = getText("//div[@id=\"status\"]");
		Assert.assertEquals(status, "Complete");
		List<WebElement> table_data = driver.findElements(By.xpath("//*[@id=\"fchArea\"]/table/tbody/tr"));
		Assert.assertTrue(table_data.size() > 0);

	}
	public static void opppagevalidation() throws InterruptedException
	{
		click("//*[@id=\"Opportunity_Tab\"]/a");
    	waiting();
        popup();
        waiting();
    	String actual_opportunities=getText("//h1[@class=\"pageType\"]");
    	Assert.assertEquals(actual_opportunities, "Opportunities");
	}
	
	@Test
    public void opportunitiesDropDown() throws InterruptedException
    {
    	
		opppagevalidation();
    	Select oppDropdown=new Select(driver.findElement(By.xpath("//*[@id=\"fcf\"]")));
    	List<WebElement> oppDropdown2= oppDropdown.getOptions();
		List<String> expected_items = Arrays.asList("All Opportunities","Closing Next Month","Closing This Month","My Opportunities","New Last Week","New This Week","Opportunity Pipeline","Private","Recently Viewed Opportunities","Won");
		for (WebElement wp: oppDropdown2)
	    
	    {
	    	Assert.assertTrue(expected_items.contains(wp.getText())); 
	    }
    }
	
	@Test
	public static void create_A_new_opty() throws InterruptedException
	{
		opppagevalidation();
		click("//input[@title=\"New\"]");
		String opp_name = "Rani1";
		sendKeys("//*[@id=\"opp3\"]",opp_name);
		sendKeys("//*[@id=\"opp4\"]","myNewAccount1");
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date());
		sendKeys("//*[@id=\"opp9\"]",timeStamp);
		Select leadSource=new Select(driver.findElement(By.xpath("//*[@id=\"opp6\"]")));
		leadSource.selectByVisibleText("Phone Inquiry");
		sendKeys("//*[@id=\"opp17\"]","");
		Select stage=new Select(driver.findElement(By.xpath("//*[@id=\"opp11\"]")));
		stage.selectByVisibleText("Needs Analysis");
		sendKeys("//*[@id=\"opp12\"]","30");
		waiting();
		scrolldown();
		click("(//input[@name=\"save\"])[1]");
		String act_opp_name = getText("//h2[@class=\"pageDescription\"]");
		Assert.assertEquals(act_opp_name, opp_name);
	}
    @Test
	public static void oppPipelineReport() throws InterruptedException
	{
		opppagevalidation();
		click("//*[@id=\"toolsContent\"]/tbody/tr/td[1]/div/div[1]/div[1]/ul/li[1]/a");
	    waiting();
		verifyReports("Opportunity Pipeline");
		
	}
    @Test
    public static void test_stuck_opportunities_report() throws InterruptedException 
    {
    	opppagevalidation();
    	click("//*[@id=\"toolsContent\"]/tbody/tr/td[1]/div/div[1]/div[1]/ul/li[2]/a");
		verifyReports("Stuck Opportunities");

		
    }
    @Test
	public static void testQuarterlySummaryReport() throws InterruptedException
	{
		opppagevalidation();
		waiting();
		System.out.println("so far done");
		Select interval=new Select(driver.findElement(By.xpath("//*[@id=\"quarter_q\"]")));
		System.out.println("so far done1");

		interval.selectByVisibleText("Current FQ");
		Select include=new Select(driver.findElement(By.xpath("//*[@id=\"open\"]")));
		include.selectByVisibleText("Open Opportunities");
		click("//input[@value=\"Run Report\"]");
		waiting();
		verifyReports("Opportunity Report");

	}
}
