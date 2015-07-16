package jp.mzw.revajaxmutator.test.editorial_calendar;

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

import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.util.*;

public class EditorialCalendarTest extends WebAppTestBase {
  @BeforeClass
  public static void beforeTestClass() throws StoreException, InterruptedException, IOException {
    WebAppTestBase.beforeTestClass("editorial-calendar.properties");
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
    driver.findElement(By.xpath("//*[@id=\"menu-posts\"]/a")).click();
    driver.findElement(By.xpath("/html/body/div/div[2]/ul/li[3]/div[3]/div/ul/li[5]/a")).click();
    Thread.sleep(1000);
    String beforeClassName = driver.findElement(By.id("cal")).findElements(By.className("rowcont")).get(0).getAttribute("id");
    mouseMove(250,175,1);
    mouseClick();
    mousePress();
    String afterClassName = driver.findElement(By.id("cal")).findElements(By.className("rowcont")).get(3).getAttribute("id");
    Assert.assertEquals(beforeClassName, afterClassName);
  }
  
  static void mouseMove(int moW,int moH,int moD){
      Robot r;
      try {
          r = new Robot();
      }
      catch (AWTException e) {
          e.printStackTrace();
          return;
      }
      PointerInfo pI = MouseInfo.getPointerInfo();
      Point point = pI.getLocation();

          int i = (int)point.getX();
          int h = (int)point.getY();
      // move
      while (!(i == moW && h == moH)) {
          if (i < moW){
              i++;
          }else if (i > moW) {
              i--;
          }
          if (h < moH){
              h++;
          }else if (h > moH) {
              h--;
          }
          r.mouseMove(i,h);
          r.delay(moD);
      }
  }
  
  static void mouseClick(){
      Robot r;
      try {
          r = new Robot();
      }
      catch (AWTException e) {
          e.printStackTrace();
          return;
      }
      r.mousePress(InputEvent.BUTTON1_MASK);
      r.mouseRelease(InputEvent.BUTTON1_MASK);
  }
  
  static void mousePress() throws InterruptedException{
      Robot r;
      try {
          r = new Robot();
      }
      catch (AWTException e) {
          e.printStackTrace();
          return;
      }
      r.mousePress(InputEvent.BUTTON1_MASK);
      Thread.sleep(600);
      r.mouseMove(250,100);
      Thread.sleep(600);
      r.mouseMove(250,120);
      Thread.sleep(600);
      r.mouseMove(250,100);
      Thread.sleep(600);
      r.mouseMove(250,200);
  }
  static void mouseRelease(){
      Robot r;
      try {
          r = new Robot();
      }
      catch (AWTException e) {
          e.printStackTrace();
          return;
      }
      r.mouseRelease(InputEvent.BUTTON1_MASK);
  }
}
