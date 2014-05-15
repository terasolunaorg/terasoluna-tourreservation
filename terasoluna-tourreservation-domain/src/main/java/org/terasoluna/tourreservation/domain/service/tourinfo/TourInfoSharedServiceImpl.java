/*
 * Copyright (C) 2013-2014 terasoluna.org
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

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.terasoluna.gfw.common.date.DateFactory;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoRepository;

@Service
@Transactional
public class TourInfoSharedServiceImpl implements TourInfoSharedService {
    private static final Logger logger = LoggerFactory
            .getLogger(TourInfoSharedServiceImpl.class);

    @Inject
    TourInfoRepository tourInfoRepository;

    @Inject
    DateFactory dateFactory;

    @Transactional(readOnly = true)
    @Override
    public TourInfo findOne(String tourCode) {
        return tourInfoRepository.findOne(tourCode);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isOverPaymentLimitTour(TourInfo tour) {
        Assert.notNull(tour, "tour must not be null");
        DateTime today = dateFactory.newDateTime().withTime(0, 0, 0, 0);
        DateTime paymentLimit = tour.getPaymentLimit();

        logger.debug("today={}, paymentLimit={}", today, paymentLimit);
        return today.isAfter(paymentLimit);
    }

    @Transactional(readOnly = true)
    @Override
    public TourInfo findOneForUpdate(String tourCode) {
        return tourInfoRepository.findOneForUpdate(tourCode);
    }

}
