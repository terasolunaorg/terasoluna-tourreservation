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
package org.terasoluna.tourreservation.app.searchtour;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.terasoluna.gfw.common.date.jodatime.DefaultJodaTimeDateFactory;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoSearchCriteria;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoService;

public class SearchTourControllerTest {

    MockMvc mockMvc;

    TourInfoService tourInfoService;

    SearchTourFormDateValidator validator;

    JodaTimeDateFactory dateFactory;

    Mapper beanMapper;

    @Before
    public void setUp() {

        // instantiate the controller to test
        SearchTourController searchTourController = new SearchTourController();

        // other members instantiation and assignment
        tourInfoService = mock(TourInfoService.class);
        validator = new SearchTourFormDateValidator();
        dateFactory = new DefaultJodaTimeDateFactory();

        beanMapper = DozerBeanMapperBuilder.buildDefault();

        searchTourController.tourInfoService = tourInfoService;
        searchTourController.validator = validator;
        searchTourController.dateFactory = dateFactory;
        searchTourController.beanMapper = beanMapper;

        // Assign custom method argument resolver and build
        // This is needed to resolve Pageable method argument
        mockMvc = MockMvcBuilders.standaloneSetup(searchTourController)
                .setCustomArgumentResolvers(
                        new HandlerMethodArgumentResolver() {

                            @Override
                            public boolean supportsParameter(
                                    MethodParameter parameter) {
                                if (parameter.getParameterType().equals(
                                        Pageable.class)) {
                                    return true;
                                }
                                return false;
                            }

                            @Override
                            public Object resolveArgument(
                                    MethodParameter parameter,
                                    ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest,
                                    WebDataBinderFactory binderFactory) throws Exception {

                                return PageRequest.of(0, 50);
                            }
                        }).build();
    }

    /**
     * This method tests the working of method annotated by @ModelAttribute. <br>
     * </p>
     */
    @Test
    public void testSearchForm() {

        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/tours").param("form", "");

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("searchtour/searchForm"));

            // Check the default data set in setupForm method
            // this will test the @ModelAttribute annotation

            DateTime dateTime = dateFactory.newDateTime();
            DateTime nextWeekDate = dateTime.plusWeeks(1);

            results.andExpect(model().attribute("searchTourForm", hasProperty(
                    "depYear", is(nextWeekDate.getYear()))));
            results.andExpect(model().attribute("searchTourForm", hasProperty(
                    "depMonth", is(nextWeekDate.getMonthOfYear()))));
            results.andExpect(model().attribute("searchTourForm", hasProperty(
                    "depDay", is(nextWeekDate.getDayOfMonth()))));

            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    /**
     * This method tests the success case of Search operation. <br>
     * </p>
     */
    @Test
    public void testSearchSuccess() {
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/tours");

        // Set mock behavior for service method
        when(tourInfoService.searchTour(any(TourInfoSearchCriteria.class), any(
                Pageable.class))).thenReturn(
                        new PageImpl<TourInfo>(new ArrayList<TourInfo>()));

        DateTime dateTime = dateFactory.newDateTime();
        DateTime nextWeekDate = dateTime.plusWeeks(1);

        getRequest.param("depYear", String.valueOf(nextWeekDate.getYear()));
        getRequest.param("depMonth", String.valueOf(nextWeekDate
                .getMonthOfYear()));
        getRequest.param("depDay", String.valueOf(nextWeekDate
                .getDayOfMonth()));
        getRequest.param("tourDays", "2");
        getRequest.param("adultCount", "2");
        getRequest.param("childCount", "2");
        getRequest.param("basePrice", "2");
        getRequest.param("depCode", "01");
        getRequest.param("arrCode", "02");

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("searchtour/searchForm"));
            results.andExpect(model().hasNoErrors());
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    /**
     * This method tests the failure case of Search operation due to validation error. This test case will confirm working of @InitiBinder
     * and @Validated annotations <br>
     * </p>
     */
    @Test
    public void testSearchFail() {
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/tours");

        // Set mock behavior for service method
        when(tourInfoService.searchTour(any(TourInfoSearchCriteria.class), any(
                Pageable.class))).thenReturn(
                        new PageImpl<TourInfo>(new ArrayList<TourInfo>()));

        // Set invalid date such that custom date validator will fail
        getRequest.param("depYear", "2000");
        getRequest.param("depMonth", "2");
        getRequest.param("depDay", "30");

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("searchtour/searchForm"));
            results.andExpect(model().hasErrors());
            results.andExpect(model().attributeErrorCount("searchTourForm", 7));
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }
}
