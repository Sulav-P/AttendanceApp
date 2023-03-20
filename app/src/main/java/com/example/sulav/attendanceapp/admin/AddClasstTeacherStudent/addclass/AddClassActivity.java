package com.example.sulav.attendanceapp.admin.AddClasstTeacherStudent.addclass;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.TeacherCheck;
import com.example.sulav.attendanceapp.admin.AdminActivity;
import com.example.sulav.attendanceapp.admin.AdminMain;
import com.example.sulav.attendanceapp.listviewhelpers.DeleteCheck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddClassActivity extends AppCompatActivity {

    private EditText ClassName;
    private EditText TeacherPassword;
    String Temail;

    AutoCompleteTextView spinnername;
    ArrayList<String> listname;
    String teachname;

    AutoCompleteTextView TeacherEmail;
    ArrayList<String> listemail;
    String teachemail;

    //Spinner spinnername;


    private AutoCompleteTextView Semester;
    String[] myStringArraySem;
    String semest;

    private AutoCompleteTextView CourseName;
    String[] myStringArrayCourse;
    String courseN;

    TeacherCheck user;
    private DatabaseReference TeachRef;


    private ProgressBar progressBar;

    private Button   ClassRegister;



    private DatabaseReference classData;
    private DatabaseReference spinnerclassData;
    private DatabaseReference courseData;
    private DatabaseReference Add_Class_CourseReference;
    private FirebaseAuth tAuth;
    private FirebaseAuth.AuthStateListener tfirebaseAuthListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        listname= new ArrayList<>();
        //listname.add("Select Teacher Name");
        listemail= new ArrayList<>();
        user= new TeacherCheck();
       // spinnername = (Spinner) findViewById(R.id.Spinnername);

        spinnername = (AutoCompleteTextView) findViewById(R.id.Spinnername);
        Semester = (AutoCompleteTextView) findViewById(R.id.etSemester);
        ClassName = (EditText)findViewById(R.id.etClassname);
        CourseName = (AutoCompleteTextView) findViewById(R.id.etCourseName);
       // TeacherName = (EditText)findViewById(R.id.TeacherName);
        TeacherEmail = (AutoCompleteTextView) findViewById(R.id.TeacherEmail);
        TeacherPassword = (EditText)findViewById(R.id.TeacherPassword);
        ClassRegister = (Button)findViewById(R.id.btnClassRegister);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);


        classData = FirebaseDatabase.getInstance().getReference("AddClass");
        TeachRef = FirebaseDatabase.getInstance().getReference("Users").child("Teacher");
        spinnerclassData = FirebaseDatabase.getInstance().getReference("SpinnerDeleteClass");
        courseData= FirebaseDatabase.getInstance().getReference("SpinnerCourse");
        Add_Class_CourseReference = FirebaseDatabase.getInstance().getReference("Class_Course_Record");
        tAuth = FirebaseAuth.getInstance();

        spinnerCheck();


        teachname= " ";
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listname);
        spinnername.setAdapter(arrayAdapter3);
        spinnername.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                teachname = (String) parent.getItemAtPosition(position);
                // here is your selected item
            }
        });


        teachemail = " ";
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listemail);
        TeacherEmail.setAdapter(arrayAdapter4);
        TeacherEmail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                teachemail = (String) parent.getItemAtPosition(position);
                // here is your selected item
            }
        });


        semest = " ";
        myStringArraySem = getResources().getStringArray(R.array.SemesterList);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,myStringArraySem);
        Semester.setAdapter(arrayAdapter1);
        Semester.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                semest = Semester.getText().toString();

            }
        });


        courseN = " ";
        myStringArrayCourse = getResources().getStringArray(R.array.Course);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,myStringArrayCourse);
        CourseName.setAdapter(arrayAdapter2);
        CourseName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                courseN = CourseName.getText().toString();
                // teachname = (String) parent.getItemAtPosition(position);
                // here is your selected item
            }
        });
        //courseN = CourseName.getText().toString();



        ClassRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClassInformation();
            }
        });


    }

    private void saveClassInformation() {
        progressBar.setVisibility(View.VISIBLE);
        final String TeacherNameD, SemesterD,ClassNameD, CourseNameD,TeacherEmailD , TeacherPasswordD;

        SemesterD = semest.trim();
        ClassNameD = ClassName.getText().toString().trim();
        CourseNameD = courseN.trim();
       // TeacherNameD = TeacherName.getText().toString().trim();
        TeacherEmailD = teachemail.trim();
        TeacherPasswordD = TeacherPassword.getText().toString().trim();
        Temail= teachname;


       if(TextUtils.isEmpty(CourseNameD) || TextUtils.isEmpty(SemesterD) || TextUtils.isEmpty(teachname) || TextUtils.isEmpty(ClassNameD) || TextUtils.isEmpty(TeacherEmailD) || TextUtils.isEmpty(TeacherPasswordD)){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AddClassActivity.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();
        }

        else{
            tAuth.signInWithEmailAndPassword(TeacherEmailD,TeacherPasswordD).addOnCompleteListener(AddClassActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AddClassActivity.this, "Invalid Teacher Email or Password",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        TeachRef.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(snapshot.getKey())){

                                        String name = (String) snapshot.child("Teacher_Name").getValue();
                                          if(teachname.equals(name)){
                                            String id2 = classData.push().getKey();
                                            String id= ClassNameD;
                                            String temail = Temail;
                                            String key = Add_Class_CourseReference.push().getKey();


                                            courseData.child(temail).child(id2).child("COURSE_NAME").setValue(CourseNameD.toString());
                                            courseData.child(temail).child(id2).child("CLASS_NAME").setValue(ClassNameD.toString());

                                            classData.child(id).child(id2).child("Semester_Name").setValue(SemesterD.toString());
                                            classData.child(id).child(id2).child("Class_Name").setValue(ClassNameD.toString());
                                            classData.child(id).child(id2).child("Course_Name").setValue(CourseNameD.toString());
                                            classData.child(id).child(id2).child("Teacher_Name").setValue(teachname.toString());
                                            classData.child(id).child(id2).child("Teacher_Email").setValue(TeacherEmailD.toString());
                                            classData.child(id).child(id2).child("Key").setValue(id2);

                                            spinnerclassData.child(id2).child("Class_Name").setValue(ClassNameD.toString());
                                            spinnerclassData.child(id2).child("Course_Name").setValue(CourseNameD.toString());
                                            spinnerclassData.child(id2).child("Teacher_Name").setValue(teachname.toString());

                                            Add_Class_CourseReference.child(key).child("CLASS_NAME").setValue(ClassNameD.toString());
                                            Add_Class_CourseReference.child(key).child("COURSE_NAME").setValue(CourseNameD.toString());


                                            progressBar.setVisibility(View.GONE);
                                            toast();
                                            finish();
                                       }
                                       else{
                                              progressBar.setVisibility(View.GONE);
                                            toast2();

                                        }
                                    }
                               }

                           }

                            @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                        });


                    }
                }
            });


        }


    }

    private void spinnerCheck() {

        TeachRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    user = ds.getValue(TeacherCheck.class);
                    assert user != null;

                    if (!listname.contains(user.getTeacher_Name())) {
                        listname.add(user.getTeacher_Name());
                    }
                    if (!listemail.contains(user.getTeacher_Email_ID())) {
                        listemail.add(user.getTeacher_Email_ID());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void toast() {
        Toast.makeText(this,"Successfully Registered",Toast.LENGTH_LONG).show();
    }
    private void toast2() {
        Toast.makeText(this,"Wrong TeacherName",Toast.LENGTH_LONG).show();
    }
}

