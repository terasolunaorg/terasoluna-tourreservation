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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.tourreservation.tourreserve.common.FunctionTestSupport;
import org.terasoluna.tourreservation.tourreserve.common.constants.MessageKeys;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/seleniumContext.xml" })
public class LogInLogOutTest extends FunctionTestSupport {

    public LogInLogOutTest() {
    }

    @Test
    public void testLoginLogoff() {

        assertEquals(getMessage(MessageKeys.LABEL_TR_COMMON_NOTLOGINMESSAGE)
                + " " + getMessage(MessageKeys.LABEL_TR_MENU_MENUMESSAGE),
                driver.findElement(By.id("messagesArea")).getText());

        // go to login screen
        driver.findElement(By.id("loginBtn")).click();

        assertEquals(getMessage(MessageKeys.LABEL_TR_COMMON_NOTLOGINMESSAGE),
                driver.findElement(By.id("messagesArea")).getText());

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

        // logout
        driver.findElement(By.id("logoutBtn")).click();

        assertEquals(getMessage(MessageKeys.LABEL_TR_COMMON_NOTLOGINMESSAGE)
                + " " + getMessage(MessageKeys.LABEL_TR_MENU_MENUMESSAGE),
                driver.findElement(By.id("messagesArea")).getText());
    }

    @Test
    public void testLoginFailure() {

        assertEquals(getMessage(MessageKeys.LABEL_TR_COMMON_NOTLOGINMESSAGE)
                + " " + getMessage(MessageKeys.LABEL_TR_MENU_MENUMESSAGE),
                driver.findElement(By.id("messagesArea")).getText());

        // go to login screen
        driver.findElement(By.id("loginBtn")).click();

        assertEquals(getMessage(MessageKeys.LABEL_TR_COMMON_NOTLOGINMESSAGE),
                driver.findElement(By.id("messagesArea")).getText());

        // input credential
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password1234");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");

        // login
        driver.findElement(By.id("loginBtn")).click();

        // login failure default message
        assertThat(driver.findElement(By.id("loginError")).getText(), is(
                "Bad credentials"));

        // go to login screen
        driver.get(applicationContextUrl + "/login");

        assertEquals(getMessage(MessageKeys.LABEL_TR_COMMON_NOTLOGINMESSAGE),
                driver.findElement(By.id("messagesArea")).getText());
    }

}
