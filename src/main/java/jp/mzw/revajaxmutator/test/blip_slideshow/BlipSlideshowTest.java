package jp.mzw.revajaxmutator.test.blip_slideshow;

import java.io.IOException;
import java.util.List;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.owasp.webscarab.model.StoreException;

import jp.mzw.revajaxmutator.test.WebAppTestBase;

public class BlipSlideshowTest extends WebAppTestBase {
	@BeforeClass
	public static void beforeTestClass() throws StoreException, InterruptedException, IOException {
		WebAppTestBase.beforeTestClass("blip-slideshow.properties");
	}

	@AfterClass
	public static void afterTestClass() {
		WebAppTestBase.afterTestClass();
	}


	@Test
	public void displaySlideshow() throws InterruptedException {
		driver.get(URL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-1")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-2")));
		
		WebElement elm = driver.findElement(By.xpath("//*[@id='show-1']/div[1]/a[1]"));
		elm.click();
		Thread.sleep(3000);
	}
	
}
