package com.kisese.brayo.wazonote;

/**
 * Created by Brayo on 7/23/2014.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StickyNoteActivity  extends ActionBarActivity {

    SharedPreferences stickyTexts;

    Button save, back;
    EditText story;
    String key = "note";
    String sticky_text;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticky_note);

        //me = new ExampleWidgetProvider(this);

        stickyTexts = getSharedPreferences("sticky", MODE_PRIVATE);
        final SharedPreferences.Editor preferencesEditor = stickyTexts.edit();


        story = (EditText)findViewById(R.id.sticky_text);
        save = (Button)findViewById(R.id.sticky_save_btn);

        Typeface t = Typeface.createFromAsset(getAssets(),"fonts/sticky.ttf" );
        story.setTypeface(t);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sticky_text = story.getText().toString();

                if(sticky_text.length() == 0){
                    alertYaError();
                }else{
                    preferencesEditor.putString(key, sticky_text);
                    preferencesEditor.commit();

                    alertYaNetwork();
                    story.setText("");
                }
            }
        });


    }

    @SuppressWarnings("deprecation")
    private void alertYaNetwork(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("	Okay Then");
        alertView.setMessage("Your note has been saved, open a new widget to display it");
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StickyNoteActivity.this,MainActivity.class);
        StickyNoteActivity.this.startActivity(intent);
        super.onBackPressed();
    }

}