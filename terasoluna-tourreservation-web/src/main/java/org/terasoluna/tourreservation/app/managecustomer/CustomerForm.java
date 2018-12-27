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
package org.terasoluna.tourreservation.app.managecustomer;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Customer form object.
 */
@Data
public class CustomerForm implements Serializable {

    /**
     * serialVersion.
     */
    private static final long serialVersionUID = -2440173040819204374L;

    @NotEmpty
    @Size(max = 20)
    @Pattern(regexp = "^[ァ-ヶ]+$")
    private String customerKana;

    @NotEmpty
    @Size(max = 20)
    @Pattern(regexp = "[^ -~｡-ﾟ]*")
    private String customerName;

    @NotNull
    @Min(1900)
    private Integer customerBirthYear;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer customerBirthMonth;

    @NotNull
    @Min(1)
    @Max(31)
    private Integer customerBirthDay;

    @NotEmpty
    @Size(max = 40)
    private String customerJob;

    @Email
    @Size(max = 30)
    private String customerMail;

    @NotEmpty
    @Pattern(regexp = "[0-9a-zA-Z]+")
    @Size(min = 4, max = 20)
    private String customerPass;

    @NotEmpty
    @Pattern(regexp = "[0-9a-zA-Z]+")
    @Size(min = 4, max = 20)
    private String customerPassConfirm;

    @Pattern(regexp = "[0-9-]+")
    @Size(min = 10, max = 13)
    private String customerTel;

    @Pattern(regexp = "[0-9]{3}-[0-9]{4}")
    private String customerPost;

    @NotEmpty
    private String customerAdd;

    @Override
    public String toString() {
        return "customerKana=" + customerKana + "&customerName=" + customerName
                + "&customerBirthYear=" + customerBirthYear
                + "&customerBirthMonth=" + customerBirthMonth
                + "&customerBirthDay=" + customerBirthDay + "&customerJob="
                + customerJob + "&customerMail=" + customerMail
                + "&customerPass=" + customerPass + "&customerPassConfirm="
                + customerPassConfirm + "&customerTel=" + customerTel
                + "&customerPost=" + customerPost + "&customerAdd="
                + customerAdd;
    }

}
