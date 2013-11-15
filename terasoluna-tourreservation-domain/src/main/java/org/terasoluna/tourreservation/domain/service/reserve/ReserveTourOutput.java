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
package org.terasoluna.tourreservation.domain.service.reserve;

import java.util.Date;

import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;


public class ReserveTourOutput {
    private PriceCalculateOutput priceCalculateOutput;

    private Reserve reserve;

    private Customer customer;

    private TourInfo tourInfo;

    private Date paymentTimeLimit;

    public Reserve getReserve() {
        return reserve;
    }

    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }

    public PriceCalculateOutput getPriceCalculateOutput() {
        return priceCalculateOutput;
    }

    public void setPriceCalculateOutput(
            PriceCalculateOutput priceCalculateOutput) {
        this.priceCalculateOutput = priceCalculateOutput;
    }

    public Date getPaymentTimeLimit() {
        return paymentTimeLimit;
    }

    public void setPaymentTimeLimit(Date paymentTimeLimit) {
        this.paymentTimeLimit = paymentTimeLimit;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public TourInfo getTourInfo() {
        return tourInfo;
    }

    public void setTourInfo(TourInfo tourInfo) {
        this.tourInfo = tourInfo;
    }

    public Customer getCustomer() {
        return customer;
    }
}
