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
package org.terasoluna.tourreservation.domain.service.customer;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.sequencer.Sequencer;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.repository.customer.CustomerRepository;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {
    @Inject
    CustomerRepository customerRepository;

    @Inject
    @Named("customerCodeSeq")
    Sequencer<String> customerCodeSeq;

    @Inject
    PasswordEncoder passwordEncoder;

    @Override
    public Customer findOne(String customerCode) {
        return customerRepository.findOne(customerCode);
    }

    @Override
    public Customer register(Customer customer, String rawPassword) {
        String customerCode = customerCodeSeq.getNext();

        String password = passwordEncoder.encode(rawPassword);

        customer.setCustomerCode(customerCode);
        customer.setCustomerPass(password);
        return customerRepository.save(customer);
    }

}
