package jp.mzw.revajaxmutator.test.gmedia_gallery;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.owasp.webscarab.model.StoreException;

import jp.mzw.revajaxmutator.FilterPlugin;
import jp.mzw.revajaxmutator.test.DockerManager;
import jp.mzw.revajaxmutator.test.RevAjaxMutatorBase;
import jp.mzw.revajaxmutator.test.WebAppTestBase;

public class GmediaGalleryTest extends WebAppTestBase {
  @BeforeClass
  public static void beforeTestClass() throws StoreException, InterruptedException, IOException {
    WebAppTestBase.beforeTestClass("gmedia-gallery.properties");
  }

  @AfterClass
  public static void afterTestClass() {
    WebAppTestBase.afterTestClass();
  }

  
  @Test
  public void displayOriginalUploader() throws InterruptedException, IOException, StoreException  {
    driver.get(URL);
    
    driver.findElement(By.id("user_login")).sendKeys("test");
    driver.findElement(By.id("user_pass")).sendKeys("testtest");;
    driver.findElement(By.id("wp-submit")).click();
//    Thread.sleep(500);
    driver.findElement(By.xpath("//*[@id=\"menu-posts\"]/a")).click();
//    Thread.sleep(500);
    driver.findElement(By.className("add-new-h2")).click();
//    Thread.sleep(500);
    driver.findElement(By.xpath("//*[@id=\"gmedia-modal\"]")).click();
    
    try {
      driver.findElement(By.id("__wp-uploader-id-2"));
      Assert.assertTrue(true);
    } catch (NoSuchElementException e) {
      Assert.assertTrue(false);
    }
  }
}
