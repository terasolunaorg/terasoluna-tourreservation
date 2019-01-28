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
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.tourreservation.tourreserve.common.FunctionTestSupport;
import org.terasoluna.tourreservation.tourreserve.common.constants.MessageKeys;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/seleniumContext.xml" })
public class CustomerRegisterValidateTest extends FunctionTestSupport {

    WebDriver driver;

    public CustomerRegisterValidateTest() {
    }

    @Before
    public void setUp() {
        driver = createWebDriver();
    }

    @Test
    public void testCustomerRegisterRequiredValidate() {

        // go to register screen
        driver.findElement(By.id("customerRegisterBtn")).click();

        // input new customer
        driver.findElement(By.name("customerKana")).sendKeys("");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertTrue(multiMessageAssert(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE)
                        .replace("{0}", getMessage(MessageKeys.CUSTOMERKANA))
                + "\n" + getMessage(
                        MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERKANA), driver
                                .findElement(By.id("customerForm.errors"))
                                .getText(), "\n"));

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertTrue(multiMessageAssert(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE)
                        .replace("{0}", getMessage(MessageKeys.CUSTOMERNAME))
                + "\n" + getMessage(
                        MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERNAME), driver
                                .findElement(By.id("customerForm.errors"))
                                .getText(), "\n"));

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertEquals(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE)
                        .replace("{0}", getMessage(MessageKeys.CUSTOMERJOB)),
                driver.findElement(By.id("customerForm.errors")).getText());

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertTrue(multiMessageAssert(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_SIZE_MESSAGE).replace(
                        "{0}", getMessage(MessageKeys.CUSTOMERTEL)).replace(
                                "{min}", "10").replace("{max}", "13") + "\n"
                + getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERTEL),
                driver.findElement(By.id("customerForm.errors")).getText(),
                "\n"));

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertEquals(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_PATTERN_MESSAGE)
                        .replace("{0}", getMessage(MessageKeys.CUSTOMERPOST))
                        .replace("{regexp}", "[0-9]{3}-[0-9]{4}"), driver
                                .findElement(By.id("customerForm.errors"))
                                .getText());

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertEquals(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE)
                        .replace("{0}", getMessage(MessageKeys.CUSTOMERADD)),
                driver.findElement(By.id("customerForm.errors")).getText());

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertTrue(multiMessageAssert(getMessage(
                MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERPASS) + "\n"
                + getMessage(
                        MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE)
                                .replace("{0}", getMessage(
                                        MessageKeys.CUSTOMERPASS)) + "\n"
                + getMessage(
                        MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_SIZE_MESSAGE)
                                .replace("{0}", getMessage(
                                        MessageKeys.CUSTOMERPASS)).replace(
                                                "{min}", "4").replace("{max}",
                                                        "20") + "\n"
                + getMessage(MessageKeys.NOTEQUALS_CUSTOMERPASS), driver
                        .findElement(By.id("customerForm.errors")).getText(),
                "\n"));

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertTrue(multiMessageAssert(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_NOTEMPTY_MESSAGE)
                        .replace("{0}", getMessage(
                                MessageKeys.CUSTOMERPASSCONFIRM)) + "\n"
                + getMessage(
                        MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERPASSCONFIRM)
                + "\n" + getMessage(
                        MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_SIZE_MESSAGE)
                                .replace("{0}", getMessage(
                                        MessageKeys.CUSTOMERPASSCONFIRM))
                                .replace("{min}", "4").replace("{max}", "20")
                + "\n" + getMessage(MessageKeys.NOTEQUALS_CUSTOMERPASS), driver
                        .findElement(By.id("customerForm.errors")).getText(),
                "\n"));

    }

    @Test
    public void testCustomerRegisterFormatValidate() {

        // go to register screen
        driver.findElement(By.id("customerRegisterBtn")).click();

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("SSSZ2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertEquals(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_PATTERN_MESSAGE)
                        .replace("{0}", getMessage(MessageKeys.CUSTOMERPOST))
                        .replace("{regexp}", "[0-9]{3}-[0-9]{4}"), driver
                                .findElement(By.id("customerForm.errors"))
                                .getText());

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerMail")).sendKeys("FDSAGDD");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertEquals(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_EMAIL_MESSAGE), driver
                        .findElement(By.id("customerForm.errors")).getText());
    }

    @Test
    public void testCustomerRegisterDateValidate() {

        // go to register screen
        driver.findElement(By.id("customerRegisterBtn")).click();

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        new Select(driver.findElement(By.id("customerBirthYear")))
                .selectByValue("2000");
        new Select(driver.findElement(By.id("customerBirthDay"))).selectByValue(
                "31");
        new Select(driver.findElement(By.id("customerBirthMonth")))
                .selectByValue("2");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertEquals(getMessage(MessageKeys.INCORRECTDATE_CUSTOMERBIRTH), driver
                .findElement(By.id("customerForm.errors")).getText());
    }

    @Test
    public void testCustomerRegisterNumberValidate() {

        // go to register screen
        driver.findElement(By.id("customerRegisterBtn")).click();

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        new Select(driver.findElement(By.id("customerBirthYear")))
                .selectByValue("2000");
        new Select(driver.findElement(By.id("customerBirthDay"))).selectByValue(
                "2");
        new Select(driver.findElement(By.id("customerBirthMonth")))
                .selectByValue("2");

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("ter");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertTrue(multiMessageAssert(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_SIZE_MESSAGE).replace(
                        "{0}", getMessage(MessageKeys.CUSTOMERPASS)).replace(
                                "{min}", "4").replace("{max}", "20") + "\n"
                + getMessage(MessageKeys.NOTEQUALS_CUSTOMERPASS), driver
                        .findElement(By.id("customerForm.errors")).getText(),
                "\n"));

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("ter123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("ter");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertTrue(multiMessageAssert(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_SIZE_MESSAGE).replace(
                        "{0}", getMessage(MessageKeys.CUSTOMERPASSCONFIRM))
                        .replace("{min}", "4").replace("{max}", "20") + "\n"
                + getMessage(MessageKeys.NOTEQUALS_CUSTOMERPASS), driver
                        .findElement(By.id("customerForm.errors")).getText(),
                "\n"));

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertEquals(getMessage(
                MessageKeys.JAVAX_VALIDATION_CONSTRAINTS_SIZE_MESSAGE).replace(
                        "{0}", getMessage(MessageKeys.CUSTOMERTEL)).replace(
                                "{min}", "10").replace("{max}", "13"), driver
                                        .findElement(By.id(
                                                "customerForm.errors"))
                                        .getText());
    }

    @Test
    public void testCustomerRegisterTypeValidate() {

        // go to register screen
        driver.findElement(By.id("customerRegisterBtn")).click();

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("test");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertEquals(getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERKANA),
                driver.findElement(By.id("customerForm.errors")).getText());

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("test");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertEquals(getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERNAME),
                driver.findElement(By.id("customerForm.errors")).getText());

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-99999999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("@@terasoluna-#");
        driver.findElement(By.name("customerPassConfirm")).sendKeys(
                "@@terasoluna-#");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertTrue(multiMessageAssert(getMessage(
                MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERPASSCONFIRM) + "\n"
                + getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERPASS),
                driver.findElement(By.id("customerForm.errors")).getText(),
                "\n"));

        // input new customer
        driver.findElement(By.id("customerKana")).clear();
        driver.findElement(By.id("customerName")).clear();
        driver.findElement(By.id("customerJob")).clear();
        driver.findElement(By.id("customerTel")).clear();
        driver.findElement(By.id("customerPost")).clear();
        driver.findElement(By.id("customerAdd")).clear();
        driver.findElement(By.id("customerPass")).clear();
        driver.findElement(By.id("customerPassConfirm")).clear();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
        driver.findElement(By.name("customerName")).sendKeys("ＴＥＲＡＳＯＬＵＮＡ");
        driver.findElement(By.name("customerJob")).sendKeys("FW");
        driver.findElement(By.name("customerTel")).sendKeys("090-999a9999");
        driver.findElement(By.name("customerPost")).sendKeys("333-2222");
        driver.findElement(By.name("customerAdd")).sendKeys("tokyo-toyosu");
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");

        // go to confirm screen
        driver.findElement(By.id("confirmBtn")).click();

        assertEquals(getMessage(MessageKeys.PATTERN_CUSTOMERFORM_CUSTOMERTEL),
                driver.findElement(By.id("customerForm.errors")).getText());
    }

    /**
     * output error message assert method
     * @param expectedStr
     * @param actualStr
     * @param splitStr
     * @return boolean
     */
    private Boolean multiMessageAssert(String expectedStr, String actualStr,
            String splitStr) {
        String[] expectedlist = expectedStr.split(splitStr);
        String[] actualStrlist = actualStr.split(splitStr);

        List<String> expected = Arrays.asList(expectedlist);

        for (int i = 0; i < actualStrlist.length; i++) {
            if (!expected.contains(actualStrlist[i])) {
                return false;
            }
        }

        return true;
    }

    @After
    public void tearDown() {
        /*
         * In case of firefox 52.9, geckodriver 0.14.0,
         * Since it crashes when closing firefox browser,
         * Open the configuration editor and close the browser.
         */
        driver.get("about:config");
        driver.quit();
    }
}
