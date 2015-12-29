package com.kisese.brayo.wazonote;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Brayo on 8/2/2014.
 */
public class SecureList extends ListActivity {

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

        CustomList adapter = new CustomList(SecureList.this, tags);

        //list=(ListView)findViewById(R.id.lv_name);
        setListAdapter(adapter);




    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SecureList.this, SecureMain.class);
        SecureList.this.startActivity(intent);
        super.onBackPressed();
    }
}
