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

	//	add in no1 before
	//	remove in no4 before
//	@Test
//	public void clickImageTest() throws Exception {
//		driver.get(URL);
//		try {
//			driver.findElements(By.className("tagged-gallery")).get(0).click();
//			Assert.assertTrue(driver.findElements(By.className("overlay")).size() > 0);
//			driver.findElement(By.id("img")).click();
//			Assert.assertTrue(driver.findElements(By.className("overlay")).size() == 0);
//		} catch (NoSuchElementException e){
//			Assert.assertTrue(false);
//		}
//	}

	//	add in no1 before
	//	remove in no4 before
//	@Test
//	public void removeImageTest() throws Exception {
//		driver.get(URL);
//		try {
//			driver.findElements(By.className("tagged-gallery")).get(0).click();
//			Assert.assertEquals("1", driver.findElement(By.id("img")).getCssValue("opacity"));
//			driver.findElement(By.id("img")).click();
//			Assert.assertTrue(driver.findElements(By.id("img")).size() == 0);
//		} catch (NoSuchElementException e){
//			Assert.assertTrue(false);
//		}
//	}
	
	// add in no1 after
	// rm in no2 before
//	@Test
//	public void checkImageSrc1() throws Exception {
//		driver.get(URL);
//		try {
//			driver.findElements(By.className("tagged-gallery")).get(0).click();
//			WebElement img = driver.findElement(By.id("img")); 
//			Assert.assertTrue(img.getAttribute("src").matches(".*img.php.*"));
//			Assert.assertTrue(Integer.parseInt(img.getCssValue("z-index")) > 5);
//		} catch (NoSuchElementException e){
//			Assert.assertTrue(false);
//		}
//	}

	// add in no2 before
	// remove in no4 before
//	@Test
//	public void checkImageSrc2() throws Exception {
//		driver.get(URL);
//		try {
//			driver.findElements(By.className("tagged-gallery")).get(0).click();
//			WebElement img = driver.findElement(By.id("img")); 
//			Assert.assertTrue(img.getAttribute("src").matches(".*null.*"));
//			Assert.assertTrue(Integer.parseInt(img.getCssValue("z-index")) > 5);
//		} catch (NoSuchElementException e){
//			Assert.assertTrue(false);
//		}
//	}

	
	// add in no4 before
	// remove in no6 before
//	@Test
//	public void clickImageTest() throws Exception {
//		driver.get(URL);
//		try {
//			driver.findElements(By.className("tg-resizecrop")).get(0).click();
//			Assert.assertTrue(driver.findElements(By.className("overlay")).size() > 0);
//			driver.findElement(By.id("img")).click();
//			Assert.assertTrue(driver.findElements(By.className("overlay")).size() == 0);
//		} catch (NoSuchElementException e){
//			Assert.assertTrue(false);
//		}
//	}

	// add in no4 before
	// remove in no6 before
//	@Test
//	public void removeImageTest() throws Exception {
//		driver.get(URL);
//		try {
//			driver.findElements(By.className("tg-resizecrop")).get(0).click();
//			Assert.assertEquals("1", driver.findElement(By.id("img")).getCssValue("opacity"));
//			driver.findElement(By.id("img")).click();
//			Assert.assertTrue(driver.findElements(By.id("img")).size() == 0);
//		} catch (NoSuchElementException e){
//			Assert.assertTrue(false);
//		}
//	}

	// add in no4 before
	// remove in no6 before
//	@Test
//	public void checkImageSrc2() throws Exception {
//		driver.get(URL);
//		try {
//			driver.findElements(By.className("tg-resizecrop")).get(0).click();
//			WebElement img = driver.findElement(By.id("img")); 
//			Assert.assertTrue(img.getAttribute("src").matches(".*img.php.*"));
//			Assert.assertTrue(Integer.parseInt(img.getCssValue("z-index")) > 5);
//		} catch (NoSuchElementException e){
//			Assert.assertTrue(false);
//		}
//	}

	// add in no6 before
	@Test
	public void clickImageTest() throws Exception {
		driver.get(URL);
		try {
			driver.findElements(By.className("tg-resizecrop")).get(0).click();
			Assert.assertTrue(driver.findElements(By.className("overlay")).size() > 0);
			driver.findElement(By.className("overlay")).click();
			Assert.assertTrue(driver.findElements(By.className("overlay")).size() == 0);
		} catch (NoSuchElementException e){
			Assert.assertTrue(false);
		}
	}

	// add in no6 before
	@Test
	public void removeImageTest() throws Exception {
		driver.get(URL);
		try {
			driver.findElements(By.className("tg-resizecrop")).get(0).click();
			Assert.assertEquals("1", driver.findElement(By.className("overlayimg")).getCssValue("opacity"));
			driver.findElement(By.className("overlay")).click();
			Assert.assertTrue(driver.findElements(By.xpath("/html/body/div[3]")).size() == 0);
		} catch (NoSuchElementException e){
			Assert.assertTrue(false);
		}
	}

	// add in no6 before
	@Test
	public void checkImageSrc2() throws Exception {
		driver.get(URL);
		try {
			driver.findElements(By.className("tg-resizecrop")).get(0).click();
			WebElement img = driver.findElement(By.xpath("/html/body/div[3]/img"));
			Assert.assertTrue(img.getAttribute("src").matches(".*icse2016-logo.*"));
			Assert.assertEquals("auto", img.getCssValue("z-index"));
		} catch (NoSuchElementException e){
			Assert.assertTrue(false);
		}
	}

	// add in no6 after
	@Test
	public void clickOverlayImg() throws Exception {
		driver.get(URL);
		try {
			driver.findElements(By.className("tg-resizecrop")).get(0).click();
			Assert.assertTrue(driver.findElements(By.className("overlayimg")).size() > 0);
			driver.findElement(By.className("overlayimg")).click();
			Assert.assertTrue(driver.findElements(By.className("overlayimg")).size() == 0);
		} catch (NoSuchElementException e){
			Assert.assertTrue(false);
		}
	}
	
	// add in no6 after
	@Test
	public void resizeWindow() throws Exception {
		driver.get(URL);
		try {
			driver.findElements(By.className("tg-resizecrop")).get(0).click();
			String val_before = driver.findElement(By.className("overlayimg")).getCssValue("margin-left");
			driver.manage().window().setSize(new Dimension(500, 50));
			String val_after = driver.findElement(By.className("overlayimg")).getCssValue("margin-left");
			Assert.assertFalse(val_before == val_after);
		} catch (NoSuchElementException e){
			Assert.assertTrue(false);
		}
	}
	
	// add in no6 after
	@Test
	public void resizeWindow2() throws Exception {
		
		driver.get(URL);
		try {
			driver.manage().window().setSize(new Dimension(1000, 500));
			driver.findElements(By.className("tg-resizecrop")).get(1).click();
			WebElement img = driver.findElement(By.className("overlayimg"));
			String val_before = img.getCssValue("margin-left");
			driver.manage().window().setSize(new Dimension(500, 50));
			String val_after = img.getCssValue("margin-left");
			Assert.assertFalse(val_before == val_after);
		} catch (NoSuchElementException e){
			Assert.assertTrue(false);
		}
	}
	

	
	
	// add in no2 before
//	@Test
//	public void checkImageSrc2() throws Exception {
//		driver.get(URL);
//		try {
//			driver.findElements(By.className("tagged-gallery")).get(0).click();
//			WebElement img = driver.findElement(By.id("img")); 
//			Assert.assertTrue(img.getAttribute("src").matches(".*tagged-gallery-test.*"));
//			Assert.assertTrue(Integer.parseInt(img.getCssValue("z-index")) > 5);
//		} catch (NoSuchElementException e){
//			Assert.assertTrue(false);
//		}
//	}

	// No6の作業中にて削除
//	@Test
//	public void clickImageTest() throws Exception {
//		driver.get(URL);
//		try {
//			driver.findElements(By.className("tg-resizecrop")).get(0).click();
////			Thread.sleep(10000);
//			Assert.assertTrue(driver.findElements(By.className("overlay")).size() > 0);
//			driver.findElement(By.id("img")).click();
//			Assert.assertTrue(driver.findElements(By.className("overlay")).size() == 0);
//		} catch (NoSuchElementException e){
//			Assert.assertTrue(false);
//		}
//	}
	
	// No6の作業中にて削除
//	@Test
//	public void removeImageTest() throws Exception {
//		driver.get(URL);
//		try {
//			driver.findElements(By.className("tg-resizecrop")).get(0).click();
//			Assert.assertEquals("1", driver.findElement(By.id("img")).getCssValue("opacity"));
//			driver.findElement(By.id("img")).click();
//			Assert.assertTrue(driver.findElements(By.id("img")).size() == 0);
//		} catch (NoSuchElementException e){
//			Assert.assertTrue(false);
//		}
//		
//	}

//	@Test
//	public void clickImageTest() throws Exception {
//		driver.get(URL);
//		try {
//			driver.findElements(By.className("tg-resizecrop")).get(0).click();
////			Thread.sleep(10000);
//			Assert.assertTrue(driver.findElements(By.className("overlay")).size() > 0);
//			driver.findElements(By.className("overlayimg")).get(0).click();
//			Assert.assertTrue(driver.findElements(By.className("overlayimg")).size() == 0);
//		} catch (NoSuchElementException e){
//			Assert.assertTrue(false);
//		}
//	}
	
//	@Test
//	public void removeImageTest() throws Exception {
//		driver.get(URL);
//		try {
//			driver.findElements(By.className("tg-resizecrop")).get(0).click();
//			Assert.assertEquals("1", driver.findElements(By.className("overlayimg")).get(0).getCssValue("opacity"));
//			driver.findElements(By.className("overlayimg")).get(0).click();
//			Assert.assertTrue(driver.findElements(By.id("overlayimg")).size() == 0);
//		} catch (NoSuchElementException e){
//			Assert.assertTrue(false);
//		}
//	}
//
//	@Test
//	public void existImageTest() throws Exception {
//		driver.get(URL);
//		try {
//			Assert.assertTrue(driver.findElements(By.className("tg-thumb")).size() > 0);
//		} catch (NoSuchElementException e) {
//		
//		}
//	}

	
	
	
	
	
// following is ignoring	
	
//	@Test
//	public void targetImageSrcTest() throws Exception {
//		driver.get(URL);
//		WebElement imgParent = driver.findElements(By.className("tg-thumb")).get(0)
//				.findElement(By.tagName("img"));
//		imgParent.click();
//		Thread.sleep(500);
//		try {
//			String src = driver.findElement(By.id("img")).getAttribute("src");
//			
//			// regression test
//			// src doesn't contain "null"
//			Assert.assertTrue(!src.contains("null"));
//		} catch (NoSuchElementException e) {
//			
//		}
//	}
	
//	@Test
//	public void changeWindowSizeTest() throws Exception {
//		driver.get(URL);
//		driver.manage().window().setSize(new Dimension(50, 50));
//		driver.findElements(By.className("tg-thumb")).get(0)
//			.findElement(By.tagName("img")).click();
//		driver.manage().window().setSize(new Dimension(500, 500));
//		try {
//			driver.findElement(By.xpath("/html/body/div[2]")).click();;
//			Assert.assertTrue(true);
//		} catch (NoSuchElementException e) {
//			Assert.assertTrue(false);
//		}
//	}
	
//	@Test
//	public void clickOverImageTest() throws Exception {
//		driver.manage().window().setSize(new Dimension(50, 50));
//		driver.get(URL);
//		driver.findElements(By.className("tg-thumb")).get(0)
//			.findElement(By.tagName("img")).click();
//		try {
//			driver.findElement(By.className("overlayimg")).click();
//			Assert.assertTrue(true);
//		} catch (NoSuchElementException e) {
//			Assert.assertTrue(false);
//		}
//	}
	
//	@Test
//	public void clickImageTest() throws Exception {
//		driver.manage().window().setSize(new Dimension(1000, 1000));
//		driver.get(URL);
//		driver.findElements(By.className("tg-thumb")).get(0)
//			.findElement(By.tagName("img")).click();
//		try {
//			driver.findElement(By.className("overlayimg")).click();
//			Assert.assertTrue(true);
//		} catch (NoSuchElementException e) {
//			Assert.assertTrue(false);
//		}
//	}
//	
//	@Test
//	public void largeImageTest() throws Exception {
//		driver.manage().window().setSize(new Dimension(300, 1000));
//		driver.get(URL);
//		driver.navigate().refresh();
//		driver.findElements(By.className("tg-thumb")).get(1)
//			.findElement(By.tagName("img")).click();
//		driver.manage().window().setSize(new Dimension(150, 1000));
//		Thread.sleep(1000);
//		driver.manage().window().setSize(new Dimension(1000, 1000));
//		Thread.sleep(1000);
//		driver.manage().window().setSize(new Dimension(400, 1000));
//		Thread.sleep(1000);
//		driver.manage().window().setSize(new Dimension(350, 1000));
//		Thread.sleep(1000);
//		driver.manage().window().setSize(new Dimension(300, 1000));
//		Thread.sleep(1000);
//		driver.manage().window().setSize(new Dimension(250, 1000));
//		Thread.sleep(1000);
//		try {
//			driver.findElement(By.className("overlayimg")).click();
//			Thread.sleep(1000);
//			Assert.assertTrue(true);
//		} catch (NoSuchElementException e) {
//			Assert.assertTrue(false);
//		}
//	}
}
