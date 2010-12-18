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

import static org.junit.Assert.assertTrue;
import com.stehno.codeperks.servlet.FlashScopeManager;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@RunWith(JMock.class)
public class FlashScopeInterceptorTest {

    private Mockery mockery = new JUnit4Mockery();
    private FlashScopeInterceptor interceptor;
    private FlashScopeManager manager;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void before() {
        this.request = new MockHttpServletRequest();
        this.response = new MockHttpServletResponse();

        this.manager = mockery.mock( FlashScopeManager.class );
        this.interceptor = new FlashScopeInterceptor(manager);
    }

    @Test
    public void preHandle() throws Exception {
        mockery.checking( new Expectations(){
            {
                one(manager).repopulate( request, response );
            }
        } );

        final boolean cont = interceptor.preHandle( request, response, new Object() );
        assertTrue(cont);
    }

    @Test
    public void postHandle() throws Exception {
        mockery.checking( new Expectations(){
            {
                one(manager).store( request, response );
            }
        } );

        interceptor.postHandle( request, response, new Object(), null );
    }

    @Test
    public void afterCompletion() throws Exception {
        // test that it does nothing
        interceptor.afterCompletion( null, null, null, null );
    }

    @After
    public void after() {
        this.interceptor = null;
        this.manager = null;
        this.request = null;
        this.response = null;
    }
}
