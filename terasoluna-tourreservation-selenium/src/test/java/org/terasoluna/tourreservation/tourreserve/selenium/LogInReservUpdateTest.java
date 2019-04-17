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
package org.terasoluna.tourreservation.tourreserve.selenium;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.tourreservation.tourreserve.common.FunctionTestSupport;
import org.terasoluna.tourreservation.tourreserve.common.constants.MessageKeys;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/seleniumContext.xml" })
public class LogInReservUpdateTest extends FunctionTestSupport {

    public LogInReservUpdateTest() {
    }

    @Test
    public void testLogInTourSearchRegist() {

        // go to login screen
        driver.findElement(By.id("loginBtn")).click();

        // input credential
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");

        // login
        driver.findElement(By.id("loginBtn")).click();

        assertEquals(getMessage(MessageKeys.LABEL_TR_MENU_MENUMESSAGE), driver
                .findElement(By.id("messagesArea")).getText());

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

        assertEquals(getMessage(
                MessageKeys.LABEL_TR_SEARCHTOUR_TITLEDETAILSCREENMESSAGE),
                driver.findElement(By.id("screenName")).getText());

        // input reservation contents
        driver.findElement(By.id("remarks")).sendKeys(
                "TERASOLUNA Server Framework for Java (5.x)");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertEquals(getMessage(
                MessageKeys.LABEL_TR_SEARCHTOUR_TITLECONFIRMSCREENMESSAGE),
                driver.findElement(By.id("screenName")).getText());

        WebElement specialNotesTable = driver.findElement(By.id(
                "specialNotesTable"));
        assertTableContents(specialNotesTable, 0, 1, null,
                "TERASOLUNA Server Framework for Java (5.x)");

        // reserve
        driver.findElement(By.id("reserveBtn")).click();

        assertEquals(getMessage(
                MessageKeys.LABEL_TR_SEARCHTOUR_RESERVESCREENTITLEMESSAGE),
                driver.findElement(By.id("screenName")).getText());
    }

    @Test
    public void testLogInReservUpdate() {

        String userName = "00000001";
        String password = "password";

        // register test data
        reserveTourForRegisterTestData(userName, password);

        driver.get(applicationContextUrl);

        // go to login screen
        driver.findElement(By.id("loginBtn")).click();

        // input credential
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(userName);

        // login
        driver.findElement(By.id("loginBtn")).click();

        // go to reserved tours list screen
        driver.findElement(By.id("reservedToursReferBtn")).click();

        assertEquals(getMessage(
                MessageKeys.LABEL_TR_MANAGERESERVATION_MANAGERESERVATIONMESSAGE),
                driver.findElement(By.id("screenName")).getText());

        // change top reservation in table
        driver.findElement(By.id("changeBtn0")).click();

        assertEquals(getMessage(
                MessageKeys.LABEL_TR_MANAGERESERVATION_MANAGERESERVATIONEDITSCREENTITLE),
                driver.findElement(By.id("screenName")).getText());

        // change person count
        new Select(driver.findElement(By.id("adultCount"))).selectByVisibleText(
                "2");
        new Select(driver.findElement(By.id("childCount"))).selectByVisibleText(
                "2");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        // back to form screen
        driver.findElement(By.id("backToFormBtn")).click();

        assertEquals(new Select(driver.findElement(By.id("adultCount")))
                .getFirstSelectedOption().getText(), "2");
        assertEquals(new Select(driver.findElement(By.id("childCount")))
                .getFirstSelectedOption().getText(), "2");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertEquals(getMessage(
                MessageKeys.LABEL_TR_MANAGERESERVATION_MANAGERESERVATIONCONFIRMSCREENTITLEMESSAGE),
                driver.findElement(By.id("screenName")).getText());

        // assert Price
        WebElement priceTable = driver.findElement(By.id("priceTable"));
        assertTableContents(priceTable, 1, 3, new ValueEditor() {
            @Override
            public String edit(String text) {
                return text.replaceAll("[^0-9]", "");
            }
        }, "54000", "27000", "81000");

        // change reservation
        driver.findElement(By.id("changeBtn")).click();

        assertEquals(getMessage(
                MessageKeys.LABEL_TR_MANAGERESERVATION_MANAGERESERVATIONUPDATEDSCREENTITLEMESSAGE),
                driver.findElement(By.id("screenName")).getText());

        // back to reserved tours list screen
        driver.findElement(By.id("backToListBtn")).click();

        WebElement reservationsTable = driver.findElement(By.id(
                "reservationsTable"));

        assertEquals(getMessage(MessageKeys.LABEL_TR_COMMON_ADULT) + getMessage(
                MessageKeys.LABEL_TR_COMMON_PERSONCOUNTPATTERN).replace("##",
                        " 2") + "\n" + getMessage(
                                MessageKeys.LABEL_TR_COMMON_CHILD) + getMessage(
                                        MessageKeys.LABEL_TR_COMMON_PERSONCOUNTPATTERN)
                                                .replace("##", " 2"),
                reservationsTable.findElement(By.xpath(".//tr[2]/td[7]"))
                        .getText());

        assertEquals("81000", reservationsTable.findElement(By.xpath(
                ".//tr[2]/td[9]")).getText().replaceAll("[^0-9]", ""));

        // show top reservation in table
        driver.findElement(By.id("showBtn0")).click();

        assertEquals(getMessage(
                MessageKeys.LABEL_TR_MANAGERESERVATION_MANAGERESERVATIONSHOWSCREENTITLEMESSAGE),
                driver.findElement(By.id("screenName")).getText());

        // go to cancel screen
        driver.findElement(By.id("cancelBtn")).click();

        assertEquals(getMessage(
                MessageKeys.LABEL_TR_MANAGERESERVATION_BEFORECANCELSCREENTITLEMESSAGE),
                driver.findElement(By.id("screenName")).getText());

        // cancel reservation
        driver.findElement(By.id("cancelBtn")).click();

        assertEquals(getMessage(
                MessageKeys.LABEL_TR_MANAGERESERVATION_AFTERCANCELSCREENTITLEMESSAGE),
                driver.findElement(By.id("screenName")).getText());

    }

    private void reserveTourForRegisterTestData(String userName,
            String password) {
        driver.get(applicationContextUrl);

        // go to login screen
        driver.findElement(By.id("loginBtn")).click();

        // input credential
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(userName);

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

        // go to top screen
        driver.findElement(By.id("goToTopLink")).click();

        // go to search tour screen
        driver.findElement(By.id("searchTourBtn")).click();

        // logout
        driver.findElement(By.id("logoutBtn")).click();

    }

}
