package com.yodi.bodyslap;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {
	private Context mContext;
	private Integer datePicker;
	public final Integer MAXIMUM_DAY = 31;
	private Integer[] dates = new Integer[MAXIMUM_DAY];	
	
	public ItemAdapter(Context context) {
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
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void setDatePicker(int input) {
		datePicker = input - 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder; // to reference the child views for later actions

		if(convertView == null) {
			LayoutInflater li = LayoutInflater.from(mContext);
			convertView = li.inflate(R.layout.item, parent, false);
			
			// cache view fields into the holder
			holder = new ViewHolder();
			
			// Set Text
			holder.dateText = (TextView) convertView.findViewById(R.id.item_text);
			
			// Associate the holder with the view for latter lookup
			convertView.setTag(holder);	

		} else {
			holder = (ViewHolder) convertView.getTag();			
		}
		
		// Get day number by position
		int dayNumber = dates[position];
		
		// Get day name
		CalendarUtils calendarUtils = new CalendarUtils();
		String dayName = calendarUtils.getDateName(dayNumber, "SHORT");
		
		// Set text resource on each position		
		holder.dateText.setText(Integer.toString(dayNumber) + " - " + dayName);

		// Update date picker
		if(datePicker != null && position == datePicker) {
			Log.d("ANDROID", Integer.toString(position));
			convertView.setBackgroundColor(Color.parseColor("#48B93D"));
		} else if (datePicker != null){
			convertView.setBackgroundColor(Color.parseColor("#484848"));
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView dateText;
		
	}
}
