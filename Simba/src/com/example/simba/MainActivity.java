package com.example.simba;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private static final String DB_NAME = "product";
	private static final String TABLE_NAME = "productinfo";
	private static final String FRIEND_ID = "code";
	private static final String FRIEND_NAME = "name";
	Cursor friendCursor;
	private SQLiteDatabase database;
	private ListView listView;
	private ArrayList<String> friends;
	String code;
	EditText searchbox;
	Button searchbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		searchbox = (EditText) findViewById(R.id.searchbox);
		searchbtn = (Button) findViewById(R.id.searchbtn);
		friends = new ArrayList<String>();

		ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this,
				DB_NAME);
		database = dbOpenHelper.openDataBase();
		setUpList();
		searchbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FindBySearch();
			}
		});
	}

	protected void FindBySearch() {
		// TODO Auto-generated method stub

		String searchvalue = searchbox.getText().toString();
		String searchquerry = "Select * from " + TABLE_NAME
				+ " where name like " + "'" + searchvalue + "%'";
		friendCursor = database.rawQuery(searchquerry, null);
		friendCursor.moveToFirst();
		if (!friendCursor.isAfterLast()) {
			do {
				String name = friendCursor.getString(1);

				Log.d("simba", "" + name);
				// Toast.makeText(getApplicationContext(), name, 1000).show();
				 friends.add(name);
			} while (friendCursor.moveToNext());
		}
		friendCursor.close();
	}

	private void setUpList() {

		friendCursor = database.query(TABLE_NAME, new String[] { FRIEND_ID,
				FRIEND_NAME }, null, null, null, null, FRIEND_NAME);
		friendCursor.moveToFirst();
		if (!friendCursor.isAfterLast()) {
			do {
				String name = friendCursor.getString(1);
				// code = friendCursor.getString(0);
				friends.add(name);
			} while (friendCursor.moveToNext());
		}
		friendCursor.close();

		// *****************************************************************************************

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, friends));
		listView = getListView();

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				details(view);
			}

			private void details(View view) {
				String selectedname = ((TextView) view).getText().toString();
				Intent intent = new Intent(getApplicationContext(),
						Detailsview.class);
				intent.putExtra("selname", selectedname);
				startActivity(intent);
			}

		});
	}

}
