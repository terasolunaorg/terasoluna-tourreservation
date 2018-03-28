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
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.joda.time.DateTime;

@Data
@ToString(exclude = { "reserveList", "accommodation", "arrival", "departure" })
@EqualsAndHashCode(exclude = { "reserveList", "accommodation", "arrival",
        "departure" })
@NoArgsConstructor
@Entity
@Table(name = "tourinfo")
public class TourInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tour_code")
    private String tourCode;

    @Basic(optional = false)
    @NotNull
    @Column(name = "planned_day")
    @Temporal(TemporalType.DATE)
    private Date plannedDay;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "plan_no")
    private String planNo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "tour_name")
    private String tourName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "tour_days")
    private int tourDays;

    @Basic(optional = false)
    @NotNull
    @Column(name = "dep_day")
    @Temporal(TemporalType.DATE)
    private Date depDay;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ava_rec_max")
    private int avaRecMax;

    @Basic(optional = false)
    @NotNull
    @Column(name = "base_price")
    private int basePrice;

    @Basic(optional = false)
    @NotNull
    @Column(name = "conductor")
    private String conductor;

    @Size(max = 4000)
    @Column(name = "tour_abs")
    private String tourAbs;

    @JoinColumn(name = "dep_code", referencedColumnName = "dep_code")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Departure departure;

    @JoinColumn(name = "arr_code", referencedColumnName = "arr_code")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Arrival arrival;

    @JoinColumn(name = "accom_code", referencedColumnName = "accom_code")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Accommodation accommodation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tourInfo")
    private List<Reserve> reserveList;

    public TourInfo(String tourCode) {
        this.tourCode = tourCode;
    }

    @Transient
    public DateTime getPaymentLimit() {
        DateTime paymentLimit = new DateTime(this.getDepDay());
        return paymentLimit.minusDays(7);
    }

}
