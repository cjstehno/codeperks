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
package com.stehno.codeperks.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stehno.codeperks.servlet.FlashScopeManager;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Spring HandlerInterceptor implementation used to integrate flash scope handling
 * into the Spring MVC framework.
 * 
 * Simply register this interceptor with the handler mapping and configure a FlashScopeManager.
 * 
 * @author cjstehno
 */
public class FlashScopeInterceptor implements HandlerInterceptor {

    private final FlashScopeManager flashScopeManager;

    /**
     * Creates a FlashScopeInterceptor with the given FlashScopeManager.
     * 
     * @param flashScopeManager the FlashScopeManager to be used.
     */
    public FlashScopeInterceptor(final FlashScopeManager flashScopeManager){
        this.flashScopeManager = flashScopeManager;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,final Object handler) throws Exception {
        flashScopeManager.repopulate( request, response );
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response,final Object handler, final ModelAndView mav) throws Exception {
        flashScopeManager.store( request, response );
    }

    @Override
    public void afterCompletion(final HttpServletRequest request,final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
        // nothing
    }
}
