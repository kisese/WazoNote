package com.kisese.brayo.wazonote.file_explorer;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kisese.brayo.wazonote.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import storage.RegistrationActivity;

/**
 * Created by Brayo on 8/1/2014.
 */
public class AndroidExplorerShare extends ListActivity {

    private List<String> item = null;
    private List<String> path = null;
    private String root;
    private TextView myPath;

    String a, date, body;
    String line;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myPath = (TextView) findViewById(R.id.path);

        root = Environment.getExternalStorageDirectory().getPath().concat("/download/wazo_note_backups");

        getDir(root);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int time = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);
        int am = c.get(Calendar.AM_PM);

        if (am == 1) {
            a = "PM";
        } else {
            a = "AM";
        }

        if (min < 10) {
            date = day + "/ " + month + "/ " + year + " - " + time + " : 0" + min + " " + a;
        } else {
            date = day + "/ " + month + "/ " + year + " - " + time + " : " + min + " " + a;
        }
    }

    private void getDir(String dirPath) {
        myPath.setText("Location: " + dirPath);
        item = new ArrayList<String>();
        path = new ArrayList<String>();
        File f = new File(dirPath);
        File[] files = f.listFiles();

        if (!dirPath.equals(root)) {
            item.add(root);
            path.add(root);
            item.add("../");
            path.add(f.getParent());
        }

        for (int i = 0; i < files.length; i++) {
            File file = files[i];

            if (!file.isHidden() && file.canRead()) {
                path.add(file.getPath());
                if (file.isDirectory()) {
                    item.add(file.getName() + "/");
                } else {
                    item.add(file.getName());
                }
            }
        }

        ArrayAdapter<String> fileList =
                new ArrayAdapter<String>(this, R.layout.row_b, item);
        setListAdapter(fileList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        final File file = new File(path.get(position));

        if (file.isDirectory()) {
            if (file.canRead()) {
                getDir(path.get(position));
            } else {
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_launcher)
                        .setTitle("[" + file.getName() + "] folder can't be read!")
                        .setPositiveButton("OK", null).show();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_launcher)
                    .setTitle("[" + file.getName() + "]")
                    .setPositiveButton("Restore", new DialogInterface.OnClickListener() {
                        public Object fileUri;

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            try {
                                File myFle = new File("/sdcard/download/wazo_note_backups/" + file.getName());
                                fileUri = FileProvider.getUriForFile(
                                        AndroidExplorerShare.this,
                                        "com.example.myapp.fileprovider",
                                        myFle);


                            } catch (IllegalArgumentException e) {
                                Log.e("File Selector",
                                        "The selected file can't be shared: " +
                                                file.getName()
                                );
                            }



                            Intent intent = new Intent(AndroidExplorerShare.this, RegistrationActivity.class);
                            intent.putExtra("link", date);
                            intent.putExtra("headline", file.getName().toString().concat("-restore"));
                            intent.putExtra("description", body);
                            AndroidExplorerShare.this.startActivity(intent);
                            alertYaSuccess();

                        }
                    }).show();
        }
    }


    @SuppressWarnings("deprecation")
    private void alertYaSuccess() {
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
}
