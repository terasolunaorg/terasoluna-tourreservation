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

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveService;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveTourInput;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveTourOutput;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateSharedService;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoSharedService;
import org.terasoluna.tourreservation.domain.service.userdetails.ReservationUserDetails;

@Component
public class ReserveTourHelper {

    @Inject
    PriceCalculateSharedService priceCalculateService;

    @Inject
    ReserveService reserveService;

    @Inject
    TourInfoSharedService tourInfoSharedService;

    @Inject
    Mapper beanMapper;

    /**
     * Fetches detailed information of a particular tour (associated entities are also fetched)
     * @param form
     * @return
     */
    public TourDetailOutput findTourDetail(ReservationUserDetails userDetails,
            String tourCode, ReserveTourForm form) {
        TourInfo tourInfo = tourInfoSharedService.findOne(tourCode);

        PriceCalculateOutput priceCalculateOutput = priceCalculateService
                .calculatePrice(tourInfo.getBasePrice(), form.getAdultCount(),
                        form.getChildCount());

        TourDetailOutput output = new TourDetailOutput();
        output.setTourInfo(tourInfo);
        output.setPriceCalculateOutput(priceCalculateOutput);

        if (userDetails != null) {
            output.setCustomer(userDetails.getCustomer());
        }
        return output;
    }

    /**
     * makes a reservation
     * @param userDetails
     * @param tourReserveForm
     * @return
     * @throws BusinessException
     */
    public ReserveTourOutput reserve(ReservationUserDetails userDetails,
            String tourCode,
            ReserveTourForm tourReserveForm) throws BusinessException {

        ReserveTourInput input = beanMapper.map(tourReserveForm,
                ReserveTourInput.class);
        input.setTourCode(tourCode);

        Customer customer = userDetails.getCustomer();
        input.setCustomer(customer);
        ReserveTourOutput tourReserveOutput = reserveService.reserve(input);
        return tourReserveOutput;
    }

}
