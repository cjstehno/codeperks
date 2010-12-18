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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *	@author Christopher J. Stehno (chris@stehno.com)
 */
public class CookieBasedFlashScopeManagerTest {

	private static final String FIELDNAME_SCOPE = "scope";
	private CookieBasedFlashScopeManager manager;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void before() {
		this.response  = new MockHttpServletResponse();
		this.request = new MockHttpServletRequest();

		this.manager = new CookieBasedFlashScopeManager();
		manager.setMaxAge( 45 );
	}

	@Test
	public void store_nothing(){
		manager.store( request, response );

		final Map<String, FlashScope> scope = extractScopeMap();
		assertEquals(0, scope.size() );
		assertEquals(0, response.getCookies().length );
	}

	@Test
	public void store_something(){
		final FlashScope flash = FlashScope.create( request );

		manager.store( request, response );

		final Map<String, FlashScope> scope = extractScopeMap();
		assertEquals(1, scope.size() );

		final Cookie[] cookies = response.getCookies();
		assertEquals(1,  cookies.length );

		assertEquals( "flash.scope.id:1", cookies[0].getName() );
		assertEquals( flash.getId(), cookies[0].getValue() );
		assertEquals( 45, cookies[0].getMaxAge() );
	}

	@Test
	public void repopulate_nothing(){
		manager.repopulate( request, response );

		assertNull( request.getAttribute( "flash" ));
	}

	@Test
	public void store_and_repopulate(){
		FlashScope.create( request );
		manager.store( request, response );

		final Cookie[] cookies = response.getCookies();

		// simulate a new request

		final MockHttpServletRequest secondRequest = new MockHttpServletRequest();
		secondRequest.setSession( request.getSession() );
		secondRequest.setCookies( cookies );

		final MockHttpServletResponse secondResponse = new MockHttpServletResponse();

		manager.repopulate( secondRequest, secondResponse );

		final FlashScope flashScope = (FlashScope)secondRequest.getAttribute( "flash" );
		assertNotNull( flashScope );
	}

	@After
	public void after() {
		this.request = null;
		this.response = null;
		this.manager = null;
	}

	@SuppressWarnings("unchecked")
	private Map<String, FlashScope> extractScopeMap() {
		final Map<String,FlashScope> scope = (Map<String, FlashScope>)ReflectionTestUtils.getField( manager, FIELDNAME_SCOPE );
		return scope;
	}
}
