package jp.mzw.revajaxmutator.test.quizzy;

import java.io.IOException;

import jp.mzw.revajaxmutator.test.WebAppTestBase;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.owasp.webscarab.model.StoreException;

public class QuizzyTest extends WebAppTestBase {
	private static final int FIRST_ITEM_ID = 0;
	private static final int SECOND_ITEM_ID = 1;
	private static final String SECOND_ITEM_TEXT = "Under construction";
	
	@BeforeClass
	public static void beforeTestClass() throws StoreException, InterruptedException, IOException {		
		WebAppTestBase.beforeTestClass("quizzy.properties");
	}

	 
	
	private void waitUntilShowWidgets(){
		WebDriverWait countdownWait = new WebDriverWait(driver, TIMEOUT);
		countdownWait.until(new ExpectedCondition<Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				try{
					driver.findElements(By.id("quizzy_quiz_opt"));
					return true;
				}catch(NoSuchElementException e){
					return false;
				}
			}
		});
	}
	
	private void waitUntilSlideAnimationEnd() throws InterruptedException{
		Thread.sleep(1000);
	}
}
