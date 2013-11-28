package com.example.simba;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Detailsview extends Activity {

	private static final String TABLE_NAME = "productinfo";
	private static final String DB_NAME = "product";
	String value;
	private SQLiteDatabase database;

	TextView setname, setcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailsviewlayout);
		setname = (TextView) findViewById(R.id.setname);
		setcode = (TextView) findViewById(R.id.setcode);
		ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this,
				DB_NAME);
		database = dbOpenHelper.openDataBase();
		getdetails();
	}

	private void getdetails() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			value = bundle.getString("selname");
		}
		
		

		Log.d("simba", "value" + value);
		Cursor infoCursor;
		String querrry = "Select * from " + TABLE_NAME + " where name = " + "'"
				+ value + "'";
		Log.d("simba", "query " + querrry);
		infoCursor = database.rawQuery(querrry, null);

		infoCursor.moveToFirst();
		if (!infoCursor.isAfterLast()) {
			do {
				String name = infoCursor.getString(1);
				String code = infoCursor.getString(0);
				Log.d("simba", "name " + name);
				Log.d("simba", "code " + code);
				setname.setText(name);
				setcode.setText(code);
			} while (infoCursor.moveToNext());
		}
		infoCursor.close();
		database.close();

	}
}
