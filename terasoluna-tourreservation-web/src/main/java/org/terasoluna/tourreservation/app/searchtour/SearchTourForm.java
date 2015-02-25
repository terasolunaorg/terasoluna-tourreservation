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
package org.terasoluna.tourreservation.app.searchtour;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

@Data
public class SearchTourForm implements Serializable {

    /**
     * serialVersion.
     */
	private static final long serialVersionUID = 6713033250301914074L;

    @NotNull
    private Integer depYear;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer depMonth;

    @NotNull
    @Min(1)
    @Max(31)
    private Integer depDay;

    @NotNull
    private Integer tourDays;

    @NotEmpty
    private String depCode;

    @NotEmpty
    private String arrCode;

    @NotNull
    @Min(0)
    private Integer adultCount;

    @NotNull
    @Min(0)
    private Integer childCount;

    @NotNull
    @Min(0)
    private Integer basePrice;
    
}

