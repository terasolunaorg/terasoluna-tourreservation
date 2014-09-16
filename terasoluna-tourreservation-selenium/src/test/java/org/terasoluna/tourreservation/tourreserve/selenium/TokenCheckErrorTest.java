/*
 * Copyright (C) 2013-2014 terasoluna.org
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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.tourreservation.tourreserve.common.FunctionTestSupport;
import org.terasoluna.tourreservation.tourreserve.common.constants.MessageKeys;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:seleniumContext.xml" })
public class TokenCheckErrorTest extends FunctionTestSupport {

    WebDriver driver;

    @Value("${selenium.baseUrl}")
    String baseUrl;

    public TokenCheckErrorTest() {
    }

    @Before
    public void setUp() {
        driver = createDefaultLocaleDriver();
    }

    @Test
    public void testCustomerRegistToken() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_CUSTOMERREGISTERBTNMESSAGE) + "']"))
                .click();

        driver.findElement(By.name("customerKana")).sendKeys("テラ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        new Select(driver.findElement(By.id("customerBirthYear")))
                .selectByValue("2000");
        new Select(driver.findElement(By.id("customerBirthDay")))
                .selectByValue("1");
        new Select(driver.findElement(By.id("customerBirthMonth")))
                .selectByValue("12");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerMail")).sendKeys(
                "terasoluna@nttd.co.jp");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_CONFIRM) + "']"))
                .click();

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("document.getElementsByName('_TRANSACTION_TOKEN')[0].setAttribute('type', 'text');");
        driver.findElement(By.xpath("//input[@name='_TRANSACTION_TOKEN']"))
                .clear();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_REGISTER) + "']"))
                .click();

        assertEquals(getMessage(MessageKeys.E_TR_FW_0001),
                driver.findElement(By.cssSelector("p")).getText());
    }

    @Test
    public void testCustomerRegistCSRFToken() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_CUSTOMERREGISTERBTNMESSAGE) + "']"))
                .click();

        driver.findElement(By.name("customerKana")).sendKeys("テラ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        new Select(driver.findElement(By.id("customerBirthYear")))
                .selectByValue("2000");
        new Select(driver.findElement(By.id("customerBirthDay")))
                .selectByValue("1");
        new Select(driver.findElement(By.id("customerBirthMonth")))
                .selectByValue("12");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerMail")).sendKeys(
                "terasoluna@nttd.co.jp");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_CONFIRM) + "']"))
                .click();

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("document.getElementsByName('_csrf')[0].setAttribute('type', 'text');");
        driver.findElement(By.xpath("//input[@name='_csrf']"))
                .clear();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_REGISTER) + "']"))
                .click();
        assertEquals(getMessage(MessageKeys.E_TR_FW_0002),
                driver.findElement(By.cssSelector("p")).getText());

    }

    
    @Test
    public void testTourSearchRegistToken() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");

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

        driver.findElement(By.id("remarks")).sendKeys(
                "Global TERASOLUNA Framewrok");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_CONFIRM) + "']"))
                .click();

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("document.getElementsByName('_TRANSACTION_TOKEN')[2].setAttribute('type', 'text');");
        driver.findElement(By.id("reserveTourForm")).findElement(By.name("_TRANSACTION_TOKEN"))
                .clear();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_SEARCHTOUR_CONFIRMEDMESSAGE)
                        + "']")).click();

        assertEquals(getMessage(MessageKeys.E_TR_FW_0001),
                driver.findElement(By.cssSelector("p")).getText());
    }

    @Test
    public void testReserveUpdateToken() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");

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

        driver.findElement(By.id("remarks")).sendKeys(
                "Global TERASOLUNA Framewrok");
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

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MANAGERESERVATION_CHANGERESERVATIONBTNMESSAGE)
                        + "']")).click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MANAGERESERVATION_CONFIRMEDITBTNMESSAGE)
                        + "']")).click();

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("document.getElementsByName('_TRANSACTION_TOKEN')[0].setAttribute('type', 'text');");
        driver.findElement(By.id("manageReservationForm"))
                .findElement(By.name("_TRANSACTION_TOKEN")).clear();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MANAGERESERVATION_CHANGERESERVATIONFINBTNMESSAGE)
                        + "']")).click();

        assertEquals(getMessage(MessageKeys.E_TR_FW_0001),
                driver.findElement(By.cssSelector("p")).getText());

        driver.findElement(By.linkText(getMessage(MessageKeys.TITLE_COMMON)))
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

    @Test
    public void testReserveCancelToken() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");

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

        driver.findElement(By.id("remarks")).sendKeys(
                "Global TERASOLUNA Framewrok");
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

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MANAGERESERVATION_CANCELRESERVATIONBTNMESSAGE)
                        + "']")).click();

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("document.getElementsByName('_TRANSACTION_TOKEN')[1].setAttribute('type', 'text');");
        driver.findElement(By.id("reservationCanelForm"))
                .findElement(By.name("_TRANSACTION_TOKEN")).clear();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MANAGERESERVATION_CANCELRESERVATIONBTNMESSAGE)
                        + "']")).click();

        assertEquals(getMessage(MessageKeys.E_TR_FW_0001),
                driver.findElement(By.cssSelector("p")).getText());

        driver.findElement(By.linkText(getMessage(MessageKeys.TITLE_COMMON)))
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
