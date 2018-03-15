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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.tourreservation.domain.model.TourInfo;

public class TourInfoRepositoryImpl implements TourInfoRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Page<TourInfo> findPageBySearchCriteria(
            TourInfoSearchCriteria criteria, Pageable pageable) {

        List<TourInfo> content = findTourInfo(criteria, pageable);
        long total = countSearchTourInfo(criteria);

        Page<TourInfo> page = new PageImpl<TourInfo>(content, pageable, total);
        return page;
    }

    protected List<TourInfo> findTourInfo(TourInfoSearchCriteria criteria,
            Pageable pageable) {

        String q = buildJpql(criteria.getTourDays(), criteria.getBasePrice());
        TypedQuery<TourInfo> query = entityManager.createQuery(q,
                TourInfo.class);

        query.setParameter("adultCount", criteria.getAdultCount());
        query.setParameter("childCount", criteria.getChildCount());
        query.setParameter("depDay", criteria.getDepDate());
        query.setParameter("depCode", criteria.getDepCode());
        query.setParameter("arrCode", criteria.getArrCode());
        if (criteria.getTourDays() != 0) {
            query.setParameter("tourDays", criteria.getTourDays());
        }
        if (criteria.getBasePrice() != 0) {
            query.setParameter("basePrice", criteria.getBasePrice());
        }

        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<TourInfo> tourInfoList = query.getResultList();
        return tourInfoList;
    }

    protected long countSearchTourInfo(TourInfoSearchCriteria criteria) {
        String q = buildJpqlCount(criteria.getTourDays(), criteria
                .getBasePrice());
        TypedQuery<Long> query = entityManager.createQuery(q, Long.class);

        query.setParameter("adultCount", criteria.getAdultCount());
        query.setParameter("childCount", criteria.getChildCount());
        query.setParameter("depDay", criteria.getDepDate());
        query.setParameter("depCode", criteria.getDepCode());
        query.setParameter("arrCode", criteria.getArrCode());
        if (criteria.getTourDays() != 0) {
            query.setParameter("tourDays", criteria.getTourDays());
        }
        if (criteria.getBasePrice() != 0) {
            query.setParameter("basePrice", criteria.getBasePrice());
        }
        Long tourCount = query.getSingleResult();
        if (tourCount == null) {
            tourCount = 0L;
        }
        return tourCount;
    }

    /**
     * tours data that match the search criteria.
     * @return jpql.
     */
    protected String buildJpql(int tourDays, int basePrice) {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT x FROM TourInfo x ");
        sb.append(
                "LEFT JOIN x.departure dep LEFT JOIN x.arrival arr WHERE (EXISTS (SELECT res.tourInfo.tourCode FROM Reserve res WHERE res.tourInfo.tourCode = x.tourCode ");
        sb.append(
                "AND (res.adultCount + res.childCount + :adultCount + :childCount) <= x.avaRecMax ) OR NOT EXISTS(SELECT res.tourInfo.tourCode FROM Reserve res WHERE res.tourInfo.tourCode = x.tourCode)) ");
        sb.append(
                "AND x.depDay = :depDay AND dep.depCode = :depCode AND arr.arrCode = :arrCode ");
        if (tourDays != 0) {
            sb.append("AND x.tourDays <= :tourDays ");
        }
        if (basePrice != 0) {
            sb.append("AND x.basePrice <= :basePrice ");
        }
        sb.append("ORDER BY x.tourDays DESC, x.basePrice DESC");

        return sb.toString();
    }

    /**
     * count the number of tours that match the search criteria.
     * @return jpql .
     */
    protected String buildJpqlCount(int tourDays, int basePrice) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(x) from TourInfo x ");
        sb.append(
                "LEFT JOIN x.departure dep LEFT JOIN x.arrival arr WHERE (EXISTS (SELECT res.tourInfo.tourCode FROM Reserve res WHERE res.tourInfo.tourCode = x.tourCode ");
        sb.append(
                "AND (res.adultCount + res.childCount + :adultCount + :childCount) <= x.avaRecMax ) OR NOT EXISTS(SELECT res.tourInfo.tourCode FROM Reserve res WHERE res.tourInfo.tourCode = x.tourCode)) ");
        sb.append(
                "AND x.depDay = :depDay AND dep.depCode = :depCode AND arr.arrCode = :arrCode ");
        if (tourDays != 0) {
            sb.append("AND x.tourDays <= :tourDays ");
        }
        if (basePrice != 0) {
            sb.append("AND x.basePrice <= :basePrice ");
        }

        return sb.toString();
    }

}
