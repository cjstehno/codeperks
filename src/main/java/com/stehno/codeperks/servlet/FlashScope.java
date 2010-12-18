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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Holder for data that is needed for the next request, but no longer.
 * 
 * General usage:
 * 
 *  FlashScope flash = FlashScope.create(request);
 *  flash.put("some","data");
 * 
 *  The flash scope will be automatically added to the outgoing request. The
 *  FlashScopeManager will handle the data exchange from the out-going request
 *  to the incoming request.
 * 
 *  The Map method implementations are delegated to an internal HashMap instance.
 * 
 * @author cjstehno
 */
public final class FlashScope implements Map<String, Object> {

    /**
     * Request attribute name used for flash scope that is to be extracted
     * from the request and stored for the next request.
     */
    public static final String ATTRIBUTE_NAME = FlashScope.class.getName();

    private final String id;
    private final Map<String, Object> data;

    private FlashScope(){
        this.data = new HashMap<String, Object>();
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Creates a new, empty flash scope object in the given request.
     * 
     * @param request the http request
     * @return an active flash scope container
     */
    public static FlashScope create(final HttpServletRequest request){
        final FlashScope flash = new FlashScope();
        request.setAttribute( ATTRIBUTE_NAME, flash );
        return flash;
    }

    /**
     * Used to retrive the id of this flash scope object. Used for testing.
     * 
     * @return the id of this flash scope
     */
    protected String getId(){
        return id;
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public boolean containsKey(final Object key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return data.containsValue(value);
    }

    @Override
    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return data.entrySet();
    }

    @Override
    public Object get(final Object key) {
        return data.get(key);
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return data.keySet();
    }

    @Override
    public Object put(final String key, final Object value) {
        return data.put(key, value);
    }

    @Override
    public void putAll(final Map<? extends String, ? extends Object> map) {
        data.putAll(map);
    }

    @Override
    public Object remove(final Object key) {
        return data.remove(key);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Collection<Object> values() {
        return data.values();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(7,13).append(id).append( data ).toHashCode();
    }

    @Override
    public boolean equals( final Object obj ) {
        boolean eq = false;
        if(obj instanceof FlashScope){
            final FlashScope fs = (FlashScope)obj;
            eq = new EqualsBuilder()
            .append(id,fs.id).append(data,fs.data)
            .isEquals();
        }
        return eq;
    }
}
