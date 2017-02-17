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
package org.terasoluna.tourreservation.app.managereservation;

import java.io.Serializable;
import java.util.Date;

/**
 * Output of Price Calculation.<br>
 */
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

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public String getReferenceEmail() {
        return referenceEmail;
    }

    public void setReferenceEmail(String referenceEmail) {
        this.referenceEmail = referenceEmail;
    }

    public String getReferenceTel() {
        return referenceTel;
    }

    public void setReferenceTel(String referenceTel) {
        this.referenceTel = referenceTel;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentCompanyName() {
        return paymentCompanyName;
    }

    public void setPaymentCompanyName(String paymentCompanyName) {
        this.paymentCompanyName = paymentCompanyName;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getAccomName() {
        return accomName;
    }

    public void setAccomName(String accomName) {
        this.accomName = accomName;
    }

    public String getCustomerKana() {
        return customerKana;
    }

    public void setCustomerKana(String customerKana) {
        this.customerKana = customerKana;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public Integer getAdultUnitPrice() {
        return adultUnitPrice;
    }

    public void setAdultUnitPrice(Integer adultUnitPrice) {
        this.adultUnitPrice = adultUnitPrice;
    }

    public Date getReservedDay() {
        return reservedDay;
    }

    public void setReservedDay(Date reservedDay) {
        this.reservedDay = reservedDay;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getTourAbs() {
        return tourAbs;
    }

    public void setTourAbs(String tourAbs) {
        this.tourAbs = tourAbs;
    }

    public String getCustomerAdd() {
        return customerAdd;
    }

    public void setCustomerAdd(String customerAdd) {
        this.customerAdd = customerAdd;
    }

    public String getCustomerJob() {
        return customerJob;
    }

    public void setCustomerJob(String customerJob) {
        this.customerJob = customerJob;
    }

    public String getTourDays() {
        return tourDays;
    }

    public void setTourDays(String tourDays) {
        this.tourDays = tourDays;
    }

    public Date getDepDay() {
        return depDay;
    }

    public void setDepDay(Date depDay) {
        this.depDay = depDay;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getChildUnitPrice() {
        return childUnitPrice;
    }

    public void setChildUnitPrice(Integer childUnitPrice) {
        this.childUnitPrice = childUnitPrice;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public Date getCustomerBirth() {
        return customerBirth;
    }

    public void setCustomerBirth(Date customerBirth) {
        this.customerBirth = customerBirth;
    }

    public String getArrName() {
        return arrName;
    }

    public void setArrName(String arrName) {
        this.arrName = arrName;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public Integer getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(Integer adultCount) {
        this.adultCount = adultCount;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getReserveNo() {
        return reserveNo;
    }

    public void setReserveNo(String reserveNo) {
        this.reserveNo = reserveNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAccomTel() {
        return accomTel;
    }

    public void setAccomTel(String accomTel) {
        this.accomTel = accomTel;
    }

    public String getCustomerPost() {
        return customerPost;
    }

    public void setCustomerPost(String customerPost) {
        this.customerPost = customerPost;
    }

    public Date getPrintDay() {
        return printDay;
    }

    public void setPrintDay(Date printDay) {
        this.printDay = printDay;
    }

    public Integer getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(Integer adultPrice) {
        this.adultPrice = adultPrice;
    }

    public Integer getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(Integer childPrice) {
        this.childPrice = childPrice;
    }

    public Integer getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Integer sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getPaymentTimeLimit() {
        return paymentTimeLimit;
    }

    public void setPaymentTimeLimit(String paymentTimeLimit) {
        this.paymentTimeLimit = paymentTimeLimit;
    }

}
