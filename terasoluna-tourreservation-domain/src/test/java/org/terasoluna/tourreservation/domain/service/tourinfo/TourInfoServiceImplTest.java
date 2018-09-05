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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.tourreservation.domain.model.Arrival;
import org.terasoluna.tourreservation.domain.model.Departure;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoRepository;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoSearchCriteria;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoServiceImpl;

public class TourInfoServiceImplTest {
    TourInfoServiceImpl tourInfoService;

    TourInfoRepository tourInfoRepository;

    JodaTimeDateFactory dateFactory;

    DateTime now;

    @Before
    public void setUp() {
        tourInfoService = new TourInfoServiceImpl();
        tourInfoRepository = mock(TourInfoRepository.class);
        dateFactory = mock(JodaTimeDateFactory.class);
        tourInfoService.tourInfoRepository = tourInfoRepository;
        now = new DateTime();

        when(dateFactory.newDateTime()).thenReturn(now);
    }

    /**
     * searchTourInfo return one object pattern
     */
    @Test
    public void testSearchTourInfo01() {

        TourInfoSearchCriteria criteria = new TourInfoSearchCriteria();
        Pageable pageable = PageRequest.of(0, 10);

        List<TourInfo> mockedList = new ArrayList<TourInfo>();

        TourInfo info = new TourInfo();
        Arrival a = new Arrival();
        a.setArrCode("1234");
        info.setArrival(a);

        Departure departure = new Departure();
        departure.setDepCode("5678");
        info.setDeparture(departure);
        info.setTourCode("12345678");

        mockedList.add(info);

        Page<TourInfo> page = new PageImpl<TourInfo>(mockedList);

        when(tourInfoRepository.findPageBySearchCriteria(criteria, pageable))
                .thenReturn(page);

        // run
        Page<TourInfo> result = tourInfoService.searchTour(criteria, pageable);

        // assert
        assertThat(result, is(page));
    }
}
