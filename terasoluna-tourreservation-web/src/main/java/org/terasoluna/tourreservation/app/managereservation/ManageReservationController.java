/*
 * Copyright (C) 2013-2017 NTT DATA Corporation
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
package org.terasoluna.tourreservation.app.managereservation;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.dozer.Mapper;
import org.springframework.security.core.Authentication;
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
import org.terasoluna.tourreservation.app.managereservation.ManageReservationForm.ReservationCancel;
import org.terasoluna.tourreservation.app.managereservation.ManageReservationForm.ReservationEdit;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.service.reserve.ReservationUpdateInput;
import org.terasoluna.tourreservation.domain.service.reserve.ReservationUpdateOutput;
import org.terasoluna.tourreservation.domain.service.reserve.ReserveService;

@Controller
@RequestMapping(value = "managereservation")
@TransactionTokenCheck(value = "managereservation")
public class ManageReservationController {

    @Inject
    ManageReservationHelper manageReservationHelper;

    @Inject
    ReserveService reserveService;

    @Inject
    Mapper dozerBeanMapper;

    /**
     * pre-initialization of form backed bean
     * @return
     */
    @ModelAttribute
    public ManageReservationForm setUpManageReservationForm() {
        ManageReservationForm form = new ManageReservationForm();
        return form;
    }

    /**
     * Shows the list of reservations for a particular user
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Authentication auth, Model model) {
        List<ReserveRowOutput> rows = manageReservationHelper.list(auth);

        model.addAttribute("rows", rows);
        return "managereservation/list";
    }

    /**
     * Shows the detailed information of a reservation
     * @param reserveNo
     * @param model
     * @return
     */
    @RequestMapping(value = "detail/{reserveNo}", method = RequestMethod.GET)
    public String detailForm(@PathVariable("reserveNo") String reserveNo,
            Model model) {
        ReservationDetailOutput output = manageReservationHelper.findDetail(
                reserveNo);
        model.addAttribute("output", output);
        return "managereservation/detailForm";
    }

    /**
     * Shows the edit screen for user to make changes to the reservation. Uses reserveNo from the path to fetch the reservation
     * info
     * @param reserveNo
     * @param form
     * @param model
     * @return
     */
    @RequestMapping(value = "update/{reserveNo}", method = RequestMethod.GET, params = "form")
    public String updateForm(@PathVariable("reserveNo") String reserveNo,
            ManageReservationForm form, Model model) {

        Reserve reserve = reserveService.findOne(reserveNo);

        // Map Model to form
        // This is needed to copy the current values of the reservation into the form
        dozerBeanMapper.map(reserve, form);

        model.addAttribute(reserve);
        return "managereservation/updateForm";
    }

    /**
     * Goes back to the edit screen for user to make changes to the reservation.
     * @param reserveNo
     * @param form
     * @param model
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST, params = "redo")
    public String updateRedo(ManageReservationForm form, Model model) {
        Reserve reserve = reserveService.findOne(form.getReserveNo());
        model.addAttribute(reserve);
        return "managereservation/updateForm";
    }

    /**
     * Shows the confirmation page after user changes edits the reservation info on the edit reservation page
     * @param reserve
     * @param model
     * @return
     */
    @TransactionTokenCheck(value = "update", type = TransactionTokenType.BEGIN)
    @RequestMapping(value = "update", method = RequestMethod.POST, params = "confirm")
    public String updateConfirm(@Validated({ ReservationEdit.class,
            Default.class }) ManageReservationForm form, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            // TODO
            // return updateRedo(form, model);
            return "managereservation/updateForm";
        }

        ReservationDetailOutput output = manageReservationHelper.findDetail(
                form);
        model.addAttribute("output", output);
        return "managereservation/updateConfirm";
    }

    /**
     * Updates the reservation after user changes edits the reservation info on the edit reservation page
     * @param form
     * @param model
     * @param model
     * @return
     */
    @TransactionTokenCheck(value = "update", type = TransactionTokenType.IN)
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Validated({ ReservationEdit.class,
            Default.class }) ManageReservationForm form, BindingResult result,
            RedirectAttributes redirectAttr) {
        if (result.hasErrors()) {
            // TODO
            // return updateRedo(form, model);
            return "managereservation/updateForm";
        }

        ReservationUpdateInput input = dozerBeanMapper.map(form,
                ReservationUpdateInput.class);

        ReservationUpdateOutput output = reserveService.update(input);
        redirectAttr.addFlashAttribute("output", output);
        return "redirect:/managereservation/update?complete";
    }

    /**
     * redirects to the update completion page
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.GET, params = "complete")
    public String updateComplete() {
        return "managereservation/updateComplete";
    }

    @TransactionTokenCheck(value = "cancel", type = TransactionTokenType.BEGIN)
    @RequestMapping(value = "cancel", method = RequestMethod.POST, params = "confirm")
    public String cancelConfirm(ManageReservationForm form, Model model) {
        ReservationDetailOutput output = manageReservationHelper.findDetail(form
                .getReserveNo());
        model.addAttribute("output", output);
        return "managereservation/cancelConfirm";
    }

    @TransactionTokenCheck(value = "cancel", type = TransactionTokenType.IN)
    @RequestMapping(value = "cancel", method = RequestMethod.POST)
    public String cancel(@Validated({ ReservationCancel.class,
            Default.class }) ManageReservationForm form, Model model) {
        String reserveNo = form.getReserveNo();
        try {
            reserveService.cancel(reserveNo);
        } catch (BusinessException e) {
            // TODO
            ReservationDetailOutput output = manageReservationHelper.findDetail(
                    reserveNo);
            model.addAttribute("output", output);
            model.addAttribute(e.getResultMessages());
            return "managereservation/cancelConfirm";
        }
        model.addAttribute("reserveNo", reserveNo);
        return "redirect:/managereservation/cancel?complete";
    }

    /**
     * redirects to the update completion page
     * @return
     */
    @RequestMapping(value = "cancel", method = RequestMethod.GET, params = "complete")
    public String cancelComplete() {
        return "managereservation/cancelComplete";
    }

    @RequestMapping(value = "downloadPDF", method = RequestMethod.GET)
    public String downloadPDF(ManageReservationForm form, Model model) {
        DownloadPDFOutput downloadPDFOutput = manageReservationHelper.createPDF(
                form.getReserveNo());
        model.addAttribute(Arrays.asList(downloadPDFOutput));
        return "reservationReport";
    }

    @RequestMapping(value = "list", method = RequestMethod.GET, params = "backTolist")
    public String backList() {
        return "redirect:list";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, params = "backTolist")
    public String updateBackList() {
        return "redirect:list";
    }
}
