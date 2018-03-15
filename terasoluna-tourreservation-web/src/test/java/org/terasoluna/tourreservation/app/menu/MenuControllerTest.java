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
package org.terasoluna.tourreservation.app.menu;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.terasoluna.tourreservation.app.menu.MenuController;

public class MenuControllerTest {

    private static final String MENU = "menu/menu";

    MockMvc mockMvc;

    /**
     * Check MenuController
     */
    @Test
    public void testInit() {
        MenuController controller = new MenuController();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Prepare get request
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(view()
                    .name(MENU));
            return;
        } catch (Exception e) {

            fail();
        }
    }

}
