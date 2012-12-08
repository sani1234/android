package com.yodi.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	public final Integer MAXIMUM_DAY = 31;
	private String[] dates = new String[MAXIMUM_DAY];	
	
	public ImageAdapter(Context context) {
		// Get context
		mContext = context;
		
		// Set today calendar as default value
		CalendarUtils calendarUtils = new CalendarUtils();
		dates = calendarUtils.getCalendarArray();		
	}
	
	@Override
	public int getCount() {
		return dates.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(convertView == null) {
			LayoutInflater li = LayoutInflater.from(mContext);
			v = li.inflate(R.layout.item, parent, false);			
		} 

		// Set Text
		TextView tv =(TextView) v.findViewById(R.id.item_text);
		
		// Set text resource on each position		
		tv.setText(dates[position]);

		return v;
	}

	
}
