package com.kisese.brayo.wazonote;

/**
 * Created by Brayo on 7/23/2014.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import storage.MainActivityList;
import storage.RegistrationActivity;

public class EditNoteActivity  extends ActionBarActivity {

    Button save, back;
    EditText title, story;
    String date, a;
    String title_text;
    String story_text;
    String check;

    String headlineValue;
    String descriptionValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);

        title = (EditText)findViewById(R.id.note_title);
        story = (EditText)findViewById(R.id.note_text);

        Intent myIntent3 = getIntent();

        headlineValue =  myIntent3.getStringExtra("headline");
        descriptionValue =  myIntent3.getStringExtra("description");

        title.setText(headlineValue);
        story.setText(descriptionValue);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int time = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);
        int am = c.get(Calendar.AM_PM);

        if(am == 1){
            a = "PM";
        }else{
            a = "AM";
        }

        if(min < 10){
            date = day + "/ " + month + "/ " + year + " - " + time + " : 0" + min + " " + a;
        }else {
            date = day + "/ " + month + "/ " + year + " - " + time + " : " + min  + " " + a;
        }

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

            Intent intent = new Intent(EditNoteActivity.this, RegistrationActivity.class);
            intent.putExtra("link", date);
            intent.putExtra("headline", title_text);
            intent.putExtra("description", story_text);
            EditNoteActivity.this.startActivity(intent);
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
        alertView.setButton("Confirm and View Notes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent2 = new Intent(EditNoteActivity.this, MainActivityList.class);
                //intent.putExtra("link", date);
                EditNoteActivity.this.startActivity(intent2);
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
        Intent myIntent3 = getIntent();

        String head =  myIntent3.getStringExtra("headline");
        String descr =  myIntent3.getStringExtra("description");
        Toast.makeText(this, "Please save changes to your note before going back", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(EditNoteActivity.this, EditNoteActivity.class);
        //intent.putExtra("link", date);
        intent.putExtra("headline", head);
        intent.putExtra("description", descr);
        EditNoteActivity.this.startActivity(intent);

        super.onBackPressed();
    }


}