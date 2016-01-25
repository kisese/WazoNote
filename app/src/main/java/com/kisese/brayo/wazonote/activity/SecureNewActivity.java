package com.kisese.brayo.wazonote.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kisese.brayo.wazonote.R;

/**
 * Created by Brayo on 8/2/2014.
 */
public class SecureNewActivity extends ActionBarActivity {

    Button back;
    EditText title, story;
    String date;
    private TextView text;
    private View layout;
    String title_text;
    String story_text;
    String check;
    String a;

    SharedPreferences notes;

    private SensorManager sensorManager; // monitors accelerometer
    private float acceleration; // acceleration
    private float currentAcceleration; // current acceleration
    private float lastAcceleration; // last acceleration

    // value used to determine whether user shook the device to erase
    private static final int ACCELERATION_THRESHOLD = 105000;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);

        notes = getSharedPreferences("secure", MODE_PRIVATE);


        title = (EditText)findViewById(R.id.note_title);
        story = (EditText)findViewById(R.id.note_text);
        LayoutInflater inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        text = (TextView) layout.findViewById(R.id.text);



        // initialize acceleration values
        acceleration = 0.00f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

        enableAccelerometerListening(); // listen for shake





        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F5F5F5")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#696969\">" + getString(R.string.app_name) + "</font"));
        getSupportActionBar().getThemedContext();
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void addNote(){
        Intent myIntent2 = getIntent();

        title_text = title.getText().toString();
        story_text = story.getText().toString();

        if(story_text.length()==0 && title_text.length()>0){
            story_text = title.getText().toString();

            String title;
            title = story_text.substring(0, Math.min(story_text.length(),30));

            SharedPreferences.Editor editor = notes.edit();
            editor.putString(title, story_text);
            editor.commit();
            showToast("Note Saved");
            Intent intent = new Intent(SecureNewActivity.this, SecureMainActivity.class);
            SecureNewActivity.this.startActivity(intent);

        }else if(title_text.length()==0 && story_text.length()>0){
            title_text = story.getText().toString();

            title_text = title_text.substring(0, Math.min(title_text.length(),30));

            SharedPreferences.Editor editor = notes.edit();
            editor.putString(title_text, story_text);
            editor.commit();
            showToast("Note Saved");
            Intent intent = new Intent(SecureNewActivity.this, SecureMainActivity.class);
            SecureNewActivity.this.startActivity(intent);
            //showToast("Please add a title");
        }else if(title_text.length()>0 && story_text.length()>0){
            title_text = title_text.substring(0, Math.min(title_text.length(),30));

            SharedPreferences.Editor editor = notes.edit();
            editor.putString(title_text, story_text);
            editor.commit();
            showToast("Note Saved");
            Intent intent = new Intent(SecureNewActivity.this, SecureMainActivity.class);
            SecureNewActivity.this.startActivity(intent);
        }
        else{



        }
    }

    // when app is sent to the background, stop listening for sensor events
    @Override
    protected void onPause(){
        super.onPause();
        disableAccelerometerListening(); // don't listen for shake
    } // end method onPause

    @Override
    protected void onStop() {
        super.onStop();
        disableAccelerometerListening(); // don't listen for shake

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        enableAccelerometerListening();
    }

    // enable listening for accelerometer events
    private void enableAccelerometerListening(){
        // initialize the SensorManager
        sensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void disableAccelerometerListening()
    {
        // stop listening for sensor events
        if (sensorManager != null)
        {
            sensorManager.unregisterListener(
                    sensorEventListener,
                    sensorManager.getDefaultSensor(
                            Sensor.TYPE_ACCELEROMETER));
            sensorManager = null;
        } // end if
    } // end method disableAccelerometerListening


    // event handler for accelerometer events
    private SensorEventListener sensorEventListener =
            new SensorEventListener()
            {
                // use accelerometer to determine whether user shook device
                @Override
                public void onSensorChanged(SensorEvent event)
                {

                    // get x, y, and z values for the SensorEvent
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    // save previous acceleration value
                    lastAcceleration = currentAcceleration;

                    // calculate the current acceleration
                    currentAcceleration = x * x + y * y + z * z;

                    // calculate the change in acceleration
                    acceleration = currentAcceleration *
                            (currentAcceleration - lastAcceleration);

                    // if the acceleration is above a certain threshold
                    if (acceleration > ACCELERATION_THRESHOLD)
                    {
                        // save note

                        if(title.getText().toString().isEmpty() && story.getText().toString().isEmpty()){

                        }else {
                            addNote();
                        }


                    } // end if
                } // end method onSensorChanged

                // required method of interface SensorEventListener
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy)
                {
                } // end method onAccuracyChanged
            }; // end anonymous inner class


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
        getMenuInflater().inflate(R.menu.add_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case android.R.id.home:
                addNote();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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
        addNote();
        super.onBackPressed();
    }
}

