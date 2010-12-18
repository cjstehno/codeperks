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

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An operation retry container which will attempt a given Retriable operation
 * until it either succeeds or the number of retries exceeds the maximum allowable.
 * 
 * @author cjstehno
 */
public class Retrier {

	private static final Log log = LogFactory.getLog(Retrier.class);
	private Class<? extends Exception>[] catchAndThrow;
	private int maxRetries;

	public Retrier(final int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public Retrier() {
		this(5);
	}

	public void setCatchAndThrow(final Class<? extends Exception>[] catchAndThrow) {
		this.catchAndThrow = catchAndThrow;
	}

	public void setMaxRetries(final int maxRetries) {
		this.maxRetries = maxRetries;
	}

	/**
	 * Execute the given Retriable operation.
	 * 
	 * @param <T> The type of return value for the operation
	 * @param op the operation
	 * @return the return value of the operation (or null)
	 * @throws Exception if there is a problem executing the operation or when the max
	 * retries have been exceeded.
	 */
	public <T> T execute( final Retriable<T> op ) throws Exception {
		boolean retry = true;
		int count = 0;
		do {
			try {
				return op.execute();

			} catch(final Exception e) {
				if( ArrayUtils.contains(catchAndThrow, e.getClass()) ) throw e;

				retry = ++count < maxRetries;
				if(log.isWarnEnabled() && !retry) {
					log.warn("RetriesFailed[" + op.getClass().getName() + "]: " + e.getMessage(), e);
				}
			}
		} while( retry );

		throw new MaxRetriesExceededException(maxRetries,op.getClass().getName());
	}
}
