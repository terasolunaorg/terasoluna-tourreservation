package org.terasoluna.tourreservation.tourreserve.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Due to a bug in geckodriver 0.14.0, wait processing does not start normally,
 * so implement WebDriverEventListener
 */
public class WaitWebDriverEventListener implements WebDriverEventListener {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Wait<WebDriver> wait = null;

    @Value("${selenium.webDriverWait}")
    protected long webDriverWait;

    @Value("${selenium.webDriverSleepWait}")
    protected long webDriverSleepWait;

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        waitPageLoading(driver);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver,
            CharSequence[] keysToSend) {
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver,
            CharSequence[] keysToSend) {
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        waitPageLoading(driver);
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
    }

    /**
     * wait until loading of the page starts, then wait until the reading is completed.
     * @param driver
     */
    private void waitPageLoading(WebDriver driver) {
        try {
            wait = new WebDriverWait(driver, webDriverWait, webDriverSleepWait);
            wait.until(
                    (ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
                            .executeScript("return document.readyState").equals(
                                    "loading"));
            wait.until(
                    (ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
                            .executeScript("return document.readyState").equals(
                                    "complete"));
        } catch (TimeoutException e) {
            logger.debug("loading is not done");
        }
    }
}
