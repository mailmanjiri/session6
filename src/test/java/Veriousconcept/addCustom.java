package Veriousconcept;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class addCustom {
	WebDriver driver;
	By USERNAME_FIELD = By.xpath("//input[@id='user_name']");
	By PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By SIGNIN_BUTTON_FIELD = By.xpath("//button[@id='login_submit']");
	By HEADER_VALIDATION= By.xpath("//h3[contains(text(), 'Desposit Vs Expense')]");
	By CUSTOMER_MENU_FIELD = By.xpath("/html/body/div[1]/aside[1]/div/nav/ul[2]/li[2]/a/span");
	By ADD_CUSTOMER_MENU_FIELD = By.xpath("//*[@id=\"customers\"]/li[2]/a/span");
	By NEW_CUSTOMER_HEADER_FIELD = By.xpath("/html/body/div[1]/section/div/div[2]/div/div[1]/div[1]/div/div/header/div/strong");
	By FULL_NAME_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[1]/div/input");
	By COMPANY_NAME_DROPDOWN_FIELD = By.xpath("//select[@name='company_name']");
	By EMAIL_FIELD = By.xpath("//input[@name='email']");
	By PHONE_NUMBER_FIELD = By.xpath("//input[@name='phone']");
	By ADDRESS_FIELD = By.xpath("//input[@name='address']");
	By CITY_FIELD = By.xpath("//input[@name='city']");
	By ZIPCODE_FIELD = By.xpath("//input[@id='port']");
	By COUNTRY_FIELD = By.xpath("//select[@name='country']");
	By GROUP_FIELD = By.xpath("//select[@name='customer_group']");
	By SAVE_BUTTON = By.xpath("//button[@id='save_btn']");
    By LESS_CHAR_ERROR_MSG_FIELD= By.xpath("//*[@id=\"general_compnay\"]/div[7]/div/div/p");
    By MORE_CHAR_ERROR_MSG_FIELD= By.xpath("//*[@id=\"general_compnay\"]/div[7]/div/div/p");

	
	//Test data
	String username;
	String password;
	String headerTextValidation = "Desposit Vs Expense";
	String environment;
	String browser;
	String fullName;
	String email;
	String phoneNumber;
	String address;
	String city;
	String zipCode;
	String companyName="Techfios";
	String LessCharErrMsg= "Error: Do not allow less than 5 digits";
	String MoreCharErrMsg= "Error: Do not allow more than 9 digits";

	
	@BeforeClass
	public void readConfig() {
		// to read a file can use either of the class= bufferedReader/InputStream/FileReader/Scanner
		InputStream input;
		try {
			input = new FileInputStream("src/main/java/config/config1.properties");
			// to understand key value concept to java, need to invoke properties class
			Properties prop= new Properties();
			prop.load(input);
			browser=prop.getProperty("browser");
			environment= prop.getProperty("url");
			username=prop.getProperty("username");
			password=prop.getProperty("password");
			fullName=prop.getProperty("fullName");
			email= prop.getProperty("email");
			phoneNumber= prop.getProperty("phoneNumber");
			address= prop.getProperty("address");
			city= prop.getProperty("city");
			zipCode= prop.getProperty("zipCode");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@BeforeMethod
	public void init() {
	if (browser.equalsIgnoreCase("chrome")) {System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
	       driver = new ChromeDriver();}
		
	else if (browser.equalsIgnoreCase("edge")) {System.setProperty("webdriver.edge.driver", "driver\\msedgedriver.exe");
		   driver= new EdgeDriver();}
	else {System.out.println("please select a browser");}
	
	
	
	
		driver.manage().deleteAllCookies();
		driver.get(environment);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void login() {

		driver.findElement(USERNAME_FIELD).sendKeys(username);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		Assert.assertEquals(driver.findElement(HEADER_VALIDATION).getText(), headerTextValidation, "page not found");
	}
	
	@Test
	public void addCustomer() {
		login();
		driver.findElement(CUSTOMER_MENU_FIELD).click();
		driver.findElement(ADD_CUSTOMER_MENU_FIELD).click();
		// validate the add customer form is available
		Assert.assertTrue(driver.findElement(NEW_CUSTOMER_HEADER_FIELD).isDisplayed(),"Add customer page is not available");
		Random rnd= new Random();
		
		driver.findElement(FULL_NAME_FIELD).sendKeys(fullName+generateRandomNum(999));
		
		
		driver.findElement(EMAIL_FIELD).sendKeys(generateRandomNum(999)+email);
		driver.findElement(PHONE_NUMBER_FIELD).sendKeys(phoneNumber+generateRandomNum(9999));
		driver.findElement(ADDRESS_FIELD).sendKeys(address);
		driver.findElement(CITY_FIELD).sendKeys(city);
		driver.findElement(ZIPCODE_FIELD).sendKeys(zipCode);
		selectFromDropDown(driver.findElement(COMPANY_NAME_DROPDOWN_FIELD),companyName);
		Select sel1 = new Select(driver.findElement(COUNTRY_FIELD));
		sel1.selectByVisibleText("United States of America");
		Select sel2 = new Select(driver.findElement(GROUP_FIELD));
		sel2.selectByVisibleText("Java");

//		driver.findElement(SAVE_BUTTON).click();
//		Assert.assertTrue(driver.getPageSource().contains(fullName), "Account unsuccessfully created");
//		
//		
	}
	
	public void selectFromDropDown(WebElement element, String visisbleText){
		Select sel = new Select(element);
		sel.selectByVisibleText(visisbleText);
		}
	
	public int generateRandomNum(int rndNum) {
		Random rnd= new Random();
		int generateRndNumber= rnd.nextInt(rndNum);
		return generateRndNumber;
	}
	
	@Test
	public void validateZipCodeField() {
		login();
		driver.findElement(CUSTOMER_MENU_FIELD).click();
		driver.findElement(ADD_CUSTOMER_MENU_FIELD).click();
		// validate the add customer form is available
		Assert.assertTrue(driver.findElement(NEW_CUSTOMER_HEADER_FIELD).isDisplayed(),"Add customer page is not available");
		
	    driver.findElement(FULL_NAME_FIELD).sendKeys(fullName+generateRandomNum(999));
		driver.findElement(EMAIL_FIELD).sendKeys(generateRandomNum(999)+email);
		driver.findElement(PHONE_NUMBER_FIELD).sendKeys(phoneNumber+generateRandomNum(9999));
		driver.findElement(ZIPCODE_FIELD).sendKeys("123");
		driver.findElement(SAVE_BUTTON).click();
		Assert.assertEquals(driver.findElement(LESS_CHAR_ERROR_MSG_FIELD).getText(), LessCharErrMsg, "error msg does not match");
        
		driver.findElement(FULL_NAME_FIELD).click();
		driver.findElement(ZIPCODE_FIELD).clear();
		driver.findElement(ZIPCODE_FIELD).sendKeys("1237979889088");
		driver.findElement(SAVE_BUTTON).click();
		Assert.assertEquals(driver.findElement(MORE_CHAR_ERROR_MSG_FIELD).getText(), MoreCharErrMsg, "error msg does not match");
		}

		
	}
	

