package jp.mzw.revajaxmutator.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jp.mzw.revajaxmutator.test.imgslider.ImgSliderTest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebAppTestBase {

	protected static String URL;
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    private static String CONFIG_FILENAME = "localenv.properties";
    
    protected static Properties CONFIG;
    protected static String FIREFOX_BIN;
    protected static String PROXY;
    protected static String PROXY_PORT;
    protected static int TIMEOUT;
    
    /**
     * Before instantiating this class,
     * explicitly call methods in the following order:
     * 1. readConfig
     * 2. launchBrowser
     * although multiple "@BeforeClass" can be allowed.
     * @throws IOException indicates "localenv.properties" not found on the resource path
     */
    @BeforeClass
    public static void beforeTestBaseClass() throws IOException {
    	readTestBaseConfig();
    	launchBrowser();
    }
	
    /**
     * Launch given Firefox browser with given proxy configuration
     */
    private static void launchBrowser() {
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
    
    /**
     * Read configuration to specify firefox and proxy
     * @throws IOException indicates "localenv.properties" not found on the resource path
     */
	private static void readTestBaseConfig() throws IOException {
		InputStream is = WebAppTestBase.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME);
		CONFIG = new Properties();
		CONFIG.load(is);

		URL = CONFIG.getProperty("url") != null ? CONFIG.getProperty("url") : "" ;
		FIREFOX_BIN = CONFIG.getProperty("firefox-bin");
		PROXY_PORT = CONFIG.getProperty("proxy_port") != null ? CONFIG.getProperty("proxy_port") : "80";
		PROXY = "127.0.0.1:" + PROXY_PORT;
		TIMEOUT = CONFIG.getProperty("timeout") != null ? Integer.parseInt(CONFIG.getProperty("timeout")) : 3;
	}

    @Before
	public void setup() {
    	driver.get(URL);
        wait = new WebDriverWait(driver, TIMEOUT);
    }
    
    @After
    public void teardown() {
    	// NOP
    }
    
    @AfterClass
    public static void afterTestBaseClass() {
    	if(JSCOVER) {
    		interruptProxyServer();
    	}
    	quitBrowser();
    }
    
    public static void quitBrowser() {
        driver.quit();
    }
    
    /*--------------------------------------------------
		For test classes
     --------------------------------------------------*/
    protected static boolean JSCOVER;
	private static Thread server;
	protected static String JSCOVER_URL = "http://localhost/jscoverage.html";
	protected static String JSCOVER_REPORT_DIR;
	protected static String JSCOVER_REPORT_FILE = "jscoverage.json";
	
    public static void beforeTestClass(String filename) throws IOException {
		InputStream is = ImgSliderTest.class.getClassLoader().getResourceAsStream(filename);
		Properties config = new Properties();
		config.load(is);
		
		URL = config.getProperty("url") != null ? config.getProperty("url") : "" ;
		
		JSCOVER = config.getProperty("jscover") != null ? Boolean.parseBoolean(config.getProperty("jscover")) : false;
		JSCOVER_REPORT_DIR = config.getProperty("jscover_report_dir") != null ? config.getProperty("jscover_report_dir") : "jscover";
		if(JSCOVER) {
			launchProxyServer();
		}
    }

    /**
     * Launch proxy server for JSCover
     */
	public static void launchProxyServer() {
		File cov_result = new File(JSCOVER_REPORT_DIR, JSCOVER_REPORT_FILE);
        if (cov_result.exists()) cov_result.delete();
        
		if(server == null) {
			server = new Thread(new Runnable() {
				@Override
				public void run() {
					jscover.Main.main(new String[]{
							"-ws",
							"--port=" + PROXY_PORT,
							"--proxy",
							"--local-storage",
							"--report-dir=" + JSCOVER_REPORT_DIR,
							"--no-instrument-reg=.*jquery.*",
							"--no-instrument-reg=.*bootstrap.*",
					});
				}
			});
			server.start();
		}
	}
    
	/**
	 * Interrupt proxy server for JSCover
	 */
    @SuppressWarnings("deprecation")
	public static void interruptProxyServer() {
        driver.get(JSCOVER_URL);
        
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(By.id("storeTab"))).click();
        driver.findElement(By.id("storeTab")).click();
        
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(By.id("storeButton")));
        driver.findElement(By.id("storeButton")).click();
        
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.textToBePresentInElement(By.id("storeDiv"), "Coverage data stored at"));
        
        server.interrupt();
    }

}
