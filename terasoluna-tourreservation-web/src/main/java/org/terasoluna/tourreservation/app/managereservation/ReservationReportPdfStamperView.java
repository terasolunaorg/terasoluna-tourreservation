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
package org.terasoluna.tourreservation.app.managereservation;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfStamperView;

import com.lowagie.text.pdf.AcroFields;

@Component
public class ReservationReportPdfStamperView extends AbstractPdfStamperView {

    @Value("${reservation.report.pdf.path}")
    String reservationReportPdfPath;

    @Override
    protected void mergePdfDocument(Map<String, Object> model,
            com.lowagie.text.pdf.PdfStamper stamper, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        DownloadPDFOutput downloadPDFOutput = (DownloadPDFOutput) model.get(
                "downloadPDFOutput");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        AcroFields form = stamper.getAcroFields();
        String referenceName = downloadPDFOutput.getReferenceName();
        if (referenceName.length() >= 30) {
            form.setFieldProperty("referenceName", "textsize", new Float(8),
                    null);
        }
        form.setField("referenceName", referenceName);
        form.setField("referenceEmail", downloadPDFOutput.getReferenceEmail());
        form.setField("referenceTel", downloadPDFOutput.getReferenceTel());
        form.setField("paymentMethod", downloadPDFOutput.getPaymentMethod());
        form.setField("paymentCompanyName", downloadPDFOutput
                .getPaymentCompanyName());
        form.setField("paymentAccount", downloadPDFOutput.getPaymentAccount());
        form.setField("childCount", String.valueOf(downloadPDFOutput
                .getChildCount()));
        form.setField("tourName", downloadPDFOutput.getTourName());
        form.setField("accomName", downloadPDFOutput.getAccomName());
        form.setField("customerKana", downloadPDFOutput.getCustomerKana());
        form.setField("customerTel", downloadPDFOutput.getCustomerTel());
        form.setField("adultUnitPrice", String.valueOf(downloadPDFOutput
                .getAdultUnitPrice()));
        form.setField("reservedDay", String.valueOf(sdf.format(downloadPDFOutput
                .getReservedDay())));
        form.setField("conductor", downloadPDFOutput.getConductor());
        form.setField("tourAbs", downloadPDFOutput.getTourAbs());
        form.setField("customerAdd", downloadPDFOutput.getCustomerAdd());
        form.setField("customerJob", downloadPDFOutput.getCustomerJob());
        form.setField("tourDays", downloadPDFOutput.getTourDays());
        form.setField("depDay", String.valueOf(sdf.format(downloadPDFOutput
                .getDepDay())));
        form.setField("customerName", downloadPDFOutput.getCustomerName());
        form.setField("childUnitPrice", String.valueOf(downloadPDFOutput
                .getChildUnitPrice()));
        form.setField("depName", downloadPDFOutput.getDepName());
        form.setField("customerBirth", String.valueOf(sdf.format(
                downloadPDFOutput.getCustomerBirth())));
        form.setField("arrName", downloadPDFOutput.getArrName());
        form.setField("customerMail", downloadPDFOutput.getCustomerMail());
        form.setField("adultCount", String.valueOf(downloadPDFOutput
                .getAdultCount()));
        form.setField("customerCode", downloadPDFOutput.getCustomerCode());
        form.setField("reserveNo", downloadPDFOutput.getReserveNo());
        form.setField("remarks", downloadPDFOutput.getRemarks());
        form.setField("accomTel", downloadPDFOutput.getAccomTel());
        form.setField("customerPost", downloadPDFOutput.getCustomerPost());
        form.setField("printDay", String.valueOf(sdf.format(downloadPDFOutput
                .getPrintDay())));
        form.setField("adultPrice", String.valueOf(downloadPDFOutput
                .getAdultPrice()));
        form.setField("childPrice", String.valueOf(downloadPDFOutput
                .getChildPrice()));
        form.setField("sumPrice", String.valueOf(downloadPDFOutput
                .getSumPrice()));
        form.setField("paymentTimeLimit", downloadPDFOutput
                .getPaymentTimeLimit());
        stamper.setFormFlattening(true);
        stamper.setFreeTextFlattening(true);
        stamper.close();

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename="
                + downloadPDFOutput.getPdfName());
    }

    @Override
    public String getUrl() {
        return reservationReportPdfPath;
    }

}
