package jp.mzw.revajaxmutator.test.tagged_gallery;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import jp.mzw.revajaxmutator.test.WebAppTestBase;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.owasp.webscarab.model.StoreException;



public class TaggedGalleryTest extends WebAppTestBase {
	
	@BeforeClass
	public static void beforeTestClass() throws StoreException, InterruptedException, IOException {		
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
	public void targetImageSrcTest() throws Exception {
		driver.get(URL);
		WebElement imgParent = driver.findElements(By.className("tg-thumb")).get(0)
				.findElement(By.tagName("img"));
		imgParent.click();
		Thread.sleep(500);
		try {
			String src = driver.findElement(By.id("img")).getAttribute("src");
			
			// src doesn't contain "null"
			Assert.assertTrue(!src.contains("null"));
		} catch (NoSuchElementException e) {
			
		}
	}
	
	@Test
	public void changeWindowSizeTest() throws Exception {
		driver.get(URL);
		driver.manage().window().setSize(new Dimension(50, 50));
		driver.findElements(By.className("tg-thumb")).get(0)
			.findElement(By.tagName("img")).click();
		driver.manage().window().setSize(new Dimension(500, 500));
		try {
			driver.findElement(By.xpath("/html/body/div[2]")).click();;
			Assert.assertTrue(true);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void clickOverImageTest() throws Exception {
		driver.manage().window().setSize(new Dimension(50, 50));
		driver.get(URL);
		driver.findElements(By.className("tg-thumb")).get(0)
			.findElement(By.tagName("img")).click();
		try {
			driver.findElement(By.className("overlayimg")).click();
			Assert.assertTrue(true);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void clickImageTest() throws Exception {
		driver.manage().window().setSize(new Dimension(1000, 1000));
		driver.get(URL);
		driver.findElements(By.className("tg-thumb")).get(0)
			.findElement(By.tagName("img")).click();
		try {
			driver.findElement(By.className("overlayimg")).click();
			Assert.assertTrue(true);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(false);
		}
	}
}
