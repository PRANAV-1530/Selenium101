package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;


public class InputFormSubmitClass {

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
		ltOptions.put("name", "TestScenario3" + this.getClass().getName());
		ltOptions.put("plugin", "git-testng");
		ltOptions.put("tags", new String[] { "Feature", "Magicleap", "Severe" });
		
		caps.setCapability("LT:Options", ltOptions);

		driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
	}

	public void TestScenario3() throws InterruptedException {

		driver.get("https://www.lambdatest.com/selenium-playground/");
		driver.manage().window().maximize();
		Thread.sleep(2000);

		WebElement InputFormLink = driver
				.findElement(By.xpath("//a[contains(@href, 'input-form-demo')]"));
		InputFormLink.click();

		WebElement submit = driver.findElement(By.xpath("//button[text()='Submit']"));
		submit.click();

		Thread.sleep(1000);

		WebElement name = driver.findElement(By.id("name"));
		String Expected_validation = name.getAttribute("validationMessage");
		String Actual_validation = "Please fill out this field.";

		if (Expected_validation.equals(Actual_validation)) {
			System.out.println("Validation is properly appear.");
		} else {
			System.out.println("Validation is not properly appear.");
		}

		name.sendKeys("TestName");

		WebElement email = driver.findElement(By.id("inputEmail4"));
		email.sendKeys("Test123@gmail.com");

		WebElement password = driver.findElement(By.id("inputPassword4"));
		password.sendKeys("Test@1234");

		WebElement company = driver.findElement(By.xpath("//*[@id=\"company\"]"));
		company.sendKeys("TestCompany");

		WebElement website = driver
				.findElement(By.xpath("//div[@class='form-group w-6/12 smtablet:w-full']/input[@id=\"websitename\"]"));
		website.sendKeys("Testdomain.com");

		WebElement country = driver.findElement(By.name("country"));
		Select select = new Select(country);
		select.selectByVisibleText("United States");

		WebElement city = driver
				.findElement(By.xpath("//div[@class='form-group w-6/12 smtablet:w-full']/input[@id='inputCity']"));
		city.sendKeys("TestCity");

		WebElement address1 = driver.findElement(By.id("inputAddress1"));
		address1.sendKeys("TestAddress1");

		WebElement address2 = driver
				.findElement(By.xpath("//div[@class='form-group w-6/12 smtablet:w-full']/input[@id='inputAddress2']"));
		address2.sendKeys("TestAddress2");

		WebElement state = driver.findElement(By.id("inputState"));
		state.sendKeys("TestState");

		WebElement zipcode = driver
				.findElement(By.xpath("//div[@class='form-group w-6/12 smtablet:w-full']/input[@id='inputZip']"));
		zipcode.sendKeys("360002");

		submit.click();

		Thread.sleep(2000);

		WebElement successmessage = driver.findElement(By.cssSelector(".success-msg"));
		String Actualmessage = successmessage.getText();
		String Expectedmessage = "Thanks for contacting us, we will get back to you shortly.";

		if (Actualmessage.equals(Expectedmessage)) {
			System.out.println("Success message is properly appear.");
		} else {
			System.out.println("Success message is not properly appear.");
		}

	}

	public void tearDown() {
		driver.executeScript("lambda-status=" + Status);
		driver.quit();
	}
	public static void main(String[] args) throws Exception {
		InputFormSubmitClass test = new InputFormSubmitClass();
		test.setup();
		test.TestScenario3();
		test.tearDown();
	}
}

