package com.jan23.commontests;

//import java.sql.Date;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Randomscenarios extends BaseTest{
	
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
	public static void openHome() throws InterruptedException
	{
		click("//li[@id=\"home_Tab\"]");
		waiting();
		popup();
	}
	@Test
	public static void verifyMenuItems(String tabName, String add_or_remove) {
        List<WebElement> tabbars = driver.findElements(By.xpath("//*[@id=\"tabBar\"]/li"));
        List<String> tabItems = new ArrayList<String>();
        for (WebElement tabbar : tabbars)
        {
        	tabItems.add(tabbar.getText());
        }
        if (add_or_remove.equals("add")) {
        	Assert.assertTrue(tabItems.contains(tabName));
        }
        else
        {
        	Assert.assertFalse(tabItems.contains(tabName));
        }

	}
	@Test
	
	public static void openAlltab() throws InterruptedException
	{
		click("//li[@id=\"AllTab_Tab\"]");
		waiting();
		popup();
		String actual_all_tabs = getText("//h1[@class=\"noSecondHeader pageType\"]");
		String exp_all_tabs = "All Tabs";
		Assert.assertEquals(actual_all_tabs, exp_all_tabs);

	}
	public static void addRemoveCustTab(String tabname, String add_or_remove) throws InterruptedException {
		click("//input[@value=\"Customize My Tabs\"]");
		waiting();
		String actual_cust_tabs = getText("//h1[@class=\"noSecondHeader pageType\"]");
		String exp_cust_tab = "Customize My Tabs";
		Assert.assertEquals(actual_cust_tabs, exp_cust_tab);
		if (add_or_remove.equals("remove")) {
	        Select selectedTabs=new Select(driver.findElement(By.xpath("//*[@id=\"duel_select_1\"]")));
	        selectedTabs.selectByVisibleText(tabname);
	        waiting();
	        click("//*[@id=\"duel_select_0_left\"]/img");
	        Select availableTabs=new Select(driver.findElement(By.xpath("//*[@id=\"duel_select_0\"]")));
	        availableTabs.selectByVisibleText(tabname);
	        String actual_Text=availableTabs.getFirstSelectedOption().getText();
	        Assert.assertEquals(actual_Text, tabname);

		}
		else {
			Select selectedTabs=new Select(driver.findElement(By.xpath("//*[@id=\"duel_select_0\"]")));
	        selectedTabs.selectByVisibleText(tabname);
	        waiting();
	        click("//*[@id=\"duel_select_0_right\"]/img");
	        Select availableTabs=new Select(driver.findElement(By.xpath("//*[@id=\"duel_select_1\"]")));
	        availableTabs.selectByVisibleText(tabname);
	        String actual_Text=availableTabs.getFirstSelectedOption().getText();
	        Assert.assertEquals(actual_Text, tabname);
		}
        waiting();
        click("//input[@name=\"save\"]");
        verifyMenuItems(tabname, add_or_remove);
	
	}
	@Test
	public static void verifyFAndLName() throws InterruptedException {
		openHome();
		String uname_xpath = "//h1[@class=\"currentStatusUserName\"]/a";
		String name = getText(uname_xpath);
		String exp_name = "Rani chiliveri";
		Assert.assertEquals(name,exp_name);
		click(uname_xpath);
		String curr_url = driver.getCurrentUrl();
		String profile_name = getText("//*[@id=\"tailBreadcrumbNode\"]");
		Assert.assertTrue(curr_url.contains("UserProfilePage"));
	}
   @Test
	public static void verifyEditedLName() throws InterruptedException {
		openHome();
		click("//h1[@class=\"currentStatusUserName\"]/a");
        click("//img[@title=\"Edit Profile\"]/parent::a");

		WebElement contactFrame=driver.findElement(By.id("contactInfoContentId"));
		driver.switchTo().frame(contactFrame);
		
		String expectedMenuName = "Edit Profile";
		String actualmenuName = getText("//h2[@id=\"contactInfoTitle\"]");
		Assert.assertEquals(actualmenuName, expectedMenuName);
		String actual_contact  = driver.findElement(By.xpath("//li[@id=\"contactTab\"]")).getAttribute("class");
		String exp_contact = "zen-current";
		Assert.assertEquals(actual_contact, exp_contact);
		// test case 4
		click("//*[@id=\"aboutTab\"]/a");
		Thread.sleep(4000);
		sendKeys("//*[@id=\"lastName\"]", "Chiliveri");
		click("//input[@value='Save All']");
		driver.switchTo().defaultContent();
		String uname_xpath = "//h1[@class=\"currentStatusUserName\"]/a";
		String name = getText(uname_xpath);
		String exp_name = "Rani chiliveri";
		Assert.assertEquals(name,exp_name);
		
	}
	@Test
	public static void tabCustomization() throws InterruptedException {
		openAlltab();
		addRemoveCustTab("Subscriptions", "remove");
        logout();
        waiting();
		WebElement uname=driver.findElement(By.xpath("//input[@id='username']"));
		uname.sendKeys("rani.ch@adcd.com");
	  
		WebElement password=driver.findElement(By.xpath("//input[@id='password']"));
		password.sendKeys("rani@12345");
	  
		WebElement login=driver.findElement(By.xpath("//*[@id='Login']"));
		login.click(); 
        waiting();

		verifyMenuItems("Subscriptions", "remove");
		openAlltab();
		addRemoveCustTab("Subscriptions", "add");

	}
	@Test
	
	public static void blockingCalEvent() throws InterruptedException {
		openHome();
		waiting();
		String date_xpath = "//span[@class=\"pageDescription\"]/a";
		String act_date = getText(date_xpath);
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());
		// Assertion
		click(date_xpath);
		waiting();
		String pagetype = getText("//h1[@class=\"pageType\"]");
		Assert.assertEquals(pagetype, "Calendar for Rani chiliveri - Day View");
		click("(//div[@class=\"hourRowLabel timeCellDnD evenHour\"]/a)[1]");
		String pageDesc = getText("//h2[@class=\"pageDescription\"]");
		Assert.assertEquals(pageDesc, "New Event");
		String oldwindow = driver.getWindowHandle();
		click("//*[@id=\"ep\"]/div[2]/div[4]/table/tbody/tr[2]/td[2]/div/a");
		Thread.sleep(3000);
	      Set<String> wid = driver.getWindowHandles();
	      String[] getWindow = wid.toArray(new String[wid.size()]);
	      //switch to active tab
	      Thread.sleep(3000);

	      driver.switchTo().window(getWindow[1]);
	      driver.manage().window().maximize();
	      String newText=getText("//li[@class=\"listItem4\"]/a");
	      System.out.println(newText);
	      click("//li[@class=\"listItem4\"]/a");
	      driver.switchTo().window(oldwindow);
	      waiting();
	      click("//*[@id=\"evt5\"]");
	      WebElement actual_Text=driver.findElement(By.xpath("//*[@id=\"evt5\"]"));
	      String s=actual_Text.getAttribute("value");
	      
	      System.out.println(s);
	      Assert.assertEquals(newText, s);
	      waiting();
	      System.out.println("another frame");
	      click("//*[@id=\"EndDateTime_time\"]");
	      System.out.println("another frame2");
	      click("//*[@id=\"timePickerItem_18\"]");	
	      waiting();
	      click("(//input[@name=\"save\"])[1]");
	      
	      
	}
	
	@Test
	public static void blockingCalEventWeekly() throws InterruptedException {
		
		openHome();
		waiting();
		String date_xpath = "//span[@class=\"pageDescription\"]/a";
		String act_date = getText(date_xpath);
		click(date_xpath);
		waiting();
		String pagetype = getText("//h1[@class=\"pageType\"]");
		Assert.assertEquals(pagetype, "Calendar for Rani chiliveri - Day View");
		scrolldown();
			
		click("(//div[@class=\"hourRowLabel timeCellDnD evenHour\"]/a)[16]");
		waiting();
		popup();
		
		String pageDesc = getText("//h2[@class=\"pageDescription\"]");
		Assert.assertEquals(pageDesc, "New Event");
		String oldwindow = driver.getWindowHandle();
		click("//*[@id=\"ep\"]/div[2]/div[4]/table/tbody/tr[2]/td[2]/div/a");
		Thread.sleep(3000);
	      Set<String> wid = driver.getWindowHandles();
	      String[] getWindow = wid.toArray(new String[wid.size()]);
	      //switch to active tab
	      Thread.sleep(3000);

	      driver.switchTo().window(getWindow[1]);
	      driver.manage().window().maximize();
	      String newText=getText("//li[@class=\"listItem4\"]/a");
	      System.out.println(newText);
	      click("//li[@class=\"listItem4\"]/a");
	      driver.switchTo().window(oldwindow);
	      waiting();
	      click("//*[@id=\"evt5\"]");
	      WebElement actual_Text=driver.findElement(By.xpath("//*[@id=\"evt5\"]"));
	      String s=actual_Text.getAttribute("value");
	      
	      System.out.println(s);
	      Assert.assertEquals(newText, s);
	      waiting();
	      //click("//*[@id=\"EndDateTime\"]");
	      Calendar calendar2= Calendar.getInstance();
	        calendar2.setTime(new Date());
	        Date today2= calendar2.getTime();
	        calendar2.add(Calendar.DAY_OF_YEAR,1);
	        Date tommorrow = calendar2.getTime();
	        // 2/14/2023
	        DateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");

	        String tommorrowAsString = dateFormat2.format(tommorrow);
	        WebElement endDate=driver.findElement(By.xpath("//*[@id=\"EndDateTime\"]"));
            endDate.clear();
	        endDate.sendKeys(tommorrowAsString);
	      waiting();
	      click("//*[@id=\"EndDateTime_time\"]");
	      
	      click("//*[@id=\"timePickerItem_18\"]");	
	      waiting();
	      scrolldown();
	      click("//*[@id=\"IsRecurrence\"]");
	      waiting();
	      click("//*[@id=\"rectypeftw\"]");

	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(new Date());
	        Date today = calendar.getTime();
	        calendar.add(Calendar.DAY_OF_YEAR, 15);
	        Date twoweeks = calendar.getTime();
	        // 2/14/2023
	        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	        String twoweeksAsString = dateFormat.format(twoweeks);

	      sendKeys("//*[@id=\"RecurrenceEndDateOnly\"]",twoweeksAsString);
	      click("(//input[@name=\"save\"])[1]");
	    
	}


}
