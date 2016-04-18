/*
 * Copyright (C) 2013-2016 NTT DATA Corporation
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
package org.terasoluna.tourreservation.app.managereservation;

import org.terasoluna.tourreservation.domain.model.Reserve;

public class ReserveRowOutput {
    
    private Reserve reserve;

    private Boolean limitExceeding;

    private String tourDays;

    public Reserve getReserve() {
        return reserve;
    }

    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }


    public Boolean getLimitExceeding() {
        return limitExceeding;
    }

    public void setLimitExceeding(Boolean limitExceeding) {
        this.limitExceeding = limitExceeding;
    }

    public String getTourDays() {
        return tourDays;
    }

    public void setTourDays(String tourDays) {
        this.tourDays = tourDays;
    }
}
