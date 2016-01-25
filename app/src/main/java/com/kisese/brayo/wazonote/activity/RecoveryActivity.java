package com.kisese.brayo.wazonote.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

/**
 * Created by Brayo on 7/29/2014.
 */
public class RecoveryActivity extends ActionBarActivity{

    private Button check_pass;
    private TextView question;
    private EditText answer;
    SharedPreferences new_password;
    SharedPreferences sec_question;
    private TextView text;
    private View layout;
    SharedPreferences sec_ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_recovery);

        question = (TextView)findViewById(R.id.question);
        answer = (EditText)findViewById(R.id.answer);
        check_pass = (Button)findViewById(R.id.check_pass);
        new_password = getSharedPreferences("password", MODE_PRIVATE);
        sec_question = getSharedPreferences("question", MODE_PRIVATE);
        sec_ans = getSharedPreferences("answer", MODE_PRIVATE);

        LayoutInflater inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        text = (TextView) layout.findViewById(R.id.text);

        String ques = sec_question.getString("sec_question", null);
        String an = sec_ans.getString("sec_answer", null);

        question.setText(ques);

        if(question.getText().toString().isEmpty()){
            showToast("No password has been set");
          //  alertYaEmpty();
            Intent intent = new Intent(RecoveryActivity.this, SecureActivity.class);
            RecoveryActivity.this.startActivity(intent);
            showToast("No password has been set");
           // Toast.makeText(this, "No password has been set", Toast.LENGTH_SHORT).show();
        }



        check_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheck();
            }
        });


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F5F5F5")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#696969\">" + getString(R.string.recovery) + "</font"));
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

    public void doCheck(){
        String an = sec_ans.getString("sec_answer", null);

        if(an.equals(answer.getText().toString())){
           // showToast("No password has been set");
            alertYaSuccess();
        }else{
            showToast("Wrong answer, please try again");
           // alertYaError();
        }
    }

    @SuppressWarnings("deprecation")
    private void alertYaError(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        //alertView.setTitle("Oops!!");
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
       // alertView.setTitle("Recovery Succesful!!");
        String pass = new_password.getString("password", null);
        alertView.setMessage("Your password is " + pass);
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
       // alertView.setTitle("Ooops!!");

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
        Intent intent = new Intent(RecoveryActivity.this, SecureActivity.class);
        RecoveryActivity.this.startActivity(intent);
        super.onBackPressed();
    }
}
