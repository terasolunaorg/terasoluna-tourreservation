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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "age")
public class Age implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "age_code")
    private String ageCode;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "age_name")
    private String ageName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "age_rate")
    private int ageRate;

    public Age() {
    }

    public Age(String ageCode) {
        this.ageCode = ageCode;
    }

    public Age(String ageCode, String ageName, int ageRate) {
        this.ageCode = ageCode;
        this.ageName = ageName;
        this.ageRate = ageRate;
    }

    public String getAgeCode() {
        return ageCode;
    }

    public void setAgeCode(String ageCode) {
        this.ageCode = ageCode;
    }

    public String getAgeName() {
        return ageName;
    }

    public void setAgeName(String ageName) {
        this.ageName = ageName;
    }

    public int getAgeRate() {
        return ageRate;
    }

    public void setAgeRate(int ageRate) {
        this.ageRate = ageRate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ageCode != null ? ageCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof Age)) {
            return false;
        }
        Age other = (Age) object;
        if ((this.ageCode == null && other.ageCode != null)
                || (this.ageCode != null && !this.ageCode.equals(other.ageCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.terasoluna.tourreserve.app.domain.Age[ ageCode=" + ageCode
                + " ]";
    }

}
