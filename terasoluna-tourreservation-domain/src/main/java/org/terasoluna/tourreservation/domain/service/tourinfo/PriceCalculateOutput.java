/*
 * Copyright (C) 2013-2015 terasoluna.org
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
package org.terasoluna.tourreservation.domain.service.tourinfo;

import java.io.Serializable;

/**
 * Output of Price Calculation.<br>
 * 
 */
public class PriceCalculateOutput implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 6982754828396123236L;

    /**
     * adult price.
     */
    private Integer adultUnitPrice = null;

    /**
     * child price.
     */
    private Integer childUnitPrice = null;

    /**
     * adult count.
     */
    private Integer adultCount = null;

    /**
     * child count.
     */
    private Integer childCount = null;

    /**
     * adult price.
     */
    private Integer adultPrice = null;

    /**
     * child price.
     */
    private Integer childPrice = null;

    /**
     * sum of price.
     */
    private Integer sumPrice = null;

    /**
     * returns adult count.
     * 
     * @return adult count
     */
    public Integer getAdultCount() {

        return adultCount;
    }

    /**
     * set adult count.
     * 
     * @param adultCount adult count
     */
    public void setAdultCount(Integer adultCount) {

        this.adultCount = adultCount;
    }

    /**
     * returns adult price.
     * 
     * @return adult price
     */
    public Integer getAdultPrice() {

        return adultPrice;
    }

    /**
     * set adult price.
     * 
     * @param adultPrice adult price
     */
    public void setAdultPrice(Integer adultPrice) {

        this.adultPrice = adultPrice;
    }

    /**
     * returns adult price.
     * 
     * @return adult price
     */
    public Integer getAdultUnitPrice() {

        return adultUnitPrice;
    }

    /**
     * set adult price.
     * 
     * @param adultUnitPrice adult price
     */
    public void setAdultUnitPrice(Integer adultUnitPrice) {

        this.adultUnitPrice = adultUnitPrice;
    }

    /**
     * returns child count.
     * 
     * @return child count
     */
    public Integer getChildCount() {

        return childCount;
    }

    /**
     * set child count.
     * 
     * @param childCount child count
     */
    public void setChildCount(Integer childCount) {

        this.childCount = childCount;
    }

    /**
     * returns child price.
     * 
     * @return child price
     */
    public Integer getChildPrice() {

        return childPrice;
    }

    /**
     * set child price.
     * 
     * @param childPrice child price
     */
    public void setChildPrice(Integer childPrice) {

        this.childPrice = childPrice;
    }

    /**
     * returns child price.
     * 
     * @return child price
     */
    public Integer getChildUnitPrice() {

        return childUnitPrice;
    }

    /**
     * set child price.
     * 
     * @param childUnitPrice child price
     */
    public void setChildUnitPrice(Integer childUnitPrice) {

        this.childUnitPrice = childUnitPrice;
    }

    /**
     * returns sum of price.
     * 
     * @return sum of price
     */
    public Integer getSumPrice() {

        return sumPrice;
    }

    /**
     * set sum of price.
     * 
     * @param sumPrice sum of price
     */
    public void setSumPrice(Integer sumPrice) {

        this.sumPrice = sumPrice;
    }

}
