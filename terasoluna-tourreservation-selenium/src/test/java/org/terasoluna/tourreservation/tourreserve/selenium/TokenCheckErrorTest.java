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
import org.openqa.selenium.JavascriptExecutor;
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
public class TokenCheckErrorTest extends FunctionTestSupport {

    WebDriver driver;

    public TokenCheckErrorTest() {
    }

    @Before
    public void setUp() {
        driver = createWebDriver();
    }

    @Test
    public void testCustomerRegistToken() {

        // go to register screen
        driver.findElement(By.id("customerRegisterBtn")).click();

        // input new customer
        driver.findElement(By.name("customerKana")).sendKeys("テラ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        new Select(driver.findElement(By.id("customerBirthYear")))
                .selectByValue("2000");
        new Select(driver.findElement(By.id("customerBirthDay"))).selectByValue(
                "1");
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

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        // falsify transaction token
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(
                "document.getElementsByName('_TRANSACTION_TOKEN')[0].setAttribute('type', 'text');");
        driver.findElement(By.name("_TRANSACTION_TOKEN")).clear();

        // register
        driver.findElement(By.id("registerBtn")).click();

        assertEquals(getMessage(MessageKeys.E_TR_FW_0001), driver.findElement(By
                .cssSelector("p")).getText());
    }

    @Test
    public void testCustomerRegistCSRFToken() {

        // go to register screen
        driver.findElement(By.id("customerRegisterBtn")).click();

        // input new customer
        driver.findElement(By.name("customerKana")).sendKeys("テラ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        new Select(driver.findElement(By.id("customerBirthYear")))
                .selectByValue("2000");
        new Select(driver.findElement(By.id("customerBirthDay"))).selectByValue(
                "1");
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

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        // falsify CSRF token
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(
                "document.getElementsByName('_csrf')[0].setAttribute('type', 'text');");
        driver.findElement(By.name("_csrf")).clear();

        // register
        driver.findElement(By.id("registerBtn")).click();

        assertEquals(getMessage(MessageKeys.E_TR_FW_0002), driver.findElement(By
                .cssSelector("p")).getText());

    }

    @Test
    public void testTourSearchRegistToken() {

        // go to login screen
        driver.findElement(By.id("loginBtn")).click();

        // input credential
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");

        // login
        driver.findElement(By.id("loginBtn")).click();

        // go to search tour screen
        driver.findElement(By.id("searchTourBtn")).click();

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

        // show top tour in table
        WebElement toursTable = driver.findElement(By.id("toursTable"));
        toursTable.findElements(By.tagName("a")).get(0).click();

        // input reservation contents
        driver.findElement(By.id("remarks")).sendKeys(
                "TERASOLUNA Server Framework for Java (5.x)");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        // falsify transaction token
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(
                "document.getElementsByName('_TRANSACTION_TOKEN')[1].setAttribute('type', 'text');");
        driver.findElement(By.id("reserveTourForm")).findElement(By.name(
                "_TRANSACTION_TOKEN")).clear();

        // reserve
        driver.findElement(By.id("reserveBtn")).click();

        assertEquals(getMessage(MessageKeys.E_TR_FW_0001), driver.findElement(By
                .cssSelector("p")).getText());
    }

    @Test
    public void testReserveUpdateToken() {

        // go to login screen
        driver.findElement(By.id("loginBtn")).click();

        // input credential
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");

        // login
        driver.findElement(By.id("loginBtn")).click();

        // go to search tour screen
        driver.findElement(By.id("searchTourBtn")).click();

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

        // show top tour in table
        WebElement toursTable = driver.findElement(By.id("toursTable"));
        toursTable.findElements(By.tagName("a")).get(0).click();

        // input reservation contents
        driver.findElement(By.id("remarks")).sendKeys(
                "TERASOLUNA Server Framework for Java (5.x)");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        // reserve
        driver.findElement(By.id("reserveBtn")).click();

        // go to top screen(back to top)
        driver.findElement(By.id("goToTopLink")).click();

        // go to reserved tours list screen
        driver.findElement(By.id("reservedToursReferBtn")).click();

        // change top reservation in table
        driver.findElement(By.id("changeBtn0")).click();

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        // falsify transaction token
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(
                "document.getElementsByName('_TRANSACTION_TOKEN')[0].setAttribute('type', 'text');");
        driver.findElement(By.id("manageReservationForm")).findElement(By.name(
                "_TRANSACTION_TOKEN")).clear();

        // change reservation
        driver.findElement(By.id("changeBtn")).click();

        assertEquals(getMessage(MessageKeys.E_TR_FW_0001), driver.findElement(By
                .cssSelector("p")).getText());

        // go to top screen(back to top)
        driver.findElement(By.id("goToTopLink")).click();

        // go to reserved tours list screen
        driver.findElement(By.id("reservedToursReferBtn")).click();

        // cancel top reservation in table
        driver.findElement(By.id("cancelBtn0")).click();

        // cancel reservation
        driver.findElement(By.id("cancelBtn")).click();

    }

    @Test
    public void testReserveCancelToken() {

        // go to login screen
        driver.findElement(By.id("loginBtn")).click();

        // input credential
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");

        // login
        driver.findElement(By.id("loginBtn")).click();

        // go to search tour screen
        driver.findElement(By.id("searchTourBtn")).click();

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

        // show top tour in table
        WebElement toursTable = driver.findElement(By.id("toursTable"));
        toursTable.findElements(By.tagName("a")).get(0).click();

        // input reservation contents
        driver.findElement(By.id("remarks")).sendKeys(
                "TERASOLUNA Server Framework for Java (5.x)");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        // reserve
        driver.findElement(By.id("reserveBtn")).click();

        // go to top screen(back to top)
        driver.findElement(By.id("goToTopLink")).click();

        // go to reserved tours list screen
        driver.findElement(By.id("reservedToursReferBtn")).click();

        // cancel top reservation in table
        driver.findElement(By.id("cancelBtn0")).click();

        // falsify transaction token
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(
                "document.getElementsByName('_TRANSACTION_TOKEN')[0].setAttribute('type', 'text');");
        driver.findElement(By.id("reservationCanelForm")).findElement(By.name(
                "_TRANSACTION_TOKEN")).clear();

        // cancel reservation
        driver.findElement(By.id("cancelBtn")).click();

        assertEquals(getMessage(MessageKeys.E_TR_FW_0001), driver.findElement(By
                .cssSelector("p")).getText());

        // go to top screen(back to top)
        driver.findElement(By.id("goToTopLink")).click();

        // go to reserved tours list screen
        driver.findElement(By.id("reservedToursReferBtn")).click();

        // cancel top reservation in table
        driver.findElement(By.id("cancelBtn0")).click();

        // cancel reservation
        driver.findElement(By.id("cancelBtn")).click();

    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
