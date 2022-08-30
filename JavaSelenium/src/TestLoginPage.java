import static org.junit.Assert.*;

import java.time.Duration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class TestLoginPage {
	
	WebDriver driver;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\jfl2\\Desktop\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
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
