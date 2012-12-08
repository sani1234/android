package com.yodi.calendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends Activity {
	private ImageAdapter imageAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove application title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		// Set image adapter instance from private variable
		imageAdapter = new ImageAdapter(this);
		
		// Bind Gridview with calendar XML and set the Adapter
		GridView gridView = (GridView) findViewById(R.id.calendar);
		gridView.setAdapter(imageAdapter);		
		
		// Handle item on Click action
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// Create Intent to passing id position to CalendarDetail
				Intent i = new Intent(getApplicationContext(),
						CalendarDetail.class);				
				i.putExtra("id", position);
				startActivity(i);
			}
		});
	}

}
