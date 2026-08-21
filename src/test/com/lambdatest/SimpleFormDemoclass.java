package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class SimpleFormDemoclass {

	private RemoteWebDriver driver;
	private String Status = "passed";

	public void setup() throws MalformedURLException {
		String username = System.getenv("LT_USERNAME") == null ? "nvnkumaredu" : System.getenv("LT_USERNAME");
		String authkey = System.getenv("LT_ACCESS_KEY") == null ? "LT_jrkzmo4xRiez217wTnyMpvuD7tHsIU5HH3lwjZLOpbhNKJU"
				: System.getenv("LT_ACCESS_KEY");
		String hub = "@hub.lambdatest.com/wd/hub";

		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browserName", "Chrome");
		
		java.util.HashMap<String, Object> ltOptions = new java.util.HashMap<String, Object>();
		ltOptions.put("platformName", "Windows 10");
		ltOptions.put("browserVersion", "103.0");
		ltOptions.put("resolution", "1024x768");
		ltOptions.put("build", "TestNG With Java");
		ltOptions.put("name", "TestScenario1" + this.getClass().getName());
		ltOptions.put("plugin", "git-testng");
		ltOptions.put("tags", new String[] { "Feature", "Magicleap", "Severe" });
		
		caps.setCapability("LT:Options", ltOptions);

		driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
	}

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

	public void tearDown() {
		driver.executeScript("lambda-status=" + Status);
		driver.quit();
	}

	public static void main(String[] args) throws Exception {
		SimpleFormDemoclass test = new SimpleFormDemoclass();
		test.setup();
		test.TestScenario1();
		test.tearDown();
	}
}
