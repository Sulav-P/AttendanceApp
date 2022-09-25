package com.example.sulav.attendanceapp.admin.AddClasstTeacherStudent.addstudent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.admin.AddClasstTeacherStudent.addteacher.AddTeacherActivity;
import com.example.sulav.attendanceapp.admin.AdminActivity;
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

public class AddStudentActivity extends AppCompatActivity {

    private EditText StudentName;
    private EditText RollNo;
    private EditText StudentClass;
    private EditText StudentPhone;
    private EditText StudentEmail;
    private EditText ParentName;
    private EditText ParentEmail;
    private EditText ParentPassword;
    private EditText StudentPassword;


    private ProgressBar progressBar;

    private Button   StudentRegister;

    Spinner spinnerclass;
    ArrayList<String> listclass;
    String classname;
    DeleteCheck user;
    DatabaseReference SpinnerReferenceDelete;


    private DatabaseReference studentData;
    private DatabaseReference studattend;
    private DatabaseReference parentData;
    private DatabaseReference classRecord;
    private DatabaseReference AddRoll_reference;


    private DatabaseReference loginstudent;
    private DatabaseReference loginparent;


    private FirebaseAuth pAuth;
    private FirebaseAuth sAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        SpinnerReferenceDelete = FirebaseDatabase.getInstance().getReference("SpinnerDeleteClass");

        listclass= new ArrayList<>();
        listclass.add("Select Class");
        user= new DeleteCheck();
        spinnerclass = (Spinner) findViewById(R.id.SpinnerClass);

        pAuth = FirebaseAuth.getInstance();
        sAuth = FirebaseAuth.getInstance();

        StudentName = (EditText)findViewById(R.id.StudentName);
        RollNo = (EditText)findViewById(R.id.RollNo);
       // StudentClass = (EditText)findViewById(R.id.StudentClass);
        StudentPhone = (EditText)findViewById(R.id.StudentPhone);
        StudentEmail = (EditText)findViewById(R.id.StudentEmail);
        ParentName = (EditText)findViewById(R.id.ParentName);
        ParentEmail = (EditText)findViewById(R.id.ParentEmail);
        ParentPassword = (EditText)findViewById(R.id.ParentPassword);
        StudentPassword = (EditText)findViewById(R.id.StudentPassword);


        StudentRegister = (Button)findViewById(R.id.btnStudentRegister);


        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        studentData = FirebaseDatabase.getInstance().getReference("AddStudent");
        studattend = FirebaseDatabase.getInstance().getReference("ATTENDREFERENCE");
        parentData = FirebaseDatabase.getInstance().getReference("AddParent");
        AddRoll_reference = FirebaseDatabase.getInstance().getReference("RollRecord");

        classRecord = FirebaseDatabase.getInstance().getReference("ClassRecord");

        loginstudent= FirebaseDatabase.getInstance().getReference().child("Users").child("Student");
        loginparent= FirebaseDatabase.getInstance().getReference().child("Users").child("Parent");

        spinnerCheck();
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




        StudentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudentInformation();
            }
        });


    }
    private void saveStudentInformation() {

        progressBar.setVisibility(View.VISIBLE);
        final String StudentNameD, StudentRollNoD , StudentClassD,StudentPhoneD, StudentEmailD,ParentNameD,ParentEmailD,ParentPasswordD,StudentPasswordD;

        StudentNameD = StudentName.getText().toString().trim();
        StudentRollNoD = RollNo.getText().toString().trim();
       // StudentClassD = StudentClass.getText().toString().trim();
        StudentPhoneD = StudentPhone.getText().toString().trim();
        StudentPasswordD = StudentPassword.getText().toString().trim();
        StudentEmailD = StudentEmail.getText().toString().trim();
        ParentNameD = ParentName.getText().toString().trim();
        ParentEmailD = ParentEmail.getText().toString().trim();
        ParentPasswordD = ParentPassword.getText().toString().trim();



        if(TextUtils.isEmpty(StudentNameD) || TextUtils.isEmpty(StudentRollNoD) ||TextUtils.isEmpty(classname) || TextUtils.isEmpty(StudentPhoneD) || TextUtils.isEmpty(StudentEmailD) || TextUtils.isEmpty(ParentEmailD) || TextUtils.isEmpty(ParentNameD) || TextUtils.isEmpty(StudentPasswordD)|| TextUtils.isEmpty(ParentPasswordD)) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AddStudentActivity.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();
        }
        else if (classname.equals("Select Class")){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AddStudentActivity.this, "Please Enter Correct  Class ", Toast.LENGTH_SHORT).show();

        }
        else if(StudentPhoneD.length()!= 10){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AddStudentActivity.this, "Please Enter 10 digit Phone No.", Toast.LENGTH_SHORT).show();

        }


        // Both sAuth and pAuth needs to run then if these statemets are successful then mapping code needs to run
        //problem is cannot run boolean check if ()function..
        else{

            sAuth.createUserWithEmailAndPassword(StudentEmailD,StudentPasswordD).addOnCompleteListener(AddStudentActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);

                      Toast.makeText(AddStudentActivity.this,"Sign Up error in Student",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // the problem is once pAuth is false we already have value for suser_id..So Error 1 occurs
                        //Possible Solution : delete the suser_id value.

                       final String suser_id = sAuth.getCurrentUser().getUid();
                       // DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Student").child(suser_id);
                       // current_user_db.setValue(true);


                        boolean stu= true;

                        if (stu) {
                            pAuth.createUserWithEmailAndPassword(ParentEmailD,ParentPasswordD).addOnCompleteListener(AddStudentActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        progressBar.setVisibility(View.GONE);

                                        Toast.makeText(AddStudentActivity.this,"Sign Up in Parent",Toast.LENGTH_SHORT).show();
                                    }
                                    else{

                                        String puser_id = pAuth.getCurrentUser().getUid();
                                      //  DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Parent").child(puser_id);
                                      //  current_user_db.setValue(true);

                                        String sid = suser_id;
                                        String pid = puser_id;

                                        String class1= classname;

                                        studentData.child(sid).child("Student_Name").setValue(StudentNameD.toString());
                                        studentData.child(sid).child("Student_RollNo").setValue(StudentRollNoD.toString());
                                        studentData.child(sid).child("Class_Name").setValue(classname.toString());
                                        studentData.child(sid).child("Phone_Number").setValue(StudentPhoneD.toString());
                                        studentData.child(sid).child(" Student_Email_ID").setValue(StudentEmailD.toString());
                                        studentData.child(sid).child("Parent_Name").setValue(ParentNameD.toString());
                                        studentData.child(sid).child(" Parent_Email_ID").setValue(ParentEmailD.toString());

                                        classRecord.child(sid).child("Class_Name").setValue(classname.toString());

                                        AddRoll_reference.push().child("Student_RollNo").setValue(StudentRollNoD.toString());

                                        studattend.child(class1).child(sid).child("Student_Name").setValue(StudentNameD.toString());
                                        studattend.child(class1).child(sid).child("Student_RollNo").setValue(StudentRollNoD.toString());


                                        loginstudent.child(sid).child(" Student_Email_ID").setValue(StudentEmailD.toString());
                                        loginstudent.child(sid).child(" Student_Name").setValue(StudentNameD.toString());


                                        loginparent.child(pid).child(" Parent_Email_ID").setValue(ParentEmailD.toString());
                                        loginparent.child(pid).child(" Parent_Name").setValue(ParentNameD.toString());


                                        parentData.child(pid).child("Parent_Name").setValue(ParentNameD.toString());
                                        parentData.child(pid).child(" Parent_Email_ID").setValue(ParentEmailD.toString());
                                        parentData.child(pid).child("Student_Name").setValue(StudentNameD.toString());
                                        parentData.child(pid).child(" Student_Email_ID").setValue(StudentEmailD.toString());



                                        progressBar.setVisibility(View.GONE);

                                        toast();

                                        finish();




                                    }
                                }
                            });


                        }



                    }

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


                    if (!listclass.contains(user.getClass_Name())) {
                        listclass.add(user.getClass_Name());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

        private void toast(){
            Toast.makeText(this,"Successfully Registered",Toast.LENGTH_LONG).show();

        }
}
