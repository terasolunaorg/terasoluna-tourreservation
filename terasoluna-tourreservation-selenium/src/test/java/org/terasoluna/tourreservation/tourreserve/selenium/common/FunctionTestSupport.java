package org.terasoluna.tourreservation.tourreserve.selenium.common;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class FunctionTestSupport extends ApplicationObjectSupport {

    /**
     * Starts a WebDriver using specified Locale information<br>
     * <p>
     * Only FireFox and Chrome are supported<br>
     * If "en" is specified in arguments, english locale is used<br>
     * If "" is specified, then WebDriver is started without any specific locale
     * </p>
     * @param localeStr
     * @return WebDriver web driver
     */
    protected WebDriver createLocaleSpecifiedDriver(String localeStr) {

        for (String activeProfile : getApplicationContext().getEnvironment()
                .getActiveProfiles()) {
            if ("chrome".equals(activeProfile)) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--lang=" + localeStr);
                return new ChromeDriver(options);
            } else if ("firefox".equals(activeProfile)) {
                break;
            } else if ("ie".equals(activeProfile)) {
                throw new UnsupportedOperationException("Cannot use Internet explorer if specifying locale");
            } else if ("phantomJs".equals(activeProfile)) {
                throw new UnsupportedOperationException("Cannot use PhantomJS if specifying locale");
            }
        }

        // firefox is default browser
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("intl.accept_languages", localeStr);
        return new FirefoxDriver(profile);
    }

}
