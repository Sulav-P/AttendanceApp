package com.example.sulav.attendanceapp.admin.ADMINFEEDBACK;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.admin.AdminActivity;
import com.example.sulav.attendanceapp.admin.AdminMain;

public class FEEDBACKMAIN extends AppCompatActivity {

    private Button StudentFeedback;
    private Button ParentFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackmain);

        StudentFeedback = (Button) findViewById(R.id.studentfeedback);
        ParentFeedback = (Button) findViewById(R.id.parentfeedback);


        StudentFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(FEEDBACKMAIN.this,ADMIN_STUDENTFEEDBACK.class);
                startActivity(intent);

            }
        });

        ParentFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(FEEDBACKMAIN.this,ADMIN_PARENTFEEDBACK.class);
                startActivity(intent);

            }
        });
    }
}
