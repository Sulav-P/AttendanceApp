package com.example.sulav.attendanceapp.admin.AddClasstTeacherStudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.admin.AddClasstTeacherStudent.addclass.AddClassActivity;
import com.example.sulav.attendanceapp.admin.AddClasstTeacherStudent.addstudent.AddStudentActivity;
import com.example.sulav.attendanceapp.admin.AddClasstTeacherStudent.addteacher.AddTeacherActivity;
import com.example.sulav.attendanceapp.admin.AdminActivity;
import com.example.sulav.attendanceapp.admin.AdminMain;


public class AddClassTeacherStudentMain extends AppCompatActivity {

    private Button addTeacher;
    private Button addClass;
    private Button addStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_teacher_student_main);

        addTeacher = (Button) findViewById(R.id.addteacher);
        addClass = (Button)findViewById(R.id.addclass);
        addStudent = (Button)findViewById(R.id.addstudent);

        addTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(AddClassTeacherStudentMain.this,AddTeacherActivity.class);
                startActivity(intent);
            }
        });

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddClassTeacherStudentMain.this,AddClassActivity.class);
                startActivity(intent);
            }
        });


        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddClassTeacherStudentMain.this,AddStudentActivity.class);
                startActivity(intent);
            }
        });

    }
}
