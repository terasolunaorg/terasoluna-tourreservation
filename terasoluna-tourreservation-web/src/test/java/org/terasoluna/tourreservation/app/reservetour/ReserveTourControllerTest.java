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

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveTourOutput;
import org.terasoluna.tourreservation.domain.service.userdetails.ReservationUserDetails;

public class ReserveTourControllerTest {

    ReservationUserDetails userDetails;

    MockMvc mockMvc;

    ReserveTourHelper reserveTourHelper;

    ReserveTourController reserveTourController;

    @Before
    public void setupForm() {

        // instantiate the controller to test
        reserveTourController = new ReserveTourController();

        // other members instantiation and assignment
        userDetails = new ReservationUserDetails(new Customer("xxxx"));
        reserveTourHelper = mock(ReserveTourHelper.class);
        reserveTourController.reserveTourHelper = reserveTourHelper;

        // build
        mockMvc = MockMvcBuilders.standaloneSetup(reserveTourController)
                .setCustomArgumentResolvers(
                        new AuthenticationPrincipalArgumentResolver())
                .addFilters(new SecurityContextPersistenceFilter()).build();
    }

    @Test
    public void testReserveFormSuccess() {

        // Prepare get request
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/tours/123/reserve").param("form", "").with(user(userDetails));

        // Set mock behavior for helper method
        when(reserveTourHelper.findTourDetail(any(ReservationUserDetails.class),
                eq("123"), any(ReserveTourForm.class))).thenReturn(
                        new TourDetailOutput());

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("reservetour/reserveForm"));
            results.andExpect(model().attribute("output", notNullValue()));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown

    }

    @Test
    public void testReadFormSuccess() {

        // Prepare get request
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/tours/123").param("form", "").with(user(userDetails));

        // Set mock behavior for helper method
        when(reserveTourHelper.findTourDetail(any(ReservationUserDetails.class),
                eq("123"), any(ReserveTourForm.class))).thenReturn(
                        new TourDetailOutput());

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("reservetour/reserveForm"));
            results.andExpect(model().attribute("output", notNullValue()));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown

    }

    @Test
    public void testReserveTourConfirmSuccess() {

        // Prepare POST request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/tours/123/reserve").param("confirm", "").with(user(
                        userDetails));

        // Set mock behavior for helper method
        when(reserveTourHelper.findTourDetail(any(ReservationUserDetails.class),
                eq("123"), any(ReserveTourForm.class))).thenReturn(
                        new TourDetailOutput());

        // Set form data
        postRequest.param("adultCount", "2");
        postRequest.param("childCount", "2");

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(model().hasNoErrors());
            results.andExpect(model().attribute("output", notNullValue()));
            results.andExpect(view().name("reservetour/reserveConfirm"));

            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testReserveTourConfirmFail() {
        // Prepare POST request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/tours/123/reserve").param("confirm", "").with(user(
                        userDetails));

        // Set mock behavior for helper method
        when(reserveTourHelper.findTourDetail(any(ReservationUserDetails.class),
                eq("123"), any(ReserveTourForm.class))).thenReturn(
                        new TourDetailOutput());

        // Do not Set any form data so that form validation will fail
        // Just perform the request

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("reservetour/reserveForm"));
            results.andExpect(model().hasErrors());
            results.andExpect(model().attributeErrorCount("reserveTourForm",
                    2));
            results.andExpect(model().attribute("output", notNullValue()));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testReserveTourReserveSuccess() {
        // Prepare POST request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/tours/123/reserve").with(user(userDetails));

        // Set mock behavior for helper method
        when(reserveTourHelper.reserve(any(ReservationUserDetails.class), eq(
                "123"), any(ReserveTourForm.class))).thenReturn(
                        new ReserveTourOutput());

        postRequest.param("adultCount", "2");
        postRequest.param("childCount", "2");

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isFound());
            results.andExpect(model().hasNoErrors());
            results.andExpect(flash().attribute("output", notNullValue()));
            results.andExpect(view().name(
                    "redirect:/tours/{tourCode}/reserve?complete"));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testReserveTourReserveFailByBusinessException() {
        // Prepare POST request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/tours/123/reserve").with(user(userDetails));

        // Set mock behavior for helper method
        when(reserveTourHelper.reserve(any(ReservationUserDetails.class), eq(
                "123"), any(ReserveTourForm.class))).thenThrow(
                        new BusinessException(""));
        when(reserveTourHelper.findTourDetail(any(ReservationUserDetails.class),
                eq("123"), any(ReserveTourForm.class))).thenReturn(
                        new TourDetailOutput());

        // Set form data
        postRequest.param("adultCount", "2");
        postRequest.param("childCount", "2");

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(model().hasNoErrors());
            results.andExpect(model().attribute("output", notNullValue()));
            results.andExpect(model().attribute("resultMessages",
                    notNullValue()));
            results.andExpect(view().name("reservetour/reserveForm"));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testReserveComplete() {
        // Prepare get request
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/tours/123/reserve").param("complete", "");

        // No Logic testing here
        // this will just test the request mapping part

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("reservetour/reserveComplete"));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testReserveRedo() {
        // Prepare get request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/tours/123/reserve").param("redo", "");

        // No Logic testing here
        // this will just test the request mapping part

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("reservetour/reserveForm"));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }
}
