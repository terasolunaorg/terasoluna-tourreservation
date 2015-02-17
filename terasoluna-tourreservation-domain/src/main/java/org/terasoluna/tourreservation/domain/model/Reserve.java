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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terasoluna.tourreservation.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "reserve")
public class Reserve implements Serializable {

    public static final String TRANSFERED = "1";

    public static final String NOT_TRANSFERED = "0";

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "reserve_no")
    private String reserveNo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "reserved_day")
    @Temporal(TemporalType.DATE)
    private Date reservedDay;

    @Basic(optional = false)
    @NotNull
    @Column(name = "adult_count")
    private int adultCount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "child_count")
    private int childCount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "transfer")
    private String transfer;

    @Basic(optional = false)
    @NotNull
    @Column(name = "sum_price")
    private int sumPrice;

    @Size(max = 1000)
    @Column(name = "remarks")
    private String remarks;

    @JoinColumn(name = "tour_code", referencedColumnName = "tour_code")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TourInfo tourInfo;

    @JoinColumn(name = "customer_code", referencedColumnName = "customer_code")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer customer;

    public Reserve() {
    }

    public Reserve(String reserveNo) {
        this.reserveNo = reserveNo;
    }

    public Reserve(String reserveNo, Date reservedDay, int adultCount,
            int childCount, String transfer, int sumPrice) {
        this.reserveNo = reserveNo;
        this.reservedDay = reservedDay;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.transfer = transfer;
        this.sumPrice = sumPrice;
    }

    public String getReserveNo() {
        return reserveNo;
    }

    public void setReserveNo(String reserveNo) {
        this.reserveNo = reserveNo;
    }

    public Date getReservedDay() {
        return reservedDay;
    }

    public void setReservedDay(Date reservedDay) {
        this.reservedDay = reservedDay;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reserveNo != null ? reserveNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof Reserve)) {
            return false;
        }
        Reserve other = (Reserve) object;
        if ((this.reserveNo == null && other.reserveNo != null)
                || (this.reserveNo != null && !this.reserveNo
                        .equals(other.reserveNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.terasoluna.tourreservation.domain.model.Reserve[ reserveNo="
                + reserveNo + " ]";
    }

}
