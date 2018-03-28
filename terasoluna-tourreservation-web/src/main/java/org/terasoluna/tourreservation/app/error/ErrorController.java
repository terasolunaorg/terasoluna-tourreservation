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
package org.terasoluna.tourreservation.app.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("error")
public class ErrorController {

    /**
     * Shows the access denied error view.
     * @return access denied error view
     */
    @RequestMapping("accessDeniedError")
    public String accessDeniedError() {
        return "common/error/accessDeniedError";
    }

    /**
     * Shows the resource not found error view.
     * @return resource not found error view
     */
    @RequestMapping("resourceNotFoundError")
    public String resourceNotFoundError() {
        return "common/error/resourceNotFoundError";
    }

    /**
     * Shows the system error view.
     * @return system error view
     */
    @RequestMapping("systemError")
    public String systemError() {
        return "common/error/systemError";
    }

    /**
     * Shows the invalid csrf token error view.
     * @return invalid csrf token error view
     */
    @RequestMapping("invalidCsrfTokenError")
    public String invalidCsrfTokenError() {
        return "common/error/invalidCsrfTokenError";
    }

    /**
     * Shows the missing csrf token error view.
     * @return missing csrf token error view
     */
    @RequestMapping("missingCsrfTokenError")
    public String missingCsrfTokenError() {
        return "common/error/missingCsrfTokenError";
    }
}
