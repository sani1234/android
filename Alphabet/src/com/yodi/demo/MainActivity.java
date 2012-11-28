package com.yodi.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import android.widget.ArrayAdapter; 
import android.widget.ListView; 
import android.widget.TextView; 
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Generate alphabet
		String[] numbers = new String[26];
		char letter;
		int i;
		
		for(letter='a', i=0; letter <='z'; letter++, i++) {
			numbers[i] = String.valueOf(letter);
		}
		
		// Get gridview layout from XML
		GridView gridView = (GridView) findViewById(R.id.gridview);
		
		// Set Adapter to connecting between context data and layout
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, numbers);
		
		// Set gridview using array adapter
		gridView.setAdapter(adapter);
		
		// Create handler for clicked item action
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

}


