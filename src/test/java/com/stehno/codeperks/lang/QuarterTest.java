/*
 *	Copyright 2006 Christopher J. Stehno (chris@stehno.com)
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

import java.util.Calendar;

import org.junit.Test;

public class QuarterTest {

	@Test
	public void start_and_end(){
		assertStartAndEnd( Quarter.FIRST, Calendar.JANUARY, Calendar.MARCH );
		assertStartAndEnd( Quarter.SECOND, Calendar.APRIL, Calendar.JUNE);
		assertStartAndEnd( Quarter.THIRD, Calendar.JULY, Calendar.SEPTEMBER);
		assertStartAndEnd( Quarter.FOURTH, Calendar.OCTOBER, Calendar.DECEMBER);
	}

	private void assertStartAndEnd(final Quarter q, final int start, final int end) {
		assertEquals( start, q.startMonth() );
		assertEquals( end, q.endMonth() );
	}

	@Test
	public void valueOf(){
		assertEquals(Quarter.FIRST,Quarter.valueOf(0));
		assertEquals(Quarter.FIRST,Quarter.valueOf(1));
		assertEquals(Quarter.FIRST,Quarter.valueOf(2));

		assertEquals(Quarter.SECOND,Quarter.valueOf(3));
		assertEquals(Quarter.SECOND,Quarter.valueOf(4));
		assertEquals(Quarter.SECOND,Quarter.valueOf(5));

		assertEquals(Quarter.THIRD,Quarter.valueOf(6));
		assertEquals(Quarter.THIRD,Quarter.valueOf(7));
		assertEquals(Quarter.THIRD,Quarter.valueOf(8));

		assertEquals(Quarter.FOURTH,Quarter.valueOf(9));
		assertEquals(Quarter.FOURTH,Quarter.valueOf(10));
		assertEquals(Quarter.FOURTH,Quarter.valueOf(11));
	}

	@Test
	public void previous(){
		assertEquals(Quarter.FOURTH,Quarter.FIRST.previous());
		assertEquals(Quarter.FIRST,Quarter.SECOND.previous());
		assertEquals(Quarter.SECOND,Quarter.THIRD.previous());
		assertEquals(Quarter.THIRD,Quarter.FOURTH.previous());
	}

	@Test
	public void next(){
		assertEquals(Quarter.SECOND,Quarter.FIRST.next());
		assertEquals(Quarter.THIRD,Quarter.SECOND.next());
		assertEquals(Quarter.FOURTH,Quarter.THIRD.next());
		assertEquals(Quarter.FIRST,Quarter.FOURTH.next());
	}
}
