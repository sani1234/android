package com.yodi.bodyslap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.text.format.DateFormat;

public class CalendarUtils {
	private int day;
	private int month;
	private int year;
	private Date date;
	private Calendar calendar;
	private Integer[] dates = new Integer[31];

	public final Integer MAXIMUM_DAY = 31;
	
	/**
	 * Constructors to set initial calendar
	 */
	public CalendarUtils() {
		// Get today date
		Date date = new Date();

		// Set initial day, month and year
		day = date.getDate();
		month = date.getMonth();
		year = date.getYear();
		
		this.calendar = Calendar.getInstance();
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
	 * Get day name and formating
	 */
	public String getDateName(int day, String format) {
		// Get the date name
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
		if(format == "SHORT") {
			// Get the date name
			dateFormat = new SimpleDateFormat("E");			
		}

		try {
			Date date = new Date(this.year, this.month, day);
			this.date = date;
		} catch (Exception e) {
			System.out.println("ERROR: Cannot build date \"" + this.year + " "
					+ this.month + " " + day + "\"");
		}
		
		return dateFormat.format(this.date);
	}
	
	/**
	 * Get today date instance
	 * @return
	 */
	public Date getToday() {
		// Get today date
		Date date = new Date();
		
		return date;
	}
	
	/**
	 * Get calendar in Array String
	 * @return
	 */
	public Integer[] getCalendarArray() {
		// Get the last date of the selected month
		int endMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		// Set calendar on Adapter using today by default
		for(int i=0; i < MAXIMUM_DAY; i++) {
			dates[i] = i+1;
		}
		
		return dates;
	}

}
