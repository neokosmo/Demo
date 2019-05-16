package demo.pack;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Demo {

	static WebDriver driver;
	private String baseUrl;
	
	@AfterTest
	public void closeBrowser() {
		driver.close();
	}
	
	@DataProvider(name="testData")
	public Object[][] testData() throws Exception {
		return Util.getDataFromExcel(Util.XLSPATH, Util.SHEET_NAME, Util.TABLE_NAME);
	}
	
	@BeforeTest
	public void launchBrowser() throws Exception {

		System.setProperty("webdriver.gecko.driver", Util.FIREFOX_DRIVER);

		File pathToFirefoxBinary = new File(Util.FIREFOX_PATH);
		FirefoxBinary firefoxBinary = new FirefoxBinary(pathToFirefoxBinary);

		FirefoxProfile fireFoxProfile = new FirefoxProfile();
		FirefoxOptions fireFoxOptions = new FirefoxOptions();

		fireFoxOptions.setBinary(firefoxBinary);
		fireFoxOptions.setProfile(fireFoxProfile);

		driver = new FirefoxDriver(fireFoxOptions);

		baseUrl = Util.BASE_URL;

		driver.manage().timeouts().implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);

		driver.get(baseUrl + "/V4/");

	}

	@Test(dataProvider="testData")
	public void userLogIn(String username,String password) throws Exception {
		String actualTitle;
		String actualBoxtitle;

			driver.findElement(By.name("uid")).clear();
			driver.findElement(By.name("uid")).sendKeys(username);

			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys(password);

			driver.findElement(By.name("btnLogin")).click();
			
			try {
				Alert alt = driver.switchTo().alert();
				actualBoxtitle = alt.getText(); // get content of the Alter Message
				alt.accept();
				assertEquals(actualBoxtitle,Util.EXPECT_ERROR);
			} catch (NoAlertPresentException Ex) {
				actualTitle = driver.getTitle();
				// On Successful login compare Actual Page Title with Expected Title
				assertEquals(actualTitle,Util.EXPECT_TITLE);
		}
	}
}
