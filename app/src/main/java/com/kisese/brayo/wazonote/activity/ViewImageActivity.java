package com.kisese.brayo.wazonote.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.kisese.brayo.wazonote.R;

import java.io.File;

/**
 * Created by Brayo on 2/11/2015.
 */
public class ViewImageActivity extends ActionBarActivity {
    // Declare Variable
    TextView text;
    ImageView imageview;
    private String path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from view_image.xml
        setContentView(R.layout.view_image);

        // Retrieve data from MainActivity on GridView item click
        Intent i = getIntent();

        // Get the position
        int position = i.getExtras().getInt("position");

        // Get String arrays FilePathStrings
        String[] filepath = i.getStringArrayExtra("filepath");

        // Get String arrays FileNameStrings
        String[] filename = i.getStringArrayExtra("filename");

        path = filepath[position];

        // Locate the TextView in view_image.xml
        text = (TextView) findViewById(R.id.imagetext);

        // Load the text into the TextView followed by the position
        text.setText(filename[position]);

        // Locate the ImageView in view_image.xml
        imageview = (ImageView) findViewById(R.id.full_image_view);

        // Decode the filepath with BitmapFactory followed by the position
        Bitmap bmp = BitmapFactory.decodeFile(filepath[position]);

        // Set the decoded bitmap into ImageView
        imageview.setImageBitmap(bmp);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F08080")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\"></font"));
        getSupportActionBar().getThemedContext();
        //getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.notifyAll();
        //Inflate the ActionBar with this menu layout
        inflater.inflate(R.menu.doodle, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_doodle:
                alertYaDelete();

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("deprecation")
    private void alertYaDelete(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        // alertView.setTitle("    Hold Up!!");
        alertView.setMessage("Delete doodle?");
        alertView.setButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                File file = new File(path);
                file.delete();
                Intent intent = new Intent(ViewImageActivity.this, MyDoodlesActivity.class);
                ViewImageActivity.this.startActivity(intent);

                alertView.dismiss();

            }
        });
        alertView.show();
    }
}
