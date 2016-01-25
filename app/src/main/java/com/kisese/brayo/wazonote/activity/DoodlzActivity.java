package com.kisese.brayo.wazonote.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kisese.brayo.wazonote.R;
import com.kisese.brayo.wazonote.view.DoodleView;

import java.util.concurrent.atomic.AtomicBoolean;


public class DoodlzActivity extends ActionBarActivity {

    private DoodleView doodleView; // drawing View
    private SensorManager sensorManager; // monitors accelerometer
    private float acceleration; // acceleration
    private float currentAcceleration; // current acceleration
    private float lastAcceleration; // last acceleration
    private AtomicBoolean dialogIsVisible = new AtomicBoolean(); // false

    // create menu ids for each menu option
    private static final int COLOR_MENU_ID = Menu.FIRST;
    private static final int WIDTH_MENU_ID = Menu.FIRST + 1;
    private static final int ERASE_MENU_ID = Menu.FIRST + 2;
    private static final int CLEAR_MENU_ID = Menu.FIRST + 3;
    private static final int SAVE_MENU_ID = Menu.FIRST + 4;




    // value used to determine whether user shook the device to erase
    private static final int ACCELERATION_THRESHOLD = 105000;

    // variable that refers to a Choose Color or Choose Line Width dialog
    private Dialog currentDialog;
    private View layout;
    private TextView text;
    private SharedPreferences notes;

    // called when this Activity is loaded

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doodle_main);

        // get reference to the DoodleView
        doodleView = (DoodleView) findViewById(R.id.doodleView);

        LayoutInflater inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        text = (TextView) layout.findViewById(R.id.text);

        //set shared preferences variable
        notes = getSharedPreferences("doodle_status", Context.MODE_PRIVATE);


        // initialize acceleration values
        acceleration = 0.00f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

        enableAccelerometerListening(); // listen for shake


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F08080")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">Doodle</font"));
        getSupportActionBar().getThemedContext();
        //getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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



    public void showToast(String story_text){
        text.setText(story_text);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    // event handler for accelerometer events
    private SensorEventListener sensorEventListener =
            new SensorEventListener()
            {
                // use accelerometer to determine whether user shook device
                @Override
                public void onSensorChanged(SensorEvent event)
                {
                    // ensure that other dialogs are not displayed
                    if (!dialogIsVisible.get())
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

                                doodleView.saveImage(); // save the current images
                                setPrefs();
                                disableAccelerometerListening();
                        } // end if
                    } // end if
                } // end method onSensorChanged

                // required method of interface SensorEventListener
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy)
                {
                } // end method onAccuracyChanged
            }; // end anonymous inner class


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu); // call super's method

        // add options to menu
        menu.add(Menu.NONE, COLOR_MENU_ID, Menu.NONE,
                R.string.menuitem_color);
        menu.add(Menu.NONE, WIDTH_MENU_ID, Menu.NONE,
                R.string.menuitem_line_width);

        menu.add(Menu.NONE, CLEAR_MENU_ID, Menu.NONE,
                R.string.menuitem_clear);
        menu.add(Menu.NONE, SAVE_MENU_ID, Menu.NONE,
                R.string.menuitem_save_image);

        return true; // options menu creation was handled
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // switch based on the MenuItem id
        switch (item.getItemId())
        {
            case COLOR_MENU_ID:
                showColorDialog(); // display color selection dialog
                return true; // consume the menu event
            case WIDTH_MENU_ID:
                showLineWidthDialog(); // display line thickness dialog
                return true; // consume the menu event

            case CLEAR_MENU_ID:
                doodleView.clear(); // clear doodleView
                return true; // consume the menu event
            case SAVE_MENU_ID:
                doodleView.saveImage(); // save the current images
                disableAccelerometerListening();
                return true; // consume the menu event
        } // end switch

        return super.onOptionsItemSelected(item); // call super's method
    }



    // display a dialog for selecting color
    private void showColorDialog()
    {
        // create the dialog and inflate its content
        currentDialog = new Dialog(this);
        currentDialog.setContentView(R.layout.color_dialog);
        currentDialog.setTitle(R.string.title_color_dialog);
        currentDialog.setCancelable(true);

        // get the color SeekBars and set their onChange listeners
        final SeekBar alphaSeekBar =
                (SeekBar) currentDialog.findViewById(R.id.alphaSeekBar);
        final SeekBar redSeekBar =
                (SeekBar) currentDialog.findViewById(R.id.redSeekBar);
        final SeekBar greenSeekBar =
                (SeekBar) currentDialog.findViewById(R.id.greenSeekBar);
        final SeekBar blueSeekBar =
                (SeekBar) currentDialog.findViewById(R.id.blueSeekBar);

        // register SeekBar event listeners
        alphaSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);
        redSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);
        greenSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);
        blueSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);

        // use current drawing color to set SeekBar values
        final int color = doodleView.getDrawingColor();
        alphaSeekBar.setProgress(Color.alpha(color));
        redSeekBar.setProgress(Color.red(color));
        greenSeekBar.setProgress(Color.green(color));
        blueSeekBar.setProgress(Color.blue(color));

        // set the Set Color Button's onClickListener
        Button setColorButton = (Button) currentDialog.findViewById(
                R.id.setColorButton);
        setColorButton.setOnClickListener(setColorButtonListener);

        dialogIsVisible.set(true); // dialog is on the screen
        currentDialog.show(); // show the dialog
    } // end method showColorDialog

    // OnSeekBarChangeListener for the SeekBars in the color dialog
    private SeekBar.OnSeekBarChangeListener colorSeekBarChanged =
            new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser)
                {
                    // get the SeekBars and the colorView LinearLayout
                    SeekBar alphaSeekBar =
                            (SeekBar) currentDialog.findViewById(R.id.alphaSeekBar);
                    SeekBar redSeekBar =
                            (SeekBar) currentDialog.findViewById(R.id.redSeekBar);
                    SeekBar greenSeekBar =
                            (SeekBar) currentDialog.findViewById(R.id.greenSeekBar);
                    SeekBar blueSeekBar =
                            (SeekBar) currentDialog.findViewById(R.id.blueSeekBar);
                    View colorView =
                            (View) currentDialog.findViewById(R.id.colorView);

                    // display the current color
                    colorView.setBackgroundColor(Color.argb(
                            alphaSeekBar.getProgress(), redSeekBar.getProgress(),
                            greenSeekBar.getProgress(), blueSeekBar.getProgress()));
                } // end method onProgressChanged

                // required method of interface OnSeekBarChangeListener
                @Override
                public void onStartTrackingTouch(SeekBar seekBar)
                {
                } // end method onStartTrackingTouch

                // required method of interface OnSeekBarChangeListener
                @Override
                public void onStopTrackingTouch(SeekBar seekBar)
                {
                } // end method onStopTrackingTouch
            }; // end colorSeekBarChanged

    // OnClickListener for the color dialog's Set Color Button
    private View.OnClickListener setColorButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // get the color SeekBars
            SeekBar alphaSeekBar =
                    (SeekBar) currentDialog.findViewById(R.id.alphaSeekBar);
            SeekBar redSeekBar =
                    (SeekBar) currentDialog.findViewById(R.id.redSeekBar);
            SeekBar greenSeekBar =
                    (SeekBar) currentDialog.findViewById(R.id.greenSeekBar);
            SeekBar blueSeekBar =
                    (SeekBar) currentDialog.findViewById(R.id.blueSeekBar);

            // set the line color
            doodleView.setDrawingColor(Color.argb(
                    alphaSeekBar.getProgress(), redSeekBar.getProgress(),
                    greenSeekBar.getProgress(), blueSeekBar.getProgress()));
            dialogIsVisible.set(false); // dialog is not on the screen
            currentDialog.dismiss(); // hide the dialog
            currentDialog = null; // dialog no longer needed
        } // end method onClick
    }; // end setColorButtonListener

    // display a dialog for setting the line width
    private void showLineWidthDialog()
    {
        // create the dialog and inflate its content
        currentDialog = new Dialog(this);
        currentDialog.setContentView(R.layout.width_dialog);
        currentDialog.setTitle(R.string.title_line_width_dialog);
        currentDialog.setCancelable(true);

        // get widthSeekBar and configure it
        SeekBar widthSeekBar =
                (SeekBar) currentDialog.findViewById(R.id.widthSeekBar);
        widthSeekBar.setOnSeekBarChangeListener(widthSeekBarChanged);
        widthSeekBar.setProgress(doodleView.getLineWidth());

        // set the Set Line Width Button's onClickListener
        Button setLineWidthButton =
                (Button) currentDialog.findViewById(R.id.widthDialogDoneButton);
        setLineWidthButton.setOnClickListener(setLineWidthButtonListener);

        dialogIsVisible.set(true); // dialog is on the screen
        currentDialog.show(); // show the dialog
    } // end method showLineWidthDialog

    // OnSeekBarChangeListener for the SeekBar in the width dialog
    private SeekBar.OnSeekBarChangeListener widthSeekBarChanged =
            new SeekBar.OnSeekBarChangeListener()
            {
                Bitmap bitmap = Bitmap.createBitmap( // create Bitmap
                        400, 100, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap); // associate with Canvas

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser)
                {
                    // get the ImageView
                    ImageView widthImageView = (ImageView)
                            currentDialog.findViewById(R.id.widthImageView);

                    // configure a Paint object for the current SeekBar value
                    Paint p = new Paint();
                    p.setColor(doodleView.getDrawingColor());
                    p.setStrokeCap(Paint.Cap.ROUND);
                    p.setStrokeWidth(progress);

                    // erase the bitmap and redraw the line
                    bitmap.eraseColor(Color.WHITE);
                    canvas.drawLine(30, 50, 370, 50, p);
                    widthImageView.setImageBitmap(bitmap);
                } // end method onProgressChanged

                // required method of interface OnSeekBarChangeListener
                @Override
                public void onStartTrackingTouch(SeekBar seekBar)
                {
                } // end method onStartTrackingTouch

                // required method of interface OnSeekBarChangeListener
                @Override
                public void onStopTrackingTouch(SeekBar seekBar)
                {
                } // end method onStopTrackingTouch
            }; // end widthSeekBarChanged

    // OnClickListener for the line width dialog's Set Line Width Button
    private View.OnClickListener setLineWidthButtonListener =
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // get the color SeekBars
                    SeekBar widthSeekBar =
                            (SeekBar) currentDialog.findViewById(R.id.widthSeekBar);

                    // set the line color
                    doodleView.setLineWidth(widthSeekBar.getProgress());
                    dialogIsVisible.set(false); // dialog is not on the screen
                    currentDialog.dismiss(); // hide the dialog
                    currentDialog = null; // dialog no longer needed
                } // end method onClick
            }; // end setColorButtonListener

    @Override
    public void onBackPressed() {
        doodleView.saveImage();
        setPrefs();
        super.onBackPressed();
    }

    public void setPrefs(){
        SharedPreferences.Editor preferencesEditor = notes.edit();
        preferencesEditor.putString("doodles_empty", "no");
        preferencesEditor.commit();

    }
} // end class DoodlzActivity

