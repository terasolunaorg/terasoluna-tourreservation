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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveService;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoSharedService;
import org.terasoluna.tourreservation.domain.service.userdetails.ReservationUserDetails;

public class ManageReservationHelperTest {

    Authentication authentication;

    ReservationUserDetails userDetails;

    ManageReservationHelper manageReservationFacade;

    ReserveService reserveService;

    TourInfoSharedService tourInfoSharedService;

    SecurityContext securityContext;

    @Before
    public void setUp() throws Exception {
        manageReservationFacade = new ManageReservationHelper();

        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);
        userDetails = new ReservationUserDetails(new Customer("xxxx"));

        reserveService = mock(ReserveService.class);
        tourInfoSharedService = mock(TourInfoSharedService.class);

        manageReservationFacade.reserveService = reserveService;
        manageReservationFacade.tourInfoSharedService = tourInfoSharedService;

    }

    @Test
    public void testList01() {
        TourInfo tour1 = new TourInfo("01");
        TourInfo tour2 = new TourInfo("02");
        Reserve reserve1 = new Reserve("001");
        Reserve reserve2 = new Reserve("002");
        reserve1.setTourInfo(tour1);
        reserve2.setTourInfo(tour2);
        tour1.setTourDays(2);
        tour2.setTourDays(4);
        List<Reserve> reserves = Arrays.asList(reserve1, reserve2);

        when(reserveService.findAllByCustomerCode("xxxx")).thenReturn(reserves);
        when(tourInfoSharedService.isOverPaymentLimit(tour1)).thenReturn(false);
        when(tourInfoSharedService.isOverPaymentLimit(tour2)).thenReturn(true);

        List<ReserveRowOutput> result = manageReservationFacade.list(
                userDetails);
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(2));
        ReserveRowOutput o1 = result.get(0);
        assertThat(o1.getLimitExceeding(), is(false));
        assertThat(o1.getReserve(), is(reserve1));
        assertThat(o1.getTourDays(), is("2"));
        ReserveRowOutput o2 = result.get(1);
        assertThat(o2.getLimitExceeding(), is(true));
        assertThat(o2.getReserve(), is(reserve2));
        assertThat(o2.getTourDays(), is("4"));

    }

}
