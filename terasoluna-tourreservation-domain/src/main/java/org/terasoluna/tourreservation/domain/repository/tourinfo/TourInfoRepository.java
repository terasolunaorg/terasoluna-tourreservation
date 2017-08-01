/*
 * Copyright (C) 2013-2016 NTT DATA Corporation
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

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.terasoluna.tourreservation.domain.model.TourInfo;

public interface TourInfoRepository extends JpaRepository<TourInfo, String>,
                                    TourInfoRepositoryCustom {

    @Query("SELECT t FROM TourInfo t WHERE t.tourCode = :tourCode")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    TourInfo findOneForUpdate(@Param("tourCode") String tourCode);

}
