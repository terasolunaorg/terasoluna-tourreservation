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

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CustomerPassEqualsValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (CustomerForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerForm customer = (CustomerForm) target;
        String pass = customer.getCustomerPass();
        String passConfirm = customer.getCustomerPassConfirm();
        if (pass == null || passConfirm == null) {
            // must be checked by @NotEmpty
            return;
        }
        if (!pass.equals(passConfirm)) {
            errors.rejectValue("customerPass", "NotEquals.customerPass",
                    "Password and password confirm is not same.");
        }
    }
}
