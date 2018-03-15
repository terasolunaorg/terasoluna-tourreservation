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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = { "customer", "tourInfo" })
@EqualsAndHashCode(exclude = { "customer", "tourInfo" })
@NoArgsConstructor
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

    public Reserve(String reserveNo) {
        this.reserveNo = reserveNo;
    }

}
