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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.tourreservation.app.common.constants.MessageId;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveService;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateSharedService;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoSharedService;
import org.terasoluna.tourreservation.domain.service.userdetails.ReservationUserDetails;

@Component
public class ManageReservationHelper {

    @Inject
    TourInfoSharedService tourInfoSharedService;

    @Inject
    PriceCalculateSharedService priceCalculateService;

    @Inject
    MessageSource messageSource;

    @Inject
    ReserveService reserveService;

    @Inject
    JodaTimeDateFactory dateFactory;

    @Inject
    Mapper dozerBeanMapper;

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

    public List<ReserveRowOutput> list(ReservationUserDetails userDetails) {
        // must be logged in
        String customerCode = userDetails.getUsername();

        List<Reserve> reserves = reserveService
                .findAllByCustomerCode(customerCode);

        List<ReserveRowOutput> rows = new ArrayList<ReserveRowOutput>();
        for (Reserve reservation : reserves) {
            ReserveRowOutput reservationRowOutput = new ReserveRowOutput();
            TourInfo tourInfo = reservation.getTourInfo();

            reservationRowOutput.setLimitExceeding(tourInfoSharedService
                    .isOverPaymentLimit(tourInfo));
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
                .isOverPaymentLimit(info));

        return output;

    }

    /**
     * Fetches detailed information of a particular reservation. This pricing information of a reservation is by using adult
     * count and child count provided as parameters. Existing adult count and child count information is not used.
     * @param form
     * @return
     */
    public ReservationDetailOutput findDetail(String reserveNo, ManageReservationForm form) {
        ReservationDetailOutput output = findDetail(reserveNo);
        // re-calculate
        TourInfo info = output.getReserve().getTourInfo();
        PriceCalculateOutput price = priceCalculateService.calculatePrice(info
                .getBasePrice(), form.getAdultCount(), form.getChildCount());
        output.setPriceCalculateOutput(price);
        return output;
    }

    public DownloadPDFOutput createPDF(String reserveNo) {
        ReservationDetailOutput reserveDetailOutput = findDetail(reserveNo);

        // assigning display string to conductor on the basis of DB value
        String conductor = null;
        if ("1".equals(reserveDetailOutput.getReserve().getTourInfo()
                .getConductor())) {
            conductor = getMessage(MessageId.LABEL_TR_COMMON_YESMESSAGE);
        } else {
            conductor = getMessage(MessageId.LABEL_TR_COMMON_NOMESSAGE);
        }

        String paymentTimeLimit = null;
        if ("1".equals(reserveDetailOutput.getReserve().getTransfer())) {
            paymentTimeLimit = getMessage(MessageId.LABEL_TR_MANAGERESERVATION_DONE);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(getMessage(MessageId.LABEL_TR_COMMON_DATEPATTERN));
            paymentTimeLimit = sdf.format(reserveDetailOutput
                    .getPaymentTimeLimit());
        }

        DownloadPDFOutput downloadPDFOutput = new DownloadPDFOutput();
        downloadPDFOutput.setReserveNo(reserveNo);
        downloadPDFOutput.setTourName(reserveDetailOutput.getReserve()
                .getTourInfo().getTourName());
        downloadPDFOutput.setReservedDay(reserveDetailOutput.getReserve()
                .getReservedDay());
        downloadPDFOutput.setDepDay(reserveDetailOutput.getReserve()
                .getTourInfo().getDepDay());

        // TODO String.valueOf ?
        downloadPDFOutput.setTourDays(String.valueOf(reserveDetailOutput
                .getReserve().getTourInfo().getTourDays()));
        downloadPDFOutput.setDepName(reserveDetailOutput.getReserve()
                .getTourInfo().getDeparture().getDepName());
        downloadPDFOutput.setArrName(reserveDetailOutput.getReserve()
                .getTourInfo().getArrival().getArrName());
        downloadPDFOutput.setConductor(conductor);
        downloadPDFOutput.setAccomName(reserveDetailOutput.getReserve()
                .getTourInfo().getAccommodation().getAccomName());
        downloadPDFOutput.setAccomTel(reserveDetailOutput.getReserve()
                .getTourInfo().getAccommodation().getAccomTel());
        downloadPDFOutput.setTourAbs(reserveDetailOutput.getReserve()
                .getTourInfo().getTourAbs());
        downloadPDFOutput.setAdultCount(reserveDetailOutput.getReserve()
                .getAdultCount());
        downloadPDFOutput.setChildCount(reserveDetailOutput.getReserve()
                .getChildCount());
        downloadPDFOutput.setRemarks(reserveDetailOutput.getReserve()
                .getRemarks());
        downloadPDFOutput.setPaymentMethod(getMessage(MessageId.LABEL_TR_COMMON_BANKTRANSFER));
        downloadPDFOutput.setPaymentCompanyName(getMessage(MessageId.LABEL_TR_COMMON_PAYMENTCOMPANYNAME));
        downloadPDFOutput.setPaymentAccount(getMessage(MessageId.LABEL_TR_COMMON_SAVINGSACCOUNT));
        downloadPDFOutput.setPaymentTimeLimit(paymentTimeLimit);

        // calculate price
        PriceCalculateOutput priceCalcResult = priceCalculateService
                .calculatePrice(reserveDetailOutput.getReserve().getTourInfo()
                        .getBasePrice(), reserveDetailOutput.getReserve()
                        .getAdultCount(), reserveDetailOutput.getReserve()
                        .getChildCount());

        // set price information
        downloadPDFOutput
                .setAdultUnitPrice(priceCalcResult.getAdultUnitPrice());
        downloadPDFOutput
                .setChildUnitPrice(priceCalcResult.getChildUnitPrice());
        downloadPDFOutput.setAdultPrice(priceCalcResult.getAdultPrice());
        downloadPDFOutput.setChildPrice(priceCalcResult.getChildPrice());
        downloadPDFOutput.setSumPrice(priceCalcResult.getSumPrice());

        // set customer information
        downloadPDFOutput.setCustomerCode(reserveDetailOutput.getCustomer()
                .getCustomerCode());
        downloadPDFOutput.setCustomerKana(reserveDetailOutput.getCustomer()
                .getCustomerKana());
        downloadPDFOutput.setCustomerName(reserveDetailOutput.getCustomer()
                .getCustomerName());
        downloadPDFOutput.setCustomerBirth(reserveDetailOutput.getCustomer()
                .getCustomerBirth());
        downloadPDFOutput.setCustomerJob(reserveDetailOutput.getCustomer()
                .getCustomerJob());
        downloadPDFOutput.setCustomerMail(reserveDetailOutput.getCustomer()
                .getCustomerMail());
        downloadPDFOutput.setCustomerTel(reserveDetailOutput.getCustomer()
                .getCustomerTel());
        downloadPDFOutput.setCustomerPost(reserveDetailOutput.getCustomer()
                .getCustomerPost());
        downloadPDFOutput.setCustomerAdd(reserveDetailOutput.getCustomer()
                .getCustomerAdd());

        // set reference information
        downloadPDFOutput.setReferenceName(getMessage(MessageId.LABEL_TR_COMMON_COMPANYNAME));
        downloadPDFOutput.setReferenceEmail(getMessage(MessageId.LABEL_TR_COMMON_COMPANYEMAIL));
        downloadPDFOutput.setReferenceTel(getMessage(MessageId.LABEL_TR_COMMON_COMPANYTEL));

        // set print date
        downloadPDFOutput.setPrintDay(dateFactory.newDate());

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
