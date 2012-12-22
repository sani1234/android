package com.yodi.calendar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {
	private ImageView mImageView;
	private String mCurrentPhotoPath;
	private static String ANDROID_TAG = "ANDROID";

	/**
	 * Set application in full-screen mode and remove title app
	 */
    private void fullScreen() {
		// Set full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
    }
    
	/**
	 * Set the picture taken from Camera into detail pages
	 */
	private void setPic() {
		int targetW = mImageView.getWidth();
		int targetH = mImageView.getHeight();
		
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;
		
		int scaleFactor = 1;
		if((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);
		}
		
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;
		
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		
		Log.d(ANDROID_TAG, "Set Bitmap " + mCurrentPhotoPath);

		mImageView.setImageBitmap(bitmap);
		mImageView.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Broadcast media scan Intent
	 */
	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		fullScreen();
		
		setContentView(R.layout.detail_calendar);
		mImageView = (ImageView) findViewById(R.id.photoImage);
		
		Intent i = getIntent();
		mCurrentPhotoPath = i.getExtras().getString("path");
		
		if(mCurrentPhotoPath != null) {
			Log.d(ANDROID_TAG, "Get Intent " + mCurrentPhotoPath);

			setPic();
			galleryAddPic();			
		}
		
		int position = i.getExtras().getInt("id");
		int dayNumber = position + 1;

		// Set position into today if not found
		if (position < 1) {
			String today = new SimpleDateFormat("dd").format(new Date());
			position = Integer.parseInt(today);
			dayNumber = position + 1;
		} 		

		// Set title of selected day
		TextView tv = (TextView) findViewById(R.id.calendar_title_text);
		CalendarUtils calendarUtils = new CalendarUtils();
		tv.setText(Integer.toString(dayNumber) + " - " +
				calendarUtils.getDateName(dayNumber, "LONG"));			
	}

}
