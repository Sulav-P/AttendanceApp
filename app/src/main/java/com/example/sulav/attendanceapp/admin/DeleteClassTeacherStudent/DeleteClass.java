package com.example.sulav.attendanceapp.admin.DeleteClassTeacherStudent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sulav.attendanceapp.admin.AddClasstTeacherStudent.addstudent.AddStudentActivity;
import com.example.sulav.attendanceapp.listviewhelpers.DeleteCheck;
import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.listviewhelpers.Email_list;
import com.example.sulav.attendanceapp.listviewhelpers.Sub_list;
import com.example.sulav.attendanceapp.teacher.STUDENT_ATTENDANCE_SHEET;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteClass extends AppCompatActivity {

    DatabaseReference SpinnerReference;
    DatabaseReference AddClass_reference;
    DatabaseReference SpinnerReferenceDelete;

    private Button ClassDelete;

    String key;

    //Spinner spinnername;
    AutoCompleteTextView spinnername;
    //Spinner spinnercourse;
    AutoCompleteTextView spinnercourse;
    Spinner spinnerclass;
    ArrayList<String> listclass;
    ArrayList<String> listcourse;
    ArrayList<String> listname;
    String classname;
    String coursename;
    String teachname;

    DeleteCheck user;


    private android.widget.ProgressBar ProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_class);

        AddClass_reference = FirebaseDatabase.getInstance().getReference("AddClass");
        SpinnerReference = FirebaseDatabase.getInstance().getReference("SpinnerCourse");
        SpinnerReferenceDelete = FirebaseDatabase.getInstance().getReference("SpinnerDeleteClass");


      /*  CourseName = (EditText) findViewById(R.id.coursename);
        ClassName = (EditText) findViewById(R.id.classname);
        TeacherName = (EditText) findViewById(R.id.teachername);  */


        listclass= new ArrayList<>();
        listclass.add("Select Class");
        listcourse= new ArrayList<>();
       // listcourse.add("Select Course");
        listname= new ArrayList<>();
       // listname.add("Select Teacher Name");
        user= new DeleteCheck();
        spinnername = (AutoCompleteTextView) findViewById(R.id.spinnername);
        spinnerclass = (Spinner) findViewById(R.id.spinnerclass);
        spinnercourse = (AutoCompleteTextView) findViewById(R.id.spinnercourse);



        ClassDelete = (Button) findViewById(R.id.cdelete);

        ProgressBar = findViewById(R.id.progressbar);
        ProgressBar.setVisibility(View.GONE);


        spinnerCheck();

        classname= "Select Class";
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listclass);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerclass.setAdapter(arrayAdapter1);

        spinnerclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerclass.setSelection(position);
                classname = spinnerclass.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        coursename = " ";
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listcourse);
        spinnercourse.setAdapter(arrayAdapter2);
        spinnercourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                coursename = (String) parent.getItemAtPosition(position);
                // here is your selected item
            }
        });

        teachname = " ";
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listname);
        spinnername.setAdapter(arrayAdapter3);
        spinnername.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                teachname = (String) parent.getItemAtPosition(position);
                // here is your selected item
            }
        });






        ClassDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClass();
            }
        });
    }




    private void deleteClass(){

        ProgressBar.setVisibility(View.VISIBLE);

       /* ClassN = ClassName.getText().toString().trim();
        CourseN = CourseName.getText().toString().trim();
        TeacherN = TeacherName.getText().toString().trim();  */
       String CourseN = coursename.trim();
       String TeacherN = teachname.trim();

        if(TextUtils.isEmpty(classname)|| TextUtils.isEmpty(CourseN) ||   TextUtils.isEmpty(TeacherN)) {
            ProgressBar.setVisibility(View.GONE);
            Toast.makeText(DeleteClass.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();
        }
        else if (classname.equals("Select Class")){
            ProgressBar.setVisibility(View.GONE);
            Toast.makeText(DeleteClass.this, "Please Enter Correct  Class ", Toast.LENGTH_SHORT).show();

        }

        else{

            final DatabaseReference ref1 = AddClass_reference.child(classname);
            final DatabaseReference ref2 = SpinnerReference.child(teachname);

            final Query reference=ref1.orderByChild("Course_Name").equalTo(coursename);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                        key=childSnapshot.getKey();
                        ref1.child(key).removeValue();
                        ref2.child(key).removeValue();
                        SpinnerReferenceDelete.child(key).removeValue();

                    }
                    toast();
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });


        }


    }

    private void spinnerCheck() {

        SpinnerReferenceDelete.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    user = ds.getValue(DeleteCheck.class);
                    assert user != null;

                    if (!listcourse.contains(user.getCourse_Name())) {
                        listcourse.add(user.getCourse_Name());
                    }

                    if (!listclass.contains(user.getClass_Name())) {
                        listclass.add(user.getClass_Name());
                    }

                    if (!listname.contains(user.getTeacher_Name())) {
                        listname.add(user.getTeacher_Name());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void toast() {
        Toast.makeText(this," Deleted Successfully",Toast.LENGTH_LONG).show();
    }
    private void toast2() {
        Toast.makeText(this," Wrong Input",Toast.LENGTH_LONG).show();
    }
}
