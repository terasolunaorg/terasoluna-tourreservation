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

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.terasoluna.tourreservation.domain.model.Age;
import org.terasoluna.tourreservation.domain.repository.age.AgeRepository;

@Service
public class PriceCalculateSharedServiceImpl implements
                                             PriceCalculateSharedService {

    @Inject
    AgeRepository ageRepository;

    @Inject
    PlatformTransactionManager transactionManager;

    private Age adultAge;

    private Age childAge;

    public PriceCalculateOutput calculatePrice(Integer basePrice,
            Integer adultCount, Integer childCount) {
        PriceCalculateOutput result = new PriceCalculateOutput();

        int adultUnitPrice = basePrice * adultAge.getAgeRate() / 100;

        result.setAdultUnitPrice(adultUnitPrice);

        int adultPrice = adultCount * adultUnitPrice;
        result.setAdultPrice(adultPrice);
        int childUnitPrice = basePrice * childAge.getAgeRate() / 100;

        result.setChildUnitPrice(childUnitPrice);

        int childPrice = childCount * childUnitPrice;

        result.setChildPrice(childPrice);
        result.setAdultCount(adultCount);
        result.setChildCount(childCount);
        result.setSumPrice(adultPrice + childPrice);

        return result;
    }

    @PostConstruct
    public void loadAges() {
        // use TransactionTemplate to avoid SQLException which tells set-readonly is not allowed
        // see https://github.com/terasolunaorg/terasoluna-tourreservation/issues/163
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(
                    TransactionStatus status) {
                childAge = ageRepository.findOne("1");
                adultAge = ageRepository.findOne("0");
            }
        });
    }

}
