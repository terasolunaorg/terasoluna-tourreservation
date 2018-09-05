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
package org.terasoluna.tourreservation.app.searchtour;

import java.util.Date;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import com.github.dozermapper.core.Mapper;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoSearchCriteria;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoService;

/**
 * Handle request of tour searching.
 */
@Slf4j
@Controller
@RequestMapping(value = "tours")
@SessionAttributes(types = SearchTourForm.class)
public class SearchTourController {

    @Inject
    TourInfoService tourInfoService;

    @Inject
    SearchTourFormDateValidator validator;

    @Inject
    JodaTimeDateFactory dateFactory;

    @Inject
    Mapper beanMapper;

    @InitBinder("searchTourForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(validator);
    }

    /**
     * pre-initialization of form backed bean
     * @return
     */
    @ModelAttribute
    public SearchTourForm setUpSearchTourForm() {
        SearchTourForm searchTourForm = new SearchTourForm();
        DateTime dateTime = dateFactory.newDateTime();
        DateTime nextWeekDate = dateTime.plusWeeks(1);
        searchTourForm.setDepYear(nextWeekDate.getYear());
        searchTourForm.setDepMonth(nextWeekDate.getMonthOfYear());
        searchTourForm.setDepDay(nextWeekDate.getDayOfMonth());

        log.debug("populate form {}", searchTourForm);
        return searchTourForm;
    }

    /**
     * clear session attributes and redirect to the search input view
     * @return redirect view for show the search input
     */
    @RequestMapping(method = RequestMethod.GET, params = "initForm")
    public String searchInitForm(SessionStatus status) {
        status.setComplete();
        return "redirect:/tours?form";
    }

    /**
     * shows the search input view
     * @return search input view
     */
    @RequestMapping(method = RequestMethod.GET, params = "form")
    public String searchForm() {
        return "searchtour/searchForm";
    }

    /**
     * Searches the tours based on user input. Search result is shown in the form of a List of TourInfo domain objects User
     * input is mapped to form backed bean SearchTourForm
     * @param searchTourForm
     * @param result
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String search(@Validated SearchTourForm searchTourForm,
            BindingResult result, Model model,
            @PageableDefault Pageable pageable) {
        if (result.hasErrors()) {
            return "searchtour/searchForm";
        }

        if (log.isDebugEnabled()) {
            log.debug("pageable={}", pageable);
        }

        TourInfoSearchCriteria criteria = beanMapper.map(searchTourForm,
                TourInfoSearchCriteria.class);

        Date depDate = new LocalDate(searchTourForm.getDepYear(), searchTourForm
                .getDepMonth(), searchTourForm.getDepDay()).toDate();
        criteria.setDepDate(depDate);

        Page<TourInfo> page = tourInfoService.searchTour(criteria, pageable);
        model.addAttribute("page", page);
        return "searchtour/searchForm";
    }

}
