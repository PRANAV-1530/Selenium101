package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class SimpleFormDemoclass {

	private RemoteWebDriver driver;
	private String Status = "passed";

	@Parameters({"browser", "version", "platform"})
	@BeforeMethod
	public void setup(String browser, String version, String platform) throws MalformedURLException {
		String username = System.getenv("LT_USERNAME") == null ? "nvnkumaredu" : System.getenv("LT_USERNAME");
		String authkey = System.getenv("LT_ACCESS_KEY") == null ? "LT_jrkzmo4xRiez217wTnyMpvuD7tHsIU5HH3lwjZLOpbhNKJU"
				: System.getenv("LT_ACCESS_KEY");
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
		ltOptions.put("name", "TestScenario1" + this.getClass().getName());
		ltOptions.put("plugin", "git-testng");
		ltOptions.put("tags", new String[] { "Feature", "Magicleap", "Severe" });
		
		capabilities.setCapability("LT:Options", ltOptions);

		driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), capabilities);
	}

	@Test
	public void TestScenario1() throws InterruptedException {

		driver.get("https://www.lambdatest.com/selenium-playground/");
		driver.manage().window().maximize();
		Thread.sleep(2000);

		WebElement SimpleFormDemoLink = driver
				.findElement(By.xpath("//a[contains(@href, 'simple-form-demo')]"));
		SimpleFormDemoLink.click();

		String Expectedurl = driver.getCurrentUrl();
		String Actualurl = "simple-form-demo";

		if (Expectedurl.contains(Actualurl)) {
			System.out.println("URL matched");
		} else {
			System.out.println("URL does not matched!");
		}

		String message = "Welcome to LambdaTest.";
		WebElement mess_send = driver.findElement(By.id("user-message"));
		Thread.sleep(1000);
		mess_send.sendKeys(message);

		Thread.sleep(1000);
		WebElement button = driver.findElement(By.id("showInput"));
		button.click();

		WebElement your_mess = driver.findElement(By.id("message"));
		String print_mess = your_mess.getText();

		if (print_mess.contains(message)) {
			System.out.println("Message is matched");
		} else {
			System.out.println("Message is not matched!");
		}

	}

	@AfterMethod
	public void tearDown() {
		driver.executeScript("lambda-status=" + Status);
		driver.quit();
	}

}
