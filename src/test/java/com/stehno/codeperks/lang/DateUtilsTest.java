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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateUtilsTest {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy HH:mm:ss.SSS");
	private static final String NOW = "10/10/2006 10:14:34.956";

	@Test
	public void unreachable(){
		final Date faroff = DateUtils.unreachableDate();
		final Date faroff2 = DateUtils.unreachableDate();
		assertEquals(new Date(Long.MAX_VALUE), faroff);
		assertSame(faroff,faroff2);
	}

	@Test
	public void get_this_month_last_year(){
		final DateRange thisMonthLastYear = DateUtils.getThisMonthLastYear( createDate(NOW) );
		assertEquals( createDateRange("10/1/2005 00:00:00.000","10/31/2005 23:59:59.999"), thisMonthLastYear );
	}

	@Test
	public void get_quarter_to_date(){
		String start = "1/1/2006 00:00:00.000";
		execQuarterToDate(start,"1/1/2006 0:00:00.000");
		execQuarterToDate(start,"1/21/2006 22:06:00.015");
		execQuarterToDate(start,"2/21/2006 09:45:00.214");
		execQuarterToDate(start,"3/31/2006 20:15:00.900");

		start = "4/1/2006 00:00:00.000";
		execQuarterToDate(start,"4/1/2006 00:00:00.000");
		execQuarterToDate(start,"4/21/2006 07:28:00.005");
		execQuarterToDate(start,"5/21/2006 13:22:00.546");
		execQuarterToDate(start,"6/30/2006 23:56:00.000");

		start = "7/1/2006 00:00:00.000";
		execQuarterToDate(start,"7/1/2006 00:00:00.000");
		execQuarterToDate(start,"7/21/2006 07:28:00.896");
		execQuarterToDate(start,"8/21/2006 13:22:00.356");
		execQuarterToDate(start,"9/30/2006 23:56:00.345");

		start = "10/1/2006 00:00:00.000";
		execQuarterToDate(start,"10/1/2006 00:00:00.000");
		execQuarterToDate(start,"10/21/2006 07:28:00.000");
		execQuarterToDate(start,"11/21/2006 13:22:00.956");
		execQuarterToDate(start,"12/31/2006 23:56:00.999");
	}

	@Test
	public void get_quarter(){
		assertEquals(createDateRange("1/1/2005 00:00:00.000","3/31/2005 23:59:59.999"),DateUtils.getQuarter(2005,Quarter.FIRST));
		assertEquals(createDateRange("4/1/2005 00:00:00.000","6/30/2005 23:59:59.999"),DateUtils.getQuarter(2005,Quarter.SECOND));
		assertEquals(createDateRange("7/1/2005 00:00:00.000","9/30/2005 23:59:59.999"),DateUtils.getQuarter(2005,Quarter.THIRD));
		assertEquals(createDateRange("10/1/2005 00:00:00.000","12/31/2005 23:59:59.999"),DateUtils.getQuarter(2005,Quarter.FOURTH));
	}

	@Test
	public void get_previous_quarter(){
		Date now = createDate("1/15/2006 13:56:00.000");
		assertEquals(createDateRange("10/1/2005 00:00:00.000","12/31/2005 23:59:59.999"),DateUtils.getPreviousQuarter(now));

		now = createDate("4/15/2006 13:56:00.000");
		assertEquals(createDateRange("1/1/2006 00:00:00.000","3/31/2006 23:59:59.999"),DateUtils.getPreviousQuarter(now));

		now = createDate("8/15/2006 13:56:00.000");
		assertEquals(createDateRange("4/1/2006 00:00:00.000","6/30/2006 23:59:59.999"),DateUtils.getPreviousQuarter(now));

		now = createDate("12/15/2006 13:56:00.000");
		assertEquals(createDateRange("7/1/2006 00:00:00.000","9/30/2006 23:59:59.999"),DateUtils.getPreviousQuarter(now));
	}

	@Test
	public void testGetYesterday(){
		assertEquals(createDateRange("12/31/2004 00:00:00.000","12/31/2004 23:59:59.999"),DateUtils.getYesterday(createDate("1/1/2005 00:15:00.987")));
		assertEquals(createDateRange("10/20/2005 00:00:00.000","10/20/2005 23:59:59.999"),DateUtils.getYesterday(createDate("10/21/2005 00:15:00.987")));
	}

	@Test
	public void testGetToday(){
		assertEquals(createDateRange("10/21/2005 00:00:00.000","10/21/2005 23:59:59.999"),DateUtils.getToday(createDate("10/21/2005 00:15:00.987")));
	}

	@Test
	public void testGetLastXDays(){
		String now = "10/1/2006 10:00:34.857";
		assertEquals(createDateRange("9/27/2006 00:00:00.000","9/30/2006 23:59:59.999"),DateUtils.getLastXDays(createDate(now),4));

		now = "1/1/2006 10:00:34.857";
		assertEquals(createDateRange("12/28/2005 00:00:00.000","12/31/2005 23:59:59.999"),DateUtils.getLastXDays(createDate(now),4));
	}

	@Test
	public void testGetYearToDate(){
		assertEquals(createDateRange("1/1/2006 00:00:00.000",NOW),DateUtils.getYearToDate(createDate(NOW)));
	}

	@Test
	public void testGetMonthToDate(){
		assertEquals(createDateRange("10/1/2006 00:00:00.000",NOW),DateUtils.getMonthToDate(createDate(NOW)));
	}

	@Test
	public void testGetPreviousYear(){
		assertEquals(createDateRange("1/1/2005 00:00:00.000","12/31/2005 23:59:59.999"),DateUtils.getPreviousYear(createDate(NOW)));
	}

	@Test
	public void testGetPreviousWeek(){
		assertEquals(createDateRange("4/9/2006 00:00:00.000","4/15/2006 23:59:59.999"),DateUtils.getPreviousWeek(createDate("4/18/2006 17:54:00.000")));
	}

	@Test
	public void testGetPreviousMonth(){
		assertEquals(createDateRange("9/1/2006 00:00:00.000","9/30/2006 23:59:59.999"),DateUtils.getPreviousMonth(createDate(NOW)));
	}

	private void execQuarterToDate(final String start, final String end){
		assertEquals(createDateRange(start,end),DateUtils.getQuarterToDate(createDate(end)));
	}

	private static DateRange createDateRange(final String start, final String end){
		return(new DateRange(createDate(start),createDate(end)));
	}

	private static Date createDate(final String val){
		Date d = null;
		try {
			d = dateFormat.parse(val);
		} catch(final ParseException pe){
			fail(pe.getMessage());
		}
		return(d);
	}
}
