package test;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.CSVAnnotation;
import utils.GenericDataProvider;
import utils.PropertiesManager;

import java.lang.reflect.Method;
import java.util.Map;

public class LoginTest extends BaseTest{

	public static final Logger logger = Logger.getLogger(LoginTest.class);

	//Navigate to Home Page Before each Test
	@BeforeMethod(alwaysRun=true)
	public void navigateToHomePage() throws Exception
	{
		String URL = PropertiesManager.getProperty("websiteURL");
		driver.navigate().to(URL);
		logger.info("Navigated to " + URL);
	}

	@Test(groups={"Smoke","Regression"},dataProvider = "dataproviderForTestCase", dataProviderClass = GenericDataProvider.class)
	@CSVAnnotation.CSVFileParameters(path = "src\\test\\java\\test-data\\LoginTestData.csv", delimiter = "###")
	public void testLogin(int rowNo, Map<String, String> inputDataMap, Method method)
	{
		try
		{
			LoginPage loginPage = new LoginPage(driver);
			String username=inputDataMap.get("username");
			String password=inputDataMap.get("password");
			Assert.assertEquals(loginPage.doLogin(username,password), true,"Login should succeed with username - " + username + " and password - " + password );
		}
		catch(Exception|AssertionError ex)
		{
			logger.error(ExceptionUtils.getStackTrace(ex));
			captureScreen(driver,method.getName());
			Assert.fail(ex.getMessage());
		}
	}
}