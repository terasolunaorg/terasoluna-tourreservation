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

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.tourreservation.tourreserve.common.FunctionTestSupport;
import org.terasoluna.tourreservation.tourreserve.common.constants.MessageKeys;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:seleniumContext.xml" })
public class NoLogInTourSearchPaginationTest extends FunctionTestSupport {

    WebDriver driver;

    @Value("${selenium.baseUrl}")
    String baseUrl;

    public NoLogInTourSearchPaginationTest() {
    }

    @Before
    public void setUp() {
        driver = createDefaultLocaleDriver();
    }

    @Test
    public void testNoLogInTourSearchPagination() {
        
        
        driver.get(baseUrl + "/terasoluna-tourreservation-web/");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        new Select(driver.findElement(By.id("depCode"))).selectByValue("01");
        new Select(driver.findElement(By.id("arrCode"))).selectByValue("01");
        driver.findElement(
                By.xpath("//input[@value='"
                        + getMessage(MessageKeys.LABEL_TR_COMMON_SEARCH) + "']"))
                .click();
        
        driver.findElement(By.linkText("2")).click();
        
        driver.findElement(By.linkText(driver.findElement(By.xpath("//td[2]")).getText())).click();
        driver.findElement(By.xpath("//input[@value='"
                + getMessage(MessageKeys.LABEL_TR_COMMON_GOBACKMESSAGE) + "']")).click();
        
        // assert that it navigates back to page 3
        assertNotNull(driver.findElement(By.id("11")));
        
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
