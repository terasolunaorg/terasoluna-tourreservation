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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.terasoluna.gfw.common.sequencer.Sequencer;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.repository.customer.CustomerRepository;

public class CustomerServiceImplTest {
    CustomerServiceImpl customerService;

    CustomerRepository customerRepository;

    Sequencer<String> sequencer;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        customerService = new CustomerServiceImpl();
        customerRepository = mock(CustomerRepository.class);
        customerService.customerRepository = customerRepository;
        customerService.passwordEncoder = new BCryptPasswordEncoder();
        sequencer = mock(Sequencer.class);
        customerService.customerCodeSeq = sequencer;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindOne01() {
        Customer c = new Customer();
        when(customerRepository.findById("xxx")).thenReturn(Optional.of(c));

        Customer result = customerService.findOne("xxx");
        assertThat(result, is(c));
    }

    @Test
    public void testFindOne02() {
        when(customerRepository.findById("xxx")).thenReturn(Optional.empty());

        Customer result = customerService.findOne("xxx");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void testRegister01() {
        Customer c = new Customer();
        when(sequencer.getNext()).thenReturn("12345678");
        customerService.register(c, "foo");

        ArgumentCaptor<Customer> customerArg = ArgumentCaptor.forClass(
                Customer.class);

        verify(customerRepository, times(1)).save(customerArg.capture());
        assertThat(customerArg.getValue(), is(c));
        assertThat(customerArg.getValue().getCustomerCode(), is("12345678"));
        /*
         * assertThat(customerArg.getValue().getCustomerPass(), is("foo{12345678}"));
         */
    }

}
