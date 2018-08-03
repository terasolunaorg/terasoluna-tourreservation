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
package org.terasoluna.tourreservation.domain.service.tourinfo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoRepository;

public class TourInfoSharedServiceImplTest {
    TourInfoSharedServiceImpl tourInfoSharedService;

    TourInfoRepository tourInfoRepository;

    JodaTimeDateFactory dateFactory;

    DateTime now;

    @Before
    public void setUp() {
        tourInfoSharedService = new TourInfoSharedServiceImpl();
        tourInfoRepository = mock(TourInfoRepository.class);
        dateFactory = mock(JodaTimeDateFactory.class);
        tourInfoSharedService.tourInfoRepository = tourInfoRepository;
        tourInfoSharedService.dateFactory = dateFactory;
        now = new DateTime();

        when(dateFactory.newDateTime()).thenReturn(now);
    }

    /**
     * searchTourInfo return no object pattern
     */
    @Test
    public void testFindOne01() {
        TourInfo info = new TourInfo();

        when(tourInfoRepository.findById("foo")).thenReturn(Optional.of(info));

        // run
        TourInfo result = tourInfoSharedService.findOne("foo");

        // assert
        assertThat(result, is(info));
    }

    @Test
    public void testIsOverPaymentLimitTour01() {
        // normal case
        TourInfo tour = new TourInfo();
        DateTime depDay = now.withTime(0, 0, 0, 0).plusDays(7); // within limit
        tour.setDepDay(depDay.toDate());

        boolean result = tourInfoSharedService.isOverPaymentLimit(tour);
        assertThat(result, is(false));
    }

    @Test
    public void testIsOverPaymentLimitTour02() {
        // invalid case
        TourInfo tour = new TourInfo();
        DateTime depDay = now.withTime(0, 0, 0, 0).plusDays(6); // over limit
        tour.setDepDay(depDay.toDate());

        boolean result = tourInfoSharedService.isOverPaymentLimit(tour);
        assertThat(result, is(true));
    }

    @Test
    public void testIsOverPaymentLimitTour03() {
        // normal case
        TourInfo tour = new TourInfo();
        DateTime depDay = now.withTime(0, 0, 0, 0).plusDays(7); // within limit
        tour.setDepDay(depDay.toDate());

        // check whether hh:mm:ss.SSS is ignored
        when(dateFactory.newDateTime()).thenReturn(now.withTime(0, 0, 0, 0)
                .plusMillis(1));

        boolean result = tourInfoSharedService.isOverPaymentLimit(tour);
        assertThat(result, is(false));
    }

    @Test
    public void testIsOverPaymentLimitTour04() {
        // normal case
        TourInfo tour = new TourInfo();
        DateTime depDay = now.withTime(0, 0, 0, 0).plusDays(7); // within limit
        tour.setDepDay(depDay.toDate());

        // check whether hh:mm:ss.SSS is ignored
        when(dateFactory.newDateTime()).thenReturn(now.withTime(0, 0, 0, 0)
                .plusHours(23).plusMinutes(59).plusSeconds(59).plusMillis(999));

        boolean result = tourInfoSharedService.isOverPaymentLimit(tour);
        assertThat(result, is(false));
    }
}
