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

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *  An object for storing two Date objects representing a range of dates from start to
 *  finish.
 *
 *  This class assumes immutable Date treatment since they only mutators for Date objects
 *  are deprecated.
 *
 *  @author Christopher J. Stehno (chris@stehno.com)
 */
public final class DateRange {

	private Date start, end;

	/**
	 *  Creates an empty date range object. Both dates are null.
	 */
	public DateRange(){
		super();
	}

	/**
	 *  Creates a date range with the specified starting and ending dates.
	 *
	 *  @param start the starting date
	 *  @param end the ending date
	 */
	public DateRange(final Date start, final Date end){
		this();
		this.start = start;
		this.end = end;
	}

	/**
	 * Copy constructor. Simply creates a new DateRange with the same starting
	 * and ending Date objects.
	 *
	 * @param range the DateRange to be copied
	 */
	public DateRange(final DateRange range){
		this(range.getStart(),range.getEnd());
	}

	/**
	 * 	Used to determine if the given date within the date range. The endpoints of the range are also inclusive.
	 *
	 */
	public boolean isWithin(final Date date){
		return((start == null || date.equals(start) || date.after(start)) && (end == null || date.equals(end) || date.before(end)));
	}

	/**
	 *  This method is used to retrieve the starting date of the range.
	 *
	 *  @return the starting date of the range
	 */
	public Date getStart(){return(start);}

	/**
	 *  This method is used to specify the starting date for the date range.
	 *
	 *  @param start the starting date for the range
	 *  @throws IllegalArgumentException if the start date is null
	 */
	public void setStart(final Date start){
		this.start = start;
	}

	/**
	 *  This method is used to specify the ending date for the date range.
	 *
	 *  @param end the ending date for the range
	 *  @throws IllegalArgumentException if the end date is null
	 */
	public void setEnd(final Date end){
		this.end = end;
	}

	/**
	 *  This method is used to retrieve the ending date of the range.
	 *
	 *  @return the ending date of the range
	 */
	public Date getEnd(){return(end);}

	/**
	 *  This method is used to retrieve a two-element Date array containing the starting and ending dates
	 *  respectively.
	 *
	 *  @return an array of Dates containing both the starting and ending dates
	 */
	public Date[] getRange(){return(new Date[]{start,end});}

	/**
	 * Used to retrieve the number of milliseconds represented by the date range. This number will
	 * always be positive.
	 *
	 * @return the number of milliseconds represented by the date range.
	 */
	public long duration(){
		return(Math.abs(end.getTime() - start.getTime()));
	}

	@Override
	public int hashCode(){
		return(new HashCodeBuilder(7,13).append(start).append(end).toHashCode());
	}

	/**
	 *  This method is used to determine whether two date ranges are equivalent.
	 *
	 *  @param obj the object to be compared with this date range
	 *  @return a value of true if the two date ranges are equal
	 */
	@Override
	public boolean equals(final Object obj){
		return(
				obj instanceof DateRange &&
				new EqualsBuilder().append(((DateRange)obj).start,start).append(((DateRange)obj).end,end).isEquals()
		);
	}

	/**
	 *  This method is used to retrieve the date range as a string. No date formatting is performed.
	 *
	 *  @return the date range represented as a string
	 */
	@Override
	public String toString(){
		final StringBuilder str = new StringBuilder("[DateRange: ");
		str.append("start='").append(getStart().toString()).append("' end='").append(getEnd().toString()).append("']");
		return(str.toString());
	}
}
