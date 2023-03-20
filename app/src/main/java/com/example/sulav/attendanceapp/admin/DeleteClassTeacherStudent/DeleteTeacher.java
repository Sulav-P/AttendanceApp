package com.example.sulav.attendanceapp.admin.DeleteClassTeacherStudent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.admin.AddClasstTeacherStudent.addclass.AddClassActivity;
import com.example.sulav.attendanceapp.teacher.TeacherActivity;
import com.example.sulav.attendanceapp.teacher.TeacherMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteTeacher extends AppCompatActivity {

    DatabaseReference Teacher_reference;
    DatabaseReference spinner_reference;
    DatabaseReference AddTeacher_reference;

    private Button Deletebutton;

    private EditText tEmail,tPassword,tName;


    private ProgressBar progressBar;

    private FirebaseAuth tAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_teacher);

        tAuth = FirebaseAuth.getInstance();

        Teacher_reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Teacher");
        AddTeacher_reference = FirebaseDatabase.getInstance().getReference("AddTeacher");
        spinner_reference = FirebaseDatabase.getInstance().getReference("SpinnerCourse");



        tEmail = (EditText) findViewById(R.id.temail);
        tPassword = (EditText) findViewById(R.id.tpassword);
       // tName = (EditText) findViewById(R.id.tName);

        Deletebutton = (Button) findViewById(R.id.tdelete);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        Deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTeacher();
            }
        });


    }

    private void deleteTeacher(){
        progressBar.setVisibility(View.VISIBLE);
        final String TeacherEmailD , TeacherPasswordD , TNameD;

        //TNameD = tName.getText().toString().trim();
        TeacherEmailD = tEmail.getText().toString().trim();
        TeacherPasswordD = tPassword.getText().toString().trim();

        if(TextUtils.isEmpty(TeacherEmailD) || TextUtils.isEmpty(TeacherPasswordD)){

            progressBar.setVisibility(View.GONE);
            Toast.makeText(DeleteTeacher.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();

        }
        else{
            tAuth.signInWithEmailAndPassword(TeacherEmailD,TeacherPasswordD).addOnCompleteListener(DeleteTeacher.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(DeleteTeacher.this, "Invalid Teacher Email or Password",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Teacher_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(snapshot.getKey())){

                                        String name = (String) snapshot.child("Teacher_Name").getValue();
                                        String user_id = tAuth.getCurrentUser().getUid();
                                        AddTeacher_reference.child(user_id).removeValue();
                                        Teacher_reference.child(user_id).removeValue();
                                        spinner_reference.child(name).removeValue();
                                        progressBar.setVisibility(View.GONE);
                                        toast();
                                        finish();
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                      /*  String user_id = tAuth.getCurrentUser().getUid();
                        AddTeacher_reference.child(user_id).removeValue();
                        Teacher_reference.child(user_id).removeValue();
                        spinner_reference.child(TNameD).removeValue();
                        progressBar.setVisibility(View.GONE);
                        toast();
                        finish();  */
                    }
                }
            });


        }

    }

    private void toast() {
        Toast.makeText(this," Deleted Successfully",Toast.LENGTH_LONG).show();
    }
}
