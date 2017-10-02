package pages;

import org.openqa.selenium.WebDriver;

public class BasePage {

	//The Browser instance that will be acted upon
	protected WebDriver driver;
	
	//Parameterized Constructor
	public BasePage(WebDriver driver)
	{
		this.driver = driver;
	}
}
