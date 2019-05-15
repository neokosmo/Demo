package demo.pack;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Demo {
	
	static WebDriver driver;
	private static String baseUrl;
	
	@BeforeTest
	public static void setup() throws Exception{
		
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
	
	@Test
	public static void userLogIn() throws Exception {
		
		driver.findElement(By.name("uid")).clear();
		driver.findElement(By.name("uid")).sendKeys(Util.USER_NAME);
		
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(Util.PASSWD);
		
		driver.findElement(By.name("btnLogin")).click();
		
	}
}
