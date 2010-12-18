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
package com.stehno.codeperks.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class StringBufferUtilsTest {

	private static final StringBuffer BUFFER_2SIDE = new StringBuffer("  alpha bravo charlie   ");
	private static final StringBuilder BUILDER_2SIDE = new StringBuilder(BUFFER_2SIDE.toString());
	private static final String TRIMMED = "alpha bravo charlie";

	@Test
	public void trim_stringbuffer(){
		assertSameWithContent(BUFFER_2SIDE, StringBufferUtils.trim(BUFFER_2SIDE), TRIMMED);
	}

	@Test
	public void trim_stringbuilder(){
		assertSameWithContent(BUILDER_2SIDE, StringBufferUtils.trim(BUILDER_2SIDE), TRIMMED);
	}

	@Test
	public void trim_stringbuffer_null(){
		assertNull(StringBufferUtils.trim((StringBuffer)null));
	}

	@Test
	public void trim_stringbuilder_null(){
		assertNull(StringBufferUtils.trim((StringBuilder)null));
	}

	@Test
	public void trim_stringbuffer_empty(){
		final StringBuffer sb = new StringBuffer("   ");
		assertSameWithContent(sb, StringBufferUtils.trim(sb), "");
	}

	@Test
	public void trim_stringbuilder_empty(){
		final StringBuilder sb = new StringBuilder("   ");
		assertSameWithContent(sb, StringBufferUtils.trim(sb), "");
	}

	private void assertSameWithContent(final Object expected,final Object result, final String target) {
		assertEquals(expected,result);
		assertSame(expected,result);
		assertEquals(target,result.toString());
	}
}
