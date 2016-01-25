package storage;

/**
 * Created by Brayo on 7/23/2014.
 */
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kisese.brayo.wazonote.R;

public class RegistrationActivity extends Activity {

    private SharedPreferences heads;

    NotesTableAdapter adapter;
    NotesDBHelper helper;
    EditText fnameEdit, lnameEdit;
    Button submitBtn, resetBtn;
    NotesDBHelper helper_ob;
    String temp = "";
    TextView tester;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        tester = (TextView)findViewById(R.id.tester);

        submitBtn = (Button) findViewById(R.id.btn_submit);
        heads = getSharedPreferences("headlines", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = heads.edit();



        Intent myIntent3 = getIntent();


        String headlineValue =  myIntent3.getStringExtra("headline");
        String descriptionValue =  myIntent3.getStringExtra("description");

       // tester.setText(descriptionValue);

        preferencesEditor.putString(headlineValue, descriptionValue);
        preferencesEditor.commit();

        adapter = new NotesTableAdapter(this);

        submitBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent myIntent3 = getIntent();

                String linkValue =  myIntent3.getStringExtra("link");
                String headlineValue =  myIntent3.getStringExtra("headline");
                String descriptionValue =  myIntent3.getStringExtra("description");

               temp = heads.getString(headlineValue, null);

                if(temp != "")
                    adapter.insertDetails(linkValue, headlineValue, descriptionValue);
                else
                 adapter.insertDetails(linkValue, headlineValue.concat("_b"), descriptionValue);

                // Toast.makeText(getApplicationContext(), Long.toString(val),
                // 300).show();
                finish();
            }
        });
    }
}

