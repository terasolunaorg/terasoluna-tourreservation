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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.TextField;

public class ReservationReportPdfStamperViewTest {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd");

    private static final int OUTPUT_BYTE_ARRAY_INITIAL_SIZE = 4096;

    private static final String RESERVATION_REPORT_PDF_PLACE = "src/main/resources/reports/reservationReport.pdf";

    private static final float REFERENCE_NAME_VARIABLE_FONTSIZE = 8.0F;

    private static final float REFERENCE_NAME_DEFAULT_FONTSIZE = 10.5F;

    ReservationReportPdfStamperView reservationReportPdfStamperView;

    @Before
    public void setUp() throws Exception {
        reservationReportPdfStamperView = new ReservationReportPdfStamperView();
    }

    @Test
    public void testGetUrl() {

        reservationReportPdfStamperView.reservationReportPdfUrl = "classpath:reports/reservationReport.pdf";
        String url = reservationReportPdfStamperView.getUrl();
        assertThat(url, is("classpath:reports/reservationReport.pdf"));
    }

    @Test
    public void testMergePdfDocument() throws Exception {

        DownloadPDFOutput downloadPDFOutput = new DownloadPDFOutput();
        downloadPDFOutput.setReferenceName("TERASOLUNA TRAVEL CUSTOMER CENTER");
        downloadPDFOutput.setReferenceEmail("customer@example.com");
        downloadPDFOutput.setReferenceTel("01-2345-6789");
        downloadPDFOutput.setPaymentMethod("Bank Transfer");
        downloadPDFOutput.setPaymentAccount("Current Account：12345678");
        downloadPDFOutput.setChildCount(3);
        downloadPDFOutput.setTourName("【おすすめ】Terasolunaツアー(6泊7日)");
        downloadPDFOutput.setAccomName("TERASOLUNAホテル第一荘");
        downloadPDFOutput.setCustomerKana("キムラ　タロウ");
        downloadPDFOutput.setCustomerTel("111-1111-1111");
        downloadPDFOutput.setAdultUnitPrice(75000);
        downloadPDFOutput.setConductor("Yes");
        downloadPDFOutput.setTourAbs(
                "そこは別天地、静寂と湯けむりの待つ宿へ… 詳しい情報はお取り合わせをお願い致します。");
        downloadPDFOutput.setCustomerAdd("千葉県八千代市上高野");
        downloadPDFOutput.setCustomerJob("プログラマ");
        downloadPDFOutput.setTourDays("7");
        downloadPDFOutput.setCustomerName("木村　太郎");
        downloadPDFOutput.setChildUnitPrice(37500);
        downloadPDFOutput.setDepName("北海道");
        downloadPDFOutput.setArrName("北海道");
        downloadPDFOutput.setCustomerMail("tarou@example.com");
        downloadPDFOutput.setAdultCount(5);
        downloadPDFOutput.setCustomerCode("00000001");
        downloadPDFOutput.setReserveNo("00000001");
        downloadPDFOutput.setRemarks("特になし");
        downloadPDFOutput.setAccomTel("018-123-4567");
        downloadPDFOutput.setCustomerPost("276-0022");
        downloadPDFOutput.setAdultPrice(375000);
        downloadPDFOutput.setChildPrice(112500);
        downloadPDFOutput.setSumPrice(487500);
        downloadPDFOutput.setPaymentTimeLimit("2019/03/24");
        downloadPDFOutput.setReservedDay(SDF.parse("2019/02/21"));
        downloadPDFOutput.setDepDay(SDF.parse("2019/03/31"));
        downloadPDFOutput.setCustomerBirth(SDF.parse("1975/01/05"));
        downloadPDFOutput.setPrintDay(SDF.parse("2019/03/06"));

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("downloadPDFOutput", downloadPDFOutput);
        model.put("downloadPDFName", "reservationReport");

        PdfStamper stamper = new PdfStamper(new PdfReader(RESERVATION_REPORT_PDF_PLACE), new ByteArrayOutputStream(OUTPUT_BYTE_ARRAY_INITIAL_SIZE));

        HttpServletRequest request = new MockHttpServletRequest();

        HttpServletResponse response = new MockHttpServletResponse();

        try {
            reservationReportPdfStamperView.mergePdfDocument(model, stamper,
                    request, response);
        } catch (Exception e) {
            e.printStackTrace();
            fail(); // FAIL when exception is thrown
        }

        AcroFields form = stamper.getAcroFields();
        assertThat(form.getFields().size(), is(35));
        assertThat(form.getField("referenceName"), is(
                "TERASOLUNA TRAVEL CUSTOMER CENTER"));
        // Can't get field properties directly from AcroFields
        AcroFields.Item referenceNameItem = form.getFieldItem("referenceName");
        PdfDictionary referenceNameMerged = referenceNameItem.getMerged(0);
        TextField referenceNameTextField = new TextField(null, null, null);
        form.decodeGenericDictionary(referenceNameMerged,
                referenceNameTextField);
        assertThat(referenceNameTextField.getFontSize(), is(
                REFERENCE_NAME_VARIABLE_FONTSIZE));
        assertThat(form.getField("referenceEmail"), is("customer@example.com"));
        assertThat(form.getField("referenceTel"), is("01-2345-6789"));
        assertThat(form.getField("paymentMethod"), is("Bank Transfer"));
        assertThat(form.getField("paymentAccount"), is(
                "Current Account：12345678"));
        assertThat(form.getField("childCount"), is("3"));
        assertThat(form.getField("tourName"), is("【おすすめ】Terasolunaツアー(6泊7日)"));
        assertThat(form.getField("accomName"), is("TERASOLUNAホテル第一荘"));
        assertThat(form.getField("customerKana"), is("キムラ　タロウ"));
        assertThat(form.getField("customerTel"), is("111-1111-1111"));
        assertThat(form.getField("adultUnitPrice"), is("75000"));
        assertThat(form.getField("reservedDay"), is("2019/02/21"));
        assertThat(form.getField("conductor"), is("Yes"));
        assertThat(form.getField("tourAbs"), is(
                "そこは別天地、静寂と湯けむりの待つ宿へ… 詳しい情報はお取り合わせをお願い致します。"));
        assertThat(form.getField("customerAdd"), is("千葉県八千代市上高野"));
        assertThat(form.getField("customerJob"), is("プログラマ"));
        assertThat(form.getField("tourDays"), is("7"));
        assertThat(form.getField("depDay"), is("2019/03/31"));
        assertThat(form.getField("customerName"), is("木村　太郎"));
        assertThat(form.getField("childUnitPrice"), is("37500"));
        assertThat(form.getField("depName"), is("北海道"));
        assertThat(form.getField("customerBirth"), is("1975/01/05"));
        assertThat(form.getField("arrName"), is("北海道"));
        assertThat(form.getField("customerMail"), is("tarou@example.com"));
        assertThat(form.getField("adultCount"), is("5"));
        assertThat(form.getField("customerCode"), is("00000001"));
        assertThat(form.getField("reserveNo"), is("00000001"));
        assertThat(form.getField("remarks"), is("特になし"));
        assertThat(form.getField("accomTel"), is("018-123-4567"));
        assertThat(form.getField("customerPost"), is("276-0022"));
        assertThat(form.getField("printDay"), is("2019/03/06"));
        assertThat(form.getField("adultPrice"), is("375000"));
        assertThat(form.getField("childPrice"), is("112500"));
        assertThat(form.getField("sumPrice"), is("487500"));
        assertThat(form.getField("paymentTimeLimit"), is("2019/03/24"));

        Object pdfStamperImp = ReflectionTestUtils.getField(stamper, "stamper");
        assertThat((boolean) ReflectionTestUtils.getField(pdfStamperImp,
                "flat"), is(true));
        assertThat((boolean) ReflectionTestUtils.getField(pdfStamperImp,
                "flatFreeText"), is(true));

        assertThat(response.getHeader("Content-Disposition"), is(
                "attachment; filename=reservationReport.pdf"));
    }

    @Test
    public void testMergePdfDocumentDefaultFontsize() throws Exception {

        DownloadPDFOutput downloadPDFOutput = new DownloadPDFOutput();
        downloadPDFOutput.setReferenceName("TERASOLUNA TRAVEL");
        downloadPDFOutput.setReservedDay(SDF.parse("2019/02/21"));
        downloadPDFOutput.setDepDay(SDF.parse("2019/03/31"));
        downloadPDFOutput.setCustomerBirth(SDF.parse("1975/01/05"));
        downloadPDFOutput.setPrintDay(SDF.parse("2019/03/06"));

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("downloadPDFOutput", downloadPDFOutput);
        model.put("downloadPDFName", "reservationReport");

        PdfStamper stamper = new PdfStamper(new PdfReader(RESERVATION_REPORT_PDF_PLACE), new ByteArrayOutputStream(OUTPUT_BYTE_ARRAY_INITIAL_SIZE));

        HttpServletRequest request = new MockHttpServletRequest();

        HttpServletResponse response = new MockHttpServletResponse();

        try {
            reservationReportPdfStamperView.mergePdfDocument(model, stamper,
                    request, response);
        } catch (Exception e) {
            e.printStackTrace();
            fail(); // FAIL when exception is thrown
        }

        AcroFields form = stamper.getAcroFields();
        assertThat(form.getField("referenceName"), is("TERASOLUNA TRAVEL"));
        // Can't get field properties directly from AcroFields
        AcroFields.Item referenceNameItem = form.getFieldItem("referenceName");
        PdfDictionary referenceNameMerged = referenceNameItem.getMerged(0);
        TextField referenceNameTextField = new TextField(null, null, null);
        form.decodeGenericDictionary(referenceNameMerged,
                referenceNameTextField);
        assertThat(referenceNameTextField.getFontSize(), is(
                REFERENCE_NAME_DEFAULT_FONTSIZE));
    }

    @Test
    public void testPrepareResponse() {

        HttpServletRequest request = new MockHttpServletRequest();

        HttpServletResponse response = new MockHttpServletResponse();

        reservationReportPdfStamperView.prepareResponse(request, response);

        // Pragma and Cache-Control are responsibility of super class
        assertThat(response.getHeader("Pragma"), is("private"));
        assertThat(response.getHeader("Cache-Control"), is(
                "private, must-revalidate"));

        // CharacterEncoding is responsibility of ReservationReportPdfStamperView class
        assertThat(response.getCharacterEncoding(), is(StandardCharsets.UTF_8
                .name()));
    }
}
