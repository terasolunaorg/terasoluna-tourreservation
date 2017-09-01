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
package org.terasoluna.tourreservation.app.searchtour;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchTourForm implements Serializable {

    /**
     * serialVersion.
     */
    private static final long serialVersionUID = 6713033250301914074L;

    @NotNull
    private Integer depYear;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer depMonth;

    @NotNull
    @Min(1)
    @Max(31)
    private Integer depDay;

    @NotNull
    private Integer tourDays;

    @NotEmpty
    private String depCode;

    @NotEmpty
    private String arrCode;

    @NotNull
    @Min(0)
    private Integer adultCount;

    @NotNull
    @Min(0)
    private Integer childCount;

    @NotNull
    @Min(0)
    private Integer basePrice;

    public Integer getDepYear() {
        return depYear;
    }

    public void setDepYear(Integer depYear) {
        this.depYear = depYear;
    }

    public Integer getDepMonth() {
        return depMonth;
    }

    public void setDepMonth(Integer depMonth) {
        this.depMonth = depMonth;
    }

    public Integer getDepDay() {
        return depDay;
    }

    public void setDepDay(Integer depDay) {
        this.depDay = depDay;
    }

    public Integer getTourDays() {
        return tourDays;
    }

    public void setTourDays(Integer tourDays) {
        this.tourDays = tourDays;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getArrCode() {
        return arrCode;
    }

    public void setArrCode(String arrCode) {
        this.arrCode = arrCode;
    }

    public Integer getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(Integer adultCount) {
        this.adultCount = adultCount;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public Integer getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Integer basePrice) {
        this.basePrice = basePrice;
    }
}
