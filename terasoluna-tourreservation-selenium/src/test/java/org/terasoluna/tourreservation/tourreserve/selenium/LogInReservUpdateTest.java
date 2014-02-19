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
import org.terasoluna.tourreservation.app.common.constants.MessageId;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:seleniumContext.xml" })
public class LogInReservUpdateTest {
    @Inject
    MessageSource messageSource;

    @Inject
    WebDriver driver;

    @Value("${selenium.baseUrl}")
    String baseUrl;

    public LogInReservUpdateTest() {
    }

    @Before
    public void setUp() {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

    @Test
    public void testLogInTourSearchRegist() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MENU_LOGINBTNMESSAGE)
                        + "']")).click();

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_COMMON_LOGIN) + "']"))
                .click();

        assertEquals(getMessage(MessageId.LABEL_TR_MENU_MENUMESSAGE), driver
                .findElement(By.cssSelector("p.box")).getText());
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MENU_SEARCHBTNMESSAGE)
                        + "']")).click();

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

        driver.findElement(
                By.linkText(driver.findElement(By.xpath("//td[2]")).getText()))
                .click();

        assertEquals(
                getMessage(MessageId.LABEL_TR_SEARCHTOUR_TITLEDETAILSCREENMESSAGE),
                driver.findElement(By.cssSelector("h2")).getText());
        driver.findElement(By.id("remarks")).sendKeys(
                "Global TERASOLUNA Framewrok");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_COMMON_CONFIRM) + "']"))
                .click();

        assertEquals(
                getMessage(MessageId.LABEL_TR_SEARCHTOUR_TITLECONFIRMSCREENMESSAGE),
                driver.findElement(By.cssSelector("h2")).getText());
        assertEquals("Global TERASOLUNA Framewrok", driver.findElement(
                By.xpath("//table[4]/tbody/tr/td[2]")).getText());
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_SEARCHTOUR_CONFIRMEDMESSAGE)
                        + "']")).click();

        assertEquals(
                getMessage(MessageId.LABEL_TR_SEARCHTOUR_RESERVESCREENTITLEMESSAGE),
                driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testLogInReservUpdate() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MENU_LOGINBTNMESSAGE)
                        + "']")).click();

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_COMMON_LOGIN) + "']"))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MENU_REFERBTNMESSAGE)
                        + "']")).click();

        assertEquals(
                getMessage(MessageId.LABEL_TR_MANAGERESERVATION_MANAGERESERVATIONMESSAGE),
                driver.findElement(By.cssSelector("span")).getText());
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MANAGERESERVATION_CHANGERESERVATIONBTNMESSAGE)
                        + "']")).click();

        assertEquals(
                getMessage(MessageId.LABEL_TR_MANAGERESERVATION_MANAGERESERVATIONEDITSCREENTITLE),
                driver.findElement(By.cssSelector("span")).getText());
        new Select(driver.findElement(By.id("adultCount")))
                .selectByVisibleText("2");
        new Select(driver.findElement(By.id("childCount")))
                .selectByVisibleText("2");

        // update
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MANAGERESERVATION_CONFIRMEDITBTNMESSAGE)
                        + "']")).click();

        // back
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MANAGERESERVATION_CHANGERESERVATIONSTABTNMESSAGE)
                        + "']")).click();

        assertEquals(new Select(driver.findElement(By.id("adultCount")))
                .getFirstSelectedOption().getText(), "2");
        assertEquals(new Select(driver.findElement(By.id("childCount")))
                .getFirstSelectedOption().getText(), "2");

        // update
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MANAGERESERVATION_CONFIRMEDITBTNMESSAGE)
                        + "']")).click();

        assertEquals(
                getMessage(MessageId.LABEL_TR_MANAGERESERVATION_MANAGERESERVATIONCONFIRMSCREENTITLEMESSAGE),
                driver.findElement(By.cssSelector("span")).getText());
        // asseert Price
        String totalPrice = driver.findElement(
                By.xpath("//table[2]/tbody/tr[4]/td[2]")).getText().replaceAll(
                "[^0-9]", "");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MANAGERESERVATION_CHANGERESERVATIONFINBTNMESSAGE)
                        + "']")).click();

        assertEquals(
                getMessage(MessageId.LABEL_TR_MANAGERESERVATION_MANAGERESERVATIONUPDATEDSCREENTITLEMESSAGE),
                driver.findElement(By.cssSelector("span")).getText());
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MANAGERESERVATION_RETURNTOLISTSCREENBTNMESSAGE)
                        + "']")).click();

        assertEquals(getMessage(MessageId.LABEL_TR_COMMON_ADULT)
                + getMessage(MessageId.LABEL_TR_COMMON_PERSONCOUNTPATTERN)
                        .replace("##", " 2")
                + "\n"
                + getMessage(MessageId.LABEL_TR_COMMON_CHILD)
                + getMessage(MessageId.LABEL_TR_COMMON_PERSONCOUNTPATTERN)
                        .replace("##", " 2"), driver.findElement(
                By.xpath("//td[7]")).getText());
        assertEquals(totalPrice, driver.findElement(By.xpath("//td[9]"))
                .getText().replaceAll("[^0-9]", ""));

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MANAGERESERVATION_SHOWDETAILSBTNMESSAGE)
                        + "']")).click();

        assertEquals(
                getMessage(MessageId.LABEL_TR_MANAGERESERVATION_MANAGERESERVATIONSHOWSCREENTITLEMESSAGE),
                driver.findElement(By.cssSelector("span")).getText());

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MANAGERESERVATION_CANCELRESERVATIONBTNMESSAGE)
                        + "']")).click();

        assertEquals(
                getMessage(MessageId.LABEL_TR_MANAGERESERVATION_BEFORECANCELSCREENTITLEMESSAGE),
                driver.findElement(By.cssSelector("h2")).getText());
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageId.LABEL_TR_MANAGERESERVATION_CANCELRESERVATIONBTNMESSAGE)
                        + "']")).click();

        assertEquals(
                getMessage(MessageId.LABEL_TR_MANAGERESERVATION_AFTERCANCELSCREENTITLEMESSAGE),
                driver.findElement(By.cssSelector("h2")).getText());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
