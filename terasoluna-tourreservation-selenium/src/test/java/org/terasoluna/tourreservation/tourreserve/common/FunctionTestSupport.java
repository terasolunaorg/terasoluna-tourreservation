/*
 * Copyright(c) 2013 NTT DATA Corporation.
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ApplicationObjectSupport;

public abstract class FunctionTestSupport extends ApplicationObjectSupport {

    @Inject
    protected WebDriver driver;

    @Value("${selenium.applicationContextUrl}")
    protected String applicationContextUrl;

    @Value("${selenium.locale:en}")
    protected Locale locale;

    @Inject
    protected MessageSource messageSource;

    @Before
    public void setUp() {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(applicationContextUrl + "?locale=" + locale.getLanguage());
    }

    @After
    public void tearDown() {
        driver.quit();
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
