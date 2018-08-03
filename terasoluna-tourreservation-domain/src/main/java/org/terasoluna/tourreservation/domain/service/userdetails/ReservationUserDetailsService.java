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
package org.terasoluna.tourreservation.domain.service.userdetails;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.repository.customer.CustomerRepository;

public class ReservationUserDetailsService implements UserDetailsService {
    @Inject
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findById(username).orElse(null);
        if (customer == null) {
            throw new UsernameNotFoundException(username + " is not found.");
        }
        return new ReservationUserDetails(customer);
    }

}
