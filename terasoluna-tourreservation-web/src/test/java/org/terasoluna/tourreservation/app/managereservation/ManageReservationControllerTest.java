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

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.terasoluna.gfw.common.codelist.i18n.I18nCodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.service.reserve.ReservationUpdateInput;
import org.terasoluna.tourreservation.domain.service.reserve.ReservationUpdateOutput;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveService;
import org.terasoluna.tourreservation.domain.service.userdetails.ReservationUserDetails;

public class ManageReservationControllerTest {

    MockMvc mockMvc;

    ManageReservationController manageReservationController;

    ManageReservationHelper manageReservationHelper;

    ReserveService reserveService;

    Mapper beanMapper;

    ReservationUserDetails userDetails;

    @Before
    public void setupForm() {

        // instantiate the controller to test
        manageReservationController = new ManageReservationController();

        // other members instantiation and assignment
        manageReservationHelper = mock(ManageReservationHelper.class);
        manageReservationHelper.existenceCodeList = mock(I18nCodeList.class);
        beanMapper = new DozerBeanMapper();
        reserveService = mock(ReserveService.class);
        userDetails = mock(ReservationUserDetails.class);
        manageReservationController.manageReservationHelper = manageReservationHelper;
        manageReservationController.beanMapper = beanMapper;
        manageReservationController.reserveService = reserveService;

        // build
        mockMvc = MockMvcBuilders.standaloneSetup(manageReservationController)
                .setCustomArgumentResolvers(
                        new AuthenticationPrincipalArgumentResolver()).build();
    }

    @Test
    public void testManageReservationListSuccess() {

        // Prepare get request
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/reservations/me");

        // Set mock behavior for helper method
        when(manageReservationHelper.list(userDetails)).thenReturn(
                new ArrayList<ReserveRowOutput>());

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managereservation/list"));
            results.andExpect(model().attribute("rows", notNullValue()));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown

    }

    @Test
    public void testManageReservationDetailSuccess() {

        // Prepare get request
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/reservations/123");

        // Set mock behavior for helper method
        when(manageReservationHelper.findDetail("123")).thenReturn(
                new ReservationDetailOutput());

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managereservation/detailForm"));
            results.andExpect(model().attribute("output", notNullValue()));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown

    }

    @Test
    public void testManageReservationUpdateFormSuccess() {
        // Prepare get request
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/reservations/123/update").param("form", "");

        // Set mock behavior for service method
        when(reserveService.findOne("123")).thenReturn(new Reserve());

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managereservation/updateForm"));
            results.andExpect(model().attribute("reserve", notNullValue()));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testManageReservationRedoSuccess() {
        // Prepare post request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/reservations/123/update").param("redo", "");

        // Set mock behavior for service method
        when(reserveService.findOne("123")).thenReturn(new Reserve());

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managereservation/updateForm"));
            results.andExpect(model().attribute("reserve", notNullValue()));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown

    }

    @Test
    public void testManageReservationUpdateConfirmSuccess() {
        // Prepare post request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/reservations/123/update").param("confirm", "");

        // Set mock behavior for helper method
        when(manageReservationHelper.findDetail(eq("123"),
                (ManageReservationForm) anyObject())).thenReturn(
                        new ReservationDetailOutput());

        // Set form data to pass @Validated check
        postRequest.param("reserveNo", "123");
        postRequest.param("adultCount", "1");
        postRequest.param("childCount", "2");

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managereservation/updateConfirm"));
            results.andExpect(model().attribute("output", notNullValue()));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testManageReservationUpdateConfirmFail() {
        // Prepare post request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/reservations/123/update").param("confirm", "");

        // Set mock behavior for helper method
        when(reserveService.findOne("123")).thenReturn(new Reserve());

        // Set form data to fail the @Validated check
        postRequest.param("reserveNo", "123");
        postRequest.param("adultCount", "10"); // Error
        postRequest.param("childCount", "10"); // Error

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managereservation/updateForm"));
            results.andExpect(model().attribute("reserve", notNullValue()));
            results.andExpect(model().hasErrors());
            results.andExpect(model().attributeErrorCount(
                    "manageReservationForm", 2));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testManageReservationUpdateSuccess() {
        // Prepare post request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/reservations/123/update");

        // Set mock behavior for helper method
        when(reserveService.update((ReservationUpdateInput) anyObject()))
                .thenReturn(new ReservationUpdateOutput());

        // Set form data to pass @Validated check
        postRequest.param("reserveNo", "123");
        postRequest.param("adultCount", "1");
        postRequest.param("childCount", "2");

        try {
            ResultActions results = mockMvc.perform(postRequest);
            // check redirect success
            results.andExpect(status().isFound());
            results.andExpect(view().name(
                    "redirect:/reservations/{reserveNo}/update?complete"));
            results.andExpect(flash().attribute("output", notNullValue()));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testManageReservationUpdateFail() {
        // Prepare post request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/reservations/123/update");

        // Set mock behavior for helper method
        // Set mock behavior for service method
        when(reserveService.findOne("123")).thenReturn(new Reserve());

        // Set form data to pass @Validated check
        postRequest.param("reserveNo", "123");
        postRequest.param("adultCount", "10");
        postRequest.param("childCount", "20");

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managereservation/updateForm"));
            results.andExpect(model().attribute("reserve", notNullValue()));
            results.andExpect(model().hasErrors());
            results.andExpect(model().attributeErrorCount(
                    "manageReservationForm", 2));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testManageReservationComplete() {
        // Prepare get request
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/reservations/123/update").param("complete", "");

        // No Logic testing here
        // this will just test the request mapping part

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managereservation/updateComplete"));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testManageReservationDownloadPDF() {
        // Prepare get request
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/reservations/123/pdf");

        // Set mock behavior for helper method
        when(manageReservationHelper.createPDF("123", Locale.ENGLISH))
                .thenReturn(new DownloadPDFOutput());
        when(manageReservationHelper.existenceCodeList.asMap(Locale.ENGLISH))
                .thenReturn(new LinkedHashMap<String, String>() {
                    {
                        put("0", "No");
                        put("1", "Yes");
                    }
                });

        // No Logic testing here
        // this will just test the request mapping part

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managereservation/report"));
            results.andExpect(model().attribute("downloadPDFOutputList", IsNull
                    .notNullValue()));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testManageReservationCancelComplete() {
        // Prepare get request
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/reservations/123/cancel").param("complete", "");

        // No Logic testing here
        // this will just test the request mapping part

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managereservation/cancelComplete"));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testManageReservationBackList() {
        // Prepare get request
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post(
                "/reservations/123/cancel").param("backTolist", "");

        // No Logic testing here
        // this will just test the request mapping part

        try {
            ResultActions results = mockMvc.perform(getRequest);
            // Test Redirect
            results.andExpect(status().isFound());
            results.andExpect(view().name("redirect:/reservations/me"));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testManageReservationUpdateBackList() {
        // Prepare post request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/reservations/123/update").param("backTolist", "");

        // No Logic testing here
        // this will just test the request mapping part

        try {
            ResultActions results = mockMvc.perform(postRequest);
            // Test Redirect
            results.andExpect(status().isFound());
            results.andExpect(view().name("redirect:/reservations/me"));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testManageReservationCancelConfirm() {
        // Prepare post request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.get(
                "/reservations/123/cancel").param("confirm", "");

        // Set mock behavior for helper method
        when(manageReservationHelper.findDetail("123")).thenReturn(
                new ReservationDetailOutput());

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managereservation/cancelConfirm"));
            results.andExpect(model().attribute("output", notNullValue()));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testManageReservationCancelSuccess() {
        // Prepare post request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/reservations/123/cancel");
        // Set mock behavior for service method
        Mockito.doNothing().when(reserveService).cancel("123");

        try {
            ResultActions results = mockMvc.perform(postRequest);
            // Test Redirect
            results.andExpect(status().isFound());
            results.andExpect(view().name(
                    "redirect:/reservations/{reserveNo}/cancel?complete"));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testManageReservationCancelFailByBusinessException() {
        // Prepare post request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/reservations/123/cancel");
        // Set mock behavior for service and helper method
        Mockito.doThrow(new BusinessException("")).when(reserveService).cancel(
                "123");
        when(manageReservationHelper.findDetail("123")).thenReturn(
                new ReservationDetailOutput());

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managereservation/cancelConfirm"));
            results.andExpect(model().attribute("output", notNullValue()));
            // Exception message existence check
            results.andExpect(model().attribute("resultMessages",
                    notNullValue()));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }
}
