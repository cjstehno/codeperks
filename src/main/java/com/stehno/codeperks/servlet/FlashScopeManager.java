/*
 * Copyright 2009 Christopher J. Stehno
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.stehno.codeperks.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Manager for the FlashScope mechanism.
 * 
 * @author cjstehno
 */
public interface FlashScopeManager {

    /**
     * Store the flash scope data, if any is found in the request and/or response.
     * 
     * @param request
     * @param response
     */
    void store(final HttpServletRequest request, final HttpServletResponse response);

    /**
     * Re-populate the flash scope data into the request and/or response.
     * 
     * @param request
     * @param response
     */
    void repopulate(final HttpServletRequest request, final HttpServletResponse response);
}
