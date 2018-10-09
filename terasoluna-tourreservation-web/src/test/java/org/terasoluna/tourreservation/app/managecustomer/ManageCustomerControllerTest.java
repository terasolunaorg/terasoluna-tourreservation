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

import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.terasoluna.tourreservation.domain.model.Customer;
import org.terasoluna.tourreservation.domain.service.customer.CustomerService;

public class ManageCustomerControllerTest {

    MockMvc mockMvc;

    CustomerService customerService;

    @Before
    public void setUp() {

        // instantiate the controller to test
        ManageCustomerController manageCustomerController = new ManageCustomerController();

        // other members instantiation and assignment
        customerService = mock(CustomerService.class);
        CustomerPassEqualsValidator cpev = new CustomerPassEqualsValidator();
        CustomerBirthdayValidator cbv = new CustomerBirthdayValidator();

        Mapper beanMapper = DozerBeanMapperBuilder.buildDefault();

        // Whenever mapping files are required, can be set as shown below

        // List<String> mappingFiles = new ArrayList<String>();
        // mappingFiles.add("META-INF/dozer/managereservation-mapping.xml");
        // dozerBeanMapper.setMappingFiles(mappingFiles);

        manageCustomerController.customerService = customerService;
        manageCustomerController.passwordEqualsValidator = cpev;
        manageCustomerController.dateValidator = cbv;
        manageCustomerController.initialBirthYear = 2000;
        manageCustomerController.initialBirthMonth = 1;
        manageCustomerController.initialBirthDay = 1;
        manageCustomerController.beanMapper = beanMapper;

        // build
        mockMvc = MockMvcBuilders.standaloneSetup(manageCustomerController)
                .build();
    }

    /**
     * Dispatches the Create form request to MockServlet. The difference between this test and the Mockito only version is that
     * you’re not directly testing the result of the method call to your test instance; you’re testing the HttpServletResponse
     * object that the call to your method generates.<br>
     */
    @Test
    public void testCreateForm() {

        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/customers/create").param("form", "");

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managecustomer/createForm"));

            // Also check the default data set in setupForm method
            // this will test the @ModelAttribute annotation

            results.andExpect(model().attribute("customerForm", hasProperty(
                    "customerBirthYear", is(2000))));
            results.andExpect(model().attribute("customerForm", hasProperty(
                    "customerBirthMonth", is(1))));
            results.andExpect(model().attribute("customerForm", hasProperty(
                    "customerBirthDay", is(1))));

            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testCreateConfirmSuccess() {

        // Prepare request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/customers/create").param("confirm", "");

        // Prepare form and set to POST request
        CustomerForm form = prepareNewForm();
        setFormForPost(form, postRequest);

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managecustomer/createConfirm"));

            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testCreateConfirmFail() {

        // Prepare request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/customers/create").param("confirm", "");

        // Prepare form
        CustomerForm form = prepareNewForm();
        // overwrite some values so that validation errors can occur
        // This will test @InitBinder and @Validated annotations

        // entered password and confirm password are different
        // passwordEqualsValidator will report error
        form.setCustomerPassConfirm("aaaaaaaa");

        // Set invalid date
        // dateValidator will report error
        form.setCustomerBirthMonth(2);
        form.setCustomerBirthDay(30);

        // Set invalid email
        // This will confirm working of @Validated annotation
        form.setCustomerMail("a.com");

        // set form to POST request
        setFormForPost(form, postRequest);

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managecustomer/createForm"));
            results.andExpect(model().attributeErrorCount("customerForm", 3));
            results.andExpect(model().attributeHasFieldErrors("customerForm",
                    "customerBirthYear"));
            results.andExpect(model().attributeHasFieldErrors("customerForm",
                    "customerPass"));
            results.andExpect(model().attributeHasFieldErrors("customerForm",
                    "customerMail"));

            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    /**
     * Test back button request<br>
     */
    @Test
    public void testCreateRedo() {
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/customers/create").param("redo", "");

        // Set form to the request to simulate correct back button behaviour
        // Since the control will be on confirmation screen, form values may be different from the default
        // Prepare form and set to POST request
        CustomerForm form = prepareNewForm();
        setFormForPost(form, postRequest);

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managecustomer/createForm"));

            // TODO Ideally this should work but somehow not working !!!
            // This will reduce the effort of checking the form for each property
            // results.andExpect(model().attribute("customerForm", form));

            CustomerForm backForm = (CustomerForm) results.andReturn()
                    .getRequest().getAttribute("customerForm");

            assertEquals(form.toString(), backForm.toString());

            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown

    }

    /**
     * Test Customer create request<br>
     */
    @Test
    public void testCreateSuccess() {

        // Prepare request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/customers/create");

        when(customerService.register(any(Customer.class), eq("12345")))
                .thenReturn(new Customer("12345678"));

        CustomerForm form = prepareNewForm();
        setFormForPost(form, postRequest);

        try {
            ResultActions results = mockMvc.perform(postRequest);
            // check redirect http status : 302
            results.andExpect(status().isFound());
            results.andExpect(view().name(
                    "redirect:/customers/create?complete"));
            results.andExpect(flash().attribute("customer", notNullValue()));
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown

    }

    /**
     * Test Customer create request - Error case<br>
     */
    @Test
    public void testCreateFail() {
        // Prepare request
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/customers/create");

        when(customerService.register(any(Customer.class), eq("12345")))
                .thenReturn(new Customer("12345678"));

        CustomerForm form = prepareNewForm();

        // Set some field such that it will throw validation error
        form.setCustomerName(null);

        setFormForPost(form, postRequest);

        try {
            ResultActions results = mockMvc.perform(postRequest);
            results.andExpect(status().isOk());
            results.andExpect(model().attributeErrorCount("customerForm", 1));
            results.andExpect(view().name("managecustomer/createForm"));

            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    @Test
    public void testCreateCompleｔe() {

        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/customers/create").param("complete", "");

        try {
            ResultActions results = mockMvc.perform(getRequest);
            results.andExpect(status().isOk());
            results.andExpect(view().name("managecustomer/createComplete"));

            return;

        } catch (Exception e) {
            e.printStackTrace();
        }

        fail(); // FAIL when exception is thrown
    }

    private CustomerForm prepareNewForm() {
        CustomerForm form = new CustomerForm();
        form.setCustomerName("名前");
        form.setCustomerKana("カナ");
        form.setCustomerAdd("address");
        form.setCustomerPass("12345");
        form.setCustomerPassConfirm("12345");
        form.setCustomerBirthDay(2);
        form.setCustomerBirthMonth(2);
        form.setCustomerBirthYear(2002);
        form.setCustomerPost("123-3344");
        form.setCustomerTel("12-121212333");
        form.setCustomerMail("a@a.com");

        return form;
    }

    private void setFormForPost(CustomerForm form,
            MockHttpServletRequestBuilder postRequest) {
        String arrParam[] = form.toString().split("&");
        for (String param : arrParam) {
            String arrKeyVal[] = param.split("=");
            postRequest.param(arrKeyVal[0], arrKeyVal[1]);
        }
    }
}
