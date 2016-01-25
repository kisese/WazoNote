package com.kisese.brayo.wazonote.activity;

/**
 * Created by Brayo on 7/23/2014.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kisese.brayo.wazonote.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Calendar;

import storage.NotesTableAdapter;

public class EditNoteActivity  extends ActionBarActivity {

    Button save, back;
    EditText title, story;
    String date, a;
    String title_text;
    String story_text;
    String check;
    NotesTableAdapter adapter;
    private TextView text;
    private View layout;
    String headlineValue;
    String descriptionValue;
    private String LOG_TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);

        title = (EditText)findViewById(R.id.note_title);
        story = (EditText)findViewById(R.id.note_text);

        LayoutInflater inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        text = (TextView) layout.findViewById(R.id.text);

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

       // save = (Button)findViewById(R.id.add_note);

       // save.setVisibility(View.GONE);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F08080")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.edit) + "</font"));
        getSupportActionBar().getThemedContext();
        //getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void addNote(){
        adapter = new NotesTableAdapter(this);

        title_text = title.getText().toString();
        story_text = story.getText().toString();

        if(story_text.length()==0 && title_text.length()>0){

            story_text = title.getText().toString();

            String title;
            title = story_text.substring(0, Math.min(story_text.length(),30));

            String temp = "";
            temp = title_text;

            if(temp != "")
                adapter.insertDetails(date, title, story_text);
            else
                adapter.insertDetails(date, title.concat("_b"), story_text);

            // Toast.makeText(getApplicationContext(), Long.toString(val),
            // 300).show();
            showToast("Note Saved");
            writeToFile();
            Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
            EditNoteActivity.this.startActivity(intent);
            finish();


        }else if(title_text.length()==0 && story_text.length()>0){
            //showToast("Please add a title");
            title_text = story.getText().toString();

            title_text = title_text.substring(0, Math.min(title_text.length(),30));

            String temp = "";
            temp = title_text;

            if(temp != "")
                adapter.insertDetails(date, title_text, story_text);
            else
                adapter.insertDetails(date, title_text.concat("_b"), story_text);

            // Toast.makeText(getApplicationContext(), Long.toString(val),
            // 300).show();
            showToast("Note Saved");
            writeToFile();
            Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
            EditNoteActivity.this.startActivity(intent);
            finish();
        }else if(title_text.length()>0 && story_text.length()>0){
            String temp = "";
            temp = title_text;

            title_text = title_text.substring(0, Math.min(title_text.length(),30));

            if(temp != "")
                adapter.insertDetails(date, title_text, story_text);
            else
                adapter.insertDetails(date, title_text.concat("_b"), story_text);

            // Toast.makeText(getApplicationContext(), Long.toString(val),
            // 300).show();
            showToast("Note Saved");
            writeToFile();
            Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
            EditNoteActivity.this.startActivity(intent);
            finish();
        }else{

        }

    }

    //backup files to external storage

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    public void writeToFile(){
        if (isExternalStorageWritable() == true && isExternalStorageReadable() == true) {
            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            String head = title.getText().toString();
            String body = story.getText().toString();

            File myDir = new File(root + "/wazo_note_backups");
            myDir.mkdirs();

            File file = new File(myDir, head.concat(".txt"));

            if (file.exists())
                file.delete();

            try {
                FileOutputStream out = new FileOutputStream(file);
                PrintWriter pw = new PrintWriter(out);

                pw.println(body);

                pw.flush();
                pw.close();
                out.close();

                // showToast("Backup Successful");
                //  Toast.makeText(this, "Backup Succesful", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //End of backup files



    public void showToast(String story_text){
        text.setText(story_text);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
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
                Intent intent2 = new Intent(EditNoteActivity.this, MainActivity.class);
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
        addNote();
        super.onBackPressed();
    }


}
