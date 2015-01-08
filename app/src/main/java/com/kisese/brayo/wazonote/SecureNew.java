package com.kisese.brayo.wazonote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Brayo on 8/2/2014.
 */
public class SecureNew extends ActionBarActivity {

    Button save, back;
    EditText title, story;
    String date;
    String title_text;
    String story_text;
    String check;
    String a;

    SharedPreferences notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);

        notes = getSharedPreferences("secure", MODE_PRIVATE);


        title = (EditText)findViewById(R.id.note_title);
        story = (EditText)findViewById(R.id.note_text);



        save = (Button)findViewById(R.id.sticky_save_btn);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addNote();
            }
        });
    }

    public void addNote(){
        Intent myIntent2 = getIntent();

        title_text = title.getText().toString();
        story_text = story.getText().toString();

        if(story_text.length()==0){
            alertYaError();
        }else if(title_text.length()==0){
            alertYaErrorTena();
        }else{


            SharedPreferences.Editor editor = notes.edit();
            editor.putString(title_text, story_text);
            editor.commit();
            alertYaNetwork();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @SuppressWarnings("deprecation")
    private void alertYaNetwork(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("	Okay Then");
        alertView.setMessage("Your note has been saved, View it on My Notes");
        alertView.setButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                alertView.dismiss();
            }
        });
        alertView.show();
    }

    @SuppressWarnings("deprecation")
    private void alertYaError(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("     Well Well Well!!");
        alertView.setMessage("Looks like you havent typed in anything, please do");
        alertView.setButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                alertView.dismiss();
            }
        });
        alertView.show();
    }

    @SuppressWarnings("deprecation")
    private void alertYaErrorTena(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("     Well Well Well!!");
        alertView.setMessage("Please type in a title");
        alertView.setButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                alertView.dismiss();
            }
        });
        alertView.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SecureNew.this, SecureMain.class);
        SecureNew.this.startActivity(intent);
        super.onBackPressed();
    }
}

