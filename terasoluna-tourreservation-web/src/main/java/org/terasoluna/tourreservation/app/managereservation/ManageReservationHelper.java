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
package org.terasoluna.tourreservation.app.managereservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.terasoluna.tourreservation.app.common.constants.MessageId;
import org.terasoluna.tourreservation.app.common.security.UserDetailsUtils;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveService;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateSharedSerivce;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoSharedService;

@Component
public class ManageReservationHelper {

    @Inject
    protected TourInfoSharedService tourInfoSharedService;

    @Inject
    protected PriceCalculateSharedSerivce priceCalculateService;

    @Inject
    protected MessageSource messageSource;

    @Inject
    protected ReserveService reserveService;

    @Inject
    protected Mapper dozerBeanMapper;

    protected String convertNightDays(int days) {
        if (days == 1) {
            return getMessage(MessageId.LABEL_TR_MANAGERESERVATION_DAYTRIP);
        }
        StringBuilder builder = new StringBuilder();
        builder.append(days - 1);
        builder.append(getMessage(MessageId.LABEL_TR_MANAGERESERVATION_NIGHT));
        builder.append(days);
        builder.append(getMessage(MessageId.LABEL_TR_MANAGERESERVATION_DAY));

        return builder.toString();
    }

    public List<ReserveRowOutput> list() {
        // must be logged in
        String customerCode = UserDetailsUtils.getUserDetails().getUsername();

        List<Reserve> reserves = reserveService
                .findByCustomerCode(customerCode);

        List<ReserveRowOutput> rows = new ArrayList<ReserveRowOutput>();
        for (Reserve reservation : reserves) {
            ReserveRowOutput reservationRowOutput = new ReserveRowOutput();
            TourInfo tourInfo = reservation.getTourInfo();

            reservationRowOutput.setLimitExceeding(tourInfoSharedService
                    .isOverPaymentLimitTour(tourInfo));
            reservationRowOutput.setTourDays(tourInfo.getTourDays() + "");// TODO
            reservationRowOutput.setReserve(reservation);
            rows.add(reservationRowOutput);
        }
        return rows;
    }

    /**
     * Fetches detailed information of a particular reservation The pricing information is calculated on the basis of adult
     * count and child count provided at the time of making the reservation. It uses existing values of adult count and child
     * count
     * @param reserveNo
     * @return
     */
    public ReservationDetailOutput findDetail(String reserveNo) {
        Reserve reserve = reserveService.findOne(reserveNo);

        TourInfo info = reserve.getTourInfo();
        int adultCount = reserve.getAdultCount();
        int childCount = reserve.getChildCount();

        ReservationDetailOutput output = new ReservationDetailOutput();

        PriceCalculateOutput price = priceCalculateService.calculatePrice(info
                .getBasePrice(), adultCount, childCount);
        output.setPriceCalculateOutput(price);

        // reserve related
        output.setReserve(reserve);
        output.setCustomer(reserve.getCustomer());

        // payment related
        output.setPaymentTimeLimit(info.getPaymentLimit().toDate());
        output.setLimitExceeding(tourInfoSharedService
                .isOverPaymentLimitTour(info));

        return output;

    }

    /**
     * Fetches detailed information of a particular reservation. This pricing information of a reservation is by using adult
     * count and child count provided as parameters. Existing adult count and child count information is not used.
     * @param form
     * @return
     */
    public ReservationDetailOutput findDetail(ManageReservationForm form) {
        ReservationDetailOutput output = findDetail(form.getReserveNo());
        // re-calculate
        TourInfo info = output.getReserve().getTourInfo();
        PriceCalculateOutput price = priceCalculateService.calculatePrice(info
                .getBasePrice(), form.getAdultCount(), form.getChildCount());
        output.setPriceCalculateOutput(price);
        return output;
    }

    public DownloadPDFOutput createPDF(String reserveNo) {
        DownloadPDFOutput downloadPDFOutput = new DownloadPDFOutput();
        return downloadPDFOutput;
    }

    /**
     * @param code
     * @return String
     */
    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }
}
