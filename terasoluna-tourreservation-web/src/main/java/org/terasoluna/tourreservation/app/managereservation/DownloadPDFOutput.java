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
package org.terasoluna.tourreservation.app.managereservation;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Output of Price Calculation.<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadPDFOutput implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 6982754828396123236L;

    private String referenceName;

    private String referenceEmail;

    private String referenceTel;

    private String paymentMethod;

    private String paymentCompanyName;

    private String paymentAccount;

    private Integer childCount;

    private String tourName;

    private String accomName;

    private String customerKana;

    private String customerTel;

    private Integer adultUnitPrice;

    private Date reservedDay;

    private String conductor;

    private String tourAbs;

    private String customerAdd;

    private String customerJob;

    private String tourDays;

    private Date depDay;

    private String customerName;

    private Integer childUnitPrice;

    private String depName;

    private Date customerBirth;

    private String arrName;

    private String customerMail;

    private Integer adultCount;

    private String customerCode;

    private String reserveNo;

    private String remarks;

    private String accomTel;

    private String customerPost;

    private Date printDay;

    private Integer adultPrice;

    private Integer childPrice;

    private Integer sumPrice;

    private String paymentTimeLimit;

    private String pdfName;

}
