package jp.mzw.revajaxmutator.test.blip_slideshow;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.*;
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
		WebAppTestBase.afterTestClass();
	}
	
	@Test
	public void displaySlideshow() throws InterruptedException {
		driver.get(URL);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-1")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-2")));
		
		WebElement elm = driver.findElement(By.xpath("//*[@id='show-2']/div[1]/a[1]"));
		elm.click();
	}
	
	@Test
	public void testGetCommMethod() throws InterruptedException, IOException, StoreException {
		// filter POST communications
		RevAjaxMutatorBase.relaunchProxyServerWith(
				new FilterPlugin("http://192.168.59.103:80/blip-slideshow-test/", "POST"));
		disableFilterPlugin = true;
		
		driver.get(URL);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-2")));
		WebElement elm = driver.findElement(By.xpath("//*[@id='show-1']/div[1]/a[1]"));

		try {
			elm.click();
			Assert.assertTrue(true);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(false);
		}
	}
	
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
