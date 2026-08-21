package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DragAndDropSlidersClass {

	private RemoteWebDriver driver;
	private String Status = "passed";

	@Parameters({"browser", "version", "platform"})
	@BeforeMethod
	public void setup(String browser, String version, String platform) throws MalformedURLException {
		String username = System.getenv("LT_USERNAME") == null ? "nvnkumaredu" : System.getenv("LT_USERNAME");
		String authkey = System.getenv("LT_ACCESS_KEY") == null ? "LT_jrkzmo4xRiez217wTnyMpvuD7tHsIU5HH3lwjZLOpbhNKJU" : System.getenv("LT_ACCESS_KEY");
		String hub = "@hub.lambdatest.com/wd/hub";

		MutableCapabilities capabilities;
		if (browser.equalsIgnoreCase("Chrome")) {
			capabilities = new ChromeOptions();
		} else if (browser.equalsIgnoreCase("Safari")) {
			capabilities = new SafariOptions();
		} else {
			capabilities = new DesiredCapabilities();
			capabilities.setCapability("browserName", browser);
		}
		
		capabilities.setCapability("platformName", platform);
		capabilities.setCapability("browserVersion", version);
		
		java.util.HashMap<String, Object> ltOptions = new java.util.HashMap<String, Object>();
		ltOptions.put("username", username);
		ltOptions.put("accessKey", authkey);
		ltOptions.put("project", "Untitled");
		ltOptions.put("selenium_version", "4.0.0");
		ltOptions.put("w3c", true);
		ltOptions.put("build", "TestNG With Java");
		ltOptions.put("name", "TestScenario2" + this.getClass().getName());
		ltOptions.put("plugin", "git-testng");
		ltOptions.put("tags", new String[] { "Feature", "Magicleap", "Severe" });
		
		capabilities.setCapability("LT:Options", ltOptions);

		driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), capabilities);
	}

	@Test
	public void TestScenario2() throws InterruptedException {

		driver.get("https://www.lambdatest.com/selenium-playground/");
		driver.manage().window().maximize();
		Thread.sleep(2000);

		WebElement DrageAndDroplink = driver.findElement(
				By.xpath("//a[contains(@href, 'drag-drop-range-sliders-demo')]"));
		DrageAndDroplink.click();

		Thread.sleep(1000);
		WebElement slider3 = driver.findElement(By.xpath(".//*[@id='slider3']/div/input"));
		// js.executeScript("arguments[0].scrollIntoView(true);", slider3);
		Thread.sleep(1000);
		Actions move = new Actions(driver);
		Actions action = (Actions) move.dragAndDropBy(slider3, 99, 0);
		action.perform();

		WebElement Expected_Range = driver.findElement(By.xpath(".//*[@id='slider3']/div/output"));
		String Expe_range = Expected_Range.getText();
		String Actual_Range = "95";

		if (Expe_range.contains(Actual_Range)) {
			System.out.println("Range is matched");
		} else {
			System.out.println("Range is not matched!");
		}

	}

	@AfterMethod
	public void tearDown() {
		driver.executeScript("lambda-status=" + Status);
		driver.quit();
	}

}

