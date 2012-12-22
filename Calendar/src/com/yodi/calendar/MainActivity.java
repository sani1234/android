package com.yodi.calendar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	final Context context = this;

	private static final int DIALOG_ALERT = 10;
	private static final String ANDROID_TAG = "ANDROID";
	private static final int CAMERA_REQUEST = 1888; 

    private String JPEG_FILE_PREFIX = "BODYSLAP_";
    private String JPEG_FILE_SUFFIX = ".jpg";
    
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
	private String mCurrentPhotoPath;

	private ItemAdapter imageAdapter;
	private Date today;
	
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
     * Setup UI when the application started
     */
    private void setupUI() {
		// Set the Top title
		TextView tv = (TextView) findViewById(R.id.top_title);
		tv.setText("Today");
		
		// Formating the date
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
		String format = dateFormat.format(today);

		// Top title
		TextView calendarDate = (TextView) findViewById(R.id.calendar_date);
		calendarDate.setText(format);
				
		// Load Album Storage Factory based on Android Version
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}
    }
    
    /**
     * Handler for detail pages for each day in Calendar
     */
    OnItemClickListener handleCalendarDetail =  new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
			// Create Intent to passing id position to CalendarDetail
			Intent i = new Intent(getApplicationContext(),
					DetailActivity.class);				
			i.putExtra("id", position);
			startActivity(i);
		}
	};
	
	/**
	 * Handler for showing multiple choices in Dialog Workout menu
	 */
	DialogInterface.OnMultiChoiceClickListener mListMenu = 
			new DialogInterface.OnMultiChoiceClickListener() {		
		@Override
		public void onClick(DialogInterface dialog, int which,
				boolean isChecked) {
			// TODO Auto-generated method stub
			Log.d(ANDROID_TAG, Integer.toString(which));
		}
	};

	/**
	 * Handler for Positive button in Dialog Workout menu
	 */
	DialogInterface.OnClickListener mYesMenu = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			dispatchTakePhotoIntent(CAMERA_REQUEST);
		}
	};
	
	/**
	 * Handler for Negative button in Dialog Workout menu
	 */
	DialogInterface.OnClickListener mNoMenu = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**
	 * Create menu list after users click on the Workout button
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		String[] options = {"PUSH-UP", "SIT-UP", "DUMB-BELL", "RUNNING"};

		switch(id) {
		case DIALOG_ALERT:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Choose Workout Menu");
			builder.setCancelable(false);

			// Set the dialog menu
			builder.setMultiChoiceItems(options, null, mListMenu);
			builder.setPositiveButton("DO", mYesMenu);
			builder.setNegativeButton("Cancel", mNoMenu);
			
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}

		return super.onCreateDialog(id);
	}
	
    /**
     * Set the albumName for storing photos
     * @return String
     */
    private String getAlbumName() {
    	return getString(R.string.album_name);
    }
    
    /**
     * Create and get the album storage directory
     * @return File
     */
    private File getAlbumDir() {
		File storageDir = null;
		
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d(ANDROID_TAG, "Can't create camera directory");
						return null;
					}
				}
			}
		} else {
			Log.v(ANDROID_TAG, "External storage is not mounted READ/WRITE");
		}
		
		return storageDir;
	}

    /**
     * Create Photo file from Camera
     * @return File
     * @throws IOException
     */
    private File createPhotoFile() throws IOException {
    	String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss")
    					   .format(new Date());
    	String imageFile = JPEG_FILE_PREFIX + timestamp + "_";
    	File albumF= getAlbumDir();
    	File imageF = File.createTempFile(imageFile, JPEG_FILE_SUFFIX, albumF);
    	
    	return imageF;
    }

    /**
     * Setup Photo file, generate and set the path of the file
     * @return File
     * @throws IOException
     */
    private File setupPhotoFile() throws IOException {
    	File photo = createPhotoFile();
    	mCurrentPhotoPath = photo.getAbsolutePath();
    	
    	return photo;    	
    }

    /**
     * Intent to handle capture image from camera and save into a file
     * @param actionCode
     */
    private void dispatchTakePhotoIntent(int actionCode) {
    	Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	
    	switch(actionCode) {
    	case CAMERA_REQUEST:
    		File photo = null;
    		
    		try {
    			photo = setupPhotoFile();
    			mCurrentPhotoPath = photo.getAbsolutePath();
    			takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
    					Uri.fromFile(photo));
    		} catch (IOException e) {
    			e.printStackTrace();
    			photo = null;
    			mCurrentPhotoPath = null;
    		}
    		break;
    	
    	default:
    		break;
    	}
    	
    	startActivityForResult(takePhotoIntent, actionCode);
    }
    

	/**
	 * Handle Photo from Camera and send into Detail pages
	 */
	private void handleCameraPhoto() {
		if(mCurrentPhotoPath != null) {
			Intent i = new Intent(getApplicationContext(),
					DetailActivity.class);
			i.putExtra("path", mCurrentPhotoPath);
			startActivity(i);

			mCurrentPhotoPath = null;
		}
	}
	
	/**
	 * Process photos that already saved by the users after taken from Camera
	 */
	protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
		switch(requestCode) {
		case CAMERA_REQUEST: {
			if(resultCode == Activity.RESULT_OK) {
				handleCameraPhoto();
			}			
			break;
		}
		}
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Building Calendar
		CalendarUtils calendarUtils = new CalendarUtils();
		today = calendarUtils.getToday();
		
		// Set app full-screen and remove title app
		fullScreen();
		
		// Setup title
		setupUI();
				
		// Setup image adapter instance from ItemAdapter
		imageAdapter = new ItemAdapter(this);
		
		// Bind Gridview with calendar XML and set the Adapter
		final GridView gridView = (GridView) findViewById(R.id.calendar);
		gridView.setAdapter(imageAdapter);

		// Handle click action on the Calendar dates
		gridView.setOnItemClickListener(handleCalendarDetail);
		
		Button workout = (Button) findViewById(R.id.top_done_button);
		
		workout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ALERT);
	
				// LayoutInflater inflater = getLayoutInflater();
				// View layout = inflater.inflate(R.layout.menu,
				// (ViewGroup) findViewById(R.id.toast_menu));
				//
				// TextView text = (TextView) layout.findViewById(R.id.menu_text);
				// text.setText("Custom toast!");
				//
				// // TODO Auto-generated method stub
				// Toast toast = new Toast(getApplicationContext());
				// toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				// toast.setView(layout);
				// toast.show();
	
				// Set date pickers cells
				imageAdapter.setDatePicker(today.getDate());
				gridView.invalidateViews();
			}
		});
	}
}
