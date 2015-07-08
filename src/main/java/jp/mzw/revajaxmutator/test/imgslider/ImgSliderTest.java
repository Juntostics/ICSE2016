package jp.mzw.revajaxmutator.test.imgslider;

import java.io.IOException;

import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.owasp.webscarab.model.StoreException;

import jp.mzw.revajaxmutator.FilterPlugin;
import jp.mzw.revajaxmutator.test.RevAjaxMutatorBase;
import jp.mzw.revajaxmutator.test.WebAppTestBase;

public class ImgSliderTest extends WebAppTestBase {

	@BeforeClass
	public static void beforeTestClass() throws StoreException, InterruptedException, IOException {
		WebAppTestBase.beforeTestClass("imgslider.properties");
	}

	@AfterClass
	public static void afterTestClass() {
		WebAppTestBase.afterTestClass();
	}
//	private static String URL = "http://localhost/imgslider/";

	@Test
	public void showICSE2016LogoTest() throws InterruptedException {
		driver.get(URL);

		wait.until(ExpectedConditions.elementToBeClickable(By.className("bx-next")));
		WebElement next_btn = driver.findElement(By.className("bx-next"));

		for (int year = 1999; year <= 2016; year++) {
			next_btn.click();

			String hidden = driver.findElement(By.id("2016")).getAttribute("aria-hidden");
			if ("false".equals(hidden)) {
				return;
			}

			Thread.sleep(500); // animation
		}
		Assert.fail("Cannot show ICSE 2016 logo");
	}

	@Test
	public void testGetCommMethod() throws InterruptedException, IOException, StoreException {
		// filter POST communications
		RevAjaxMutatorBase.relaunchProxyServerWith(
				new FilterPlugin("http://localhost:80/imgslider/api", "POST"));
		disableFilterPlugin = true;

		driver.get(URL);

		wait.until(ExpectedConditions.elementToBeClickable(By.className("bx-next")));
		WebElement next_btn = driver.findElement(By.className("bx-next"));

		for (int year = 1999; year <= 2016; year++) {
			next_btn.click();

			String hidden = driver.findElement(By.id("2016")).getAttribute("aria-hidden");
			if ("false".equals(hidden)) {
				RevAjaxMutatorBase.disableFilterPlugin();
				return;
			}

			Thread.sleep(500); // animation
		}
		Assert.fail("Cannot show ICSE 2016 logo");
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
