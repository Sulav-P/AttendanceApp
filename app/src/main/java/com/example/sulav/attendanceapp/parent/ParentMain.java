package com.example.sulav.attendanceapp.parent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.sulav.attendanceapp.AttendanceView.AttenadanceViewMain;
import com.example.sulav.attendanceapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class ParentMain extends AppCompatActivity {
    private Button pLogout;
    private Button parentbuttonmain1 ;
    private Button parentbuttonmain2;

    private ProgressBar progressBar;


    private FirebaseAuth plogoutAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);

        plogoutAuth = FirebaseAuth.getInstance();


        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);



        parentbuttonmain1 = (Button) findViewById(R.id.parentbutton_atten_setdate) ;
        parentbuttonmain1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSETDATE();
            }
        });

        parentbuttonmain2 = (Button) findViewById(R.id.parentbutton_feedback);
        parentbuttonmain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFEEDBACK();
            }
        });

       /* pLogout = (Button) findViewById(R.id.btnpLOGOUT);
        pLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                plogoutAuth.signOut();
                progressBar.setVisibility(View.VISIBLE);
                Intent intent =  new Intent(ParentMain.this,ParentActivity.class);
                startActivity(intent);
                finish();
            }
        });  */

    }

    public void openSETDATE(){
        Intent parentbutton_atten_setdateintent = new Intent(this, AttenadanceViewMain.class);
        startActivity (parentbutton_atten_setdateintent);
    }
    public void openFEEDBACK() {
        Intent parentbutton_feedbackintent = new Intent(this, PARENTFEEDBACK.class);
        startActivity(parentbutton_feedbackintent);
    }

}
