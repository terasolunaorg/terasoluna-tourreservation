/*
 * Copyright (C) 2013-2016 NTT DATA Corporation
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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "customer_code")
    private String customerCode;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "customer_name")
    private String customerName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "customer_kana")
    private String customerKana;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "customer_pass")
    private String customerPass;

    @Basic(optional = false)
    @NotNull
    @Column(name = "customer_birth")
    @Temporal(TemporalType.DATE)
    private Date customerBirth;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "customer_job")
    private String customerJob;

    @Size(max = 300)
    @Column(name = "customer_mail")
    private String customerMail;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "customer_tel")
    private String customerTel;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "customer_post")
    private String customerPost;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "customer_add")
    private String customerAdd;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Reserve> reserveList;

    public Customer() {
    }

    public Customer(String customerCode) {
        this.customerCode = customerCode;
    }

    public Customer(String customerCode, String customerName,
            String customerKana, String customerPass, Date customerBirth,
            String customerJob, String customerTel, String customerPost,
            String customerAdd) {
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.customerKana = customerKana;
        this.customerPass = customerPass;
        this.customerBirth = customerBirth;
        this.customerJob = customerJob;
        this.customerTel = customerTel;
        this.customerPost = customerPost;
        this.customerAdd = customerAdd;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerKana() {
        return customerKana;
    }

    public void setCustomerKana(String customerKana) {
        this.customerKana = customerKana;
    }

    public String getCustomerPass() {
        return customerPass;
    }

    public void setCustomerPass(String customerPass) {
        this.customerPass = customerPass;
    }

    public Date getCustomerBirth() {
        return customerBirth;
    }

    public void setCustomerBirth(Date customerBirth) {
        this.customerBirth = customerBirth;
    }

    public String getCustomerJob() {
        return customerJob;
    }

    public void setCustomerJob(String customerJob) {
        this.customerJob = customerJob;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getCustomerPost() {
        return customerPost;
    }

    public void setCustomerPost(String customerPost) {
        this.customerPost = customerPost;
    }

    public String getCustomerAdd() {
        return customerAdd;
    }

    public void setCustomerAdd(String customerAdd) {
        this.customerAdd = customerAdd;
    }

    public List<Reserve> getReserveList() {
        return reserveList;
    }

    public void setReserveList(List<Reserve> reserveList) {
        this.reserveList = reserveList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerCode != null ? customerCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerCode == null && other.customerCode != null)
                || (this.customerCode != null && !this.customerCode.equals(
                        other.customerCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.terasoluna.tourreservation.domain.model.Customer[ customerCode="
                + customerCode + " ]";
    }

}
