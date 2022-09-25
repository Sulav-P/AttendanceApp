package com.example.sulav.attendanceapp.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.sulav.attendanceapp.AttendanceView.AttenadanceViewMain;
import com.example.sulav.attendanceapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class StudentMain extends AppCompatActivity {

    private Button sLogout;
    private Button AttendanceView;
    private Button SendFeedback;
    private Button SendLeaveRequest;
    String Name;

    private ProgressBar progressBar;

    private FirebaseAuth slogoutAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        Intent intent = getIntent();
        Name = intent.getExtras().getString("KeyStudent");

        slogoutAuth = FirebaseAuth.getInstance();

       // sLogout = (Button) findViewById(R.id.btnsLOGOUT);
        AttendanceView = (Button) findViewById(R.id.attendanceview);
        SendFeedback = (Button) findViewById(R.id.sendfeedback);
        SendLeaveRequest = (Button) findViewById(R.id.sendleaverequest);


        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);


     /*   sLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                slogoutAuth.signOut();
                progressBar.setVisibility(View.GONE);
                Intent intent =  new Intent(StudentMain.this,StudentActivity.class);
                startActivity(intent);
                finish();
            }
        });  */

        AttendanceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(StudentMain.this,AttenadanceViewMain.class);
                startActivity(intent);

            }
        });

        SendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(StudentMain.this,STUDENTFEEDBACK.class);
                intent.putExtra("SName", Name);
                startActivity(intent);

            }
        });

        SendLeaveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(StudentMain.this,LEAVE_REQUEST.class);
                intent.putExtra("SName", Name);
                startActivity(intent);

            }
        });


    }
}
