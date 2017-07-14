/*
 * Copyright (C) 2013-2016 NTT DATA Corporation
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.tourreservation.tourreserve.common.FunctionTestSupport;
import org.terasoluna.tourreservation.tourreserve.common.constants.MessageKeys;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:seleniumContext.xml" })
public class SystemErrorTest extends FunctionTestSupport {

    WebDriver driver;

    @Value("${selenium.baseUrl}")
    String baseUrl;

    public SystemErrorTest() {
    }

    @Before
    public void setUp() {
        driver = createDefaultLocaleDriver();
    }

    @Test
    public void testSystemError() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");

        // go to login screen
        driver.findElement(By.id("loginBtn")).click();

        // input credential
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");

        // login
        driver.findElement(By.id("loginBtn")).click();

        // go to reserved tours list screen
        driver.findElement(By.id("reservedToursReferBtn")).click();

        driver.get(baseUrl
                + "/terasoluna-tourreservation-web/reservations/aaaaaa");

        assertEquals(getMessage(MessageKeys.E_TR_FW_0003), driver.findElement(By
                .cssSelector("li")).getText());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
