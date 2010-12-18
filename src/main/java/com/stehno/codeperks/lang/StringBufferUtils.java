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
package com.stehno.codeperks.lang;

/**
 *	Utilities useful for working with StringBuffers and StringBuilders.
 *
 * @author Christopher J. Stehno (chris@stehno.com)
 */
public final class StringBufferUtils {

	private StringBufferUtils(){}

	/**
	 * Trims the whitespace (if any) from the front and back of the given
	 * StringBuffer. The incoming buffer is directly modified, though it is
	 * also returned for convenience.
	 *
	 * If null is passed in, null will be returned. If an empty (or all
	 * whitespace) buffer is passed in, an empty buffer will be returned.
	 *
	 * @param sb the StringBuffer to be trimmed
	 * @return the same StringBuffer instance as was passed in
	 */
	public static final StringBuffer trim(final StringBuffer sb){
		if(sb == null) return null;

		while( sb.length() > 0 && Character.isWhitespace( sb.charAt(sb.length()-1) ) ){
			sb.deleteCharAt(sb.length()-1);
		}
		while( sb.length() > 0 && Character.isWhitespace( sb.charAt(0) ) ){
			sb.deleteCharAt(0);
		}
		return sb;
	}

	/**
	 * Trims the whitespace (if any) from the front and back of the given
	 * StringBuilder. The incoming builder is directly modified, though it is
	 * also returned for convenience.
	 *
	 *	If null is passed in, null will be returned. If an empty (or all
	 * 	whitespace) builder is passed in, an empty builder will be returned.
	 *
	 * @param sb the StringBuilder to be trimmed
	 * @return the same StringBuilder instance as was passed in
	 */
	public static final StringBuilder trim(final StringBuilder sb){
		if(sb == null) return null;

		while( sb.length() > 0 && Character.isWhitespace( sb.charAt(sb.length()-1) ) ){
			sb.deleteCharAt(sb.length()-1);
		}
		while( sb.length() > 0 && Character.isWhitespace( sb.charAt(0) ) ){
			sb.deleteCharAt(0);
		}
		return sb;
	}
}
