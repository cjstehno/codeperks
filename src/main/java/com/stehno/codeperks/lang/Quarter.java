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

/**
 * Enumeration of the four quarters of a year.
 *
 * @author Christopher J. Stehno (chris@stehno.com)
 */
public enum Quarter {

	FIRST(Calendar.JANUARY,Calendar.MARCH),
	SECOND(Calendar.APRIL,Calendar.JUNE),
	THIRD(Calendar.JULY,Calendar.SEPTEMBER),
	FOURTH(Calendar.OCTOBER,Calendar.DECEMBER);

	private final int startMonth, endMonth;

	/**
	 * Creates a quarter with the given start and end month.
	 *
	 * @param startMonth the first month of the quarter
	 * @param endMonth the last month of the quarter
	 */
	private Quarter(final int startMonth, final int endMonth){
		this.startMonth = startMonth;
		this.endMonth = endMonth;
	}

	/**
	 * Used to retrieve the first month of the quarter.
	 *
	 * @return the first month of the quarter
	 */
	public int startMonth(){return(startMonth);}

	/**
	 * Used to retrieve the last month of the quarter.
	 *
	 * @return the last month of the quarter
	 */
	public int endMonth(){return(endMonth);}

	/**
	 * Used to convert the month value to its corresponding Quarter object. The values
	 * are 0-based (as they would come from a GregorianCalendar object).
	 *
	 * @param month the number of the month (0-based)
	 * @return the Quarter containing the given month
	 */
	public static Quarter valueOf(final int month){
		Quarter qtr = null;
		if(month < 3){qtr = Quarter.FIRST;}
		else if(month > 2 && month < 6){qtr = Quarter.SECOND;}
		else if(month > 5 && month < 9){qtr = Quarter.THIRD;}
		else if(month > 8){qtr = Quarter.FOURTH;}
		return(qtr);
	}

	/**
	 * Used to retrieve the quarter that comes before this quarter.
	 *
	 * @return the previous quarter
	 */
	public Quarter previous(){
		final Quarter[] qtrs = values();

		int idx = ordinal()-1;
		if(idx < 0){idx = qtrs.length-1;}

		return(qtrs[idx]);
	}

	/**
	 * Used to retrieve the quarter that follows this quarter.
	 *
	 * @return the next quarter
	 */
	public Quarter next(){
		final Quarter[] qtrs = values();

		int idx = ordinal()+1;
		if(idx >= qtrs.length){idx = 0;}

		return(qtrs[idx]);
	}
}