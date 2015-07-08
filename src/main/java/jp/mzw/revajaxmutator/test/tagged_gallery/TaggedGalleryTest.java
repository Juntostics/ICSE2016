package jp.mzw.revajaxmutator.test.tagged_gallery;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import jp.mzw.revajaxmutator.test.DockerManager;
import jp.mzw.revajaxmutator.test.WebAppTestBase;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.owasp.webscarab.model.StoreException;

public class TaggedGalleryTest extends WebAppTestBase {
	@BeforeClass
	public static void beforeTestClass() throws StoreException, InterruptedException, IOException {
//		Properties config = getConfig("tagged-gallery.properties");
//		String dumpfile = config.getProperty("mysql_dump_file") != null ? config.getProperty("mysql_dump_file") : null;
//		if(dumpfile != null) {
//			DockerManager.runContainer4WpPluginTest("mysql", dumpfile, "yuta/tg");
//		}
		
		WebAppTestBase.beforeTestClass("tagged-gallery.properties");
	}

	@AfterClass
	public static void afterTestClass() {
		((JavascriptExecutor) driver).executeScript("var xhr = new XMLHttpRequest(); xhr.open('POST', '/jscoverage-store'); xhr.send();");
		String localStorage = (String)((JavascriptExecutor) driver).executeScript("return localStorage['jscover']");
		try {
			Properties config = getConfig("tagged-gallery.properties");
			String jscover_report_dir = config.getProperty("jscover_report_dir") != null ? config.getProperty("jscover_report_dir") : null;
			if(jscover_report_dir != null) {
				FileUtils.write(new File(jscover_report_dir, "jscoverage.json"), localStorage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		WebAppTestBase.afterTestClass();
	}

	@Test
	public void test() throws Exception {
		driver.get(URL);
		driver.findElements(By.className("tg-thumb")).get(0)
				.findElement(By.tagName("img")).click();
		try {
			driver.findElement(By.id("overlay"));
		} catch (NoSuchElementException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void test2() throws Exception {
		driver.get(URL);
		WebElement imgParent = driver.findElements(By.className("tg-thumb")).get(0)
				.findElement(By.tagName("img")).findElement(By.xpath("./parent::*"));
		imgParent.click();
		try {
			driver.findElement(By.id("overlay"));
			Assert.assertTrue(false);
		} catch (NoSuchElementException e) {
			
		}
	}

}
