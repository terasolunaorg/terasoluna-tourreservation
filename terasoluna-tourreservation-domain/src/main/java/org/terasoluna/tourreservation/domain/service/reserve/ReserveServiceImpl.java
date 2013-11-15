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
package org.terasoluna.tourreservation.domain.service.reserve;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.date.DateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.common.sequencer.Sequencer;
import org.terasoluna.tourreservation.domain.common.constants.MessageId;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.model.Reserve;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.reserve.ReserveRepository;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateSharedSerivce;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoSharedService;

@Transactional
@Service
public class ReserveServiceImpl implements ReserveService {

    private static final Logger logger = LoggerFactory
            .getLogger(ReserveServiceImpl.class);

    @Inject
    protected ReserveRepository reserveRepository;

    @Inject
    protected TourInfoSharedService tourInfoSharedService;

    @Inject
    protected PriceCalculateSharedSerivce priceCalculateService;

    @Inject
    protected DateFactory dateFactory;

    @Inject
    protected Mapper dozerBeanMapper;

    @Resource
    protected Sequencer<String> reserveNoSeq;

    @Transactional(readOnly = true)
    @Override
    public Reserve findOne(String reserveNo) {
        Reserve reserve = reserveRepository.findOne(reserveNo);
        validateReservation(reserve);
        return reserve;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Reserve> findByCustomerCode(String customerCode) {
        Customer customer = new Customer(customerCode);
        List<Reserve> reserves = reserveRepository.findByCustomer(customer);
        return reserves;
    }

    @Override
    public ReserveTourOutput reserveTour(ReserveTourInput input) throws BusinessException {

        TourInfo tourInfo = tourInfoSharedService.findOneForUpdate(input
                .getTourCode());
        DateTime today = dateFactory.newDateTime().withTime(0, 0, 0, 0);

        // * check date
        DateTime paymentLimit = tourInfo.getPaymentLimit();
        // error if today is after payment limit
        if (tourInfoSharedService.isOverPaymentLimitTour(tourInfo)) {
            ResultMessages message = ResultMessages.error().add(
                    MessageId.E_TR_0004);
            throw new BusinessException(message);
        }

        // * check vacancy
        int reserveMember = input.getAdultCount() + input.getChildCount();
        int aveRecMax = tourInfo.getAvaRecMax();
        // retrieve the number of current reservations
        Long sumCount = reserveRepository.findReservedSumByTourInfo(tourInfo);
        if (sumCount == null) {
            sumCount = 0L;
        }

        // calculate vacancy
        long vacantCount = aveRecMax - sumCount;
        logger.debug("vacantCount({}), reserveMember({})", vacantCount,
                reserveMember);
        // error if reserved number is larger than available vacancy
        if (vacantCount < reserveMember) {
            ResultMessages message = ResultMessages.error().add(
                    MessageId.E_TR_0005);
            throw new BusinessException(message);
        }

        // * reserve
        PriceCalculateOutput priceCalculateOutput = priceCalculateService
                .calculatePrice(tourInfo.getBasePrice(), input.getAdultCount(),
                        input.getChildCount());
        String reserveNo = reserveNoSeq.getNext();

        Reserve reserve = dozerBeanMapper.map(input, Reserve.class);

        reserve.setTourInfo(tourInfo);
        reserve.setSumPrice(priceCalculateOutput.getSumPrice());
        reserve.setReserveNo(reserveNo);
        reserve.setReservedDay(today.toDate());
        reserve.setTransfer(Reserve.NOT_TRANSFERED);
        reserveRepository.save(reserve);
        logger.debug("reserved {}", reserve);

        // * create output
        ReserveTourOutput tourReserveOutput = new ReserveTourOutput();
        tourReserveOutput.setPriceCalculateOutput(priceCalculateOutput);
        tourReserveOutput.setReserve(reserve);
        tourReserveOutput.setTourInfo(tourInfo);
        tourReserveOutput.setPaymentTimeLimit(paymentLimit.toDate());
        tourReserveOutput.setCustomer(input.getCustomer());

        // fetch to avoid lazy LazyInitializationException
        tourInfo.getAccommodation().getAccomCode();
        tourInfo.getDeparture().getDepCode();
        tourInfo.getArrival().getArrCode();
        return tourReserveOutput;
    }

    @Override
    public void cancel(String reserveNo) throws BusinessException {
        logger.debug("cancel reserveNo={}", reserveNo);
        Reserve reserve = reserveRepository.findOne(reserveNo);
        logger.debug("check for cancel {}", reserve);
        validateReservation(reserve);

        String transfer = reserve.getTransfer();
        if (Reserve.TRANSFERED.equals(transfer)) {
            ResultMessages message = ResultMessages.error().add(
                    MessageId.E_TR_0001);
            throw new BusinessException(message);
        }

        TourInfo info = reserve.getTourInfo();

        // compare system date and payment limit.
        // if the payment limit has been exceeded,
        // navigate to business error screen
        if (tourInfoSharedService.isOverPaymentLimitTour(info)) {
            ResultMessages message = ResultMessages.error().add(
                    MessageId.E_TR_0002);
            throw new BusinessException(message);
        }

        // cancel the reservation
        // reserve = reserveRepository.findForUpdate(reserveNo); TODO
        reserve = reserveRepository.findOneForUpdate(reserveNo);
        if (reserve != null) {
            reserveRepository.delete(reserveNo);
        } else {
            ResultMessages message = ResultMessages.error().add(
                    MessageId.E_TR_0003);
            throw new BusinessException(message);
        }
        logger.debug("canceled reserveNo={}", reserveNo);
    }

    @Override
    public void update(Reserve reserve) {
        reserveRepository.save(reserve);
    }

    @Override
    public ReservationUpdateOutput update(ReservationUpdateInput input) throws BusinessException {
        Reserve reserve = findOne(input.getReserveNo());

        dozerBeanMapper.map(input, reserve, "reserve_map_nonnull");

        TourInfo info = reserve.getTourInfo();
        PriceCalculateOutput price = priceCalculateService.calculatePrice(info
                .getBasePrice(), input.getAdultCount(), input.getChildCount());

        reserve.setSumPrice(price.getSumPrice());
        reserveRepository.save(reserve);

        ReservationUpdateOutput output = new ReservationUpdateOutput();
        output.setReserve(reserve);
        output.setPriceCalculateOutput(price);
        output.setPaymentTimeLimit(info.getPaymentLimit().toDate());

        // eager fetch to avoid lazy-init exception
        info.getDeparture().getDepCode();
        info.getArrival().getArrCode();

        return output;
    }

    /**
     * check whether the reservation is reserved by login user.
     * @param reserve
     * @throws SystemException
     */
    protected void validateReservation(Reserve reserve) throws SystemException {
        // String loginCustomerCode = authService.getAuthentication().getName();
        // Customer customer = reserve.getCustomer();
        // if (customer == null) {
        // // TODO
        // throw new SystemException(MessageId.E_TR_9001,
        // getMessage(MessageId.E_TR_9001));
        // }
        //
        // String targetCustomerCode = customer.getCustomerCode();
        // if (!loginCustomerCode.equals(targetCustomerCode)) {
        // // TODO
        // throw new SystemException(MessageId.E_TR_9002,
        // getMessage(MessageId.E_TR_9002));
        // }
    }

}
