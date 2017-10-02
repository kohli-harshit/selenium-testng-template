package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

	By headerWelcome = By.xpath("//h2[text()='Welcome to WordPress!']");

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public Boolean isLoaded()
	{
		if(driver.findElements(headerWelcome).size()>0){
			return true;
		}
		return false;
	}
}
