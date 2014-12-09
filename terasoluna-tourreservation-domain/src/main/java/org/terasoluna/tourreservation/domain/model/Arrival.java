/*
 * Copyright (C) 2013-2014 terasoluna.org
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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "arrival")
public class Arrival implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "arr_code")
    private String arrCode;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "arr_name")
    private String arrName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "arrival")
    private List<TourInfo> tourinfoList;

    public Arrival() {
    }

    public Arrival(String arrCode) {
        this.arrCode = arrCode;
    }

    public Arrival(String arrCode, String arrName) {
        this.arrCode = arrCode;
        this.arrName = arrName;
    }

    public String getArrCode() {
        return arrCode;
    }

    public void setArrCode(String arrCode) {
        this.arrCode = arrCode;
    }

    public String getArrName() {
        return arrName;
    }

    public void setArrName(String arrName) {
        this.arrName = arrName;
    }

    public List<TourInfo> getTourinfoList() {
        return tourinfoList;
    }

    public void setTourinfoList(List<TourInfo> tourinfoList) {
        this.tourinfoList = tourinfoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (arrCode != null ? arrCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof Arrival)) {
            return false;
        }
        Arrival other = (Arrival) object;
        if ((this.arrCode == null && other.arrCode != null)
                || (this.arrCode != null && !this.arrCode.equals(other.arrCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.terasoluna.tourreservation.domain.model.Arrival[ arrCode="
                + arrCode + " ]";
    }

}
