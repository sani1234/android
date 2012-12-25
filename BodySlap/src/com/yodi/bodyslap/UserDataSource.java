package com.yodi.bodyslap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDataSource {
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	// Get all tracker table's columns 
	private String[] allColumns = { DatabaseHelper.COLUMN_ID,
			DatabaseHelper.COLUMN_DAY, DatabaseHelper.COLUMN_IS_EXERCISE
	};

	/**
	 * Constructor to load context
	 * @param context
	 */
	public UserDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}
	
	/**
	 * Make database in Write mode
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	/**
	 * Close database sqlite file access 
	 */
	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Create a new records for tracker
	 * @param tracker
	 * @return
	 */
	public UserDatabase createTracker(Integer day, Integer isExercise) {
		ContentValues values = new ContentValues();
		values.put(dbHelper.COLUMN_DAY, day);
		values.put(dbHelper.COLUMN_IS_EXERCISE, isExercise);
		
		Cursor mCursor;
		
		// Check if days is exists
		mCursor = database.query(dbHelper.TABLE_TRACKER, 
				allColumns, dbHelper.COLUMN_DAY + "=?",
				new String[] {Integer.toString(day)}, null, null, null, null);
				
		// If data exists, then update the records
		if(mCursor != null && mCursor.getCount() > 0) {
			mCursor.moveToFirst();
			
			database.update(dbHelper.TABLE_TRACKER, values,
					dbHelper.COLUMN_ID + "=?",
					new String[] {String.valueOf(mCursor.getColumnIndex("id"))}
			);

		} else {
			// Set the new records ID
			long insertId = database.insert(dbHelper.TABLE_TRACKER, null, values);
			
			// Inserting a new records 
			mCursor = database.query(dbHelper.TABLE_TRACKER,
					allColumns, dbHelper.COLUMN_ID + " = " + insertId,
					null, null, null, null);
			
			mCursor.moveToFirst();			
		}

		// Mapping cursor data into newData
		UserDatabase newData = cursorToUser(mCursor);
		
		// Close cursor connection
		mCursor.close();
		
		return newData;
	}
	
	/**
	 * Fetching selected tracker data by ID
	 */
	public Cursor fetchTrackerbyID(long id) {
		Cursor mCursor = database.query(dbHelper.TABLE_TRACKER, 
					allColumns, dbHelper.COLUMN_ID + "=?",
					new String[] {String.valueOf(id)}, null, null, null, null);
		
		if(mCursor != null) {
			mCursor.moveToFirst();
		}
		
		return mCursor;
	}

	/**
	 * Fetching all data in Tracker
	 */
	public Cursor fetchAllTracker() {
		Cursor mCursor = database.query(dbHelper.TABLE_TRACKER, 
				allColumns, null, null, null, null, null);
		
		if(mCursor != null) {
			mCursor.moveToFirst();
		}
		
		return mCursor;
	}
	
	/**
	 * Delete all tracker records
	 */
	public void deleteTrackerRecords() {
		database.delete(dbHelper.TABLE_TRACKER, null, null);
	}
	
	/**
	 * Mapping cursor into UserDatabase handler
	 * @param cursor
	 * @return
	 */
	private UserDatabase cursorToUser(Cursor cursor) {
		UserDatabase userDatabase = new UserDatabase();
		userDatabase.setId(cursor.getLong(0));
		userDatabase.setDay(cursor.getInt(1));
		userDatabase.setIsExercise(cursor.getInt(2));

		return userDatabase;
	}

}
