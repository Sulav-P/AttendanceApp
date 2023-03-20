package com.example.sulav.attendanceapp.parent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.admin.AdminActivity;
import com.example.sulav.attendanceapp.student.StudentActivity;
import com.example.sulav.attendanceapp.student.StudentMain;
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

public class ParentActivity extends AppCompatActivity {
    private EditText pEmail,pPassword;
    private Button pLogin;

    public String p;

    private ProgressBar progressBar;

    private FirebaseAuth pAuth;
    private FirebaseAuth plogoutAuth;
    private FirebaseAuth.AuthStateListener pfirebaseAuthListener;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        plogoutAuth = FirebaseAuth.getInstance();
       // plogoutAuth.signOut();
        pAuth = FirebaseAuth.getInstance();


        pfirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null){
                    ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Parent");


                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(snapshot.getKey())){

                                   p= FirebaseAuth.getInstance().getCurrentUser().getUid();


                                    Intent intent =  new Intent(ParentActivity.this,ParentMain.class);
                                    startActivity(intent);

                                    Toast.makeText(ParentActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();

                                    finish();

                                }
                            }
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ParentActivity.this, "YOU ARE NOT PARENT", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        };

        pEmail = (EditText) findViewById(R.id.pemail);
        pPassword = (EditText) findViewById(R.id.ppassword);

        pLogin = (Button) findViewById(R.id.plogin);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        pLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                final String email=pEmail.getText().toString().trim();
                final String password=pPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ParentActivity.this, "Please Enter All required Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                pAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(ParentActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ParentActivity.this, "Sign In Error",Toast.LENGTH_SHORT).show();
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
        pAuth.addAuthStateListener(pfirebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        pAuth.removeAuthStateListener(pfirebaseAuthListener);
    }


}
