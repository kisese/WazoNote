package com.kisese.brayo.wazonote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import storage.MainActivityList;


public class MainActivity extends ActionBarActivity {

    private Button newNote;
    private Button myNotes;
    private Button stickyNotes;
    private Button secureNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNote = (Button)findViewById(R.id.btn_new_note);
        myNotes = (Button)findViewById(R.id.btn_my_notes);
        stickyNotes = (Button)findViewById(R.id.btn_sticker);
        secureNote = (Button)findViewById(R.id.btn_secure);

        clickFunctions();
    }


    public void clickFunctions(){
        myNotes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //myNotes.setBackgroundResource(R.drawable.white_bg);
                Intent noteList = new Intent(MainActivity.this, MainActivityList.class);
                MainActivity.this.startActivity(noteList);
                //myNotes.setBackgroundResource(R.drawable.blue_bg);
            }
        });

        newNote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent noteMpya = new Intent(MainActivity.this, AddNoteActivity.class);
                MainActivity.this.startActivity(noteMpya);
            }
        });

        stickyNotes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent noteSticky = new Intent(MainActivity.this, StickyNoteActivity.class);
                MainActivity.this.startActivity(noteSticky);
            }
        });

        secureNote.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent noteSecure = new Intent(MainActivity.this, SecureActivity.class);
                MainActivity.this.startActivity(noteSecure);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_help) {
            alertYaHelp();
           // return true;
        }else if(id == R.id.action_about){
            alertYaAbout();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("deprecation")
    private void alertYaHelp(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("Help");
        alertView.setMessage("* Wazo Note is a simple notes app that will help you save, backup and secure your notes\n\n"
                +"* Backups will be in form of .txt files and they will be stored in your SD Cards DOWNLOADS folder\n\n" +
                "* From the main screen you can add various types of notes \n\n" +
                "* Some screens have actionbar icons to perform various tasks \n\n"+
                "* IMPORTANT: \n\n" +
                "* The My Notes page will provide you with the backup option from the Action Bar along with displaying your saved notes\n\n" +
                "* To Edit a Note, open it from the my notes page and select the edit icon from the ActionBar \n\n" +
                "* The same applies for performing a backup or deleting \n\n" +
                "* For secure notes, the Secure Notes page will provide you with options to set anew password, reset or recover a password" +
                 " based on a securit question \n\n" +
                 "* With a password you can save secure notes which will only be accesible when you enter the password.\n\n" +
                 "* For sticky notes, The Sticky Note page will add a sticky note which will be visible via a homescreen widget" +
                        "(This may not wrk on some devices, if you have trouble with refreshing try creating a new widget)\n\n" +
                  " I do Hope you find the application useful.\n\n Regards. brayokisese@gmail.com");
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
    private void alertYaAbout(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("Wazo Note");
        alertView.setMessage("save, secure and backup your notes.\n\n" +
                "brayokisese@gmail.com \n PurpleLabs");
        alertView.setButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub



                alertView.dismiss();
            }
        });
        alertView.show();
    }
}
