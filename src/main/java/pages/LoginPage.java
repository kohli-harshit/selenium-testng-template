package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.PropertiesManager;

public class LoginPage extends BasePage {

	public static final Logger logger = Logger.getLogger(LoginPage.class);

	private String loginPageURL = PropertiesManager.getProperty("websiteURL") + "/wp-login.php";
	private By txtLogin = By.id("user_login");
	private By txtPassword = By.id("user_pass");
	private By btnLogin = By.id("wp-submit");

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public boolean doLogin(String username,String password) throws InterruptedException {
		//Navigate
		driver.navigate().to(loginPageURL);
		logger.info("Navigated to " + loginPageURL);
		Thread.sleep(500);

		//Enter Credentials
		driver.findElement(txtLogin).sendKeys(username);
		Thread.sleep(500);
		driver.findElement(txtPassword).sendKeys(password);
		driver.findElement(btnLogin).click();
		logger.debug("Entered username = " + username + " and password = " + password);

		//Check Home page loaded
		HomePage homePage = new HomePage(driver);
		Boolean isHomePagePresent= homePage.isLoaded();
		logger.debug("Home Page found = " + isHomePagePresent);
		return isHomePagePresent;
	}
}
