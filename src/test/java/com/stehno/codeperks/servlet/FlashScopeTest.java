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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import com.stehno.codeperks.servlet.FlashScope;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class FlashScopeTest {

    private MockHttpServletRequest request;

    @Before
    public void before() {
        this.request = new MockHttpServletRequest();
    }

    @Test
    public void test_it(){
        final FlashScope flash = FlashScope.create( request );

        assertNotNull(flash.getId());
        assertNotNull(request.getAttribute( FlashScope.ATTRIBUTE_NAME ));

        final String key = "somekey";
        final Object value = new Object();

        flash.put( key, value );

        assertEquals( value, flash.get( key ) );
        assertTrue( flash.containsKey( key ) );
        assertTrue( flash.containsValue( value ) );
        assertEquals( 1, flash.size() );
        assertEquals( value, flash.remove( key ) );

        flash.put( key, value );
        assertFalse( flash.isEmpty() );

        assertEquals( value, flash.values().iterator().next() );
        assertEquals( key, flash.keySet().iterator().next() );
        assertEquals( value, flash.entrySet().iterator().next().getValue() );

        flash.clear();
        assertTrue( flash.isEmpty() );

        final Object other = new Object();
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("newkey",other);
        flash.putAll( map );

        assertTrue( flash.containsValue( other ) );
    }

    @Test
    public void test_equals_and_hash(){
        final FlashScope flash1 = FlashScope.create( request );
        final FlashScope flash2 = FlashScope.create( request );

        assertTrue(flash1.equals( flash1 ));
        assertEquals(flash1.hashCode(), flash1.hashCode());

        assertFalse(flash1.equals(flash2));
        assertFalse(flash1.hashCode() == flash2.hashCode());
    }

    @After
    public void after() {
        this.request = null;
    }
}
