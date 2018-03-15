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
package org.terasoluna.tourreservation.app.reservetour;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveTourOutput;
import org.terasoluna.tourreservation.domain.service.userdetails.ReservationUserDetails;

import javax.inject.Inject;

/**
 * Handle request of tour searching.
 */
@Slf4j
@Controller
@RequestMapping(value = "tours")
@TransactionTokenCheck(value = "tours")
public class ReserveTourController {

    @Inject
    ReserveTourHelper reserveTourHelper;

    /**
     * pre-initialization of form backed bean
     * @return
     */
    @ModelAttribute
    public ReserveTourForm setUpReserveTourForm() {
        return new ReserveTourForm();
    }

    /**
     * Shows the detail view of a tour
     * @param form
     * @param model
     * @return
     */
    @RequestMapping(value = { "{tourCode}",
            "{tourCode}/reserve" }, method = RequestMethod.GET, params = "form")
    public String reserveForm(
            @AuthenticationPrincipal ReservationUserDetails userDetails,
            @PathVariable("tourCode") String tourCode, ReserveTourForm form,
            Model model) {
        log.debug("retrieve tour {}", tourCode);

        TourDetailOutput output = reserveTourHelper.findTourDetail(userDetails,
                tourCode, form);

        model.addAttribute("output", output);

        return "reservetour/reserveForm";
    }

    /**
     * Shows the confirmation page before reserving a tour
     * @param form
     * @param bindingResult
     * @param model
     * @return
     */
    @TransactionTokenCheck(value = "reserve", type = TransactionTokenType.BEGIN)
    @RequestMapping(value = "{tourCode}/reserve", method = RequestMethod.POST, params = "confirm")
    public String confirm(
            @AuthenticationPrincipal ReservationUserDetails userDetails,
            @PathVariable("tourCode") String tourCode,
            @Validated ReserveTourForm form, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return reserveForm(userDetails, tourCode, form, model);
        }
        log.debug("confirm the reservation details for the following tour {}",
                tourCode);

        TourDetailOutput output = reserveTourHelper.findTourDetail(userDetails,
                tourCode, form);
        model.addAttribute("output", output);

        return "reservetour/reserveConfirm";
    }

    /**
     * reserves a tour
     * @param form
     * @param bindingResult
     * @param redirectAttr
     * @return
     */
    @TransactionTokenCheck(value = "reserve", type = TransactionTokenType.IN)
    @RequestMapping(value = "{tourCode}/reserve", method = RequestMethod.POST)
    public String reserve(
            @AuthenticationPrincipal ReservationUserDetails userDetails,
            @PathVariable("tourCode") String tourCode,
            @Validated ReserveTourForm form, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttr) {
        log.debug("reserve tour {}", tourCode);

        if (bindingResult.hasErrors()) {
            return reserveForm(userDetails, tourCode, form, model);
        }

        try {
            ReserveTourOutput output = reserveTourHelper.reserve(userDetails,
                    tourCode, form);
            redirectAttr.addFlashAttribute("output", output);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return reserveForm(userDetails, tourCode, form, model);
        }

        return "redirect:/tours/{tourCode}/reserve?complete";
    }

    /**
     * redirects to the reservation completion page
     * @return
     */
    @RequestMapping(value = "{tourCode}/reserve", method = RequestMethod.GET, params = "complete")
    public String reserveComplete() {
        return "reservetour/reserveComplete";
    }

    @RequestMapping(value = "{tourCode}/reserve", method = RequestMethod.POST, params = "redo")
    public String reserveRedo(
            @AuthenticationPrincipal ReservationUserDetails userDetails,
            @PathVariable("tourCode") String tourCode, ReserveTourForm form,
            Model model) {
        return reserveForm(userDetails, tourCode, form, model);
    }

}
