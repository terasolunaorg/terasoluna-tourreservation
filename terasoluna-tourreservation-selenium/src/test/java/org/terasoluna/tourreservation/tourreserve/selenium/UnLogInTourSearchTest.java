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

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.tourreservation.tourreserve.common.FunctionTestSupport;
import org.terasoluna.tourreservation.tourreserve.common.constants.MessageKeys;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/seleniumContext.xml" })
public class UnLogInTourSearchTest extends FunctionTestSupport {

    WebDriver driver;

    public UnLogInTourSearchTest() {
    }

    @Before
    public void setUp() {
        driver = createWebDriver();
    }

    @Test
    public void testUnLogInTourSearch() {

        assertEquals(getMessage(MessageKeys.LABEL_TR_COMMON_NOTLOGINMESSAGE)
                + " " + getMessage(MessageKeys.LABEL_TR_MENU_MENUMESSAGE),
                driver.findElement(By.id("messagesArea")).getText());

        // go to search tour screen
        driver.findElement(By.id("searchTourBtn")).click();

        assertEquals(getMessage(MessageKeys.LABEL_TR_COMMON_NOTLOGINMESSAGE),
                driver.findElement(By.id("messagesArea")).getText());

        // input search criteria
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

        // search tour
        driver.findElement(By.id("searchBtn")).click();

        // paging
        driver.findElement(By.linkText("2")).click();

        WebElement toursTable = driver.findElement(By.id("toursTable"));

        String basePrice = toursTable.findElement(By.xpath(".//tr[2]/td[7]"))
                .getText().replaceAll("[^0-9]", "");

        // show top tour in table
        toursTable.findElements(By.tagName("a")).get(0).click();

        assertEquals(getMessage(MessageKeys.LABEL_TR_COMMON_NOTLOGINMESSAGE),
                driver.findElement(By.id("messagesArea")).getText());

        assertEquals(getMessage(
                MessageKeys.LABEL_TR_SEARCHTOUR_TITLEDETAILSCREENMESSAGE),
                driver.findElement(By.id("screenName")).getText());

        WebElement priceTable = driver.findElement(By.id("priceTable"));
        assertEquals(basePrice, priceTable.findElement(By.xpath(
                ".//tr[2]/td[2]")).getText().replaceAll("[^0-9]", ""));

        // go to login screen
        driver.findElement(By.id("loginBtn")).click();

        // input credential
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");

        // login
        driver.findElement(By.id("loginBtn")).click();

        assertEquals(getMessage(
                MessageKeys.LABEL_TR_SEARCHTOUR_TITLEDETAILSCREENMESSAGE),
                driver.findElement(By.id("screenName")).getText());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
