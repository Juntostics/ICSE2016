package jp.mzw.revajaxmutator.test.imgslider;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.owasp.webscarab.model.StoreException;

import jp.mzw.revajaxmutator.test.WebAppTestBase;

public class ImgSliderTest extends WebAppTestBase {
	
    @BeforeClass
    public static void beforeTestClass() throws java.io.IOException, StoreException, InterruptedException {
    	WebAppTestBase.beforeTestClass("imgslider.properties");
    }
    
    @AfterClass
    public static void afterTestClass() {
    	WebAppTestBase.afterTestClass();
    }
    
    @Test
    public void showICSE2016LogoTest() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.className("bx-next")));
    	WebElement next_btn = driver.findElement(By.className("bx-next"));
    	
        for(int year = 1999; year <= 2016; year++) {
        	next_btn.click();
        	
        	String hidden = driver.findElement(By.id("2016")).getAttribute("aria-hidden");
        	if("false".equals(hidden)) {
        		return;
        	}
        	
        	Thread.sleep(500); // animation
        }
        Assert.fail("Cannot show ICSE 2016 logo");
    }
}
