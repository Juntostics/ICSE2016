package jp.mzw.revajaxmutator.test.themes_plus;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import jp.mzw.revajaxmutator.test.DockerManager;
import jp.mzw.revajaxmutator.test.WebAppTestBase;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.owasp.webscarab.model.StoreException;

public class ThemesPlusTest extends WebAppTestBase {
	@BeforeClass
	public static void beforeTestClass() throws StoreException, InterruptedException, IOException {
		WebAppTestBase.beforeTestClass("themes-plus.properties");
		
		Properties config = getConfig("themes-plus.properties");
		String dumpfile = config.getProperty("mysql_dump_file") != null ? config.getProperty("mysql_dump_file") : null;
		if(dumpfile != null) {
			DockerManager.runContainer4WpPluginTest(dumpfile);
		}
	}

	@AfterClass
	public static void afterTestClass() {
		WebAppTestBase.afterTestClass();
	}

	@Test
	public void testCountdown() throws Exception {
		driver.get(URL);
		int startSecond = getCountdownTimerSecond();
		Thread.sleep(3000);
		int endSecond = getCountdownTimerSecond();
		Assert.assertTrue(startSecond > endSecond);
	}
	
	private void waitUntilCountdownTimerDigitsToBePresent(){
		WebDriverWait countdownWait = new WebDriverWait(driver, TIMEOUT);
		countdownWait.until(new ExpectedCondition<Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				return driver.findElements(By.className("cd-digit")).size() > 0;
			}
		});
	}
	
	private int getCountdownTimerSecond(){
		List<WebElement> countdownTimerDigits = driver.findElements(By.className("cd-digit"));
		waitUntilCountdownTimerDigitsToBePresent();
		return Integer.valueOf(countdownTimerDigits.get(countdownTimerDigits.size()-1).getText().trim());
	}

}
