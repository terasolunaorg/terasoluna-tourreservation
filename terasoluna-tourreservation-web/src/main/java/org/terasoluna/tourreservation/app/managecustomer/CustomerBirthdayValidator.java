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
package org.terasoluna.tourreservation.app.managecustomer;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CustomerBirthdayValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (CustomerForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerForm customer = (CustomerForm) target;
        try {
            new DateTime(customer.getCustomerBirthYear(), customer
                    .getCustomerBirthMonth(), customer
                            .getCustomerBirthDay(), 0, 0).toDate();
        } catch (IllegalArgumentException e) {
            errors.rejectValue("customerBirthYear",
                    "IncorrectDate.customerBirth",
                    "Incorrect date was entered.");
        }

    }
}
