package com.yodi.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CalendarDetail extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_calendar);
		
		Intent i = getIntent();
		int position = i.getExtras().getInt("id");
		
		// Set title
		TextView tv = (TextView) findViewById(R.id.day_calendar);
		tv.setText(Integer.toString(position + 1));
	}

}
