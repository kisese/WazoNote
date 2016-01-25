package com.kisese.brayo.wazonote.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.kisese.brayo.wazonote.R;
import com.kisese.brayo.wazonote.adapter.CustomList;

/**
 * Created by Brayo on 8/2/2014.
 */
public class SecureListActivity extends ListActivity {

    SharedPreferences notes;


    private String[] dates;
    private String[] tags;
    private String[] headers;
    private String[] len;
    private ListView list;
    private String[] bodys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.db_main);

        notes = getSharedPreferences("secure", MODE_PRIVATE);


        tags = notes.getAll().keySet().toArray(new String[0]);

        CustomList adapter = new CustomList(SecureListActivity.this, tags);

        //list=(ListView)findViewById(R.id.lv_name);
        setListAdapter(adapter);




    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SecureListActivity.this, SecureMainActivity.class);
        SecureListActivity.this.startActivity(intent);
        super.onBackPressed();
    }
}
