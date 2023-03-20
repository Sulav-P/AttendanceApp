package com.example.sulav.attendanceapp.AttendanceView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sulav.attendanceapp.R;

public class AttenadanceViewMain extends AppCompatActivity {
    Button AttendChoosePerson;
    Button AttendChooseClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attenadance_view_main);

        AttendChoosePerson=(Button) findViewById(R.id.AttendChoosePerson);
        AttendChooseClass=(Button) findViewById(R.id.AttendChooseClass);

        AttendChoosePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttenadanceViewMain.this,IndividualAttendance.class);
                startActivity(intent);
            }
        });

        AttendChooseClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttenadanceViewMain.this,WholeClassAttendance.class);
                startActivity(intent);
            }
        });
    }
}
