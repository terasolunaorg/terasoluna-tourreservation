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
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class TourconPK implements Serializable {
    private static final long serialVersionUID = 4050472818445821131L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tour_code")
    private String tourCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tour_con_code")
    private String tourConCode;

    public TourconPK() {
    }

    public TourconPK(String tourCode, String tourConCode) {
        this.tourCode = tourCode;
        this.tourConCode = tourConCode;
    }

    public String getTourCode() {
        return tourCode;
    }

    public void setTourCode(String tourCode) {
        this.tourCode = tourCode;
    }

    public String getTourConCode() {
        return tourConCode;
    }

    public void setTourConCode(String tourConCode) {
        this.tourConCode = tourConCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tourCode != null ? tourCode.hashCode() : 0);
        hash += (tourConCode != null ? tourConCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof TourconPK)) {
            return false;
        }
        TourconPK other = (TourconPK) object;
        if ((this.tourCode == null && other.tourCode != null)
                || (this.tourCode != null && !this.tourCode
                        .equals(other.tourCode))) {
            return false;
        }
        if ((this.tourConCode == null && other.tourConCode != null)
                || (this.tourConCode != null && !this.tourConCode
                        .equals(other.tourConCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.terasoluna.tourreserve.app.domain.TourconPK[ tourCode="
                + tourCode + ", tourConCode=" + tourConCode + " ]";
    }

}
