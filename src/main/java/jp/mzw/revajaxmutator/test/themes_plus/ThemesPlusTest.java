package jp.mzw.revajaxmutator.test.themes_plus;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import jp.mzw.revajaxmutator.test.DockerManager;
import jp.mzw.revajaxmutator.test.WebAppTestBase;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.owasp.webscarab.model.StoreException;

public class ThemesPlusTest extends WebAppTestBase {
	@BeforeClass
	public static void beforeTestClass() throws StoreException, InterruptedException, IOException {
		WebAppTestBase.beforeTestClass("themes-plus.properties");
	}

	@AfterClass
	public static void afterTestClass() {
		try {
			Properties config = getConfig("themes-plus.properties");
			String jscover_report_dir = config.getProperty("jscover_report_dir") != null ? config.getProperty("jscover_report_dir") : null;
			if(jscover_report_dir != null) {
				File cov_result = new File(jscover_report_dir, "jscoverage.json");
		        if (cov_result.exists()) cov_result.delete();
		        ((JavascriptExecutor) driver).executeScript("jscoverage_report();");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		WebAppTestBase.afterTestClass();
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
