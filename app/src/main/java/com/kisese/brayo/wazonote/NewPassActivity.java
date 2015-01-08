package com.kisese.brayo.wazonote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Brayo on 7/29/2014.
 */
public class NewPassActivity extends ActionBarActivity{

    private EditText new_pass;
    private EditText rep_pass;
    private EditText sec_qstn;
    private EditText sec_answer;
    private Button set;
    SharedPreferences new_password;
    SharedPreferences sec_question;
    SharedPreferences sec_ans;

    String a1, a2, a3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_password);

        new_password = getSharedPreferences("password", MODE_PRIVATE);
        sec_question = getSharedPreferences("question", MODE_PRIVATE);
        sec_ans = getSharedPreferences("answer", MODE_PRIVATE);


try{
        if(sec_question.getString("sec_question", null).length() > 1) {
            Intent intent = new Intent(NewPassActivity.this, SecureActivity.class);
            NewPassActivity.this.startActivity(intent);
            Toast.makeText(this, "A password has already been set", Toast.LENGTH_SHORT).show();
            }
        }catch(NullPointerException e){
    e.printStackTrace();
}

        new_pass = (EditText)findViewById(R.id.new_password);
        rep_pass = (EditText)findViewById(R.id.repeat_password);
        sec_qstn = (EditText)findViewById(R.id.security_question);
        sec_answer = (EditText)findViewById(R.id.security_answer);

        set = (Button)findViewById(R.id.submit_password);


        setPass();
    }


    public void setPass(){
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1 = new_pass.getText().toString();
                String pass2 = rep_pass.getText().toString();
                String secu = sec_qstn.getText().toString();
                String ans = sec_answer.getText().toString();

                if(pass1.equals(pass2)){

                    if(sec_answer.getText().toString().length() > 1){

                        SharedPreferences.Editor editor = new_password.edit();
                        editor.putString("password", pass1);
                        editor.commit();


                        SharedPreferences.Editor editor2 = sec_question.edit();
                        editor2.putString("sec_question", secu);
                        editor2.commit();

                        SharedPreferences.Editor editor3 = sec_ans.edit();
                        editor3.putString("sec_answer", ans);
                        editor3.commit();

                        a1 = pass1;
                        a2 = secu;
                        a3 = ans;

                        alertYaSuccess();
                    }else{
                        noTextAlert();
                    }
                }else{
                    noMatchAlert();
                }
            }
        });

    }

    @SuppressWarnings("deprecation")
    private void noMatchAlert(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("Ooops!!");
        alertView.setMessage("Your passwords don't match, please try again");
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
    private void noTextAlert(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("Ooops!!");
        alertView.setMessage("Your security details are not valid, please try again");
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
    private void alertYaSuccess(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("Success!!");
        alertView.setMessage("Your details are as follows \n password: " + a1 + "\n security question: " + a2 + "\n security answer: " + a3 + "\n " +
                "Proceed to login to save your notes securely");
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
        Intent intent = new Intent(NewPassActivity.this, SecureActivity.class);
        NewPassActivity.this.startActivity(intent);
        super.onBackPressed();
    }
    }
