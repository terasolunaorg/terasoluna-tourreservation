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
package org.terasoluna.tourreservation.app.managecustomer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.FieldError;
import org.terasoluna.tourreservation.app.managecustomer.CustomerForm;
import org.terasoluna.tourreservation.app.managecustomer.CustomerPassEqualsValidator;

public class CustomerPassEqualsValidatorTest {

    /**
     * check validate normal return
     */
    @Test
    public void testValidate01() {
        CustomerPassEqualsValidator validator = new CustomerPassEqualsValidator();
        CustomerForm customer = new CustomerForm();
        customer.setCustomerPass("12345");
        customer.setCustomerPassConfirm("12345");

        BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

        // run
        validator.validate(customer, result);

        // assert
        assertThat(result.hasErrors(), is(false));
        assertThat(result.getErrorCount(), is(0));
    }

    /**
     * password check error
     */
    @Test
    public void testValidate02() {
        CustomerPassEqualsValidator validator = new CustomerPassEqualsValidator();
        CustomerForm customer = new CustomerForm();
        customer.setCustomerPass("12345");
        customer.setCustomerPassConfirm("1234");

        BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

        // run
        validator.validate(customer, result);

        // assert
        assertThat(result.hasErrors(), is(true));

        FieldError error = result.getFieldError("customerPass");

        if (error != null) {
            assertThat(error.getCode(), is("NotEquals.customerPass"));
            assertThat(error.getDefaultMessage(), is(
                    "Password and password confirm is not same."));
        } else {
            fail("error");
        }

    }

    /**
     * check validate return nothing passConfirm is null
     */
    @Test
    public void testValidate03() {
        CustomerPassEqualsValidator validator = new CustomerPassEqualsValidator();
        CustomerForm customer = new CustomerForm();
        customer.setCustomerPass("12345");

        BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

        // run
        validator.validate(customer, result);

        // assert
        assertThat(result.hasErrors(), is(false));
        assertThat(result.getErrorCount(), is(0));
    }

    /**
     * check validate return nothing password is null
     */
    @Test
    public void testValidate04() {
        CustomerPassEqualsValidator validator = new CustomerPassEqualsValidator();
        CustomerForm customer = new CustomerForm();
        customer.setCustomerPassConfirm("12345");

        BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

        // run
        validator.validate(customer, result);

        // assert
        assertThat(result.hasErrors(), is(false));
        assertThat(result.getErrorCount(), is(0));

    }

}
