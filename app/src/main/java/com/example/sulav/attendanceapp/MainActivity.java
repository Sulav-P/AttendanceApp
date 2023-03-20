package com.example.sulav.attendanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.sulav.attendanceapp.admin.AdminActivity;
import com.example.sulav.attendanceapp.parent.ParentActivity;
import com.example.sulav.attendanceapp.student.StudentActivity;
import com.example.sulav.attendanceapp.teacher.TeacherActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public ImageButton btnadmin;
    public ImageButton btnStudent;
    public ImageButton btnteacher;
    public ImageButton btnparent;
    public Button btnlogoutmain;
    private FirebaseAuth alogoutAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alogoutAuth = FirebaseAuth.getInstance();


        btnadmin = (ImageButton) findViewById(R.id.btnADMIN);
        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });

       btnteacher = (ImageButton) findViewById(R.id.btnTEACHER);
        btnteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TeacherActivity.class);
                startActivity(intent);
            }
        });

        btnStudent = (ImageButton) findViewById(R.id.btnSTUDENT);
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent (MainActivity.this,StudentActivity.class);
                startActivity(intent);
            }
        });

        btnparent = (ImageButton) findViewById(R.id.btnPARENT);
        btnparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent (MainActivity.this,ParentActivity.class);
                startActivity(intent);
            }
        });

     /*   btnlogoutmain = (Button)findViewById(R.id.btnlogoutmain);
        btnlogoutmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alogoutAuth.signOut();

            }
        });  */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        alogoutAuth.signOut();

        return super.onOptionsItemSelected(item);
    }
}
