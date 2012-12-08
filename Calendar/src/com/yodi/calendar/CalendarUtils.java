package com.yodi.calendar;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
	private int day;
	private int month;
	private int year;
	private Calendar calendar;
	private String[] dates;

	public final Integer MAXIMUM_DAY = 31;
	
	/**
	 * Constructors to set initial calendar
	 */
	public CalendarUtils() {
		// Get today date
		Date now = new Date();

		// Set initial day, month and year
		day = now.getDate();
		month = now.getMonth();
		year = now.getYear();
		
		this.calendar = Calendar.getInstance();

		// Build dates array
		dates = new String[MAXIMUM_DAY];
	}

	/**
	 * Create calendar Instance
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public void setCalendar(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
		
		// Set the calendar
		calendar.set(year, month, day);
	}
	
	/**
	 * Get calendar instance
	 * @return
	 */
	public Calendar getCalendar() {
		return calendar;
	}
	
	/**
	 * Get calendar in Array String
	 * @return
	 */
	public String[] getCalendarArray() {
		// Get the last date of the selected month
		int endMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		// Set calendar on Adapter using today by default
		for(int i=0; i < MAXIMUM_DAY; i++) {
			dates[i] = Integer.toString(i+1);
		}
		
		return dates;
	}

}
