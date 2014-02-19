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
package org.terasoluna.tourreservation.app.reservetour;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveTourOutput;

/**
 * Handle request of tour searching.
 */
@Controller
@RequestMapping(value = "reservetour")
@SessionAttributes(types = ReserveTourForm.class)
@TransactionTokenCheck(value = "reservetour")
public class ReserveTourController {

    private static final Logger logger = LoggerFactory
            .getLogger(ReserveTourController.class);

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
    @RequestMapping(value = "read", method = RequestMethod.GET)
    public String reserveForm(ReserveTourForm form, Model model,
            SessionStatus status) {
        logger.debug("retieve tour {}", form.getTourCode());

        status.setComplete();

        TourDetailOutput output = reserveTourHelper.findTourDetail(form);

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
    @RequestMapping(value = "reserve", method = RequestMethod.POST, params = "confirm")
    public String confirm(@Valid ReserveTourForm form,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "reservetour/reserveForm";
        }
        logger.debug(
                "confirm the reservation details for the following tour {}",
                form.getTourCode());

        TourDetailOutput output = reserveTourHelper.findTourDetail(form);
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
    @RequestMapping(value = "reserve", method = RequestMethod.POST)
    public String reserve(@Valid ReserveTourForm form,
            BindingResult bindingResult, RedirectAttributes redirectAttr,
            Model model) {
        logger.debug("reserve tour {}", form.getTourCode());

        if (bindingResult.hasErrors()) {
            TourDetailOutput output = reserveTourHelper.findTourDetail(form);
            model.addAttribute("output", output);
            return "reservetour/reserveForm";
        }

        try {
            ReserveTourOutput output = reserveTourHelper.reserve(form);
            redirectAttr.addFlashAttribute("output", output);
        } catch (BusinessException e) {
            TourDetailOutput output = reserveTourHelper.findTourDetail(form);
            model.addAttribute("output", output);
            model.addAttribute(e.getResultMessages());
            return "reservetour/reserveForm";
        }

        return "redirect:/reservetour/reserve?complete";
    }

    /**
     * redirects to the reservation completion page
     * @return
     */
    @RequestMapping(value = "reserve", method = RequestMethod.GET, params = "complete")
    public String reserveComplete(SessionStatus status) {
        status.setComplete();
        return "reservetour/reserveComplete";
    }

    @RequestMapping(value = "read", method = RequestMethod.POST, params = "redo")
    public String reserveRedo() {
        return "redirect:/reservetour/read";
    }
}
