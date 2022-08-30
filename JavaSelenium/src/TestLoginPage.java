import static org.junit.Assert.*;

import java.nio.file.FileSystems;
import java.time.Duration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestLoginPage {
	
	WebDriver driver;
	static String absPath = FileSystems.getDefault().getPath(new String()).toAbsolutePath().toString();
	static String driverPath = absPath + "\\chromedriver.exe";
	static String baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Starting login page test cycle!");
		System.out.println("Driver Path: "+ driverPath);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Ending login page test cycle!");
	}

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", driverPath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
		
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@After
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}
	
	@Test
	public void testAccessLoginPage() {
		String loginTitle = driver.findElement(By.className("orangehrm-login-title")).getAttribute("innerHTML");
		String copyrightText = driver.findElement(By.className("orangehrm-copyright")).getText();
		assertEquals("Login", loginTitle);
		assertEquals("OrangeHRM OS 5.1", copyrightText);
	}

	@Test
	public void testLoginSuccessfull() {
		WebElement usernameField = driver.findElement(By.name("username"));
		WebElement passwordField = driver.findElement(By.name("password"));
		
		usernameField.click();
		usernameField.sendKeys("Admin");
		
		passwordField.click();
		passwordField.sendKeys("admin123");
		
		WebElement loginButton = driver.findElement(By.className("orangehrm-login-button"));
		loginButton.click();
		
		String homepageTitle = driver.findElement(By.className("oxd-topbar-header-breadcrumb-module")).getText();
		assertEquals("PIM", homepageTitle);
	}
	
	@Test
	public void testLoginFail() {
		WebElement usernameField = driver.findElement(By.name("username"));
		WebElement passwordField = driver.findElement(By.name("password"));
		
		usernameField.click();
		usernameField.sendKeys("Admin");
		
		passwordField.click();
		passwordField.sendKeys("admin1234");
		
		WebElement loginButton = driver.findElement(By.className("orangehrm-login-button"));
		loginButton.click();
		
		String alertCredentials = driver.findElement(By.className("oxd-alert-content-text")).getText();
		assertEquals("Invalid credentials", alertCredentials);
	}
}
