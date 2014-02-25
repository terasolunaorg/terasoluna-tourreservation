/*
 * Copyright (C) 2013 terasoluna.org
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

import static org.junit.Assert.assertEquals;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.tourreservation.app.common.constants.MessageId;
import org.terasoluna.tourreservation.tourreserve.selenium.common.FunctionTestSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:seleniumContext.xml" })
public class LogInLogOutTest extends FunctionTestSupport {
    @Inject
    MessageSource messageSource;

    WebDriver driver;

    @Value("${selenium.baseUrl}")
    String baseUrl;

    public LogInLogOutTest() {
    }

    @Before
    public void setUp() {
        driver = createLocaleSpecifiedDriver(Locale.getDefault().toLanguageTag());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

    @Test
    public void testLoginLogoff() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");
        assertEquals(getMessage(MessageId.LABEL_TR_COMMON_NOTLOGINMESSAGE) + " "
                + getMessage(MessageId.LABEL_TR_MENU_MENUMESSAGE),
                driver.findElement(By.cssSelector("p.box")).getText());

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MENU_LOGINBTNMESSAGE) + "']"))
                .click();

        assertEquals(getMessage(MessageId.LABEL_TR_COMMON_NOTLOGINMESSAGE), driver
                .findElement(By.cssSelector("p.box")).getText());
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_COMMON_LOGIN) + "']"))
                .click();

        assertEquals(getMessage(MessageId.LABEL_TR_MENU_MENUMESSAGE),
                driver.findElement(By.cssSelector("p.box")).getText());
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MENU_SEARCHBTNMESSAGE) + "']"))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_COMMON_LOGOUT) + "']"))
                .click();

        assertEquals(getMessage(MessageId.LABEL_TR_COMMON_NOTLOGINMESSAGE) + " "
                + getMessage(MessageId.LABEL_TR_MENU_MENUMESSAGE),
                driver.findElement(By.cssSelector("p.box")).getText());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
