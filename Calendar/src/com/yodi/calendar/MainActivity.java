package com.yodi.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;


public class MainActivity extends Activity {
	private ItemAdapter imageAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove application title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		// Set image adapter instance from private variable
		imageAdapter = new ItemAdapter(this);
		
		// Bind Gridview with calendar XML and set the Adapter
		GridView gridView = (GridView) findViewById(R.id.calendar);
		gridView.setAdapter(imageAdapter);
		
		// Top title
		TextView tv = (TextView) findViewById(R.id.top_title);
		tv.setText("Today");
		
		CalendarUtils calendarUtils = new CalendarUtils();
		Date today = calendarUtils.getToday();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
		String format = dateFormat.format(today);
		
		// Top title
		TextView calendarDate = (TextView) findViewById(R.id.calendar_date);
		calendarDate.setText(format);
		
		// Handle item on Click action
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// Create Intent to passing id position to CalendarDetail
				Intent i = new Intent(getApplicationContext(),
						DetailActivity.class);				
				i.putExtra("id", position);
				startActivity(i);
			}
		});
	}

}
