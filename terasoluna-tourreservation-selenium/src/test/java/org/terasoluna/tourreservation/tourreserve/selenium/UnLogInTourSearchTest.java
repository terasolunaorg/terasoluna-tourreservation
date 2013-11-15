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

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.tourreservation.app.common.constants.MessageId;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:seleniumContext.xml" })
public class UnLogInTourSearchTest {
    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected WebDriver driver;

    @Value("${selenium.baseUrl}")
    protected String baseUrl;

    public UnLogInTourSearchTest() {
    }

    @Before
    public void setUp() {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

    @Test
    public void testUnLogInTourSearch() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");

        assertEquals(getMessage(MessageId.LABEL_TR_COMMON_NOTLOGINMESSAGE) + " "
                + getMessage(MessageId.LABEL_TR_MENU_MENUMESSAGE),
                driver.findElement(By.cssSelector("p.box")).getText());
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MENU_SEARCHBTNMESSAGE) + "']"))
                .click();

        assertEquals(getMessage(MessageId.LABEL_TR_COMMON_NOTLOGINMESSAGE), driver
                .findElement(By.cssSelector("p.box")).getText());

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
                        + getMessage(MessageId.LABEL_TR_COMMON_SEARCH) + "']"))
                .click();

        driver.findElement(By.linkText("2")).click();

        String basePrice = driver.findElement(By.xpath("//td[7]")).getText()
                .replaceAll("[^0-9]", "");

        driver.findElement(
                By.linkText(driver.findElement(By.xpath("//td[2]")).getText()))
                .click();

        assertEquals(getMessage(MessageId.LABEL_TR_COMMON_NOTLOGINMESSAGE), driver
                .findElement(By.cssSelector("p.box")).getText());
        assertEquals(getMessage(MessageId.LABEL_TR_SEARCHTOUR_TITLEDETAILSCREENMESSAGE),
                driver.findElement(By.cssSelector("h2")).getText());

        assertEquals(basePrice,
                driver.findElement(By.xpath("//table[2]/tbody/tr[2]/td[2]"))
                        .getText().replaceAll("[^0-9]", ""));

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MENU_LOGINBTNMESSAGE) + "']"))
                .click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_COMMON_LOGIN) + "']"))
                .click();

        assertEquals(getMessage(MessageId.LABEL_TR_SEARCHTOUR_TITLEDETAILSCREENMESSAGE),
                driver.findElement(By.cssSelector("h2")).getText());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
