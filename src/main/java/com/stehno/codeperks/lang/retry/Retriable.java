/*
 *	Copyright 2009 Christopher J. Stehno (chris@stehno.com)
 *
 * 	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 * 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package com.stehno.codeperks.lang.retry;

/**
 * The interface used to define a Retriable operation for with with the
 * {@link Retrier}.
 * 
 * @author cjstehno
 * @param <T>
 */
public interface Retriable<T> {

	/**
	 * Execute the operation with the given (or null) return value.
	 * 
	 * @return any return value from the operation
	 * @throws Exception
	 */
	public T execute() throws Exception;
}
