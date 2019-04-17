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
package org.terasoluna.tourreservation.tourreserve.common;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class WebDriverManagerFactoryBean<T extends WebDriver>
                                                 implements FactoryBean<T>,
                                                 InitializingBean {

    private String propertyFileLocation;

    public void setPropertyFileLocation(String propertyFileLocation) {
        this.propertyFileLocation = propertyFileLocation;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (propertyFileLocation != null) {
            WebDriverManager.config().setProperties(propertyFileLocation);
        }
    }

}
