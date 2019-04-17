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
public class CustomerRegisterTest extends FunctionTestSupport {

    public CustomerRegisterTest() {
    }

    @Test
    public void testCustomerRegister() {

        // go to register screen
        driver.findElement(By.id("customerRegisterBtn")).click();

        // input new customer
        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
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

        // confirm registration contents
        confirmRegistrationContents();

        // go back
        driver.findElement(By.id("backToFormBtn")).click();

        // reenter confirm password again
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        // confirm registration contents again
        confirmRegistrationContents();

        // Register
        driver.findElement(By.id("registerBtn")).click();

        // Retention of ID that are registered
        String loginID = driver.findElement(By.xpath("//strong[2]")).getText();

        // go to menu screen
        driver.findElement(By.id("goToMenuBtn")).click();

        // go to login screen
        driver.findElement(By.id("loginBtn")).click();

        // input credential
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("tera123");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(loginID);

        // login
        driver.findElement(By.id("loginBtn")).click();

        assertEquals(getMessage(MessageKeys.LABEL_TR_MENU_MENUMESSAGE), driver
                .findElement(By.id("messagesArea")).getText());
    }

    private void confirmRegistrationContents() {
        WebElement customerTable = driver.findElement(By.id("customerTable"));
        assertTableContents(customerTable, 0, 1, null, "テラソルナ", "ＴＥＲＡＳＯＬＵＮＡ",
                ("2000" + getMessage(MessageKeys.LABEL_TR_COMMON_YEAR) + "12"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_MONTH) + "01"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_DAY)), "FW",
                "terasoluna@nttd.co.jp", "090-99999999", "333-2222",
                "tokyo-toyosu", "********");
    }

}
