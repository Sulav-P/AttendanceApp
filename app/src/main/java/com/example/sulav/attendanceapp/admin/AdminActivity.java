package com.example.sulav.attendanceapp.admin;

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
//import com.example.sulav.attendanceapp.admin.Registration.RegisterTeacher;
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

public class AdminActivity extends AppCompatActivity {
    private EditText aEmail,aPassword;
    private Button aLogin,aRegistration;

    private ProgressBar progressBar;

    private FirebaseAuth aAuth;
    private FirebaseAuth logoutAuth;
    private FirebaseAuth.AuthStateListener afirebaseAuthListener;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2);

        logoutAuth = FirebaseAuth.getInstance();
       // logoutAuth.signOut();
        aAuth = FirebaseAuth.getInstance();

        afirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null){

                    ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Admin");

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(snapshot.getKey())){
                                    Intent intent =  new Intent(AdminActivity.this,AdminMain.class);
                                    startActivity(intent);
                                    Toast.makeText(AdminActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();

                                   finish();
                                    // return;
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(AdminActivity.this, "YOU ARE NOT ADMIN", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        };


        aEmail = (EditText) findViewById(R.id.aemail);
        aPassword = (EditText) findViewById(R.id.apassword);

        aLogin = (Button) findViewById(R.id.alogin);
       aRegistration = (Button) findViewById(R.id.aregistration);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);


       aRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final String email=aEmail.getText().toString().trim();
                final String password=aPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AdminActivity.this, "Please Enter All required Fields", Toast.LENGTH_SHORT).show();
                }
                else {

                aAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(AdminActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(AdminActivity.this,"Sign Up Error",Toast.LENGTH_SHORT).show();
                        }else{

                            String user_id = aAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Admin").child(user_id);
                            current_user_db.setValue(true);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
                }
            }
        });


        aLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final String email=aEmail.getText().toString().trim();
                final String password=aPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AdminActivity.this, "Please Enter All required Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                aAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(AdminActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(AdminActivity.this, "Sign In Error",Toast.LENGTH_SHORT).show();
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
        aAuth.addAuthStateListener(afirebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        aAuth.removeAuthStateListener(afirebaseAuthListener);
    }

}
