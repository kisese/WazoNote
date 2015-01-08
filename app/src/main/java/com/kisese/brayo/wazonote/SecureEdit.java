package com.kisese.brayo.wazonote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;

import storage.RegistrationAdapter;
import storage.RegistrationOpenHelper;

/**
 * Created by Brayo on 8/2/2014.
 */
public class SecureEdit  extends ActionBarActivity {
    RegistrationAdapter regadapter;
    RegistrationOpenHelper openHelper;
    int rowId;
    Cursor c;



    private TextView description;
    private TextView headline;
    private TextView link;

    String url_str;
    String head_str;
    private String LOG_TAG;

    String a;
    String date;
    private String TAG;


    SharedPreferences tarehe;
    SharedPreferences headlines;
    SharedPreferences body;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editregister);

        tarehe = getSharedPreferences("tarehe", MODE_PRIVATE);
        headlines = getSharedPreferences("headline", MODE_PRIVATE);
        body = getSharedPreferences("body", MODE_PRIVATE);


        link = (TextView) findViewById(R.id.read_link);
        headline = (TextView) findViewById(R.id.read_head);
        description = (TextView) findViewById(R.id.read_desc);

        link.setText(tarehe.getString("tarehe", null));
        headline.setText(headlines.getString("headline", null));
        description.setText(body.getString("body", null));

        url_str = tarehe.getString("tarehe", null);
        head_str = headlines.getString("headline", null);


        getDate();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.notifyAll();
        //Inflate the ActionBar with this menu layout
        inflater.inflate(R.menu.nav_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //on actionBar item clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_edit:
                Toast.makeText(this, "Preparing to Edit", Toast.LENGTH_SHORT).show();
                //add a function t create the dialog
                Intent intent = new Intent(SecureEdit.this, EditNoteActivity.class);
                intent.putExtra("headline", headline.getText());
                intent.putExtra("description", description.getText());
                SecureEdit.this.startActivity(intent);
                regadapter.deletOneRecord(rowId);
                finish();
                break;

            case R.id.action_delete:
                alertYaErrorTena();
                break;


            case R.id.action_backup:
                Toast.makeText(this, "Preparing to Backup", Toast.LENGTH_SHORT).show();
                //doBackup();
                alertYaBack();


                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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



    public void doBackup(){
        if (isExternalStorageWritable() == true && isExternalStorageReadable() == true) {
            String head = headline.getText().toString();
            String body = description.getText().toString();
            getAlbumStorageDir(head);

            try{
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(head, Context.MODE_PRIVATE));
                outputStreamWriter.write(body);
                outputStreamWriter.close();
            }catch(IOException e){
                Log.e(TAG, "File Write failed");
                alertYaBackupFailed();
            }

        } else {
            alertNoSpace();
        }
    }


    public void writeToFile(){
        if (isExternalStorageWritable() == true && isExternalStorageReadable() == true) {
            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            String head = headline.getText().toString();
            String body = description.getText().toString();

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

                Toast.makeText(this, "Backup Succesful", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //End of backup files


    public void getDate(){

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
    }


    @SuppressWarnings("deprecation")
    private void alertNoSpace(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("     Oops!!");
        alertView.setMessage("Looks like your SD card is out of space");
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
        alertView.setTitle("    Hold Up!!");
        alertView.setMessage("Are you sure you want to delete this note");
        alertView.setButton("Delete", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                regadapter.deletOneRecord(rowId);
                alertView.dismiss();
                finish();
            }
        });
        alertView.show();
    }

    @SuppressWarnings("deprecation")
    private void alertYaBack(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("    Hold Up!!");
        alertView.setMessage("Press Backup to backup this note, It will be stored in a backup folder in your SD cards DOWNLOAD folder");
        alertView.setButton("Backup", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                writeToFile();
                alertView.dismiss();
                finish();
            }
        });
        alertView.show();
    }

    @SuppressWarnings("deprecation")
    private void alertYaBackupFailed(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("    Hold Up!!");
        alertView.setMessage("Note backup failed, please try again");
        alertView.setButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                alertView.dismiss();
                finish();
            }
        });
        alertView.show();
    }


}
