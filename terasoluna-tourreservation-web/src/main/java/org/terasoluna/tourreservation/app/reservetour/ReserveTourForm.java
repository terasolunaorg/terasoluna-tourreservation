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
package org.terasoluna.tourreservation.app.reservetour;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Tour reserve form object.
 */
@Data
public class ReserveTourForm implements Serializable {

    /**
     * serialVersion.
     */
    private static final long serialVersionUID = -6732565610738816899L;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer adultCount;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer childCount;

    @Size(min = 0, max = 80)
    private String remarks;

}
