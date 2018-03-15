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
package org.terasoluna.tourreservation.domain.repository.tourinfo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.tourreservation.domain.model.TourInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
@Transactional
@Rollback
public class TourInfoRepositoryImplTest {

    @Inject
    TourInfoRepository tourInfoRepository;

    @Inject
    NamedParameterJdbcTemplate jdbcTemplate;

    String tourCode;

    DateTime depDay;

    DateTime plannedDay;

    String tourAbs;

    String tourName;

    int basePrice;

    TourInfoSearchCriteria criteria;

    @Before
    public void setUp() {
        criteria = new TourInfoSearchCriteria();
        tourCode = "8888888888";
        depDay = new DateTime(2014, 2, 2, 0, 0, 0);
        plannedDay = new DateTime(2013, 12, 31, 0, 0, 0);
        tourAbs = "wonderful travel !";
        tourName = "test tour";
        basePrice = 20000;

        String q = "INSERT INTO tourinfo(tour_code, planned_day, plan_no, tour_name, "
                + "tour_days, dep_day, ava_rec_max, dep_code, arr_code, accom_code, "
                + "base_price, conductor, tour_abs) values(:tourCode, :plannedDay, "
                + ":planNo, :tourName, :tourDays, :depDay, :avaRecMax, :depCode, :arrCode, "
                + ":accomCode, :basePrice, :conductor, :tourAbs)";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tourCode", tourCode);
        param.put("plannedDay", plannedDay.toDate());
        param.put("planNo", "0101");
        param.put("tourName", tourName);
        param.put("tourDays", 1);
        param.put("depDay", depDay.toDate());
        param.put("avaRecMax", 2147483647);
        param.put("depCode", "01");
        param.put("arrCode", "01");
        param.put("accomCode", "0001");
        param.put("basePrice", basePrice);
        param.put("conductor", 1);
        param.put("tourAbs", tourAbs);
        jdbcTemplate.update(q, param);
    }

    /**
     * BasePrice and TourDays dont set value Case
     */
    @Test
    public void testSearchTourInfo01() {
        // search data
        criteria.setDepDate(depDay.toDate());
        criteria.setAdultCount(1);
        criteria.setArrCode("01");
        criteria.setBasePrice(0);
        criteria.setChildCount(1);
        criteria.setDepCode("01");
        criteria.setTourDays(0);

        Pageable pageable = new PageRequest(0, 10);

        // run
        Page<TourInfo> page = tourInfoRepository.findPageBySearchCriteria(
                criteria, pageable);

        assertThat(page.getTotalPages(), is(1));
        assertThat(page.getNumber(), is(0));

        TourInfo tour = page.getContent().get(0);

        // assert
        assertThat(tour.getAvaRecMax(), is(2147483647));
        assertThat(tour.getBasePrice(), is(basePrice));
        assertThat(tour.getConductor(), is("1"));
        assertThat(tour.getDepDay().getTime(), is(depDay.getMillis()));
        assertThat(tour.getPlannedDay().getTime(), is(plannedDay.getMillis()));
        assertThat(tour.getTourAbs(), is(tourAbs));
        assertThat(tour.getTourCode(), is(tourCode));
        assertThat(tour.getTourDays(), is(1));
        assertThat(tour.getTourName(), is(tourName));

        assertThat(tour.getAccommodation().getAccomCode(), is("0001"));
        assertThat(tour.getAccommodation().getAccomName(), is(
                "TERASOLUNAホテル第一荘"));
        assertThat(tour.getAccommodation().getAccomTel(), is("018-123-4567"));

        assertThat(tour.getDeparture().getDepCode(), is("01"));
        assertThat(tour.getDeparture().getDepName(), is("北海道"));

    }

    /**
     * BasePrice and TourDays set value Case
     */
    @Test
    public void testSearchTourInfo02() {
        // search data
        criteria.setDepDate(new LocalDate(2012, 7, 10).toDate());
        criteria.setAdultCount(1);
        criteria.setArrCode("01");
        criteria.setBasePrice(10);
        criteria.setChildCount(1);
        criteria.setDepCode("01");
        criteria.setTourDays(2);

        Pageable pageable = new PageRequest(0, 10);
        // run
        Page<TourInfo> page = tourInfoRepository.findPageBySearchCriteria(
                criteria, pageable);

        // assert
        assertThat(page.getTotalPages(), is(0));
        assertThat(page.getNumber(), is(0));
    }
}
