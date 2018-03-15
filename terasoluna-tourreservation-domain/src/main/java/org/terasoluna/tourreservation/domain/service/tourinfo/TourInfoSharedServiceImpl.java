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

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoRepository;

@Slf4j
@Service
@Transactional
public class TourInfoSharedServiceImpl implements TourInfoSharedService {

    @Inject
    TourInfoRepository tourInfoRepository;

    @Inject
    JodaTimeDateFactory dateFactory;

    @Override
    public TourInfo findOne(String tourCode) {
        return tourInfoRepository.findOne(tourCode);
    }

    @Override
    public boolean isOverPaymentLimit(TourInfo tour) {
        Assert.notNull(tour, "tour must not be null");
        DateTime today = dateFactory.newDateTime().withTime(0, 0, 0, 0);
        DateTime paymentLimit = tour.getPaymentLimit();

        log.debug("today={}, paymentLimit={}", today, paymentLimit);
        return today.isAfter(paymentLimit);
    }

    @Override
    public TourInfo findOneForUpdate(String tourCode) {
        return tourInfoRepository.findOneForUpdate(tourCode);
    }

}
