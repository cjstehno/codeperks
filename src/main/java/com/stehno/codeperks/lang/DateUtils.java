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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *  This class contains a set of static methods used in the creation and dates and date ranges. These
 *  methods are used to determine the starting and ending dates for the given date range using the
 *  specified GregorianCalendar object. These methods work on a clone of the Date object,
 *  therefore the Date reference passed is not modified.<br><br>
 *
 *  This class assumes immutable Date treatment since they only mutators for Date objects
 *  are deprecated.
 *
 *  <b>Usage:</b><br>
 *  The <code>DateUtils</code> class provides a powerful tool for determining the starting and ending dates for
 *  commonly used ranges such as the previous month, previous quarter or previous week. Most all of the
 *  methods require a <code>Date</code> object as a parameter. This is so that the reference date may be
 *  retrieved. The date passed to the method is not modified by the method.<br/>
 *  A <code>DateRange</code> object is returned as the result of most of the methods. This object contains the starting
 *  and ending date values for the calculated time span.
 *  <br><br>
 *
 *  @see java.util.GregorianCalendar
 *  @see java.util.Date
 *
 *  @author Christopher J. Stehno (chris@stehno.com)
 */
public class DateUtils {

	private static volatile Date UNREACHABLE = null;

	private DateUtils(){
		super();
	}

	/**
	 * Provides an "unreachable" date. This date is lazily created and then
	 * reused for subsequent calls.
	 *
	 * @return a Date object very far in the future so as to be unreachable
	 * @see <a href="http://coffeaelectronica.com/blog/2005/02/end-of-time-well-date-anyway/">End of Time, Well Date Anyway</a>
	 */
	public static final Date unreachableDate(){
		// Note: as of Java 5, double checked locking is fixed
		if(UNREACHABLE == null){
			synchronized (DateUtils.class) {
				if(UNREACHABLE == null) UNREACHABLE = new Date(Long.MAX_VALUE);
			}
		}
		return UNREACHABLE;
	}

	/**
	 *   This method is used to determine the date range for the current quarter to date.
	 *
	 *   @param date the Date that the date range is to be based on
	 *   @return an array of two Date objects, the first of which is the start date, the second the end date of the date range
	 */
	public static DateRange getQuarterToDate(final Date date){
		final GregorianCalendar c = makeCalendar(date);
		final DateRange range = new DateRange();

		range.setEnd(c.getTime());

		// Get the dates for the start and the end of the quarter
		c.set(Calendar.MONTH,Quarter.valueOf(c.get(Calendar.MONTH)).startMonth());
		c.set(Calendar.DAY_OF_MONTH,1);
		beginningOfDay(c);

		range.setStart(c.getTime());

		return(range);
	}

	/**
	 *  This method is used to retrieve the date range represented by the specified quarter of the year.
	 *  This method creates a new GregorianCalendar instance to create the date range.
	 *
	 *  @param year the year of the quarter
	 *  @param quarter the quarter of the year
	 *  @return the date range representing the specified quarter of the year
	 */
	public static DateRange getQuarter(final int year, final Quarter quarter){
		final GregorianCalendar c = makeCalendar(null);
		final DateRange range = new DateRange();

		c.set(Calendar.YEAR,year);

		// Get the Date for the starting month
		c.set(Calendar.MONTH,quarter.startMonth());
		c.set(Calendar.DAY_OF_MONTH,1);
		beginningOfDay(c);
		range.setStart(c.getTime());

		// Get the Date for the ending month
		c.set(Calendar.MONTH,quarter.endMonth());
		c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
		endOfDay(c);
		range.setEnd(c.getTime());

		return(range);
	}

	/**
	 *   This method is used to determine the date range for the previous quarter.
	 *
	 *   @param date the Date that the date range is to be based on
	 *   @return an array of two Date objects, the first of which is the start date, the second the end date of the date range
	 */
	public static DateRange getPreviousQuarter(final Date date){
		final GregorianCalendar c = makeCalendar(date);
		final DateRange range = new DateRange();
		final Quarter prevQtr = Quarter.valueOf(c.get(Calendar.MONTH)).previous();

		// roll back a year if necessary
		if(prevQtr.equals(Quarter.FOURTH)){c.roll(Calendar.YEAR,-1);}

		// Get the dates for the start and the end of the quarter
		c.set(Calendar.MONTH,prevQtr.startMonth());
		c.set(Calendar.DAY_OF_MONTH,1);
		beginningOfDay(c);
		range.setStart(c.getTime());

		c.set(Calendar.MONTH,prevQtr.endMonth());
		c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
		endOfDay(c);
		range.setEnd(c.getTime());

		return(range);
	}

	/**
	 *   This method is used to determine the date range for the previous day.
	 *
	 *   @param date the Date that the date range is to be based on
	 *   @return the date range
	 */
	public static DateRange getYesterday(final Date date){
		final GregorianCalendar c = makeCalendar(date);

		c.roll(Calendar.DAY_OF_YEAR,-1);
		if(c.get(Calendar.DAY_OF_YEAR) == 365){
			c.roll(Calendar.YEAR,-1);
		}

		final DateRange range = new DateRange();

		beginningOfDay(c);
		range.setStart(c.getTime());

		endOfDay(c);
		range.setEnd(c.getTime());

		return(range);
	}

	/**
	 *   This method is used to determine the date range for the current day.
	 *
	 *   @param date the Date that the date range is to be based on
	 *   @return the date range for the given date
	 */
	public static DateRange getToday(final Date date){
		final GregorianCalendar c = makeCalendar(date);
		final DateRange range = new DateRange();

		beginningOfDay(c);
		range.setStart(c.getTime());

		endOfDay(c);
		range.setEnd(c.getTime());

		return(range);
	}

	/**
	 *   This method is used to determine the date range for the last x days (the current day is not included).
	 *
	 *   @param date the Date that the date range is to be based on
	 *   @param days the number of days the range is to span (previous to the current date)
	 *   @return date range for the last x days
	 */
	public static DateRange getLastXDays(final Date date, final int days){
		final GregorianCalendar c = makeCalendar(date);

		final DateRange range = new DateRange();

		c.roll(Calendar.DAY_OF_YEAR,-1);
		if(c.get(Calendar.DAY_OF_YEAR) == 365){
			c.roll(Calendar.YEAR,-1);
		}

		endOfDay(c);
		range.setEnd(c.getTime());

		c.roll(Calendar.DAY_OF_YEAR,-(days-1));

		beginningOfDay(c);
		range.setStart(c.getTime());

		return(range);
	}

	/**
	 *   This method is used to determine the date range for the start of the current year to the current day in that year.
	 *
	 *   @param date the Date that the date range is to be based on
	 *   @return an array of two Date objects, the first of which is the start date, the second the end date of the date range
	 */
	public static DateRange getYearToDate(final Date date){
		final GregorianCalendar c = makeCalendar(date);

		final DateRange range = new DateRange();

		range.setEnd(c.getTime());

		c.set(Calendar.DAY_OF_YEAR,1);

		beginningOfDay(c);
		range.setStart(c.getTime());

		return(range);
	}

	/**
	 *   This method is used to determine the date range for the start of the current month to the current day.
	 *
	 *   @param date the Date that the date range is to be based on
	 *   @return an array of two Date objects, the first of which is the start date, the second the end date of the date range
	 */
	public static DateRange getMonthToDate(final Date date){
		final GregorianCalendar c = makeCalendar(date);

		final DateRange range = new DateRange();

		range.setEnd(c.getTime());

		c.set(Calendar.DAY_OF_MONTH,1);

		beginningOfDay(c);
		range.setStart(c.getTime());

		return(range);
	}

	/**
	 *   This method is used to determine the date range for the previous year.
	 *
	 *   @param date Date that the date range is to be based on
	 *   @return an array of two Date objects, the first of which is the start date, the second the end date of the date range
	 */
	public static DateRange getPreviousYear(final Date date){
		final GregorianCalendar c = makeCalendar(date);

		final DateRange range = new DateRange();

		c.roll(Calendar.YEAR,-1);

		c.set(Calendar.DAY_OF_YEAR,c.getActualMinimum(Calendar.DAY_OF_YEAR));
		beginningOfDay(c);
		range.setStart(c.getTime());

		c.set(Calendar.DAY_OF_YEAR,c.getActualMaximum(Calendar.DAY_OF_YEAR));
		endOfDay(c);
		range.setEnd(c.getTime());

		return(range);
	}

	/**
	 *   This method is used to determine the date range for the current month in the previous year.
	 *
	 *   @param date the Date that the date range is to be based on
	 *   @return an array of two Date objects, the first of which is the start date, the second the end date of the date range
	 */
	public static DateRange getThisMonthLastYear(final Date date){
		final GregorianCalendar c = makeCalendar(date);

		final DateRange range = new DateRange();

		final int curMon = c.get(Calendar.MONTH);

		c.roll(Calendar.YEAR,-1);
		c.set(Calendar.MONTH,curMon);

		c.set(Calendar.DAY_OF_MONTH,1);
		beginningOfDay(c);
		range.setStart(c.getTime());

		c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
		endOfDay(c);
		range.setEnd(c.getTime());

		return(range);
	}

	/**
	 *   This method is used to determine the date range for the week previous to the current week.
	 *
	 *   @param date the Date that the date range is to be based on
	 *   @return an array of two Date objects, the first of which is the start date, the second the end date of the date range
	 */
	public static DateRange getPreviousWeek(final Date date){
		final GregorianCalendar c = makeCalendar(date);

		final DateRange range = new DateRange();

		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		c.roll(Calendar.WEEK_OF_YEAR,-1);

		beginningOfDay(c);
		range.setStart(c.getTime());

		c.roll(Calendar.DAY_OF_YEAR,6);

		endOfDay(c);
		range.setEnd(c.getTime());

		return(range);
	}

	/**
	 *   This method is used to determine the date range for the month previous to the current month.
	 *
	 *   @param date the Date that the date range is to be based on
	 *   @return an array of two Date objects, the first of which is the start date, the second the end date of the date range
	 */
	public static DateRange getPreviousMonth(final Date date){
		final GregorianCalendar c = makeCalendar(date);

		final DateRange range = new DateRange();

		c.roll(Calendar.MONTH,-1);
		c.set(Calendar.DAY_OF_MONTH,1);

		beginningOfDay(c);
		range.setStart(c.getTime());

		c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));

		endOfDay(c);
		range.setEnd(c.getTime());

		return(range);
	}

	/**
	 *  This method is used to create the necessary calendar object from the given date. The date is cloned.
	 *
	 *  @param date the date to be set as current
	 *  @return a calendar representing the given date
	 */
	private static GregorianCalendar makeCalendar(final Date date){
		final GregorianCalendar c = (GregorianCalendar)GregorianCalendar.getInstance();
		if(date != null){
			c.setTime((Date)date.clone());
		}
		return(c);
	}

	/**
	 * Used to set the time of the calendar to the beginning of the day (00:00:00.000).
	 *
	 * @param cal the calendar
	 */
	private static void beginningOfDay(final Calendar cal){
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
	}

	/**
	 * Used to set the time of the calendar to the end of the day (23:59:59.999)
	 *
	 * @param cal
	 */
	private static void endOfDay(final Calendar cal){
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
		cal.set(Calendar.MILLISECOND,999);
	}
}
