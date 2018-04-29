import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.NoSuchElementException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
 
import static org.junit.Assert.*;
import static org.testng.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/* -----------------------------------------------------------------------------
* This program demonstrates data-driven automation of
* trusted authentication to https://developer.sisco.com 
* domain using existing https://ciscospark.com account.
* After reading properties of configuration file it tests
* validity of those values by verifying UI authentication.
* 
* @version 1.0
* @author  Vlad
* -----------------------------------------------------------------------------
*/


public class TrustedAuthentication {
/**
* @param args
*/
	
	private static MyProperties myProps = new MyProperties();

	private WebDriver driver;
	private String baseUrl = "https://developer.cisco.com/";

	
	public void myLoginLogoutRequest() {
		myProps.loadAndPrint();
		String myChomeDriver = MyProperties.props.getProperty("webdriver.mychrome.driver");
		System.setProperty("webdriver.chrome.driver", myChomeDriver);
        String appUrl = baseUrl;
		String email = MyProperties.props.getProperty("cisco.spark.login.email");
		String pswd = MyProperties.props.getProperty("cisco.spark.login.pswd");
		// 	WebDriver driver = new FirefoxDriver();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// launch the browser and open the application url
        driver.get(appUrl);
          
        // declare and initialize the variable to store the expected title of the webpage.
        String expectedStartTitle = "Cisco DevNet:";
          
        // fetch the title of the web page and save it into a string variable
        String actualTitle = driver.getTitle();
          
        // compare the expected title of the page with the actual title of the page and print the result
        if (actualTitle.startsWith(expectedStartTitle))
        	System.out.println("Verification Successful - The correct title is displayed on the web page.");
        else
        	System.out.println("Verification Failed - An incorrect title is displayed on the web page.");

        // maximize the browser window
        driver.manage().window().maximize();

        // click on the Login link
        driver.findElement(By.xpath("//div[@id='devnet-app']/div/div/div/div/div/div[3]/div/span")).click();
        // click on login with Spark
        driver.findElement(By.xpath("//div[2]/div/div/div[2]/div/div[7]/div/div")).click();

        // enter login info
        driver.findElement(By.id("IDToken1")).click();
        driver.findElement(By.id("IDToken1")).clear();
        driver.findElement(By.id("IDToken1")).sendKeys(email);
        
        driver.findElement(By.id("IDButton2")).click();
           
        driver.findElement(By.id("IDToken2")).click();
        driver.findElement(By.id("IDToken2")).clear();
        driver.findElement(By.id("IDToken2")).sendKeys(pswd);

        // find login button by id or by name
        driver.findElement(By.id("Button1")).click();
        // driver.findElement(By.name("Login.Submit")).click();
           
        // logout and close the web browser           
        driver.findElement(By.xpath("//div[@id='devnet-app']/div/div/div/div/div/div[3]/div/span[2]")).click();
        driver.findElement(By.xpath("//div[@id='cl-profile-menu']/div/span/div/div/div[2]/section[4]/ul/li/div")).click();
        driver.close();
        System.out.println("Test script executed successfully.");
	}


	  @Before
	  public void setUp() throws Exception {
	 	   System.setProperty("webdriver.chrome.driver", "path-to-your-chromedriver");
	 	   // driver = new FirefoxDriver();
	 	   driver = new ChromeDriver();
	 	   driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	 	   driver.get(baseUrl);
	  }

	
	  @Test
	  public void testPageTitle() throws Exception {
	        String expectedStartTitle = "Cisco DevNet:";
	        String actualTitle = driver.getTitle();
	        assertTrue(actualTitle.startsWith(expectedStartTitle), "Wrong page title");
	  }

	  @Test
	  public void testLoginLocators() throws Exception {
		  	String LoginLinkXpath = "//div[@id='devnet-app']/div/div/div/div/div/div[3]/div/span";
		  	assertTrue(isElementPresent(By.xpath(LoginLinkXpath)),"Login link is not present");
	  }

	  @Test
	  public void testLoginLogoutActions() throws Exception {
		  	   String email = "your-CiscoSpark-email";
		  	   String pswd="your-CiscoSpark-password";
		  	   // click on the Login link
		  	   driver.findElement(By.xpath("//div[@id='devnet-app']/div/div/div/div/div/div[3]/div/span")).click();
	           // click on login with Spark
	           driver.findElement(By.xpath("//div[2]/div/div/div[2]/div/div[7]/div/div")).click();
	           // enter login info
	           driver.findElement(By.id("IDToken1")).click();
	           driver.findElement(By.id("IDToken1")).clear();
	           driver.findElement(By.id("IDToken1")).sendKeys(email);
	           driver.findElement(By.id("IDButton2")).click();
	           driver.findElement(By.id("IDToken2")).click();
	           driver.findElement(By.id("IDToken2")).clear();
	           driver.findElement(By.id("IDToken2")).sendKeys(pswd);
	           // find login button by id or by name
	           driver.findElement(By.id("Button1")).click();
	           // driver.findElement(By.name("Login.Submit")).click();
	           // view profile
	           driver.findElement(By.xpath("//div[@id='devnet-app']/div/div/div/div/div/div[3]/div/span[2]")).click();
	           driver.findElement(By.xpath("//div[@id='cl-profile-menu']/div/span/div/div/div[2]/section[2]/ul/li[2]/div")).click();
	           // logout           
	           driver.findElement(By.xpath("//div[@id='devnet-app']/div/div/div/div/div/div[3]/div/span[2]")).click();
	           driver.findElement(By.xpath("//div[@id='cl-profile-menu']/div/span/div/div/div[2]/section[4]/ul/li/div")).click();
	           
	           testLoginLocators();
	  }
	  
	  @After
	  public void tearDown() throws Exception {
	    driver.quit();
	  }


	  private boolean isElementPresent(By by) {
		    try {
			      driver.findElement(by);
			      return true;
			} 
		    catch (NoSuchElementException e) {
			      return false;
			}
	  }

	
       public static void main(String[] args) {
    		String PropFile = ".\\wd.cfg"; // for Windows Environment
    		if (System.getProperty("os.name").contains("Linux"))
    			PropFile = "./wd.cfg";
       		if (args.length < 1) {
       			System.out.println("Usage: java TrustedAuthentication PropFile");
       			System.out.println("Running demo version with default params");
       		}
       		else {
       			PropFile = args[0];
       		}

       		MyProperties.propsFileName = PropFile;
       		TrustedAuthentication ta = new TrustedAuthentication();
       		for (int i=1; i<=3; i++) {
    		   System.out.println("iteration " + i);
        	   ta.myLoginLogoutRequest();
       		}
       }
}
