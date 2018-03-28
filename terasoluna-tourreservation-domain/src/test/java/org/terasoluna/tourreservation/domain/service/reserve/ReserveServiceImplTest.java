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
package org.terasoluna.tourreservation.domain.service.reserve;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessageType;
import org.terasoluna.gfw.common.message.ResultMessages;
import static org.terasoluna.gfw.common.message.StandardResultMessageType.*;
import org.terasoluna.gfw.common.sequencer.Sequencer;
import org.terasoluna.tourreservation.domain.common.constants.MessageId;
import org.terasoluna.tourreservation.domain.model.Accommodation;
import org.terasoluna.tourreservation.domain.model.Arrival;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.model.Departure;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.reserve.ReserveRepository;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateSharedService;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoSharedService;

public class ReserveServiceImplTest {

    ReserveServiceImpl reserveService;

    ReserveRepository reserveRepository;

    TourInfoSharedService tourInfoSharedService;

    PriceCalculateSharedService priceCalculateSerivce;

    JodaTimeDateFactory dateFactory;

    Sequencer<String> sequencer;

    DozerBeanMapper beanMapper;

    DateTime now = new DateTime();

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        reserveService = new ReserveServiceImpl();
        reserveRepository = mock(ReserveRepository.class);
        tourInfoSharedService = mock(TourInfoSharedService.class);
        dateFactory = mock(JodaTimeDateFactory.class);
        sequencer = mock(Sequencer.class);
        priceCalculateSerivce = mock(PriceCalculateSharedService.class);
        AuthorizedReserveSharedServiceImpl authorizedReserveSharedService = new AuthorizedReserveSharedServiceImpl();
        authorizedReserveSharedService.reserveRepository = reserveRepository;

        beanMapper = new DozerBeanMapper();
        List<String> mappingFiles = new ArrayList<String>();
        mappingFiles.add("META-INF/dozer/domain-mapping.xml");
        beanMapper.setMappingFiles(mappingFiles);

        reserveService.reserveRepository = reserveRepository;
        reserveService.tourInfoSharedService = tourInfoSharedService;
        reserveService.dateFactory = dateFactory;
        reserveService.reserveNoSeq = sequencer;
        reserveService.priceCalculateService = priceCalculateSerivce;
        reserveService.authorizedReserveSharedService = authorizedReserveSharedService;
        reserveService.beanMapper = beanMapper;

        when(dateFactory.newDateTime()).thenReturn(now);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindOne01() {
        Reserve reserve = new Reserve();
        when(reserveRepository.findOne("foo")).thenReturn(reserve);

        Reserve result = reserveService.findOne("foo");
        assertThat(result, is(reserve));
    }

    @Test
    public void testFindOne02() {
        when(reserveRepository.findOne("foo")).thenReturn(null);

        Reserve result = reserveService.findOne("foo");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void testFindByCustomerCode01() {
        TourInfo tour1 = new TourInfo("01");
        TourInfo tour2 = new TourInfo("02");
        Reserve reserve1 = new Reserve("001");
        Reserve reserve2 = new Reserve("002");
        reserve1.setTourInfo(tour1);
        reserve2.setTourInfo(tour2);
        tour1.setTourDays(2);
        tour2.setTourDays(4);
        List<Reserve> reserves = Arrays.asList(reserve1, reserve2);

        Customer c = new Customer("xxxx");
        when(reserveRepository.findAllByCustomer(c)).thenReturn(reserves);
        when(tourInfoSharedService.isOverPaymentLimit(tour1)).thenReturn(false);
        when(tourInfoSharedService.isOverPaymentLimit(tour2)).thenReturn(true);

        List<Reserve> result = reserveService.findAllByCustomerCode("xxxx");
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(2));
        assertThat(result.get(0), is(reserve1));
        assertThat(result.get(1), is(reserve2));
    }

    @Test
    public void testFindByCustomerCode03() {
        Customer c = new Customer("xxxx");
        when(reserveRepository.findAllByCustomer(c)).thenReturn(
                new ArrayList<Reserve>());
        List<Reserve> result = reserveService.findAllByCustomerCode("xxxx");
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(0));
    }

    @Test
    public void testReserve01() {
        // normal case

        TourInfo tour = new TourInfo("01");
        DateTime depDay = now.withTime(0, 0, 0, 0).plusDays(7); // within limit
        tour.setDepDay(depDay.toDate());
        tour.setTourDays(2);
        tour.setAvaRecMax(10);
        tour.setBasePrice(10000);
        tour.setAccommodation(new Accommodation());
        tour.setDeparture(new Departure());
        tour.setArrival(new Arrival());
        when(tourInfoSharedService.findOne("01")).thenReturn(tour);
        when(tourInfoSharedService.findOneForUpdate("01")).thenReturn(tour);
        when(reserveRepository.countReservedPersonSumByTourInfo(tour))
                .thenReturn(7L); // 1+2+7 <= 10
        when(sequencer.getNext()).thenReturn("123456");

        ReserveTourInput input = new ReserveTourInput();
        input.setAdultCount(1);
        input.setChildCount(2);
        input.setTourCode("01");
        input.setRemarks("aa");

        PriceCalculateOutput priceCalculateOutput = new PriceCalculateOutput();
        priceCalculateOutput.setSumPrice(100000);
        priceCalculateOutput.setAdultCount(1);
        priceCalculateOutput.setChildCount(2);
        when(priceCalculateSerivce.calculatePrice(10000, 1, 2)).thenReturn(
                priceCalculateOutput);

        // normal
        ReserveTourOutput output = reserveService.reserve(input);

        ArgumentCaptor<Reserve> capture = ArgumentCaptor.forClass(
                Reserve.class);
        verify(reserveRepository, atLeast(1)).save(capture.capture());

        Reserve r = capture.getValue();
        assertThat(output.getReserve(), is(r));
        assertThat(r.getAdultCount(), is(1));
        assertThat(r.getChildCount(), is(2));
        assertThat(r.getSumPrice(), is(100000));
        assertThat(r.getTourInfo().getTourCode(), is("01"));
        assertThat(r.getRemarks(), is("aa"));
        assertThat(r.getTransfer(), is(Reserve.NOT_TRANSFERED));
        assertThat(r.getReserveNo(), is("123456"));
        assertThat(r.getReservedDay(), is(now.withTime(0, 0, 0, 0).toDate()));
    }

    @Test(expected = BusinessException.class)
    public void testReserve02() {
        // in case of over payment limit
        TourInfo tour = new TourInfo("01");
        tour.setTourDays(2);
        tour.setAvaRecMax(10);
        tour.setBasePrice(10000);
        tour.setAccommodation(new Accommodation());
        tour.setDeparture(new Departure());
        tour.setArrival(new Arrival());
        when(tourInfoSharedService.isOverPaymentLimit(tour)).thenReturn(true); // !!today is over limit
        tour.setTourDays(2);
        tour.setAvaRecMax(10);
        when(tourInfoSharedService.findOne("01")).thenReturn(tour);
        when(tourInfoSharedService.findOneForUpdate("01")).thenReturn(tour);
        when(reserveRepository.countReservedPersonSumByTourInfo(tour))
                .thenReturn(7L); // 1+2+7 <= 10
        when(sequencer.getNext()).thenReturn("123456");

        ReserveTourInput input = new ReserveTourInput();
        input.setAdultCount(1);
        input.setChildCount(2);
        input.setTourCode("01");
        input.setRemarks("aa");

        PriceCalculateOutput priceCalculateOutput = new PriceCalculateOutput();
        priceCalculateOutput.setSumPrice(100000);
        priceCalculateOutput.setAdultCount(1);
        priceCalculateOutput.setChildCount(2);
        when(priceCalculateSerivce.calculatePrice(10000, 1, 2)).thenReturn(
                priceCalculateOutput);

        try {
            reserveService.reserve(input);
            fail("error route");
        } catch (BusinessException e) {
            ResultMessages messages = e.getResultMessages();
            assertThat(messages.isNotEmpty(), is(true));
            assertThat(messages.getType(), is((ResultMessageType) ERROR));
            assertThat(messages.getList().size(), is(1));
            assertThat(messages.getList().get(0).getCode(), is(
                    MessageId.E_TR_0004));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void testReserve03() {
        // in case of no vacancy
        TourInfo tour = new TourInfo("01");
        tour.setTourDays(2);
        tour.setAvaRecMax(10);
        tour.setBasePrice(10000);
        tour.setAccommodation(new Accommodation());
        tour.setDeparture(new Departure());
        tour.setArrival(new Arrival());
        tour.setTourDays(2);
        tour.setAvaRecMax(10);
        when(tourInfoSharedService.findOne("01")).thenReturn(tour);
        when(tourInfoSharedService.findOneForUpdate("01")).thenReturn(tour);
        when(reserveRepository.countReservedPersonSumByTourInfo(tour))
                .thenReturn(8L); // !!1+2+8 > 10
        when(sequencer.getNext()).thenReturn("123456");

        ReserveTourInput input = new ReserveTourInput();
        input.setAdultCount(1);
        input.setChildCount(2);
        input.setTourCode("01");
        input.setRemarks("aa");

        PriceCalculateOutput priceCalculateOutput = new PriceCalculateOutput();
        priceCalculateOutput.setSumPrice(100000);
        priceCalculateOutput.setAdultCount(1);
        priceCalculateOutput.setChildCount(2);
        when(priceCalculateSerivce.calculatePrice(10000, 1, 2)).thenReturn(
                priceCalculateOutput);

        try {
            reserveService.reserve(input);
            fail("error route");
        } catch (BusinessException e) {
            ResultMessages messages = e.getResultMessages();
            assertThat(messages.isNotEmpty(), is(true));
            assertThat(messages.getType(), is((ResultMessageType) ERROR));
            assertThat(messages.getList().size(), is(1));
            assertThat(messages.getList().get(0).getCode(), is(
                    MessageId.E_TR_0005));
            throw e;
        }
    }

    @Test
    public void testCancel01() {
        Reserve reserve = new Reserve("001");
        reserve.setAdultCount(1);
        reserve.setChildCount(2);

        TourInfo tour = new TourInfo("01");
        reserve.setTourInfo(tour);
        reserve.setTransfer(Reserve.NOT_TRANSFERED);

        when(reserveRepository.findOne("001")).thenReturn(reserve);
        when(reserveRepository.findOneForUpdate("001")).thenReturn(reserve);
        when(tourInfoSharedService.isOverPaymentLimit(tour)).thenReturn(false); // within limit

        reserveService.cancel("001");

        ArgumentCaptor<String> argOfDelete = ArgumentCaptor.forClass(
                String.class);
        verify(reserveRepository, times(1)).delete(argOfDelete.capture());

        assertThat(argOfDelete.getValue(), is("001"));
    }

    @Test(expected = BusinessException.class)
    public void testCancel02() {
        Reserve reserve = new Reserve("001");
        reserve.setAdultCount(1);
        reserve.setChildCount(2);

        TourInfo tour = new TourInfo("01");
        reserve.setTourInfo(tour);
        reserve.setTransfer(Reserve.TRANSFERED); // !!!TRANSFERED

        when(reserveRepository.findOne("001")).thenReturn(reserve);
        when(reserveRepository.findOneForUpdate("001")).thenReturn(reserve);
        when(tourInfoSharedService.isOverPaymentLimit(tour)).thenReturn(false); // within limit

        try {
            reserveService.cancel("001");
            fail("error route");
        } catch (BusinessException e) {
            ResultMessages messages = e.getResultMessages();
            assertThat(messages.isNotEmpty(), is(true));
            assertThat(messages.getType(), is((ResultMessageType) ERROR));
            assertThat(messages.getList().size(), is(1));
            assertThat(messages.getList().get(0).getCode(), is(
                    MessageId.E_TR_0001));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void testCancel03() {
        Reserve reserve = new Reserve("001");
        reserve.setAdultCount(1);
        reserve.setChildCount(2);

        TourInfo tour = new TourInfo("01");
        reserve.setTourInfo(tour);
        reserve.setTransfer(Reserve.NOT_TRANSFERED);

        when(reserveRepository.findOne("001")).thenReturn(reserve);
        when(reserveRepository.findOneForUpdate("001")).thenReturn(reserve);
        when(tourInfoSharedService.isOverPaymentLimit(tour)).thenReturn(true); // !!!over limit

        try {
            reserveService.cancel("001");
            fail("error route");
        } catch (BusinessException e) {
            ResultMessages messages = e.getResultMessages();
            assertThat(messages.isNotEmpty(), is(true));
            assertThat(messages.getType(), is((ResultMessageType) ERROR));
            assertThat(messages.getList().size(), is(1));
            assertThat(messages.getList().get(0).getCode(), is(
                    MessageId.E_TR_0002));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void testCancel04() {
        Reserve reserve = new Reserve("001");
        reserve.setAdultCount(1);
        reserve.setChildCount(2);

        TourInfo tour = new TourInfo("01");
        reserve.setTourInfo(tour);
        reserve.setTransfer(Reserve.NOT_TRANSFERED);

        when(reserveRepository.findOne("001")).thenReturn(reserve,
                (Reserve) null); // !!!return null for second time
        when(reserveRepository.findOneForUpdate("001")).thenReturn(
                (Reserve) null); // return null
        when(tourInfoSharedService.isOverPaymentLimit(tour)).thenReturn(false); // within limit

        try {
            reserveService.cancel("001");
            fail("error route");
        } catch (BusinessException e) {
            ResultMessages messages = e.getResultMessages();
            assertThat(messages.isNotEmpty(), is(true));
            assertThat(messages.getType(), is((ResultMessageType) ERROR));
            assertThat(messages.getList().size(), is(1));
            assertThat(messages.getList().get(0).getCode(), is(
                    MessageId.E_TR_0003));
            throw e;
        }
    }

    @Test
    public void testUpdate01() {
        PriceCalculateOutput priceCalculateOutput = new PriceCalculateOutput();
        priceCalculateOutput.setSumPrice(100000);
        priceCalculateOutput.setAdultCount(1);
        priceCalculateOutput.setChildCount(2);
        when(priceCalculateSerivce.calculatePrice(10000, 1, 2)).thenReturn(
                priceCalculateOutput);

        ReservationUpdateInput input = new ReservationUpdateInput();
        input.setReserveNo("foo");
        input.setAdultCount(1);
        input.setChildCount(2);

        Reserve reserve = new Reserve();
        TourInfo tour = new TourInfo("aaa");
        tour.setArrival(new Arrival());
        tour.setDeparture(new Departure());
        tour.setBasePrice(10000);
        reserve.setTourInfo(tour);

        when(reserveRepository.findOne("foo")).thenReturn(reserve);
        when(reserveRepository.findOneForUpdate("foo")).thenReturn(reserve);
        when(reserveRepository.save(reserve)).thenReturn(reserve);
        // run
        ReservationUpdateOutput output = reserveService.update(input);
        assertThat(output, is(output));
    }

}
