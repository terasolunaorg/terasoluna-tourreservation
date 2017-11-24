/*
 * Copyright (C) 2013-2017 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.terasoluna.tourreservation.tourreserve.common;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class FunctionTestSupport extends ApplicationObjectSupport {

    @Inject
    protected MessageSource messageSource;

    @Value("${selenium.applicationContextUrl}")
    protected String applicationContextUrl;

    @Value("${selenium.locale:en}")
    protected Locale locale;

    /**
     * Starts a WebDriver<br>
     * </p>
     * @return WebDriver web driver
     */
    protected WebDriver createWebDriver() {
        WebDriver driver = null;
        for (String activeProfile : getApplicationContext().getEnvironment()
                .getActiveProfiles()) {
            if ("chrome".equals(activeProfile)) {
                driver = new ChromeDriver();
                break;
            } else if ("firefox".equals(activeProfile)) {
                break;
            } else if ("ie".equals(activeProfile)) {
                driver = new InternetExplorerDriver();
                break;
            }
        }

        if (driver == null) {
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("brouser.startup.homepage_override.mstone",
                    "ignore");
            profile.setPreference("network.proxy.type", 0);
            driver = new FirefoxDriver(profile);
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(applicationContextUrl + "?locale=" + locale.getLanguage());

        return driver;
    }

    /**
     * Get localized message from message source.
     * @param code message code
     * @return localized message
     */
    protected String getMessage(String code) {
        return messageSource.getMessage(code, null, locale);
    }

    /**
     * Assert table contents.
     * @param table WebElement of target table
     * @param expectedContents expected values of table content
     */
    protected void assertTableContents(WebElement table, int rowOffset,
            int cellIndex, ValueEditor valueEditor,
            String... expectedContents) {
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        assertThat(tableRows.size(), is(expectedContents.length + rowOffset));
        for (int i = rowOffset; i < (tableRows.size() - rowOffset); i++) {
            WebElement row = tableRows.get(i);
            WebElement contentCell = row.findElements(By.tagName("td")).get(
                    cellIndex);
            String text = contentCell.getText();
            if (valueEditor != null) {
                text = valueEditor.edit(text);
            }
            assertThat(text, is(expectedContents[i - rowOffset]));
        }
    }

    protected interface ValueEditor {
        String edit(String text);
    }

}
