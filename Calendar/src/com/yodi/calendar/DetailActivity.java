package com.yodi.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_calendar);
		
		Intent i = getIntent();
		int position = i.getExtras().getInt("id");
		int dayNumber = position + 1;
				
		// Set title
		TextView tv = (TextView) findViewById(R.id.calendar_title_text);
		CalendarUtils calendarUtils = new CalendarUtils();
		tv.setText(Integer.toString(dayNumber) + " - " +
				calendarUtils.getDateName(dayNumber, "LONG"));
	}

}
