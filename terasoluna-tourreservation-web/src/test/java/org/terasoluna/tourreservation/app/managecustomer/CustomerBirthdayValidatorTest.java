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
import org.terasoluna.tourreservation.app.managecustomer.CustomerBirthdayValidator;
import org.terasoluna.tourreservation.app.managecustomer.CustomerForm;

public class CustomerBirthdayValidatorTest {

    /**
     * check validate normal return
     */
    @Test
    public void testValidate01() {
        CustomerBirthdayValidator validator = new CustomerBirthdayValidator();

        CustomerForm customer = new CustomerForm();
        customer.setCustomerBirthYear(2011);
        customer.setCustomerBirthMonth(2);
        customer.setCustomerBirthDay(28);

        BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

        // run
        validator.validate(customer, result);

        // assert
        assertThat(result.hasErrors(), is(false));
    }

    /**
     * Date parse Error
     */
    @Test
    public void testValidate02() {
        CustomerBirthdayValidator validator = new CustomerBirthdayValidator();

        CustomerForm customer = new CustomerForm();
        customer.setCustomerBirthYear(2011);
        customer.setCustomerBirthMonth(02);
        customer.setCustomerBirthDay(29);

        BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

        // run
        validator.validate(customer, result);

        // assert
        assertThat(result.hasErrors(), is(true));

        FieldError error = result.getFieldError("customerBirthYear");

        if (error != null) {
            assertThat(error.getCode(), is("IncorrectDate.customerBirth"));
            assertThat(error.getDefaultMessage(), is(
                    "Incorrect date was entered."));

        } else {
            fail("error");
        }
    }

    /**
     * check yeap year
     */
    @Test
    public void testValidate03() {
        CustomerBirthdayValidator validator = new CustomerBirthdayValidator();

        CustomerForm customer = new CustomerForm();
        customer.setCustomerBirthYear(2012);
        customer.setCustomerBirthMonth(2);
        customer.setCustomerBirthDay(29);

        BindingResult result = new DirectFieldBindingResult(customer, "CustomerForm");

        // run
        validator.validate(customer, result);

        // assert
        assertThat(result.hasErrors(), is(false));
    }
}
