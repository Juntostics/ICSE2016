package jp.mzw.revajaxmutator.test.blip_slideshow;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.owasp.webscarab.model.StoreException;

import jp.mzw.revajaxmutator.FilterPlugin;
import jp.mzw.revajaxmutator.test.DockerManager;
import jp.mzw.revajaxmutator.test.RevAjaxMutatorBase;
import jp.mzw.revajaxmutator.test.WebAppTestBase;

public class BlipSlideshowTest extends WebAppTestBase {
	@BeforeClass
	public static void beforeTestClass() throws StoreException, InterruptedException, IOException {
//		Properties config = getConfig("blip-slideshow.properties");
//		String dumpfile = config.getProperty("mysql_dump_file") != null ? config.getProperty("mysql_dump_file") : null;
//		if(dumpfile != null) {
//			DockerManager.runContainer4WpPluginTest("mysql", dumpfile, "yuta/bs");
//		}
		
		WebAppTestBase.beforeTestClass("blip-slideshow.properties");
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
	
	//first test
	@Test
	public void checkWorking() throws InterruptedException{
		driver.get(URL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("slideshow")));
		
		List<WebElement> elms = driver.findElements(By.className("slideshow")).get(0).findElements(By.tagName("div"));
		Assert.assertTrue(elms.size() > 0);
		Thread.sleep(10000000);
		driver.findElements(By.className("slideshow")).get(0).findElements(By.tagName("a")).get(0).click();
	}

	@Test
	public void clickImage() throws InterruptedException{
		driver.get(URL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("slideshow")));
		
		List<WebElement> elms = driver.findElements(By.className("slideshow")).get(0).findElements(By.tagName("div"));
		Assert.assertTrue(elms.size() > 0);
	}

	
//	@Test
//	public void displaySlideshow() throws InterruptedException {
//		driver.get(URL);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-1")));
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-2")));
//		
//		WebElement elm = driver.findElement(By.xpath("//*[@id='show-2']/div[1]/a[1]"));
//		elm.click();
//	}
//			
//	
//	@Test
//	public void stopSlider() throws InterruptedException {
//		driver.get(URL);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-1")));
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-2")));
//		
//		Actions actions = new Actions(driver);
//		WebElement target = driver.findElement(By.xpath("/html/body/div/div/div/div/article/div/div[2]"));
//		actions.moveToElement(target);
//		actions.perform();
//		
//		Thread.sleep(1000);
//		
//		WebElement elm = driver.findElement(By.xpath("/html/body/div/div/div/div/article/div/div[2]/div[3]/ul/li[3]/a"));
//		elm.click();
//	}
//	
	
//	Default set test
//	@Test
//	public void testGetCommMethod() throws InterruptedException, IOException, StoreException {
//		// filter POST communications
//		RevAjaxMutatorBase.relaunchProxyServerWith(
//				new FilterPlugin("http://192.168.59.103:80/blip-slideshow-test/", "POST"));
//		disableFilterPlugin = true;
//		
//		driver.get(URL);
//		
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-2")));
//		WebElement elm = driver.findElement(By.xpath("//*[@id='show-1']/div[1]/a[1]"));
//
//		try {
//			elm.click();
//			Assert.assertTrue(true);
//		} catch (NoSuchElementException e) {
//			Assert.assertTrue(false);
//		}
//	}
	
	private boolean disableFilterPlugin = false;
	@Rule
	public TestWatcher watchman = new TestWatcher() {
		@Override
		protected void failed(Throwable th, Description d) {
			try {
				if(disableFilterPlugin) {
					RevAjaxMutatorBase.disableFilterPlugin();
					disableFilterPlugin = false;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
}
