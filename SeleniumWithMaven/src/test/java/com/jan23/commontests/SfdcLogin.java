package com.jan23.commontests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SfdcLogin extends BaseTest {
	

	@AfterMethod
	public void close()
	{
		driver.close();
	}
	@Test(enabled=false)
	public static void loginErrorMessageTc1() throws InterruptedException
	{
		driverSetup();
		WebElement uname=driver.findElement(By.xpath("//input[@id='username']"));
		uname.sendKeys("rani.ch@adcd.com");
	  
		WebElement password=driver.findElement(By.xpath("//input[@id='password']"));
		password.clear();
	  
		WebElement login=driver.findElement(By.xpath("//*[@id='Login']"));
		login.click();
		String expectedErrorMessage=driver.findElement(By.xpath("//*[@id=\"error\"]")).getText();
		String actualErrorMessage="Please enter your password.";
		Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
		
		
	}
	@Test(enabled=false)
	public static void loginSaleforce() throws InterruptedException
	{
		login();

		String actual_name=driver.findElement(By.id("userNavLabel")).getText();
		
		Assert.assertEquals(actual_name, "Rani chiliveri");	
	}
	@Test(enabled=false)
	public static void checkRememberMe() throws InterruptedException
	{
		driverSetup();
		WebElement uname=driver.findElement(By.xpath("//input[@id='username']"));
		uname.sendKeys("rani.ch@adcd.com");
	  
		WebElement password=driver.findElement(By.xpath("//input[@id='password']"));
		password.sendKeys("rani@12345 ");
	  
		WebElement rememberMe=driver.findElement(By.xpath("//*[@id=\"rememberUn\"]"));
		rememberMe.click();
		
		WebElement login=driver.findElement(By.xpath("//*[@id='Login']"));
		login.click();
		
		clickMenu();
		// logout
		driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[5]")).click();
		Thread.sleep(3000);
		String actualUserName=driver.findElement(By.xpath("//*[@id=\"idcard-identity\"]")).getText();
		String expectedUserName="rani.ch@adcd.com";
		Assert.assertEquals(expectedUserName, actualUserName);
		
	}
	@Test(enabled=false)
	public static void forgotPassword() throws InterruptedException
	{
		driverSetup();
		WebElement uname=driver.findElement(By.xpath("//input[@id='username']"));
		uname.clear();
		uname.sendKeys("rani.ch@adcd.com");

		WebElement password=driver.findElement(By.xpath("//input[@id='password']"));
		password.clear();
		driver.findElement(By.xpath("//*[@id=\"forgot_password_link\"]")).click();
		WebElement forgotpageUserName=driver.findElement(By.xpath("//*[@id=\"un\"]"));
		forgotpageUserName.sendKeys("rani.ch@adcd.com");
		
		driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
		String actualMessage=driver.findElement(By.xpath("//*[@id=\"header\"]")).getText();
		String expectedMessage="Check Your Email";
		Assert.assertEquals(expectedMessage, actualMessage);
		
		
	}
	@Test(enabled=false)
	public static void wrongCredentials() throws InterruptedException 
	{
		driverSetup();
		WebElement uname=driver.findElement(By.xpath("//input[@id='username']"));
		uname.sendKeys("123");
	  
		WebElement password=driver.findElement(By.xpath("//input[@id='password']"));
		password.sendKeys(" 22131");
	  
		WebElement login=driver.findElement(By.xpath("//*[@id='Login']"));
		login.click();
		String actualErrorName=driver.findElement(By.xpath("//*[@id=\"error\"]")).getText();
		String expectedUserName="Please check your username and password. If you still can't log in, contact your Salesforce administrator.";
		Assert.assertEquals(expectedUserName, actualErrorName);
	}
	}
	
	
