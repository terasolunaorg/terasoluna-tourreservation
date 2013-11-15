/*
 * Copyright (C) 2013 terasoluna.org
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
package org.terasoluna.tourreservation.app.searchtour;

import javax.inject.Inject;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.terasoluna.gfw.common.date.DateFactory;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoSearchCriteria;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoService;

/**
 * Handle request of tour searching.
 */
@Controller
@RequestMapping(value = "searchtour")
@SessionAttributes(types = TourInfoSearchCriteria.class)
public class SearchTourController {

    private static final Logger logger = LoggerFactory
            .getLogger(SearchTourController.class);

    @Inject
    protected TourInfoService tourInfoService;

    @Inject
    protected TourInfoSearchCriteriaDateValidator validator;

    @Inject
    protected DateFactory dateFactory;

    @InitBinder("tourInfoSearchCriteria")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(validator);
    }

    /**
     * pre-initialization of form backed bean
     * @param model
     * @return
     */
    @ModelAttribute
    public TourInfoSearchCriteria setUpTourInfoSearchCriteria() {
        TourInfoSearchCriteria criteria = new TourInfoSearchCriteria();
        DateTime dateTime = dateFactory.newDateTime();
        DateTime nextWeekDate = dateTime.plusWeeks(1);
        criteria.setDepYear(nextWeekDate.getYear());
        criteria.setDepMonth(nextWeekDate.getMonthOfYear());
        criteria.setDepDay(nextWeekDate.getDayOfMonth());

        logger.debug("populate form {}", criteria);
        return criteria;
    }

    /**
     * shows the search input view
     * @return search input view
     */
    @RequestMapping(value = "search", method = RequestMethod.GET, params = "form")
    public String searchForm(SessionStatus status) {
        status.setComplete();
        return "searchtour/searchForm";
    }

    /**
     * Searches the tours based on user input. Search result is shown in the form of a List of TourInfo domain objects User
     * input is mapped to form backed bean SearchTourForm
     * @param form
     * @param result
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(@Valid TourInfoSearchCriteria criteria,
            BindingResult result, Model model,
            @PageableDefault Pageable pageable) {
        if (result.hasErrors()) {
            return "searchtour/searchForm";
        }

        if (logger.isDebugEnabled()) {
            logger.info("pageable={}", pageable);
        }

        Page<TourInfo> page = tourInfoService.searchTour(criteria, pageable);
        model.addAttribute("page", page);
        return "searchtour/searchForm";
    }

}
