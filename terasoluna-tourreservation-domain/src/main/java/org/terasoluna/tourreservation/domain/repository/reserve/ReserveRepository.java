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

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.model.TourInfo;

public interface ReserveRepository extends JpaRepository<Reserve, String> {

    @Query("SELECT r FROM Reserve r WHERE r.reserveNo = :reserveNo")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Reserve findOneForUpdate(@Param("reserveNo") String reserveNo);

    @Query("SELECT r FROM Reserve AS r LEFT JOIN FETCH r.tourInfo AS t LEFT JOIN FETCH t.departure LEFT JOIN FETCH t.arrival WHERE r.reserveNo = :reserveNo")
    Reserve findOneWithDetail(@Param("reserveNo") String reserveNo);

    @Query("SELECT SUM(r.adultCount + r.childCount) FROM Reserve r WHERE r.tourInfo = :tourInfo")
    Long countReservedPersonSumByTourInfo(@Param("tourInfo") TourInfo tourInfo);

    @Query("SELECT r FROM Reserve AS r LEFT JOIN FETCH r.tourInfo AS t LEFT JOIN FETCH t.departure LEFT JOIN FETCH t.arrival WHERE r.customer = :customer ORDER BY t.depDay, r.reserveNo")
    List<Reserve> findAllByCustomer(@Param("customer") Customer customer);
}
