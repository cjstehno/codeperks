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
 * Exception denoting the case when the maximum number of retries has been exceeded.
 * 
 * @author cjstehno
 */
public class MaxRetriesExceededException extends Exception {

	private static final long serialVersionUID = 743303439720893377L;
	private int max;

	/**
	 * Creates a new MaxRetriesExceededException reporting the given number of max
	 * retries and a message.
	 * 
	 * @param max the max number of retries exceeded
	 * @param message the message
	 */
	public MaxRetriesExceededException(final int max, final String message){
		super(message);
		this.max = max;
	}

	/**
	 * Retrieves the max number of retries that was exceeded.
	 * 
	 * @return
	 */
	public int getMax() {
		return max;
	}
}
