package jp.mzw.revajaxmutator.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebAppTestBase {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    protected static Properties mConfig;
    protected static String FIREFOX_BIN;
    protected static String PROXY;
    protected static int TIMEOUT;
    
    protected static String URL = "http://localhost";
	
	private static void readConfig() throws IOException {
		InputStream is = WebAppTestBase.class.getClassLoader().getResourceAsStream("localenv.properties");
		mConfig = new Properties();
		mConfig.load(is);

		FIREFOX_BIN = mConfig.getProperty("firefox-bin");
		PROXY = mConfig.getProperty("proxy") != null ? mConfig.getProperty("proxy") : "127.0.0.1:8080";
		TIMEOUT = mConfig.getProperty("timeout") !=null ? Integer.parseInt(mConfig.getProperty("timeout")) : 3;
	}
    
    @BeforeClass
    public static void launchBrowser() throws IOException {
    	readConfig();
    	
        DesiredCapabilities cap = new DesiredCapabilities();
        
        if(FIREFOX_BIN != null) {
        	FirefoxBinary binary = new FirefoxBinary(new File(FIREFOX_BIN));
        	cap.setCapability(FirefoxDriver.BINARY, binary);
        }
        
        org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
        proxy.setHttpProxy(PROXY);
        proxy.setFtpProxy(PROXY);
        proxy.setSslProxy(PROXY);
        cap.setCapability(CapabilityType.PROXY, proxy);

        driver = new FirefoxDriver(cap);
    }

    @Before
	public void setup() {
    	driver.get(URL);
        wait = new WebDriverWait(driver, TIMEOUT);
    }
    
    @AfterClass
    public static void quitBrowser() {
        driver.quit();
    }

}
