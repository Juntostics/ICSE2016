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

	@Test
	public void hideDiscriptionTest() throws InterruptedException{
		driver.get(URL);
		waitUntilShowWidgets();
		try{
			driver.findElement(By.id("quizzy_quiz_opt" + FIRST_ITEM_ID)).click();
			waitUntilSlideAnimationEnd();
			String secondItemDiscription = driver.findElement(By.id("quizzy_quiz_desc" + SECOND_ITEM_ID)).getText().trim();
			
			if("".equals(secondItemDiscription)){
				Assert.assertTrue(true);
			}else if(SECOND_ITEM_TEXT.equals(secondItemDiscription)){
				Assert.assertTrue(false);
			}else{
				Assert.assertTrue(false);
			}

		}catch(NoSuchElementException e){
			Assert.assertTrue(false);
		}
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
