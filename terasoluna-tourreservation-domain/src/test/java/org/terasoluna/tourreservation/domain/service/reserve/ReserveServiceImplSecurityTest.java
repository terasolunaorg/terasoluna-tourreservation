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
package org.terasoluna.tourreservation.domain.service.reserve;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.tourreservation.domain.model.*;
import org.terasoluna.tourreservation.domain.repository.reserve.ReserveRepository;
import org.terasoluna.tourreservation.domain.service.userdetails.ReservationUserDetails;

import javax.inject.Inject;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ReserveServiceImplSecurityTest {

    @Inject
    ReserveRepository mockReserveRepository;

    @Inject
    ReserveService reserveService;

    @Inject
    JodaTimeDateFactory dateFactory;

    @Before
    @After
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Before
    @After
    public void resetMocks() {
        reset(mockReserveRepository);
    }

    @Test
    public void testFindOneForOwnerReservation() {

        // setup
        {
            setupMockAuthentication("C0000001");
            setupMockReserveRepository("C0000001", "R000000001");
        }

        // test
        Reserve reserve;
        {
            reserve = reserveService.findOne("R000000001");
        }

        // assert
        {
            assertThat(reserve.getReserveNo(), is("R000000001"));
            assertThat(reserve.getCustomer().getCustomerCode(), is("C0000001"));
        }

    }

    @Test
    public void testFindOneForNotFound() {

        // setup
        {
            setupMockAuthentication("C0000001");
        }

        // test
        Reserve reserve;
        {
            reserve = reserveService.findOne("R000000001");
        }

        // assert
        {
            assertThat(reserve, is(nullValue()));
        }

    }

    @Test(expected = AccessDeniedException.class)
    public void testFindOneForOtherOwnerReservation() {

        // setup
        {
            setupMockAuthentication("C0000001");
            setupMockReserveRepository("C0000002", "R000000001");
        }

        // test
        {
            reserveService.findOne("R000000001");
        }

    }

    @Test
    public void testUpdateForOwnerReservation() {

        // setup
        {
            setupMockAuthentication("C0000001");
            setupMockReserveRepository("C0000001", "R000000001");
        }

        // test
        ReservationUpdateOutput output;
        {
            ReservationUpdateInput input = new ReservationUpdateInput();
            input.setReserveNo("R000000001");
            input.setAdultCount(1);
            input.setChildCount(1);
            output = reserveService.update(input);
        }

        // assert
        {
            verify(mockReserveRepository, times(1)).save((Reserve) anyObject());
            assertThat(output.getReserve().getReserveNo(), is("R000000001"));
            assertThat(output.getReserve().getCustomer().getCustomerCode(), is("C0000001"));
        }


    }

    @Test
    public void testUpdateForOtherOwnerReservation() {

        // setup
        {
            setupMockAuthentication("C0000001");
            setupMockReserveRepository("C0000002", "R000000001");
        }

        // test
        {
            ReservationUpdateInput input = new ReservationUpdateInput();
            input.setReserveNo("R000000001");
            try {
                reserveService.update(input);
                fail();
            } catch (AccessDeniedException e) {
            }
        }

        // assert
        {
            verify(mockReserveRepository, never()).save((Reserve) anyObject());
        }
    }

    @Test
    public void testCancelForOwnerReservation() {

        // setup
        {
            setupMockAuthentication("C0000001");
            setupMockReserveRepository("C0000001", "R000000001");
        }

        // test
        {
            reserveService.cancel("R000000001");
        }

        // assert
        {
            verify(mockReserveRepository, times(1)).delete("R000000001");
        }

    }

    @Test
    public void testCancelForOtherOwnerReservation() {

        // setup
        {
            setupMockAuthentication("C0000001");
            setupMockReserveRepository("C0000002", "R000000001");
        }

        // test
        {
            try {
                reserveService.cancel("R000000001");
                fail();
            } catch (AccessDeniedException e) {
            }
        }

        // assert
        {
            verify(mockReserveRepository, never()).delete("R000000001");
        }

    }

    private void setupMockAuthentication(String customerCode) {
        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.isAuthenticated()).thenReturn(true);
        when(mockAuthentication.getPrincipal()).thenReturn(new ReservationUserDetails(new Customer(customerCode)));
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
    }

    private void setupMockReserveRepository(String customerCode, String reserveNo) {
        Reserve reserve = new Reserve(reserveNo);
        reserve.setCustomer(new Customer(customerCode));
        TourInfo tourInfo = new TourInfo();
        tourInfo.setDepDay(dateFactory.newDateTime().plusDays(8).toDate());
        tourInfo.setDeparture(new Departure());
        tourInfo.setArrival(new Arrival());
        reserve.setTourInfo(tourInfo);

        when(mockReserveRepository.findOne(reserveNo)).thenReturn(reserve);
        when(mockReserveRepository.findOneForUpdate(reserveNo)).thenReturn(reserve);
    }

}
