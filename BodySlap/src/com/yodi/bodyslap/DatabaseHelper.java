package com.yodi.bodyslap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Implementation of SQLiteOpenHelper
 * This class contains bodyslap database setup
 * @author yodi
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	/** Setup table tracker **/
	public static final String TABLE_TRACKER = "tracker";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DAY = "day";
	public static final String COLUMN_IS_EXERCISE= "is_exercise";	  

	private static final String DATABASE_NAME = "bodyslaper.db";
	private static final int DATABASE_VERSION = 1;

	/** Database creation sql statement **/
	private static final String DATABASE_CREATE = "create table "
	      + TABLE_TRACKER + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_DAY
	      + " integer, " + COLUMN_IS_EXERCISE + " integer);";

	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACKER);
		onCreate(db);
	}

}
