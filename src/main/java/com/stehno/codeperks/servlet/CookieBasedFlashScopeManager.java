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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie-based implementation of the FlashScopeManager interface. Uses a
 * short-term cookie to store the flash scope key mapping per request.
 * 
 * @author cjstehno
 */
public class CookieBasedFlashScopeManager implements FlashScopeManager {

    private static final int DEFAULT_MAXAGE = 30;
    private static final String SCOPE_KEY = "flash";
    private static final String COOKIE_NAME = "flash.scope.id:";
    private final Map<String, FlashScope> scope;
    private int maxAge = DEFAULT_MAXAGE;

    /**
     * Creates a cookie-based {@link FlashScopeManager}.
     */
    public CookieBasedFlashScopeManager(){
        this.scope = new HashMap<String, FlashScope>();
    }

    /**
     * Specify the maximum age value for the tracking cookie (in seconds). The default is
     * 30 seconds.
     * 
     * @param maxAge the maximum age in seconds of the tracking cookie.
     */
    public void setMaxAge( final int maxAge ) {
        this.maxAge = maxAge;
    }

    /**
     * Used to store the flash scope data, if any is found in the request. The flash scope data
     * will be a FlashScope object stored as a request attribute with the FlashScope.ATTRIBUTE_NAME
     * used as the mapping key.
     * 
     * @param request the http request
     * @param response the http response
     */
    @Override
    public void store(final HttpServletRequest request, final HttpServletResponse response) {
        final FlashScope flash = (FlashScope) request.getAttribute(FlashScope.ATTRIBUTE_NAME);
        if(flash != null){
            final Cookie cookie = new Cookie(cookieName( request.getSession(true).getId() ),flash.getId());
            cookie.setMaxAge(maxAge);

            response.addCookie(cookie);

            scope.put(flash.getId(), flash);
        }
    }

    /**
     * Used to repopulate the flash scope data into the incoming request, based on the presence
     * of the tracking cookie. If the tracking cookie is present, the flash data will be pulled
     * from the storage map and added to the request as request attribute data.
     * 
     * @param request the http request
     * @param response the http response
     */
    @Override
    public void repopulate(final HttpServletRequest request, final HttpServletResponse response) {
        final Cookie flashCookie = findCookie( request );
        if(flashCookie != null){
            // delete the cookie
            flashCookie.setMaxAge(0);
            response.addCookie(flashCookie);

            final String flashId = flashCookie.getValue();
            if(flashId != null){
                final FlashScope flash = scope.remove( flashId );
                if(flash != null){
                    request.setAttribute(SCOPE_KEY, flash);
                }
            }
        }
    }

    /**
     * Renders the cookie name based on the session id.
     * 
     * @param sessionId the current session id
     * @return the sessionId-based cookie name
     */
    private String cookieName(final String sessionId){
        return COOKIE_NAME + sessionId;
    }

    /**
     * Finds the flash scope tracking cookie if it exists in the given request.
     * 
     * @param request
     * @return the flash scope tracking cookie or null.
     */
    private Cookie findCookie( final HttpServletRequest request ) {
        Cookie flashCookie = null;
        final Cookie[] cookies = request.getCookies();
        if(cookies != null){
            final String cookieName = cookieName( request.getSession(true).getId() );
            for(final Cookie cookie : cookies){
                if(cookieName.equals(cookie.getName())){
                    flashCookie = cookie;
                    break;
                }
            }
        }
        return flashCookie;
    }
}
