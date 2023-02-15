package com.jan23.commontests;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreateAccount extends BaseTest{

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
	public static void openAccoutPage() throws InterruptedException
	{
		driver.findElement(By.xpath("//*[@id=\"Account_Tab\"]/a")).click();
		
		Thread.sleep(3000);
		popup();
		Thread.sleep(3000);
	
	}
	@Test
	public static void createAccount() throws InterruptedException
	{
		openAccoutPage();
		click("//*[@id=\"createNewButton\"]");
		Thread.sleep(3000);
		click("//*[@id=\"createNewMenu\"]/a[3]");
		Thread.sleep(3000);
		String actual_new_acc = getText("//*[@id=\"bodyCell\"]/div[1]/div[1]/div[1]/h2");
		Assert.assertEquals(actual_new_acc, "New Account");
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

		String new_acc_name = "myNewAccount"+timeStamp;
		driver.findElement(By.xpath("//*[@id=\"acc2\"]")).sendKeys(new_acc_name);
		Select type_dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"acc6\"]")));
		type_dropdown.selectByVisibleText("Technology Partner");
		scrolldown();
		Select pri_dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"00NDn00000TDb8X\"]")));
		pri_dropdown.selectByVisibleText("High");
		click("//*[@id=\"topButtonRow\"]/input[1]");
		Thread.sleep(3000);

		String actual_acc_name = getText("//*[@id=\"contactHeaderRow\"]/div[2]/h2");
		Assert.assertEquals(actual_acc_name, new_acc_name);
		
	}
	@Test
	public static void createView() throws InterruptedException
	{
		openAccoutPage();
		click("//*[@id=\"filter_element\"]/div/span/span[2]/a[2]");
		String new_view = getText("//*[@id=\"bodyCell\"]/div[1]/div[1]/div[1]/h2");
		Assert.assertEquals(new_view, "Create New View");
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());

		String new_view_name = "myNewView"+timeStamp;
		sendKeys("//*[@id=\"fname\"]", new_view_name);
		String new_view_unique_name = "myNewuniqueView"+timeStamp;
		sendKeys("//*[@id=\"devname\"]", new_view_unique_name);
		click("//*[@id=\"editPage\"]/div[1]/table/tbody/tr/td[2]/input[1]");
		Thread.sleep(3000);
		Select acc_view = new Select(driver.findElement(By.xpath("//select[@title=\"View:\"]")));
		acc_view.selectByVisibleText(new_view_name);
		String created_new_view = acc_view.getFirstSelectedOption().getText();
		Assert.assertEquals(created_new_view, new_view_name);

	}

	@Test
	public static void editView() throws InterruptedException
	{
		openAccoutPage();
		click("//*[@id=\"filter_element\"]/div/span/span[2]/a[1]");
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());
		String myupdated_view = "myupdated_view"+timeStamp;
		waiting();

		sendKeys("//*[@id=\"fname\"]", myupdated_view);
		waiting();
		Select field = new Select(driver.findElement(By.xpath("//*[@id=\"fcol1\"]")));
		field.selectByVisibleText("Account Name");
		Select operator = new Select(driver.findElement(By.xpath("//*[@id=\"fop1\"]")));
		operator.selectByVisibleText("contains");
		String filter_word = "my";
		sendKeys("//*[@id=\"fval1\"]",filter_word);
		scrolldown();
		click("//*[@id=\"editPage\"]/div[3]/table/tbody/tr/td[2]/input[1]");
		Thread.sleep(3000);
		Select acc_view = new Select(driver.findElement(By.xpath("//select[@title=\"View:\"]")));
		acc_view.selectByVisibleText(myupdated_view);
		String created_new_view = acc_view.getFirstSelectedOption().getText();
		Assert.assertEquals(created_new_view, myupdated_view);
		List<WebElement> table_rows = driver.findElements(By.xpath("//*[@id=\"ext-gen12\"]//table/tbody/tr/td[4]"));
		for (WebElement t_w: table_rows)
		{
			System.out.println(t_w.getText());
			Assert.assertTrue(t_w.getText().contains(filter_word));
		}		
	}
	@Test
	public static void mergeAccounts() throws InterruptedException
	{
		openAccoutPage();
		createAccount();
		openAccoutPage();
		click("//*[@id=\"toolsContent\"]/tbody/tr/td[2]/div/div/div/ul/li[4]/span/a");
		sendKeys("//*[@id=\"srch\"]","myNewAccount");
		click("//*[@id=\"stageForm\"]/div/div[2]/div[4]/input[2]");
		waiting();
		click("//*[@id=\"stageForm\"]/div/div[2]/div[1]/div/input[1]");
		click("//*[@id=\"stageForm\"]/div/div[2]/div[1]/div/input[2]");
		driver.switchTo().alert().dismiss();
		String actual_page_title = getText("//*[@id=\"bodyCell\"]/div/div[1]/div[1]/h1");
		Assert.assertEquals(actual_page_title, "Merge My Accounts");
		String first_acc_name = getText("//*[@id=\"stageForm\"]/div/div[2]/div[4]/table/tbody/tr[1]/th[1]");
		String second_acc_name = getText("//*[@id=\"stageForm\"]/div/div[2]/div[4]/table/tbody/tr[1]/th[2]");
		Assert.assertTrue(first_acc_name.contains("myNewAccount"));
		Assert.assertTrue(second_acc_name.contains("myNewAccount2023"));
		click("//*[@id=\"stageForm\"]/div/div[2]/div[1]/div/input[2]");
		driver.switchTo().alert().accept();
	}
	@Test
	public static void createAccountReport() throws InterruptedException
	{
		openAccoutPage();
		click("//*[@id=\"toolsContent\"]/tbody/tr/td[1]/div/div/div[1]/ul/li[2]/a");
		Thread.sleep(3000);
		String expected_reportpage="Unsaved Report";
		String actual_reportpage=getText("//*[@id=\"thePage:sectionHeader\"]/div/div/div[1]/h2");
		Assert.assertEquals(expected_reportpage, actual_reportpage);
		click("//input[@name=\"dateColumn\"]");
		click("//div[@class=\"dateColumnCategory\"]/parent::div/div[3]");
		String timeDate = new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date());
		sendKeys("//input[@name=\"startDate\"]", timeDate);
		sendKeys("//input[@name=\"endDate\"]", timeDate);
		click("//button[text()=\"Save\"]");
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());
		String report_name = "NewReport"+timeStamp;
		String report_u_name = "NewUReport"+timeStamp;

		sendKeys("//input[@name=\"reportName\"]", report_name);
		sendKeys("//input[@name=\"reportDevName\"]", report_u_name);
		waiting();
		click("//table[@id=\"dlgSaveReport\"]//button[text()=\"Save\"]");
		waiting();
		String actual_page_title = getText("//h2[@class=\"pageDescription\"]");
		Assert.assertEquals(actual_page_title, report_name);
	}



}
