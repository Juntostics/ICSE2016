package jp.mzw.revajaxmutator.test.blipslideshow;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.owasp.webscarab.model.StoreException;

import jp.mzw.revajaxmutator.test.WebAppTestBase;

public class BlipSlideshowTest extends WebAppTestBase {
	@BeforeClass
	public static void beforeTestClass() throws StoreException, InterruptedException, IOException {
		WebAppTestBase.beforeTestClass("blipslideshow.properties");
	}

	@AfterClass
	public static void afterTestClass() {
		WebAppTestBase.afterTestClass();
	}

	
	@Test
	public void displaySlideshow() throws InterruptedException {
		driver.get(URL);
		Thread.sleep(10000);
	}

}
