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

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.tourreservation.tourreserve.common.FunctionTestSupport;
import org.terasoluna.tourreservation.tourreserve.common.constants.MessageKeys;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:seleniumContext.xml" })
public class UnauthorizedAccessTest extends FunctionTestSupport {
    @Inject
    MessageSource messageSource;

    WebDriver driver;

    @Value("${selenium.baseUrl}")
    String baseUrl;

    public UnauthorizedAccessTest() {
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
    public void testUnloginUnauthorizedAccess() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_LOGINBTNMESSAGE) + "']"))
                .click();

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000002");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_LOGIN) + "']"))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_SEARCHBTNMESSAGE) + "']"))
                .click();

        DateTime dt = new DateTime();
        DateTime dtPlus = dt.plusDays(8);
        
        new Select(driver.findElement(By.id("depYear"))).selectByValue(Integer
                .toString(dtPlus.getYear()));
        new Select(driver.findElement(By.id("depMonth"))).selectByValue(Integer
                .toString(dtPlus.getMonthOfYear()));
        new Select(driver.findElement(By.id("depDay"))).selectByValue(Integer
                .toString(dtPlus.getDayOfMonth()));
        new Select(driver.findElement(By.id("depCode"))).selectByValue("01");
        new Select(driver.findElement(By.id("arrCode"))).selectByValue("01");

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_SEARCH) + "']"))
                .click();

        driver.findElement(By.linkText("2")).click();

        driver.findElement(
                By.linkText(driver.findElement(By.xpath("//td[2]")).getText()))
                .click();

        driver.findElement(By.id("remarks")).sendKeys("test");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_CONFIRM) + "']"))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_SEARCHTOUR_CONFIRMEDMESSAGE)
                        + "']")).click();

        driver.findElement(By.linkText(getMessage(MessageKeys.TITLE_COMMON)))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_REFERBTNMESSAGE) + "']"))
                .click();

        String reservNum = driver.findElement(By.cssSelector("td")).getText();
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_LOGOUT) + "']"))
                .click();

        driver.get(baseUrl
                + "/terasoluna-tourreservation-web/managereservation/detail/"
                + reservNum);

        assertEquals(getMessage(MessageKeys.LABEL_TR_COMMON_NOTLOGINMESSAGE), driver
                .findElement(By.cssSelector("p.box")).getText());
    }

    //@Test
    public void testLoginUnauthorizedAccess() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_LOGINBTNMESSAGE) + "']"))
                .click();

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000002");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_LOGIN) + "']"))
                .click();

        driver.findElement(By.linkText(getMessage(MessageKeys.TITLE_COMMON)))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_REFERBTNMESSAGE) + "']"))
                .click();

        String reservNum = driver.findElement(By.cssSelector("td")).getText()
                .replaceAll("[^0-9]", "");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_LOGOUT) + "']"))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_LOGINBTNMESSAGE) + "']"))
                .click();

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_LOGIN) + "']"))
                .click();

        driver.get(baseUrl
                + "/terasoluna-tourreservation-web/managereservation/detail/"
                + reservNum);

        assertEquals(getMessage(MessageKeys.E_TR_FW_0004),
                driver.findElement(By.cssSelector("li")).getText());

        driver.findElement(By.linkText(getMessage(MessageKeys.TITLE_COMMON)))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_REFERBTNMESSAGE) + "']"))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_LOGOUT) + "']"))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_LOGINBTNMESSAGE) + "']"))
                .click();

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000002");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_LOGIN) + "']"))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_REFERBTNMESSAGE) + "']"))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MANAGERESERVATION_CANCELRESERVATIONBTNMESSAGE)
                        + "']")).click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MANAGERESERVATION_CANCELRESERVATIONBTNMESSAGE)
                        + "']")).click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
