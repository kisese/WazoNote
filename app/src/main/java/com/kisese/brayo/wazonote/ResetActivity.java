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
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Brayo on 7/29/2014.
 */
public class ResetActivity extends ActionBarActivity{


    private Button check_pass;
    private TextView question;
    private EditText answer;
    SharedPreferences new_password;
    SharedPreferences sec_question;
    SharedPreferences sec_ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_reset);

        question = (TextView)findViewById(R.id.reset_question);
        answer = (EditText)findViewById(R.id.reset_answer);
        check_pass = (Button)findViewById(R.id.reset_pass);
        new_password = getSharedPreferences("password", MODE_PRIVATE);
        sec_question = getSharedPreferences("question", MODE_PRIVATE);
        sec_ans = getSharedPreferences("answer", MODE_PRIVATE);


        String ques = sec_question.getString("sec_question", null);


        question.setText(ques);

        if(question.getText().toString().isEmpty()){
            alertYaEmpty();
            Intent intent = new Intent(ResetActivity.this, SecureActivity.class);
            ResetActivity.this.startActivity(intent);
            Toast.makeText(this, "No password has been set", Toast.LENGTH_SHORT).show();
        }



        check_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheck();
            }
        });



    }

    public void doCheck(){
        String an = sec_ans.getString("sec_answer", null);



        if(an.equals(answer.getText().toString())){
            SharedPreferences.Editor e1 = new_password.edit();
            SharedPreferences.Editor e2 = sec_question.edit();
            SharedPreferences.Editor e3 = sec_ans.edit();
            e1.remove("password");
            e2.remove("sec_question");
            e3.remove("sec_answer");
            e1.commit();
            e2.commit();
            e3.commit();

            alertYaSuccess();

        }else{
            alertYaError();
        }
    }

    @SuppressWarnings("deprecation")
    private void alertYaError(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("Oops!!");
        alertView.setMessage("Wrong answer, please try again");
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
        alertView.setTitle("Reset Succesful!!");
        alertView.setMessage("Your password has been reset please provide a new one");
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
        Intent intent = new Intent(ResetActivity.this, SecureActivity.class);
        ResetActivity.this.startActivity(intent);
        super.onBackPressed();
    }
}
