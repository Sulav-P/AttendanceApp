package com.example.sulav.attendanceapp.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


import com.example.sulav.attendanceapp.AttendanceView.AttenadanceViewMain;
import com.example.sulav.attendanceapp.admin.ADMINFEEDBACK.FEEDBACKMAIN;
import com.example.sulav.attendanceapp.admin.DeleteClassTeacherStudent.DeleteClassTeacherStudent;
import com.example.sulav.attendanceapp.admin.ADMINFEEDBACK.ADMIN_PARENTFEEDBACK;
import com.example.sulav.attendanceapp.admin.AddClasstTeacherStudent.AddClassTeacherStudentMain;

import com.example.sulav.attendanceapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminMain extends AppCompatActivity {

    private Button aLogout;
    //  private Button Registration;
    //  private Button Deleteregisterdusers;
    private Button Addclassteacherstudent;
    private Button Userfeedbacks;
    private Button Deleteclassteacherstudent;
    private Button AttendView;

    private ProgressBar progressBar;


    private FirebaseAuth alogoutAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        alogoutAuth = FirebaseAuth.getInstance();


      //  aLogout = (Button) findViewById(R.id.btnaLOGOUT);
        //  Registration = (Button)findViewById(R.id.registration);
        Addclassteacherstudent = (Button) findViewById(R.id.addclassteacherstudent);
        Userfeedbacks = (Button) findViewById(R.id.userfeedbacks);
        Deleteclassteacherstudent = (Button) findViewById(R.id.deleteclassteacherstudent);
        AttendView = (Button) findViewById(R.id.Aattendview);
        //  Deleteregisterdusers =(Button)findViewById(R.id.deleteregisterdusers);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);


      /*  aLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                alogoutAuth.signOut();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(AdminMain.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

      /*  Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMain.this,RegisterMain.class);
                startActivity(intent);
            }
        });

        Deleteregisterdusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMain.this,DeleteUser.class);
                startActivity(intent);
            }
        });
        */

        Addclassteacherstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMain.this, AddClassTeacherStudentMain.class);
                startActivity(intent);
            }
        });


        Userfeedbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMain.this, FEEDBACKMAIN.class);
                startActivity(intent);
            }
        });

        Deleteclassteacherstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMain.this, DeleteClassTeacherStudent.class);
                startActivity(intent);
            }
        });

        AttendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMain.this, AttenadanceViewMain.class);
                startActivity(intent);
            }
        });


    }
}