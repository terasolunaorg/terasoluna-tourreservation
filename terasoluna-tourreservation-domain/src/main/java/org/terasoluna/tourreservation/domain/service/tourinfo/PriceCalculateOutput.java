/*
 * Copyright (C) 2013-2018 NTT DATA Corporation
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

import lombok.Data;

/**
 * Output of Price Calculation.<br>
 */
@Data
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

}
