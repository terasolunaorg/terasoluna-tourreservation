/*
 * Copyright (C) 2013-2017 NTT DATA Corporation
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.tourreservation.app.common.security.UserDetailsUtils;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveService;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveTourInput;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveTourOutput;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateSharedSerivce;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoSharedService;
import org.terasoluna.tourreservation.domain.service.userdetails.ReservationUserDetails;

@Component
public class ReserveTourHelper {

	@Inject
	PriceCalculateSharedSerivce priceCalculateService;

	@Inject
	ReserveService reserveService;

	@Inject
	TourInfoSharedService tourInfoSharedService;

	@Inject
	Mapper dozerBeanMapper;

	/**
	 * Fetches detailed information of a particular tour (associated entities
	 * are also fetched)
	 * 
	 * @param form
	 * @return
	 */
	public TourDetailOutput findTourDetail(Authentication auth, ReserveTourForm form) {
		TourDetailOutput output = new TourDetailOutput();
		TourInfo tourInfo = tourInfoSharedService.findOne(form.getTourCode());

		PriceCalculateOutput priceCalculateOutput = priceCalculateService
				.calculatePrice(tourInfo.getBasePrice(), form.getAdultCount(),
						form.getChildCount());
		output.setTourInfo(tourInfo);
		output.setPriceCalculateOutput(priceCalculateOutput);
		ReservationUserDetails userDetails = UserDetailsUtils.getUserDetails(auth);
		
		if (userDetails != null) {
			output.setCustomer(userDetails.getCustomer());
		}
		return output;
	}

	/**
	 * makes a reservation
	 * 
	 * @param tourDetailForm
	 * @return
	 * @throws BusinessException
	 */
	public ReserveTourOutput reserve(Authentication auth, ReserveTourForm tourReserveForm)
			throws BusinessException {

		ReserveTourInput input = dozerBeanMapper.map(tourReserveForm,
				ReserveTourInput.class);

		Customer customer = UserDetailsUtils.getUserDetails(auth).getCustomer();
		input.setCustomer(customer);
		ReserveTourOutput tourReserveOutput = reserveService.reserve(input);
		return tourReserveOutput;
	}

}
