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
package org.terasoluna.tourreservation.app.managereservation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ManageReservationForm {

    // validation groups
    public static interface ReservationEdit {
    };

    public static interface ReservationCancel {
    };

    @NotNull
    private String reserveNo;

    @NotNull(groups = ReservationEdit.class)
    @Min(value = 0, groups = ReservationEdit.class)
    @Max(value = 5, groups = ReservationEdit.class)
    private Integer adultCount;

    @NotNull(groups = ReservationEdit.class)
    @Min(value = 0, groups = ReservationEdit.class)
    @Max(value = 5, groups = ReservationEdit.class)
    private Integer childCount;

}
