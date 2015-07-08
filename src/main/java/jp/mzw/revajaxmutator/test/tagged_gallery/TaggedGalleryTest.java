package jp.mzw.revajaxmutator.test.tagged_gallery;

import java.io.IOException;
import jp.mzw.revajaxmutator.test.WebAppTestBase;

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
		WebAppTestBase.afterTestClass();
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
	public void goal_test() throws Exception {
		driver.get(URL);
		WebElement imgParent = driver.findElements(By.className("tg-thumb")).get(0)
				.findElement(By.tagName("img"));
		imgParent.click();
		Thread.sleep(500);
		try {
			String src = driver.findElement(By.id("img")).getAttribute("src");
			
			// src doesn't contain "null"
			Assert.assertTrue(!src.contains("null"));
//			Assert.assertEquals(src, "http://192.168.59.103/wp-content/plugins/tagged-gallery/img.php?img=http://192.168.59.103/wp-content/uploads/2015/07/icse2016-logo.jpg&size=700x700");
		} catch (NoSuchElementException e) {
			
		}
	}

}
