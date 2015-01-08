package com.kisese.brayo.wazonote;

import android.app.AlertDialog;
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
public class SecureList extends ActionBarActivity {

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

        list=(ListView)findViewById(R.id.lv_name);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                notes.edit().remove(tags[position]).commit();


                alertYaDelete();
            }
        });
    }



    @SuppressWarnings("deprecation")
    private void alertYaDelete(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("Notice!");
        alertView.setMessage("Your content will be deleted");
        alertView.setButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(SecureList.this, SecureList.class);
                SecureList.this.startActivity(intent);

                alertView.dismiss();
            }
        });
        alertView.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SecureList.this, SecureMain.class);
        SecureList.this.startActivity(intent);
        super.onBackPressed();
    }
}
