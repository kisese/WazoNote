package com.kisese.brayo.wazonote.activity;

/**
 * Created by Brayo on 7/23/2014.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kisese.brayo.wazonote.R;

public class StickyNoteActivity  extends ActionBarActivity {

    SharedPreferences stickyTexts;

    Button save, back;
    EditText story;
    String key = "note";
    String sticky_text;
    private TextView text;
    private View layout;


    private SensorManager sensorManager; // monitors accelerometer
    private float acceleration; // acceleration
    private float currentAcceleration; // current acceleration
    private float lastAcceleration; // last acceleration

    // value used to determine whether user shook the device to erase
    private static final int ACCELERATION_THRESHOLD = 105000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticky_note);

        //me = new ExampleWidgetProvider(this);




        story = (EditText)findViewById(R.id.sticky_text);
        save = (Button)findViewById(R.id.sticky_save_btn);
        LayoutInflater inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        text = (TextView) layout.findViewById(R.id.text);

        Typeface t = Typeface.createFromAsset(getAssets(),"fonts/sticky.ttf" );
        story.setTypeface(t);


        // initialize acceleration values
        acceleration = 0.00f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

        enableAccelerometerListening(); // listen for shake




        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F08080")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">Sticky Note</font"));
        getSupportActionBar().getThemedContext();
        //getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void showToast(String story_text){
        text.setText(story_text);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void save(){
        sticky_text = story.getText().toString();

        if(sticky_text.length() == 0){
            showToast("Please type in something");
        }else{
            stickyTexts = getSharedPreferences("sticky", MODE_PRIVATE);
            final SharedPreferences.Editor preferencesEditor = stickyTexts.edit();

            preferencesEditor.putString(key, sticky_text);
            preferencesEditor.commit();

            showToast("Note Saved");
            Intent intent = new Intent(StickyNoteActivity.this,MainActivity.class);
            StickyNoteActivity.this.startActivity(intent);
            //story.setText("");
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

                        if(story.getText().toString().isEmpty()){

                        }else {
                            save();
                        }


                    } // end if
                } // end method onSensorChanged

                // required method of interface SensorEventListener
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy)
                {
                } // end method onAccuracyChanged
            }; // end anonymous inner class



    @Override
    public void onBackPressed() {
        save();
        super.onBackPressed();
    }

}