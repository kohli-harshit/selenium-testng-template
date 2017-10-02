package test;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.PropertiesManager;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class BaseTest {

	protected WebDriver driver;
	public static final Logger logger = Logger.getLogger(BaseTest.class);
	
	@BeforeSuite(alwaysRun=true)
	public void Start_Engine() throws Exception
	{
        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");
		PropertiesManager.initializeProperties();
	    logger.info("Properties Initialized");
	}
	
	@BeforeMethod(alwaysRun=true)
	public void launchBrowser() throws Exception
	{
		switch(PropertiesManager.getProperty("browserType"))
		{
			case "INTERNET_EXPLORER":
				InternetExplorerDriverManager.getInstance().setup();
				driver = new InternetExplorerDriver();
				break;
			case "FIREFOX":
				FirefoxDriverManager.getInstance().setup();
				driver = new FirefoxDriver();
				break;
			case "CHROME":
				ChromeDriverManager.getInstance().setup();
				driver = new ChromeDriver();
				break;
			case "PHANTOMJS":
				PhantomJsDriverManager.getInstance().setup();
				driver = new PhantomJSDriver();
			default:
				FirefoxDriverManager.getInstance().setup();
				driver = new FirefoxDriver();
		}

		driver.manage().timeouts().implicitlyWait(Long.parseLong(PropertiesManager.getProperty("implicitWait")), TimeUnit.SECONDS);
	    logger.info("Browser Launched");
	}
	
	@AfterMethod(alwaysRun=true)
	public void closeBrowser() throws Exception
	{		
		driver.close();
	}

	@AfterSuite(alwaysRun=true)
	public static void tearDown() throws Exception
	{
	}

	public static String captureScreen(WebDriver driver, String testName){

		String dest=null;

		try
		{
			TakesScreenshot screen = (TakesScreenshot) driver;
			File src = screen.getScreenshotAs(OutputType.FILE);

			dest = System.getProperty("user.dir")+"//snapshots//"+testName+".png";

			File target = new File(dest);
			FileUtils.copyFile(src, target);
		}
		catch (Exception e)
		{
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return dest;
	}
}
