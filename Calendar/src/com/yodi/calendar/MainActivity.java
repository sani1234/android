package com.yodi.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	final Context context = this;
	private static final int DIALOG_ALERT = 10;
	private static final String ANDROID_TAG = "ANDROID";
	
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
				showDialog(DIALOG_ALERT);

//				LayoutInflater inflater = getLayoutInflater();
//				View layout = inflater.inflate(R.layout.menu,
//						(ViewGroup) findViewById(R.id.toast_menu));
//				
//				TextView text = (TextView) layout.findViewById(R.id.menu_text);
//				text.setText("Custom toast!");
//				
//				// TODO Auto-generated method stub
//				Toast toast = new Toast(getApplicationContext());
//				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//				toast.setView(layout);
//				toast.show();
				
				// Set date pickers cells 
				imageAdapter.setDatePicker(today.getDate());
				gridView.invalidateViews();
			}
		});
	}
	
	protected Dialog onCreateDialog(int id) {
		String[] options = {"RED", "YELLOW", "GREEN"};

		switch(id) {
		case DIALOG_ALERT:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Choose Workout Menu");
			builder.setCancelable(false);
			builder.setItems(options, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Log.d(ANDROID_TAG, Integer.toString(which));
				}
			});
			
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
//					MainActivity.this.finish();
				}
			});
			
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}
		return super.onCreateDialog(id);
	}
	

}
