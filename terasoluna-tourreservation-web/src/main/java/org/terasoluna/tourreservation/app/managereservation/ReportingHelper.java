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

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.date.DateFactory;
import org.terasoluna.tourreservation.app.common.constants.MessageId;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateSharedSerivce;

@Component
public class ReportingHelper {

    @Inject
    protected ManageReservationHelper manageReservationService;

    @Inject
    protected PriceCalculateSharedSerivce priceCalculateService;

    @Inject
    protected MessageSource messageSource;

    @Inject
    protected DateFactory dateFactory;

    public DownloadPDFOutput createPDF(String reserveNo) {

        ReservationDetailOutput reserveDetailOutput = manageReservationService
                .findDetail(reserveNo);

        // if (reserveDetailOutput == null) {
        // TODO
        // throw new Exception("error.common.00008:予約情報が見つかりません。");

        // }

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
            // TODO use propertyUtil to fetch the format
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
        downloadPDFOutput.setPaymentTimeLimit(paymentTimeLimit);

        // 料金を計算するクラス(共通処理：CP0009)を実行する。
        PriceCalculateOutput priceCalcResult = priceCalculateService
                .calculatePrice(reserveDetailOutput.getReserve().getTourInfo()
                        .getBasePrice(), reserveDetailOutput.getReserve()
                        .getAdultCount(), reserveDetailOutput.getReserve()
                        .getChildCount());

        // 料金情報を出力値に設定する。
        downloadPDFOutput
                .setAdultUnitPrice(priceCalcResult.getAdultUnitPrice());
        downloadPDFOutput
                .setChildUnitPrice(priceCalcResult.getChildUnitPrice());
        downloadPDFOutput.setAdultPrice(priceCalcResult.getAdultPrice());
        downloadPDFOutput.setChildPrice(priceCalcResult.getChildPrice());
        downloadPDFOutput.setSumPrice(priceCalcResult.getSumPrice());

        // 顧客情報を出力値に設定する。
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

        // 印刷日を出力値に設定する。
        downloadPDFOutput.setPrintDay(dateFactory.newDate());

        // リストに帳票出力を加える。
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
