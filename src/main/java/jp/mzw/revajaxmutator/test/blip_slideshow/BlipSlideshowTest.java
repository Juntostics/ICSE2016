package jp.mzw.revajaxmutator.test.blip_slideshow;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.owasp.webscarab.model.StoreException;

import jp.mzw.revajaxmutator.test.DockerManager;
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
}
