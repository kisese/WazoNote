package com.kisese.brayo.wazonote;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Brayo on 7/29/2014.
 */
public class SecureMain extends ActionBarActivity{

    private Button newNote;
    private Button myNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.secure_main);

        newNote = (Button)findViewById(R.id.secure_new_note);
        myNotes = (Button)findViewById(R.id.secure_my_notes);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F08080")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\">" + getString(R.string.app_name) + "</font"));
        getSupportActionBar().getThemedContext();
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        clickFunctions();
    }


    public void clickFunctions(){
        myNotes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //myNotes.setBackgroundResource(R.drawable.white_bg);
                Intent noteList = new Intent(SecureMain.this, SecureList.class);
                SecureMain.this.startActivity(noteList);
                //myNotes.setBackgroundResource(R.drawable.blue_bg);
            }
        });

        newNote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent noteMpya = new Intent(SecureMain.this, SecureNew.class);
                SecureMain.this.startActivity(noteMpya);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SecureMain.this, SecureActivity.class);
        SecureMain.this.startActivity(intent);
        super.onBackPressed();
    }
}
