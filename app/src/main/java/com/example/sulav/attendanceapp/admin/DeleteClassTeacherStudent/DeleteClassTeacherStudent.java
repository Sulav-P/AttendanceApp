package com.example.sulav.attendanceapp.admin.DeleteClassTeacherStudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.admin.AddClasstTeacherStudent.AddClassTeacherStudentMain;
import com.example.sulav.attendanceapp.admin.AdminMain;

public class DeleteClassTeacherStudent extends AppCompatActivity {

    private Button DeleteTeacher;
    private Button DeleteParent;
    private Button DeleteStudent;
    private Button DeleteClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_class_teacher_student);

        DeleteTeacher = (Button) findViewById(R.id.deleteteacher);
        DeleteParent = (Button) findViewById(R.id.deleteparent);
        DeleteStudent = (Button) findViewById(R.id.deletestudent);
        DeleteClass = (Button) findViewById(R.id.deleteclass);

        DeleteTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteClassTeacherStudent.this, DeleteTeacher.class);
                startActivity(intent);
            }
        });

        DeleteParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteClassTeacherStudent.this, DeleteParent.class);
                startActivity(intent);
            }
        });

        DeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteClassTeacherStudent.this, DeleteStudent.class);
                startActivity(intent);
            }
        });

        DeleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteClassTeacherStudent.this, DeleteClass.class);
                startActivity(intent);
            }
        });


    }
}
