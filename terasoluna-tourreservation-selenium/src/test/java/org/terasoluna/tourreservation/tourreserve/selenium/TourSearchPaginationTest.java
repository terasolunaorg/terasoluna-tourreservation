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

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.tourreservation.tourreserve.common.FunctionTestSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/seleniumContext.xml" })
public class TourSearchPaginationTest extends FunctionTestSupport {

    WebDriver driver;

    public TourSearchPaginationTest() {
    }

    @Before
    public void setUp() {
        driver = createWebDriver();
    }

    /**
     * Pagination tag related test about usage of page and size parameters (without login). <br>
     * Tests the use of page On returning from a detail screen, tests whether the page number is the same the one selected
     * before going to the detail page.<br>
     */
    @Test
    public void testTourSearchPagination1() {

        // go to search tour screen
        driver.findElement(By.id("searchTourBtn")).click();

        // input search criteria
        new Select(driver.findElement(By.id("depCode"))).selectByValue("01");
        new Select(driver.findElement(By.id("arrCode"))).selectByValue("01");

        // search tour
        driver.findElement(By.id("searchBtn")).click();

        // paging
        driver.findElement(By.linkText("3")).click();

        // show top tour in table
        WebElement toursTable = driver.findElement(By.id("toursTable"));
        toursTable.findElements(By.tagName("a")).get(0).click();

        // back to tours
        driver.findElement(By.id("backToToursBtn")).click();

        // currentPage query check
        assertThat(driver.findElement(By.className("active")).getText(), is(
                "3"));
    }

    /**
     * Pagination tag related test about usage of page and size parameters (with login). <br>
     * On returning from a detail screen, tests whether the page number is the same the one selected before going to the detail
     * page.<br>
     */
    @Test
    public void testTourSearchPagination2() {

        // go to search tour screen
        driver.findElement(By.id("searchTourBtn")).click();

        // input search criteria
        new Select(driver.findElement(By.id("depCode"))).selectByValue("01");
        new Select(driver.findElement(By.id("arrCode"))).selectByValue("01");

        // search tour
        driver.findElement(By.id("searchBtn")).click();

        // paging
        driver.findElement(By.linkText("3")).click();

        // show top tour in table
        WebElement toursTable = driver.findElement(By.id("toursTable"));
        toursTable.findElements(By.tagName("a")).get(0).click();

        // go to login screen
        driver.findElement(By.id("loginBtn")).click();

        // input credential
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("00000001");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");

        // login
        driver.findElement(By.id("loginBtn")).click();

        // back to tours
        driver.findElement(By.id("backToToursBtn")).click();

        // currentPage query check
        assertThat(driver.findElement(By.className("active")).getText(), is(
                "3"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
