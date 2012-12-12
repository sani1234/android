package com.yodi.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private ItemAdapter imageAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
				
		// Top title
		TextView tv = (TextView) findViewById(R.id.top_title);
		tv.setText("Today");
		
		// Building Calendar
		CalendarUtils calendarUtils = new CalendarUtils();
		final Date today = calendarUtils.getToday();
		
		// Formating the date
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
		String format = dateFormat.format(today);

		// Top title
		TextView calendarDate = (TextView) findViewById(R.id.calendar_date);
		calendarDate.setText(format);
		
		// Set image adapter instance from private variable
		imageAdapter = new ItemAdapter(this);
		
		// Bind Gridview with calendar XML and set the Adapter
		final GridView gridView = (GridView) findViewById(R.id.calendar);
		gridView.setAdapter(imageAdapter);

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
		
		Button workout = (Button) findViewById(R.id.top_done_button);
		workout.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Context context = getApplicationContext();
				String text = "Yeah! You're ready workout now!";
				Toast toast = Toast.makeText(context, text, 
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
				
				final int numVisibleChildren = gridView.getChildCount();
				final int firstVisiblePosition = gridView.getFirstVisiblePosition();

				for ( int i = 0; i < numVisibleChildren; i++ ) {
				    int positionOfView = firstVisiblePosition + i;
				    if (positionOfView == today.getDay()) {
				        View view = gridView.getChildAt(i);
				        view.setBackgroundColor(0xffffffff); 
				    }
				}
				
				
			}
		});
	}

}
