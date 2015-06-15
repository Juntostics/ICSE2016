package jp.mzw.revajaxmutator.test.jscover;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jp.mzw.revajaxmutator.test.WebAppTestBase;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JSCoverTest {
	
	@BeforeClass
	public static void launch() throws IOException {
		readProperties();
		launchProxyServer();
		launchBrowser();
	}

	protected static String CONFIG_FILENAME = "jscover.properties";
    protected static Properties mConfig;
	
    protected static String FIREFOX_BIN;
	protected static String PROXY_PORT;
	protected static int TIMEOUT;

	private static Thread server;
	protected static String JSCOVER_REPORT_DIR;
	protected static String JSCOVER_REPORT_FILE = "jscoverage.json";
	
	protected static WebDriver driver;
    protected static WebDriverWait wait;
	protected static String URL;
	protected static String PROXY;

    private static final String FIRST_QUIZ_TITLE = "Some quizzes about Japan";
    private static File configPhpFile = new File("/Users/yuta/public_html/quizzy/quizzy/quizzyConfig.php");

	public static void readProperties() throws IOException {
		InputStream is = WebAppTestBase.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME);
		mConfig = new Properties();
		mConfig.load(is);

		FIREFOX_BIN = mConfig.getProperty("firefox-bin");
		PROXY_PORT = mConfig.getProperty("proxy_port") != null ? mConfig.getProperty("proxy_port") : "3128";
		PROXY = "127.0.0.1:" + PROXY_PORT;
		TIMEOUT = mConfig.getProperty("timeout") != null ? Integer.parseInt(mConfig.getProperty("timeout")) : 3;
		
		JSCOVER_REPORT_DIR = mConfig.getProperty("jscover_report_dir") != null ? mConfig.getProperty("jscover_report_dir") : "jscover";
		
		URL = mConfig.getProperty("target_url") != null ? mConfig.getProperty("target_url") : null;
		configPhpFile = mConfig.getProperty("config_php_file_path") != null ? new File(mConfig.getProperty("config_php_file_path")) : null;
	}

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
					});
				}
			});
			server.start();
		}
	}
	
	public static void launchBrowser() {
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
	
	@Before
	public void teardown() {
		// NOP
	}
	
	@AfterClass
	public static void quit() {
		// Save coverage result
        driver.get("http://localhost/jscoverage.html");
        new WebDriverWait(driver, 2).until(
                ExpectedConditions.elementToBeClickable(By.id("storeTab"))).click();
        new WebDriverWait(driver, 2).until(
                ExpectedConditions.elementToBeClickable(By.id("storeButton"))).click();
        new WebDriverWait(driver, 2).until(
                ExpectedConditions.textToBePresentInElement(By.id("storeDiv"), "Coverage data stored at"));

		driver.quit();
		server.interrupt();
	}
	
	///// Tests for Quizzy
    // Test what should happen is actually happens
    @Test
    public void followUseCase() {
        WebElement element = wait.until(visibilityOfElementLocated(By.tagName("input")));
        element.click();
        WebElement desc = wait.until(visibilityOfElementLocated(By.className("quizzy_quiz_desc")));
        assertEquals(FIRST_QUIZ_TITLE, desc.getText());

        // start quiz
        click(By.id("quizzy_start_b"));
        WebElement checkButton
            = wait.until(visibilityOfElementLocated(By.id("quizzy_q0_foot_chk")));

        // choice first option and answer
        click(By.id("quizzy_q0_opt0"));
        checkButton.click();

        WebElement nextButton
            = wait.until(visibilityOfElementLocated(By.id("quizzy_q0_foot_nxt")));
        WebElement best = findElement(By.className("quizzy_opt_best"));
        assertTrue(best != null);
        assertEquals("✓", best.getText());
        nextButton.click();

        // choice first option and answer
        checkButton
            = wait.until(visibilityOfElementLocated(By.id("quizzy_q1_foot_chk")));
        click(By.id("quizzy_q1_opt0"));
        checkButton.click();
        WebElement mid = wait.until(visibilityOfElementLocated(By.className("quizzy_opt_mid")));
        assertEquals("5", mid.getText());
        desc = wait.until(visibilityOfElementLocated(By.id("quizzy_q1_exp")));
        desc = desc.findElement(By.tagName("p"));
        assertEquals("It's on February", desc.getText());

        nextButton = wait.until(visibilityOfElementLocated(By.id("quizzy_q1_foot_nxt")));
        nextButton.click();

        // get score
        WebElement score = wait.until(visibilityOfElementLocated(By.className("quizzy_result_score")));
        assertEquals("20", score.getText());

        WebElement againButton = findElement(By.className("quizzy_result_foot")).findElement(By.tagName("input"));
        againButton.click();

        // user can start quiz again
        wait.until(elementToBeClickable(By.id("quizzy_start_b")));
    }

    // test what shouldn't happen actually do not happen
    @Test
    public void tryInvalidOperation() {
        WebElement startButton = wait.until(visibilityOfElementLocated(By.id("quizzy_start_b")));
        // we cannot start quiz before selecting option
        startButton.click();
        try {
            wait.until(visibilityOfElementLocated(By.id("quizzy_q0_foot_chk")));
            fail("test should not start");
        } catch (TimeoutException e) {
            // expected exception
        }

        // start quiz
        click(By.tagName("input"));
        click(By.id("quizzy_start_b"));
        WebElement checkButton
            = wait.until(visibilityOfElementLocated(By.id("quizzy_q0_foot_chk")));

        // choice first option and answer
        click(By.id("quizzy_q0_opt0"));
        checkButton.click();
        WebElement nextButton
            = wait.until(visibilityOfElementLocated(By.id("quizzy_q0_foot_nxt")));
        nextButton.click();

        checkButton
            = wait.until(visibilityOfElementLocated(By.id("quizzy_q1_foot_chk")));

        // we cannot check answer unless we choice option
        checkButton.click();
        try {
            wait.until(visibilityOfElementLocated(By.className("quizzy_opt_mid")));
            fail("we cannot check answer unless choosing option");
        } catch (TimeoutException e) {
            // expected exception
        }
    }

    // test for sliding up. when user choose option, the one user choose and the
    // best one is left, other options must be hidden.
    @Test
    public void testSlideUp() throws InterruptedException {
        WebElement element = wait.until(visibilityOfElementLocated(By.tagName("input")));
        element.click();

        // start quiz
        click(By.id("quizzy_start_b"));
        WebElement checkButton
            = wait.until(visibilityOfElementLocated(By.id("quizzy_q0_foot_chk")));

        // choice first option and answer
        click(By.id("quizzy_q0_opt0"));
        checkButton.click();

        WebElement nextButton
            = wait.until(visibilityOfElementLocated(By.id("quizzy_q0_foot_nxt")));
        assertTrue(findElement(By.id("quizzy_q0_opt0")).isDisplayed());
        assertFalse(findElement(By.id("quizzy_q0_opt1")).isDisplayed());
        assertFalse(findElement(By.id("quizzy_q0_opt2")).isDisplayed());
        nextButton.click();

        // choice first option and answer
        checkButton
            = wait.until(visibilityOfElementLocated(By.id("quizzy_q1_foot_chk")));
        click(By.id("quizzy_q1_opt2"));
        checkButton.click();
        Thread.sleep(1000);
        nextButton = wait.until(visibilityOfElementLocated(By.id("quizzy_q1_foot_nxt")));

        assertFalse(findElement(By.id("quizzy_q1_opt0")).isDisplayed());
        assertTrue(findElement(By.id("quizzy_q1_opt1")).isDisplayed());
        assertTrue(findElement(By.id("quizzy_q1_opt2")).isDisplayed());
    }

    @Test
    public void testDescription() {
        WebElement element1 = wait.until(visibilityOfElementLocated(By.tagName("input")));
        assertFalse(findElement(By.className("quizzy_quiz_desc")).isDisplayed());
        element1.click();
        WebElement desc = wait.until(visibilityOfElementLocated(By.className("quizzy_quiz_desc")));
        assertEquals(FIRST_QUIZ_TITLE, desc.getText());
        assertFalse(driver.findElements(By.className("quizzy_quiz_desc")).get(1).isDisplayed());
        WebElement element2 = driver.findElements(By.tagName("input")).get(1);
        element2.click();
        desc = wait.until(visibilityOfElementLocated(By.id("quizzy_quiz_desc1")));
        assertFalse(driver.findElement(By.id("quizzy_quiz_desc0")).isDisplayed());
    }

    // test when playing test two times
    @Test
    public void doQuizTwice() throws InterruptedException {
        followAnotherUseCaseClickingLabel();
        Thread.sleep(200);
        WebElement againButton = findElement(By.className("quizzy_result_foot")).findElement(By.tagName("input"));
        againButton.click();
        Thread.sleep(3000);
        assertFalse(driver.findElement(By.id("quizzy_quiz_desc0")).isDisplayed());
        assertFalse(driver.findElement(By.id("quizzy_quiz_desc1")).isDisplayed());
        assertFalse(driver.findElements(By.tagName("input")).get(0).isSelected());
        assertFalse(driver.findElements(By.tagName("input")).get(1).isSelected());
        followUseCase();
    }

    // test when clicking label, and when clicking second option
    @Test
    public void followAnotherUseCaseClickingLabel() {
        WebElement label = wait.until(
                visibilityOfElementLocated(By.className("quizzy_quiz_lbl")));
        label.click();
        WebElement desc = wait.until(visibilityOfElementLocated(By.className("quizzy_quiz_desc")));
        assertEquals(FIRST_QUIZ_TITLE, desc.getText());

        // start quiz
        click(By.id("quizzy_start_b"));
        WebElement checkButton
            = wait.until(visibilityOfElementLocated(By.id("quizzy_q0_foot_chk")));

        WebElement nextButton = findElement(By.id("quizzy_q0_foot_nxt"));
        assertFalse("Next button must be hidden before choosing option",
                nextButton.isDisplayed());

        // choice second option and answer
        click(By.id("quizzy_q0_opt1"));
        checkButton.click();

        nextButton
            = wait.until(visibilityOfElementLocated(By.id("quizzy_q0_foot_nxt")));
        WebElement worst = findElement(By.className("quizzy_opt_worst"));
        assertTrue(worst != null);
        nextButton.click();

        // choice second option and answer
        checkButton
            = wait.until(visibilityOfElementLocated(By.id("quizzy_q1_foot_chk")));
        assertFalse("Previous next button must be disappear",
                driver.findElement(By.id("quizzy_q0_foot_nxt")).isDisplayed());
        click(By.id("quizzy_q1_opt1"));
        checkButton.click();
        WebElement best = wait.until(visibilityOfElementLocated(
                By.xpath("//*[@id='quizzy_q1_opt1_val']/*[@class='quizzy_opt_best']")));
        assertEquals("30", best.getText());
        desc = wait.until(visibilityOfElementLocated(By.id("quizzy_q1_exp")));
        desc = desc.findElement(By.tagName("p"));
        assertEquals("Exactly.", desc.getText());

        nextButton = wait.until(visibilityOfElementLocated(By.id("quizzy_q1_foot_nxt")));
        nextButton.click();

        // get score
        WebElement score = wait.until(visibilityOfElementLocated(By.className("quizzy_result_score")));
        assertEquals("30", score.getText());
    }

    // test 'Loading..' message is correctly shown. This test insert sleep
    // into the PHP file so that WebDriver certainly capture the message.
    @Test
    public void testLoadingByInsertingDelay() throws IOException, InterruptedException {
        insertOrRemoveDelayToConfigFile(true);
        loadUrl();
        try {
            WebElement loading = driver.findElement(By.id("quizzy"));
            if (!loading.getText().startsWith("Loading")) {
                throw new IllegalStateException("Loading message must be shown");
            }
            WebElement element = wait.until(visibilityOfElementLocated(By.tagName("input")));
            element.click();
            // start quiz
            click(By.id("quizzy_start_b"));
            wait.until(presenceOfElementLocated(By.xpath("//*[@id='quizzy']/*[@class='loading bottom left']")));
            WebElement checkButton
                = wait.until(visibilityOfElementLocated(By.id("quizzy_q0_foot_chk")));

            // choice first option and answer
            FileUtils.write(new File("/Users/yuta/Desktop/testlog_c.txt"), "choice first");
            click(By.id("quizzy_q0_opt0"));
            checkButton.click();
            wait.until(visibilityOfElementLocated(By.xpath("//*[@id='quizzy']/*[@class='loading bottom left']")));

            insertOrRemoveDelayToConfigFile(false);
        } catch (Exception e) { // Catch all Exception here to make sure we can clean up configPhpFile.
            insertOrRemoveDelayToConfigFile(false);
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            fail(writer.toString());
        }
    }

    // Utility methods
    private void loadUrl() {
        driver.get(URL);
    }
    
    private void insertOrRemoveDelayToConfigFile(boolean isInsert) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(configPhpFile));
        // Reading
        List<String> contents = new ArrayList<String>();
        String content;
        while ((content = reader.readLine()) != null)
            contents.add(content);
        reader.close();

        // Writing
        BufferedWriter writer = new BufferedWriter(new FileWriter(configPhpFile));
        if (isInsert) {
            writer.write("<?php sleep(1); ?>");
            writer.write(System.lineSeparator());
        }
        for (int i = isInsert ? 0 : 1; i < contents.size(); i++) {
            writer.write(contents.get(i));
            writer.write(System.lineSeparator());
        }
        writer.flush();
        writer.close();
    }

    private void click(By by) {
        WebElement target = findElement(by);
        target.click();
    }

    private WebElement findElement(By by) {
        return driver.findElement(by);
    }
}

