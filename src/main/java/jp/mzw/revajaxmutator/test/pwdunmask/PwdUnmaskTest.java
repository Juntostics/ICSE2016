package jp.mzw.revajaxmutator.test.pwdunmask;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import jp.mzw.revajaxmutator.test.WebAppTestBase;

public class PwdUnmaskTest extends WebAppTestBase {

	static String URL;
	static String PATH_TO_FILE;
	static String UPDATE_PASSWD;

	static String PAPER_SELECT_ID 	= "paper";
	static String PAPER_PASSWD_ID 	= "pwd";
	static String PAPER_SUBMIT_ID 	= "submit";
	static String RESULT_ID 		= "result";
	static String UNMASKCHB_ID 		= "pwdunmask";

	static String SUCCESS_RESULT 	= "Your paper was successfully submitted.";

    @BeforeClass
    public static void readConfig() throws IOException {
		InputStream is = PwdUnmaskTest.class.getClassLoader().getResourceAsStream("pwdunmask.properties");
		Properties config = new Properties();
		config.load(is);
		
		URL = config.getProperty("url") != null ? config.getProperty("url") : "" ;
		PATH_TO_FILE = config.getProperty("path_to_file") != null ? config.getProperty("path_to_file") : "";
		UPDATE_PASSWD = config.getProperty("update_passwd") != null ? config.getProperty("update_passwd") : "";
    }

    @Test
    public void testSubmit() throws InterruptedException {
    	Thread.sleep(10000);
    	
    	driver.findElement(By.id(PAPER_SELECT_ID)).sendKeys(PATH_TO_FILE);
    	driver.findElement(By.id(PAPER_PASSWD_ID)).sendKeys(UPDATE_PASSWD);
    	driver.findElement(By.id(PAPER_SUBMIT_ID)).click();
    	assertEquals(SUCCESS_RESULT, driver.findElement(By.id(RESULT_ID)).getText());
    }
}
