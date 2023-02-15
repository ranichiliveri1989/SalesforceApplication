package com.jan23.commontests;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UserMenuDropDown extends BaseTest{
	
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
	
	@Test
	public static void selectMyProfile() throws InterruptedException, AWTException
	
	{
		
		//test case 1
		clickMenu();
		
		//test case 2
		
		WebElement myprofile=driver.findElement(By.xpath("//a[contains(text(),'My Profile')]"));
		myprofile.click();
		String actualPageTitle = driver.getTitle();
		String expectedPageTitle = "User: Rani";
		Assert.assertTrue(expectedPageTitle.contains(actualPageTitle));
		Thread.sleep(3000);
		
		//test case3
		
		scrolldown();
		
		WebElement profile=driver.findElement(By.xpath("//*[@id=\"chatterTab\"]/div[2]/div[2]/div[1]/h3/div/div/a/img"));
		profile.click();
		Thread.sleep(4000);
		
		WebElement contactFrame=driver.findElement(By.id("contactInfoContentId"));
		driver.switchTo().frame(contactFrame);
		Thread.sleep(4000);
		
		String expectedMenuName = "Edit Profile";
		
		// test case 4
		WebElement about=driver.findElement(By.xpath("//*[@id=\"aboutTab\"]/a"));
		about.click();
		Thread.sleep(4000);

		WebElement lastName=driver.findElement(By.xpath("//*[@id=\"lastName\"]"));
		lastName.clear();
		String updatedLastname = "chiliveri";
		lastName.sendKeys(updatedLastname);
		
		
		driver.findElement(By.xpath("//input[@value='Save All']")).click();
		driver.switchTo().defaultContent();
		
		
		Actions at = new Actions(driver);
	    at.sendKeys(Keys.PAGE_UP).build().perform();
	      
		String actualupdatedPageTitle = driver.findElement(By.xpath("//*[@id=\"tailBreadcrumbNode\"]")).getText();
		Assert.assertTrue(actualupdatedPageTitle.contains(updatedLastname));
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//test case5
		
		WebElement post=driver.findElement(By.xpath("//span[text()=\"Post\"]"));
		post.click();
		WebElement postFrame=driver.findElement(By.xpath("//iframe[@class=\"cke_wysiwyg_frame cke_reset\"]"));
		driver.switchTo().frame(postFrame);
		
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(4000);
		
		WebElement postText=driver.findElement(By.xpath("//html/body[contains(@class,'chatterPublisherRTE')]"));
		String str= "hi everyone,how are you?";
		postText.click();
		postText.sendKeys(str);
		
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.switchTo().parentFrame();
		WebElement postShare=driver.findElement(By.xpath("//input[@id=\"publishersharebutton\"]"));
		postShare.click();
		
		Thread.sleep(4000);
		
		
		String updatedText=driver.findElement(By.xpath("(//span[contains(@class,\"feeditemtext cxfeeditemtext\")]/p)[1]")).getText();
		Assert.assertEquals(updatedText, str);
		
		
		
		Thread.sleep(4000);
		
	}
	@Test
	public static void filevalidation() throws InterruptedException
	{
       clickMenu();
		
		WebElement myprofile=driver.findElement(By.xpath("//a[contains(text(),'My Profile')]"));
		myprofile.click();
		
		Thread.sleep(4000);
		
		WebElement file=driver.findElement(By.xpath("//*[@id=\"publisherAttachContentPost\"]/span[1]")); 
		file.click();
		
		Thread.sleep(4000);
		
		WebElement uploadFile=driver.findElement(By.xpath("//a[@id=\"chatterUploadFileAction\"]"));
		uploadFile.click();
		
		Thread.sleep(4000);
		
		WebElement chooseFile=driver.findElement(By.xpath("//*[@id=\"chatterFile\"]"));
		System.out.print(chooseFile);
		chooseFile.sendKeys("C:\\pic.png");
		
		driver.findElement(By.xpath("//input[@id=\"publishersharebutton\"]")).click();
		
		Thread.sleep(4000);
		
		String actualFile=driver.findElement(By.xpath("(//div[@class=\"contentFileTitle\"]/a/span)[1]")).getText();
		
		Assert.assertEquals(actualFile,"pic");	
		
	}
	@Test
	public static void photoUpload() throws InterruptedException
	{
       clickMenu();
		
		WebElement myprofile=driver.findElement(By.xpath("//a[contains(text(),'My Profile')]"));
		myprofile.click();
		
		Thread.sleep(4000);
		
		WebElement uploadpic=driver.findElement(By.xpath("//*[@id=\"uploadLink\"]"));
		Actions action=new Actions(driver);
		action.click(uploadpic).build().perform();
		
		Thread.sleep(4000);
		WebElement picChooseFileframe=driver.findElement(By.xpath("//*[@id=\"uploadPhotoContentId\"]"));
		driver.switchTo().frame(picChooseFileframe);
		
		Thread.sleep(3000);
		
		WebElement picChooseFile=driver.findElement(By.xpath("//*[@id=\"j_id0:uploadFileForm:uploadInputFile\"]"));
		picChooseFile.sendKeys("C:\\pic.png");
		
		Thread.sleep(3000);
		
		WebElement save=driver.findElement(By.xpath("//*[@id=\"j_id0:uploadFileForm:uploadBtn\"]"));
		
		save.click();
		
		Thread.sleep(4000);
		
		WebElement drag=driver.findElement(By.xpath("//*[@id=\"j_id0:outer\"]/div[1]/div/div/div/div[5]"));
		
		Actions crop = new Actions(driver);
		crop.clickAndHold(drag).moveByOffset(10,10).release().build().perform();
		
		Thread.sleep(4000);
		
		WebElement picsave=driver.findElement(By.xpath("//*[@id=\"j_id0:j_id7:save\"]"));
		picsave.click();
		
		
		Thread.sleep(4000);
		String ActualPicValidation=driver.findElement(By.xpath("(//*[@id=\"photoSection\"]/div/a)[1]")).getText();
		String ExpectedpicValidation="Update";
		Assert.assertEquals(ActualPicValidation, ExpectedpicValidation);
	}
	
	@Test
	public static void mySettings() throws InterruptedException, AWTException
	{
		//test case 7.1
		selectUserMenu();
		
		Thread.sleep(5000);
		//test case 7.2
		driver.findElement(By.xpath("//a[@title=\"My Settings\"]")).click();;
		Thread.sleep(3000);
		String actualSettingsValue=driver.findElement(By.xpath("//*[@id=\"PersonalSetup_font\"]/span[2]")).getText();
		String expectedSettingValue="My Settings";
		Assert.assertEquals(expectedSettingValue, actualSettingsValue);
		//test 7.3
		driver.findElement(By.xpath("//div[@id=\"PersonalInfo\"]/a")).click();
		driver.findElement(By.id("LoginHistory_font")).click();
		Thread.sleep(3000);
        
		scrolldown();
		
		String actualloginHistory=driver.findElement(By.xpath("//div[@class=\"pShowMore\"]/a")).getText();
		String expectedloginHistory="Download login history for last six months, including logins from outside the website, such as API logins (Excel .csv file) »";
		Assert.assertEquals(expectedloginHistory, actualloginHistory);
		Thread.sleep(3000);
		//test 7.4
		
	driver.findElement(By.xpath("//div[@id=\"DisplayAndLayout\"]/a")).click();
	Thread.sleep(2000);
		driver.findElement(By.xpath("//span[@id=\"CustomizeTabs_font\"]")).click();
		Thread.sleep(3000);
		Select customApp=new Select(driver.findElement(By.xpath("//*[@id=\"p4\"]")));
		customApp.selectByVisibleText("Salesforce Chatter");
		Thread.sleep(3000);
		
		WebElement d=driver.findElement(By.xpath("//*[@id=\"duel_select_0\"]"));
		Select availableTabs=new Select(d);
			Thread.sleep(3000);
	      availableTabs.selectByValue("report");
		Thread.sleep(3000);
		driver.findElement(By.id("duel_select_0_right")).click();
	Thread.sleep(3000);
	
		Select selectedTabs=new Select(driver.findElement(By.id("duel_select_1")));
	selectedTabs.selectByValue("report");
		String actual_text = selectedTabs.getFirstSelectedOption().getText();
		String expectedselectedTabsvalue="Reports";
		Assert.assertEquals(expectedselectedTabsvalue, actual_text);
//		
		// test 7.1 email
		driver.findElement(By.xpath("//*[@id=\"EmailSetup\"]/a")).click();
		driver.findElement(By.xpath("//span[@id=\"EmailSettings_font\"]")).click();
		WebElement name = driver.findElement(By.xpath("//*[@id=\"sender_name\"]"));
		name.clear();
		name.sendKeys("Rani ch");
		WebElement email = driver.findElement(By.xpath("//*[@id=\"sender_email\"]"));
		email.clear();
		email.sendKeys("rani.chiliveri@gmail.com");
		scrolldown();
		driver.findElement(By.xpath("//*[@id=\"bottomButtonRow\"]/input[1]")).click();
		String actual_text1 = driver.findElement(By.xpath("//*[@id=\"meSaveCompleteMessage\"]/table/tbody/tr/td[2]/div")).getText();
     	String exp_text = "Your settings have been successfully saved.";
		Assert.assertEquals(actual_text1, exp_text);
		
		// test calendar
		scrolldown();
		driver.findElement(By.xpath("//*[@id=\"CalendarAndReminders_font\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"Reminders_font\"]")).click();
		String oldwindow = driver.getWindowHandle();
		scrolldown();
		driver.findElement(By.xpath("//*[@id=\"testbtn\"]")).click();
		Thread.sleep(3000);
	      Set<String> wid = driver.getWindowHandles();
	      String[] getWindow = wid.toArray(new String[wid.size()]);
	      //switch to active tab
	      Thread.sleep(3000);

	      driver.switchTo().window(getWindow[1]);
	      String new_win_text_actual = driver.findElement(By.xpath("//*[@id=\"summary0\"]/div")).getText();
	      String exp_new_win_name = "Sample Event";
	      System.out.println(new_win_text_actual);
	      Assert.assertTrue(new_win_text_actual.contains(exp_new_win_name));
	      driver.close();
	      Thread.sleep(2000);
	      //switch to parent
	      driver.switchTo().window(oldwindow);
	}

	@Test
	public static void developerConsole() throws InterruptedException, AWTException
	{
		//test case 7.1
		selectUserMenu();
		
		Thread.sleep(5000);
		//test case 7.2
		String oldwindow = driver.getWindowHandle();
		driver.findElement(By.xpath("//a[@title=\"Developer Console (New Window)\"]")).click();
		Thread.sleep(3000);
	      Set<String> wid = driver.getWindowHandles();
	      String[] getWindow = wid.toArray(new String[wid.size()]);
	      //switch to active tab
	      Thread.sleep(3000);

	      driver.switchTo().window(getWindow[1]);
	      String new_win_text_actual = driver.getTitle();
	      String exp_new_win_name = "Developer Console";
	      Assert.assertEquals(new_win_text_actual,exp_new_win_name);
	      driver.close();
	      driver.switchTo().window(oldwindow);
	}

	@Test
	
	public static void logout() throws InterruptedException
	{
		//test case 7.1
		logout();
		String exp_url = "https://abcd422-dev-ed.develop.my.salesforce.com/secur/logout.jsp";
		String act_url = driver.getCurrentUrl();
		Assert.assertEquals(act_url, exp_url);
	}
}
	

		

	
		
		
		
		
		
	


