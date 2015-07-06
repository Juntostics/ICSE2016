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
