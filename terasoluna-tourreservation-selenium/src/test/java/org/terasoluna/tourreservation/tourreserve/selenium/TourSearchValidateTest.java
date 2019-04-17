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

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.Select;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.tourreservation.tourreserve.common.FunctionTestSupport;
import org.terasoluna.tourreservation.tourreserve.common.constants.MessageKeys;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/seleniumContext.xml" })
public class TourSearchValidateTest extends FunctionTestSupport {

    private static final int width = 1024;

    private static final int height = 768;

    @Inject
    JodaTimeDateFactory dateFactory;

    public TourSearchValidateTest() {
    }

    @Before
    public void setUp() {
        super.setUp();
        driver.manage().window().setSize(new Dimension(width, height));
    }

    @Test
    public void testRequiredValidate() {

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
        new Select(driver.findElement(By.id("depCode"))).selectByValue("");
        new Select(driver.findElement(By.id("arrCode"))).selectByValue("01");

        // search tour
        driver.findElement(By.id("searchBtn")).click();

        assertEquals(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE)
                        .replace("{0}", getMessage(MessageKeys.DEPCODE)), driver
                                .findElement(By.id("searchTourForm.errors"))
                                .getText());

        // input credential
        new Select(driver.findElement(By.id("depCode"))).selectByValue("01");
        new Select(driver.findElement(By.id("arrCode"))).selectByValue("");

        // search tour
        driver.findElement(By.id("searchBtn")).click();

        assertEquals(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE)
                        .replace("{0}", getMessage(MessageKeys.ARRCODE)), driver
                                .findElement(By.id("searchTourForm.errors"))
                                .getText());
    }

    @Test
    public void testDateValidate() {

        // go to search tour screen
        driver.findElement(By.id("searchTourBtn")).click();

        // input credential
        new Select(driver.findElement(By.id("depCode"))).selectByValue("01");
        new Select(driver.findElement(By.id("arrCode"))).selectByValue("01");

        int currentYear = dateFactory.newDateTime().getYear();

        new Select(driver.findElement(By.id("depYear"))).selectByValue(String
                .valueOf(currentYear));
        new Select(driver.findElement(By.id("depMonth"))).selectByValue("2");
        new Select(driver.findElement(By.id("depDay"))).selectByValue("30");

        // search tour
        driver.findElement(By.id("searchBtn")).click();

        assertEquals(getMessage(MessageKeys.INCORRECTDATE_INPUTDATE), driver
                .findElement(By.id("searchTourForm.errors")).getText());
    }

}
