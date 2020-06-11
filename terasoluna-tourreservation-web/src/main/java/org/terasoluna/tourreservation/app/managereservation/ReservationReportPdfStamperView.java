/*
 * Copyright(c) 2013 NTT DATA Corporation.
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

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfStamperView;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfStamper;

@Component
public class ReservationReportPdfStamperView extends AbstractPdfStamperView {

    private static final String DOWNLOAD_PDF_EXTENSION = ".pdf";

    private static final int REFERENCE_NAME_MAXIMUM_VALUE_WITH_NORMAL_FONTSIZE = 30;

    private static final float REFERENCE_NAME_VARIABLE_FONTSIZE = 8.0F;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @Value("${reservation.reportPdfUrl}")
    String reservationReportPdfUrl;

    @Override
    public String getUrl() {
        return reservationReportPdfUrl;
    }

    @Override
    protected void mergePdfDocument(Map<String, Object> model,
            PdfStamper stamper, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        DownloadPDFOutput downloadPDFOutput = (DownloadPDFOutput) model.get(
                "downloadPDFOutput");
        String downloadPDFName = (String) model.get("downloadPDFName");

        AcroFields form = stamper.getAcroFields();
        String referenceName = downloadPDFOutput.getReferenceName();
        if (referenceName.getBytes(StandardCharsets.UTF_8
                .name()).length >= REFERENCE_NAME_MAXIMUM_VALUE_WITH_NORMAL_FONTSIZE) {
            form.setFieldProperty("referenceName", "textsize",
                    REFERENCE_NAME_VARIABLE_FONTSIZE, null);
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
        form.setField("reservedDay", sdf.format(downloadPDFOutput
                .getReservedDay()));
        form.setField("conductor", downloadPDFOutput.getConductor());
        form.setField("tourAbs", downloadPDFOutput.getTourAbs());
        form.setField("customerAdd", downloadPDFOutput.getCustomerAdd());
        form.setField("customerJob", downloadPDFOutput.getCustomerJob());
        form.setField("tourDays", downloadPDFOutput.getTourDays());
        form.setField("depDay", sdf.format(downloadPDFOutput.getDepDay()));
        form.setField("customerName", downloadPDFOutput.getCustomerName());
        form.setField("childUnitPrice", String.valueOf(downloadPDFOutput
                .getChildUnitPrice()));
        form.setField("depName", downloadPDFOutput.getDepName());
        form.setField("customerBirth", sdf.format(downloadPDFOutput
                .getCustomerBirth()));
        form.setField("arrName", downloadPDFOutput.getArrName());
        form.setField("customerMail", downloadPDFOutput.getCustomerMail());
        form.setField("adultCount", String.valueOf(downloadPDFOutput
                .getAdultCount()));
        form.setField("customerCode", downloadPDFOutput.getCustomerCode());
        form.setField("reserveNo", downloadPDFOutput.getReserveNo());
        form.setField("remarks", downloadPDFOutput.getRemarks());
        form.setField("accomTel", downloadPDFOutput.getAccomTel());
        form.setField("customerPost", downloadPDFOutput.getCustomerPost());
        form.setField("printDay", sdf.format(downloadPDFOutput.getPrintDay()));
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

        response.setHeader("Content-Disposition", "attachment; filename="
                + downloadPDFName + DOWNLOAD_PDF_EXTENSION);
    }

    @Override
    protected void prepareResponse(HttpServletRequest request,
            HttpServletResponse response) {
        super.prepareResponse(request, response);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    }

}
