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
import org.terasoluna.tourreservation.tourreserve.common.FunctionTestSupport;
import org.terasoluna.tourreservation.tourreserve.common.constants.MessageKeys;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:seleniumContext.xml" })
public class CustomerRegisterTest extends FunctionTestSupport {
    @Inject
    MessageSource messageSource;

    WebDriver driver;

    @Value("${selenium.baseUrl}")
    String baseUrl;

    public CustomerRegisterTest() {
    }

    @Before
    public void setUp() {
        driver = createLocaleSpecifiedDriver(Locale.getDefault().toLanguageTag());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

    @Test
    public void testCustomerRegister() {
        driver.get(baseUrl + "/terasoluna-tourreservation-web");

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_CUSTOMERREGISTERBTNMESSAGE) + "']"))
                .click();

        driver.findElement(By.name("customerKana")).sendKeys("テラソルナ");
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

        // confirm registration contents
        confirmRegistrationContents();
        
        // back
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_GOBACKMESSAGE) + "']"))
                .click();
        
        // reenter confirm password again
        driver.findElement(By.name("customerPass")).sendKeys("tera123");
        driver.findElement(By.name("customerPassConfirm")).sendKeys("tera123");
        
        // confirm
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_CONFIRM) + "']"))
                .click();
        
        // confirm registration contents again
        confirmRegistrationContents();
        
        // Register
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_REGISTER) + "']"))
                .click();

        // Retention of ID that are registered
        String loginID = driver.findElement(By.xpath("//strong[2]")).getText();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_GOTOMENUMESSAGE) + "']"))
                .click();

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_MENU_LOGINBTNMESSAGE) + "']"))
                .click();

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("tera123");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(loginID);

        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_LOGIN) + "']"))
                .click();

        assertEquals(getMessage(MessageKeys.LABEL_TR_MENU_MENUMESSAGE),
                driver.findElement(By.cssSelector("p.box")).getText());
    }

    private void confirmRegistrationContents() {
        assertEquals("テラソルナ", driver.findElement(By.xpath("//td[2]")).getText());
        assertEquals("ＴＥＲＡＳＯＬＵＮＡ", driver
                .findElement(By.xpath("//tr[2]/td[2]")).getText());
        assertEquals("2000" + getMessage(MessageKeys.LABEL_TR_COMMON_YEAR) + " 12"
                + getMessage(MessageKeys.LABEL_TR_COMMON_MONTH) + " 1"
                + getMessage(MessageKeys.LABEL_TR_COMMON_DAY),
                driver.findElement(By.xpath("//tr[3]/td[2]")).getText());
        assertEquals("FW", driver.findElement(By.xpath("//tr[4]/td[2]"))
                .getText());
        assertEquals("terasoluna@nttd.co.jp",
                driver.findElement(By.xpath("//tr[5]/td[2]")).getText());
        assertEquals("090-99999999",
                driver.findElement(By.xpath("//tr[6]/td[2]")).getText());
        assertEquals("333-2222", driver.findElement(By.xpath("//tr[7]/td[2]"))
                .getText());
        assertEquals("tokyo-toyosu",
                driver.findElement(By.xpath("//tr[8]/td[2]")).getText());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
