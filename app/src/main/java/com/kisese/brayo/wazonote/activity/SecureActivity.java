package com.kisese.brayo.wazonote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kisese.brayo.wazonote.R;


/**
 * Created by Brayo on 7/26/2014.
 */
public class SecureActivity extends ActionBarActivity {

    private EditText pass;
    private EditText sec_answer;
    private TextView sec_qstn, set_password, reset_password, recover_password;
    private Button register;
    private Button login;
    private RelativeLayout layo;
    SharedPreferences checker;
    private TextView text;
    private View layout;
    SharedPreferences new_password;
    SharedPreferences sec_question;
    SharedPreferences sec_ans;
    String s;


    private static String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure);

        pass = (EditText)findViewById(R.id.password);
        layo = (RelativeLayout)findViewById(R.id.lay);
        login = (Button)findViewById(R.id.login);
        set_password = (TextView)findViewById(R.id.set_password);
        reset_password = (TextView)findViewById(R.id.reset_password);
        recover_password = (TextView)findViewById(R.id.recover_password);

        new_password = getSharedPreferences("password", MODE_PRIVATE);
        sec_question = getSharedPreferences("question", MODE_PRIVATE);
        sec_ans = getSharedPreferences("answer", MODE_PRIVATE);

        LayoutInflater inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        text = (TextView) layout.findViewById(R.id.text);



        s = new_password.getString("password", null);

        set_password.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecureActivity.this, NewPassActivity.class);
                SecureActivity.this.startActivity(intent);
            }
        });


        reset_password.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //showToast("Proceeding to reset password");
               // Toast.makeText(getApplicationContext(), "Proceeding to reset password", Toast.LENGTH_SHORT).show();
                Intent intentb = new Intent(SecureActivity.this, ResetActivity.class);
                SecureActivity.this.startActivity(intentb);
            }
        });

        recover_password.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               // showToast("Proceeding to recover password");
               // Toast.makeText(getApplicationContext(), "Proceeding to recover password", Toast.LENGTH_SHORT).show();
                //add a function t create the dialog
                Intent intenta = new Intent(SecureActivity.this, RecoveryActivity.class);
                SecureActivity.this.startActivity(intenta);
            }
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F08080")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#696969\">" + getString(R.string.secure_main) + "</font"));
        getSupportActionBar().getThemedContext();
        //getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //check if its the first run of the activity

        /*
        if(initial == null){
            register.setText("Set Password");


        }else{
            register.setText("");
            register.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ced1")));
        }*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });


    }


    public void doLogin(){

        try {

            if (new_password.getString("password", null).equals(pass.getText().toString())) {
                Intent intentc = new Intent(SecureActivity.this, SecureMainActivity.class);
                SecureActivity.this.startActivity(intentc);
            } else {
                showToast("Your login details are not valid, please try again");
               // alertYaPassword();
            }
        }catch(NullPointerException e){
            e.printStackTrace();
            showToast("No password has been set");
            //alertYaEmpty();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.notifyAll();
        //Inflate the ActionBar with this menu layout
        //inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SecureActivity.this,MainActivity.class);
        SecureActivity.this.startActivity(intent);
        super.onBackPressed();
    }

    public void showToast(String story_text){
        text.setText(story_text);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}
