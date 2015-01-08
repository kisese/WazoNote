package com.kisese.brayo.wazonote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Brayo on 7/26/2014.
 */
public class SecureActivity extends ActionBarActivity {

    private EditText pass;
    private EditText sec_answer;
    private TextView sec_qstn;
    private Button register;
    private Button login;
    private RelativeLayout layo;
    SharedPreferences checker;
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

        new_password = getSharedPreferences("password", MODE_PRIVATE);
        sec_question = getSharedPreferences("question", MODE_PRIVATE);
        sec_ans = getSharedPreferences("answer", MODE_PRIVATE);


        s = new_password.getString("password", null);

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
                Intent intentc = new Intent(SecureActivity.this, SecureMain.class);
                SecureActivity.this.startActivity(intentc);
            } else {
                alertYaPassword();
            }
        }catch(NullPointerException e){
            e.printStackTrace();
            alertYaEmpty();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.notifyAll();
        //Inflate the ActionBar with this menu layout
        inflater.inflate(R.menu.pass_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_recover:



                Toast.makeText(this, "Proceeding to recover password", Toast.LENGTH_SHORT).show();
                //add a function t create the dialog
                Intent intenta = new Intent(SecureActivity.this, RecoveryActivity.class);
                SecureActivity.this.startActivity(intenta);
                break;

            case R.id.action_reset:

                Toast.makeText(this, "Proceeding to reset password", Toast.LENGTH_SHORT).show();


                Intent intentb = new Intent(SecureActivity.this, ResetActivity.class);
                SecureActivity.this.startActivity(intentb);
                break;

            case R.id.action_add:
                Intent intent = new Intent(SecureActivity.this, NewPassActivity.class);
                SecureActivity.this.startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("deprecation")
    private void alertYaPassword(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("Ooops!!");
        alertView.setMessage("Your login details are not valid, please try again");
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
    private void alertYaEmpty(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("Ooops!!");
        alertView.setMessage("No password has been set");
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
        Intent intent = new Intent(SecureActivity.this,MainActivity.class);
        SecureActivity.this.startActivity(intent);
        super.onBackPressed();
    }

}
