package com.example.sulav.attendanceapp.student;

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
//import com.example.sulav.attendanceapp.teacher.TeacherActivity;
//import com.example.sulav.attendanceapp.teacher.TeacherMain;
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

public class StudentActivity extends AppCompatActivity {

    private EditText sEmail,sPassword;
    private Button sLogin;

    private ProgressBar progressBar;

    private FirebaseAuth sAuth;
    private FirebaseAuth slogoutAuth;
    private FirebaseAuth.AuthStateListener sfirebaseAuthListener;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        slogoutAuth = FirebaseAuth.getInstance();
      //  slogoutAuth.signOut();
        sAuth = FirebaseAuth.getInstance();

        sfirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null){
                    ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Student");

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(snapshot.getKey())){

                                    String name = (String) snapshot.child("Student_Name").getValue();
                                    Intent intent =  new Intent(StudentActivity.this,StudentMain.class);
                                    intent.putExtra("KeyStudent", name);
                                    startActivity(intent);
                                    Toast.makeText(StudentActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();

                                    finish();
                                    // return;
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(StudentActivity.this, "YOU ARE NOT STUDENT", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        };
        sEmail = (EditText) findViewById(R.id.semail);
        sPassword = (EditText) findViewById(R.id.spassword);

        sLogin = (Button) findViewById(R.id.slogin);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);



        sLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                final String email=sEmail.getText().toString().trim();
                final String password=sPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(StudentActivity.this, "Please Enter All required Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    sAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(StudentActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(StudentActivity.this, "Sign In Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        sAuth.addAuthStateListener(sfirebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sAuth.removeAuthStateListener(sfirebaseAuthListener);
    }
}
