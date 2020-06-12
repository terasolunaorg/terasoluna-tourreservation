/*
 * Copyright(c) 2013 NTT DATA Corporation.
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
package org.terasoluna.tourreservation.app.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("error")
public class ErrorController {

    /**
     * Shows the access denied error view.
     * @return access denied error view
     */
    @RequestMapping(value = "accessDeniedError", method = RequestMethod.GET)
    public String accessDeniedError() {
        return "common/error/accessDeniedError";
    }

    /**
     * Shows the resource not found error view.
     * @return resource not found error view
     */
    @RequestMapping(value = "resourceNotFoundError", method = RequestMethod.POST)
    public String resourceNotFoundError() {
        return "common/error/resourceNotFoundError";
    }

    /**
     * Shows the system error view.
     * @return system error view
     */
    @RequestMapping(value = "systemError", method = RequestMethod.POST)
    public String systemError() {
        return "common/error/systemError";
    }

    /**
     * Shows the invalid csrf token error view.
     * @return invalid csrf token error view
     */
    @RequestMapping(value = "invalidCsrfTokenError", method = RequestMethod.POST)
    public String invalidCsrfTokenError() {
        return "common/error/invalidCsrfTokenError";
    }

    /**
     * Shows the missing csrf token error view.
     * @return missing csrf token error view
     */
    @RequestMapping(value = "missingCsrfTokenError", method = RequestMethod.POST)
    public String missingCsrfTokenError() {
        return "common/error/missingCsrfTokenError";
    }
}
