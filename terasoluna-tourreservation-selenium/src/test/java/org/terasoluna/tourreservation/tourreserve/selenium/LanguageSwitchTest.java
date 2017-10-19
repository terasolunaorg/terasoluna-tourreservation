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
package org.terasoluna.tourreservation.tourreserve.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.tourreservation.tourreserve.common.FunctionTestSupport;
import org.terasoluna.tourreservation.tourreserve.common.constants.MessageKeys;

import java.util.Locale;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/seleniumContext.xml" })
public class LanguageSwitchTest extends FunctionTestSupport {

    WebDriver driver;

    public LanguageSwitchTest() {
    }

    @Before
    public void setUp() {
        driver = createWebDriver();
    }

    @Test
    public void testSwitchLanguage() {

        boolean isEn = locale.getLanguage().equalsIgnoreCase("en");
        String jaTitle = messageSource.getMessage(MessageKeys.TITLE_COMMON,
                null, Locale.JAPANESE);
        String enTitle = messageSource.getMessage(MessageKeys.TITLE_COMMON,
                null, Locale.ENGLISH);
        String jaSwitchLinkName = "日本語";
        String jaSwitchLinkId = "switchJa";
        String enSwitchLinkName = "English";
        String enSwitchLinkId = "switchEn";

        // switch language (default : en -> ja)
        {
            String beforeTitle = driver.getTitle();

            // switch
            driver.findElement(By.id(isEn ? jaSwitchLinkId : enSwitchLinkId))
                    .click();

            String afterTitle = driver.getTitle();
            String switchLinkName = driver.findElement(By.id(isEn
                    ? enSwitchLinkId : jaSwitchLinkId)).getText();

            assertThat(beforeTitle, is(isEn ? enTitle : jaTitle));
            assertThat(afterTitle, is(isEn ? jaTitle : enTitle));
            assertThat(switchLinkName, is(isEn ? enSwitchLinkName
                    : jaSwitchLinkName));
        }

        // switch language (default : ja -> en)
        {
            // switch
            driver.findElement(By.id(isEn ? enSwitchLinkId : jaSwitchLinkId))
                    .click();

            String afterTitle = driver.getTitle();
            String switchLinkName = driver.findElement(By.id(isEn
                    ? jaSwitchLinkId : enSwitchLinkId)).getText();

            assertThat(afterTitle, is(isEn ? enTitle : jaTitle));
            assertThat(switchLinkName, is(isEn ? jaSwitchLinkName
                    : enSwitchLinkName));
        }

    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
