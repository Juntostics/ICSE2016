package jp.mzw.revajaxmutator.test.imgslider;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import jp.mzw.revajaxmutator.test.WebAppTestBase;

public class ImgSliderTest extends WebAppTestBase {
	
    @BeforeClass
    public static void beforeTestClass() throws java.io.IOException {
    	WebAppTestBase.beforeTestClass("imgslider.properties");
    }
    
    @Test
    public void showICSE2016LogoTest() throws InterruptedException {
    	driver.get(URL);
    	
        wait.until(ExpectedConditions.elementToBeClickable(By.className("bx-next")));
    	WebElement next_btn = driver.findElement(By.className("bx-next"));
    	
        for(int year = 1999; year <= 2016; year++) {
        	next_btn.click();
        	
        	String hidden = driver.findElement(By.id("2016")).getAttribute("aria-hidden");
        	if("false".equals(hidden)) {
        		return;
        	}
        	
        	Thread.sleep(1000); // animation
        }
        Assert.fail("Cannot show ICSE 2016 logo");
    }
    
//    private static void waitSlide(final String id) {
//        wait.until(new ExpectedCondition<Boolean>() {
//        	public Boolean apply(WebDriver driver) {
//        		WebElement li = driver.findElement(By.id(id));
//        		String attr = li.getAttribute("aria-hidden");
//        		if("false".equals(attr)) { // show
//        			return true;
//        		}
//        		return false;
//        	}
//        });
//    }
    
}
