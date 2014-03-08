package org.terasoluna.tourreservation.tourreserve.common;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class FunctionTestSupport extends ApplicationObjectSupport {

    @Inject
    MessageSource messageSource;

    /**
     * Starts a WebDriver using specified Locale information<br>
     * <p>
     * Only FireFox and Chrome are supported<br>
     * If "en" is specified in arguments, English locale is used<br>
     * If "" is specified, then WebDriver is started without any specific locale
     * </p>
     * @param localeStr
     * @return WebDriver web driver
     */
    protected WebDriver createLocaleSpecifiedDriver(String localeStr) {
        WebDriver driver = null;
        for (String activeProfile : getApplicationContext().getEnvironment()
                .getActiveProfiles()) {
            if ("chrome".equals(activeProfile)) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--lang=" + localeStr);
                driver = new ChromeDriver(options);
                break;
            } else if ("firefox".equals(activeProfile)) {
                break;
            } else if ("ie".equals(activeProfile)) {
                logger.warn("Cannot use Internet explorer if specifying locale. Startup the browser without any specific locale.");
                driver = new InternetExplorerDriver();
                break;
            }
        }

        if (driver == null) {
            // firefox is default browser
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("intl.accept_languages", localeStr);
            driver = new FirefoxDriver(profile);
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        return driver;
    }

    /**
     * Starts a WebDriver using default Locale<br>
     * <p>
     * Only FireFox and Chrome are supported<br>
     * </p>
     * @return WebDriver web driver
     */
    protected WebDriver createDefaultLocaleDriver() {
        String localeStr = Locale.getDefault().getLanguage();
        return createLocaleSpecifiedDriver(localeStr);
    }

    /**
     * Get localized message from message source.
     * @param code message code
     * @return localized message
     */
    protected String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

}
