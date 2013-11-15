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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.terasoluna.tourreservation.domain.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tourcon")
public class Tourcon implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected TourconPK tourconPK;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "tour_con_name")
    private String tourConName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "tour_con_mail")
    private String tourConMail;

    @JoinColumn(name = "tour_code", referencedColumnName = "tour_code", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TourInfo tourInfo;

    public Tourcon() {
    }

    public Tourcon(TourconPK tourconPK) {
        this.tourconPK = tourconPK;
    }

    public Tourcon(TourconPK tourconPK, String tourConName, String tourConMail) {
        this.tourconPK = tourconPK;
        this.tourConName = tourConName;
        this.tourConMail = tourConMail;
    }

    public Tourcon(String tourCode, String tourConCode) {
        this.tourconPK = new TourconPK(tourCode, tourConCode);
    }

    public TourconPK getTourconPK() {
        return tourconPK;
    }

    public void setTourconPK(TourconPK tourconPK) {
        this.tourconPK = tourconPK;
    }

    public String getTourConName() {
        return tourConName;
    }

    public void setTourConName(String tourConName) {
        this.tourConName = tourConName;
    }

    public String getTourConMail() {
        return tourConMail;
    }

    public void setTourConMail(String tourConMail) {
        this.tourConMail = tourConMail;
    }

    public TourInfo getTourInfo() {
        return tourInfo;
    }

    public void setTourInfo(TourInfo tourInfo) {
        this.tourInfo = tourInfo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tourconPK != null ? tourconPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof Tourcon)) {
            return false;
        }
        Tourcon other = (Tourcon) object;
        if ((this.tourconPK == null && other.tourconPK != null)
                || (this.tourconPK != null && !this.tourconPK
                        .equals(other.tourconPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.terasoluna.tourreserve.app.domain.Tourcon[ tourconPK="
                + tourconPK + " ]";
    }

}
