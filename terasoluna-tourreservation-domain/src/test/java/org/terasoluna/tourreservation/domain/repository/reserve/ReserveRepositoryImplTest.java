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
package org.terasoluna.tourreservation.domain.repository.reserve;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.model.TourInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
@Transactional
@Rollback
public class ReserveRepositoryImplTest {

    @Inject
    NamedParameterJdbcTemplate jdbcTemplate;

    @Inject
    ReserveRepository reserveRepository;

    @Test
    public void testFindWithDetail01() {
        String tourCode = "0000000001";
        DateTime reservedDay = new DateTime(2016, 1, 1, 0, 0, 0);
        TourInfo tourInfo = new TourInfo();
        tourInfo.setTourCode(tourCode);
        String customerCode = "00000001";
        String reserveNo = "10000000";
        String q = "INSERT INTO reserve(reserve_no, tour_code, reserved_day, "
                + "adult_count, child_count, customer_code, transfer, sum_price, remarks) "
                + "VALUES (:reserveNo, :tourCode, :reservedDay, :adultCount, :childCount, "
                + ":customerCode, :transfer, :sumPrice, :remarks)";
        {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("reserveNo", reserveNo);
            param.put("tourCode", tourCode);
            param.put("reservedDay", reservedDay.toDate());
            param.put("adultCount", 1);
            param.put("childCount", 2);
            param.put("customerCode", customerCode);
            param.put("transfer", "0");
            param.put("sumPrice", 1000);
            param.put("remarks", "TEST");
            jdbcTemplate.update(q, param);
        }

        Reserve reserve = reserveRepository.findOneWithDetail(reserveNo);
        assertThat(reserve, is(notNullValue()));
        assertThat(reserve.getReserveNo(), is(reserveNo));
        assertThat(reserve.getReservedDay(), is(reservedDay.toDate()));
        assertThat(reserve.getAdultCount(), is(1));
        assertThat(reserve.getChildCount(), is(2));
        assertThat(reserve.getTransfer(), is("0"));
        assertThat(reserve.getSumPrice(), is(1000));
        assertThat(reserve.getRemarks(), is("TEST"));
        assertThat(reserve.getCustomer(), is(notNullValue()));
        assertThat(reserve.getCustomer().getCustomerCode(), is(customerCode));
        assertThat(reserve.getTourInfo(), is(notNullValue()));
        assertThat(reserve.getTourInfo().getTourCode(), is(tourCode));
    }

    @Test
    public void testFindWithDetail02() {
        String reserveNo = "10000000";
        Reserve reserve = reserveRepository.findOneWithDetail(reserveNo);
        assertThat(reserve, is(nullValue()));
    }

    @Test
    public void testFindReservedSumByTourInfo01() {
        String tourCode = "0000000001";
        DateTime reservedDay = new DateTime(2016, 1, 1, 0, 0, 0);
        TourInfo tourInfo = new TourInfo();
        tourInfo.setTourCode(tourCode);
        String customerCode = "00000001";

        String q = "INSERT INTO reserve(reserve_no, tour_code, reserved_day, "
                + "adult_count, child_count, customer_code, transfer, sum_price, remarks) "
                + "VALUES (:reserveNo, :tourCode, :reservedDay, :adultCount, :childCount, "
                + ":customerCode, :transfer, :sumPrice, :remarks)";
        {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("reserveNo", "10000000");
            param.put("tourCode", tourCode);
            param.put("reservedDay", reservedDay.toDate());
            param.put("adultCount", 1);
            param.put("childCount", 1);
            param.put("customerCode", customerCode);
            param.put("transfer", "0");
            param.put("sumPrice", 1000);
            param.put("remarks", "TEST");
            jdbcTemplate.update(q, param);
        }
        {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("reserveNo", "10000001");
            param.put("tourCode", tourCode);
            param.put("reservedDay", reservedDay.toDate());
            param.put("adultCount", 2);
            param.put("childCount", 3);
            param.put("customerCode", customerCode);
            param.put("transfer", "0");
            param.put("sumPrice", 1000);
            param.put("remarks", "TEST");
            jdbcTemplate.update(q, param);
        }

        // run
        Long retValue = reserveRepository.countReservedPersonSumByTourInfo(
                tourInfo);

        // assert
        assertThat(retValue, is(7L));
    }

    @Test
    public void testFindReservedSumByTourInfo02() {
        TourInfo tourInfo = new TourInfo();
        tourInfo.setTourCode("xxxxx");

        // run
        Long retValue = reserveRepository.countReservedPersonSumByTourInfo(
                tourInfo);

        // assert
        assertThat(retValue, is(nullValue()));
    }

    @Test
    public void testFindByCustomer01() {
        String tourCode = "8888888888";
        LocalDate today = LocalDate.now();
        LocalDate reservedDay = today;
        LocalDate depDay = today.plusDays(32);
        LocalDate plannedDay = today.minusDays(1);
        String tourAbs = "wonderful travel !";
        String tourName = "test tour";
        String customerCode = "00000001";

        {
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
            param.put("basePrice", 20000);
            param.put("conductor", 1);
            param.put("tourAbs", tourAbs);
            jdbcTemplate.update(q, param);
        }

        {
            String q = "INSERT INTO reserve(reserve_no, tour_code, reserved_day, "
                    + "adult_count, child_count, customer_code, transfer, sum_price, remarks) "
                    + "VALUES (:reserveNo, :tourCode, :reservedDay, :adultCount, :childCount, "
                    + ":customerCode, :transfer, :sumPrice, :remarks)";
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("reserveNo", "00000000");
            param.put("tourCode", tourCode);
            param.put("reservedDay", reservedDay.toDate());
            param.put("adultCount", 1);
            param.put("childCount", 1);
            param.put("customerCode", customerCode);
            param.put("transfer", "0");

            param.put("sumPrice", 1000);
            param.put("remarks", "TEST");
            jdbcTemplate.update(q, param);
        }

        Customer customer = new Customer();
        customer.setCustomerCode(customerCode);

        // run
        List<Reserve> reservationList = reserveRepository.findAllByCustomer(
                customer);

        // assert
        assertThat(reservationList, is(notNullValue()));
        assertThat(reservationList.size() > 0, is(true));
        Reserve r = reservationList.get(reservationList.size() - 1); // last
                                                                     // inserted
        assertThat(r.getAdultCount(), is(1));
        assertThat(r.getChildCount(), is(1));
        assertThat(r.getReservedDay(), is(reservedDay.toDate()));
        assertThat(r.getRemarks(), is("TEST"));
        assertThat(r.getReserveNo(), is("00000000"));
        assertThat(r.getSumPrice(), is(1000));
        assertThat(r.getTransfer(), is("0"));

        assertThat(r.getCustomer().getCustomerAdd(), is("千葉県八千代市上高野"));
        assertThat(r.getCustomer().getCustomerBirth().getTime(), is(DateTime
                .parse("1975-01-05").getMillis()));
        assertThat(r.getCustomer().getCustomerCode(), is("00000001"));
        assertThat(r.getCustomer().getCustomerJob(), is("プログラマ"));
        assertThat(r.getCustomer().getCustomerKana(), is("キムラ　タロウ"));
        assertThat(r.getCustomer().getCustomerMail(), is("tarou@example.com"));
        assertThat(r.getCustomer().getCustomerName(), is("木村　太郎"));
        assertThat(r.getCustomer().getCustomerPass(), is(
                "$2a$12$Jfwcv/ZpfE0QjVlLT9CB9eqTLrYdAsvfGxXKvRMlpkfEn.9Uirgou"));
        assertThat(r.getCustomer().getCustomerPost(), is("276-0022"));
        assertThat(r.getCustomer().getCustomerTel(), is("111-1111-1111"));

        assertThat(r.getTourInfo().getAvaRecMax(), is(2147483647));
        assertThat(r.getTourInfo().getBasePrice(), is(20000));
        assertThat(r.getTourInfo().getConductor(), is("1"));
        assertThat(r.getTourInfo().getDepDay(), is(depDay.toDate()));
        assertThat(r.getTourInfo().getPlannedDay(), is(plannedDay.toDate()));
        assertThat(r.getTourInfo().getTourAbs(), is(tourAbs));
        assertThat(r.getTourInfo().getTourCode(), is(tourCode));
        assertThat(r.getTourInfo().getTourDays(), is(1));
        assertThat(r.getTourInfo().getTourName(), is(tourName));
        assertThat(r.getTourInfo().getAccommodation().getAccomCode(), is(
                "0001"));
        assertThat(r.getTourInfo().getAccommodation().getAccomName(), is(
                "TERASOLUNAホテル第一荘"));
        assertThat(r.getTourInfo().getAccommodation().getAccomTel(), is(
                "018-123-4567"));

        assertThat(r.getTourInfo().getDeparture().getDepCode(), is("01"));
        assertThat(r.getTourInfo().getDeparture().getDepName(), is("北海道"));
    }

    @Test
    public void testFindByCustomer02() {
        String customerCode = "xxxxxxxx";

        Customer customer = new Customer();
        customer.setCustomerCode(customerCode);

        // run
        List<Reserve> reservationList = reserveRepository.findAllByCustomer(
                customer);

        assertThat(reservationList, is(notNullValue()));
        assertThat(reservationList.size(), is(0));
    }
}
