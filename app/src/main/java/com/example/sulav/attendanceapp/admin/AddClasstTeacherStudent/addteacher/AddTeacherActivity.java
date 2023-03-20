package com.example.sulav.attendanceapp.admin.AddClasstTeacherStudent.addteacher;

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
import android.widget.Toast;

import com.example.sulav.attendanceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTeacherActivity extends AppCompatActivity {

    private EditText TeacherName;
    private EditText TeacherPhone;
    private EditText TeacherEmail , tPassword;
   // private EditText TeacherQualification;
    private EditText TeacherAddress;

    private AutoCompleteTextView TeacherQualification;
    private static final String[] myStringArray = new String[]{"MASTERS","BACHELORS","PH.D"};
    String qualification;

    private ProgressBar progressBar;

    private Button   TeacherRegister;

    private DatabaseReference teacherData;
    private DatabaseReference loginteacherData;
    private FirebaseAuth tAuth;
    private FirebaseAuth.AuthStateListener tfirebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        tAuth = FirebaseAuth.getInstance();




        TeacherName = (EditText)findViewById(R.id.TeacherName);
        TeacherPhone = (EditText)findViewById(R.id.TeacherPhone);
        TeacherEmail = (EditText)findViewById(R.id.TeacherEmail);
        tPassword = (EditText)findViewById(R.id.tpassword);
        TeacherQualification = (AutoCompleteTextView) findViewById(R.id.TeacherQualification);
        TeacherAddress = (EditText)findViewById(R.id.TeacherAddress);
        TeacherRegister = (Button)findViewById(R.id.btnTeacherRegister);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        teacherData = FirebaseDatabase.getInstance().getReference("AddTeacher");

        //used to map the email
        loginteacherData = FirebaseDatabase.getInstance().getReference().child("Users").child("Teacher");
        qualification = " ";

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,myStringArray);
        TeacherQualification.setAdapter(arrayAdapter3);
        TeacherQualification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                qualification = TeacherQualification.getText().toString();
            }
        });


        TeacherRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTeacherInformation();
            }
        });
    }

    private void saveTeacherInformation() {
        progressBar.setVisibility(View.VISIBLE);
       final String TeacherNameD, TeacherPhoneD,TeacherAddressD,TeacherQualificationD;
        final String TeacherEmailD , TPasswordD;


        TeacherNameD = TeacherName.getText().toString().trim();
        TeacherPhoneD = TeacherPhone.getText().toString().trim();
        TeacherEmailD = TeacherEmail.getText().toString().trim();
        TPasswordD = tPassword.getText().toString().trim();
       // TeacherQualificationD = TeacherQualification.getText().toString().trim();
        TeacherAddressD = TeacherAddress.getText().toString().trim();

        TeacherQualificationD = qualification.trim();


        if(TextUtils.isEmpty(TeacherNameD) || TextUtils.isEmpty(TeacherPhoneD) || TextUtils.isEmpty(TeacherEmailD) || TextUtils.isEmpty(TeacherQualificationD) || TextUtils.isEmpty(TeacherAddressD) || TextUtils.isEmpty(TPasswordD)){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AddTeacherActivity.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();
        }
        else if(TeacherPhoneD.length()!=10){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AddTeacherActivity.this, "Please Enter 10 digit Phone No.", Toast.LENGTH_SHORT).show();

        }
        else {


            tAuth.createUserWithEmailAndPassword(TeacherEmailD, TPasswordD).addOnCompleteListener(AddTeacherActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AddTeacherActivity.this, "Invalid Teacher E-mail or Password", Toast.LENGTH_SHORT).show();
                    } else {
                       String user_id = tAuth.getCurrentUser().getUid();
                       // DatabaseReference current_user_db = loginteacherData.child(user_id);
                       // current_user_db.setValue(true);

                        String id = user_id;

                        teacherData.child(id).child("Teacher_Name").setValue(TeacherNameD.toString());
                        teacherData.child(id).child("Phone_Number").setValue(TeacherPhoneD.toString());
                        teacherData.child(id).child("Email_ID").setValue(TeacherEmailD.toString());
                        teacherData.child(id).child(" Highest_Qualification").setValue(TeacherQualificationD.toString());
                        teacherData.child(id).child("Address").setValue(TeacherAddressD.toString());

                        loginteacherData.child(id).child("Teacher_Email_ID").setValue(TeacherEmailD.toString());
                        loginteacherData.child(id).child("Teacher_Name").setValue(TeacherNameD.toString());

                        toast();
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }
                }
            });



        }


    }

    private void toast() {
        Toast.makeText(this,"Successfully Registered",Toast.LENGTH_LONG).show();
    }




}
