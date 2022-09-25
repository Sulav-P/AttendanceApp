package com.example.sulav.attendanceapp.teacher;

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
import com.example.sulav.attendanceapp.TeacherCheck;
import com.example.sulav.attendanceapp.admin.AdminActivity;
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

public class TeacherActivity extends AppCompatActivity {
    private EditText tEmail,tPassword;
    private Button tLogin;

    private ProgressBar progressBar;

    private FirebaseAuth tAuth;
    private FirebaseAuth tlogoutAuth;
    private DatabaseReference ref;
    private FirebaseAuth.AuthStateListener tfirebaseAuthListener;

   /* AutoCompleteTextView TeacherEmail;
    ArrayList<String> listEmail;
    ArrayList<String> listName;
    String Teachemail;
    TeacherCheck user;
    private DatabaseReference TeachRef;  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

       /* listEmail= new ArrayList<>();
        listName= new ArrayList<>();
        user= new TeacherCheck();
        TeacherEmail = (AutoCompleteTextView) findViewById(R.id.TeachEmail);

        TeachRef = FirebaseDatabase.getInstance().getReference("Users").child("Teacher");
        spinnerCheck();
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listEmail);
        TeacherEmail.setAdapter(arrayAdapter4);
        TeacherEmail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Teachemail = (String) parent.getItemAtPosition(position);
                // here is your selected item
            }
        });  */

        tlogoutAuth = FirebaseAuth.getInstance();
       // tlogoutAuth.signOut();
        tAuth = FirebaseAuth.getInstance();

        tfirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null){

                    ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Teacher");

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(snapshot.getKey())){
                                    String name = (String) snapshot.child("Teacher_Name").getValue();
                                    //String id =  tEmail.getText().toString().trim();
                                    String key=snapshot.getKey();
                                    Intent intent =  new Intent(TeacherActivity.this,TeacherMain.class);
                                    intent.putExtra("KeyT", name);
                                    startActivity(intent);
                                    Toast.makeText(TeacherActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                    finish();
                                    // return;
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(TeacherActivity.this, "YOU ARE NOT TEACHER", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    // User is signed out
                }
                // ...
            }
        };


        tEmail = (EditText) findViewById(R.id.temail);
        tPassword = (EditText) findViewById(R.id.tpassword);

        tLogin = (Button) findViewById(R.id.tlogin);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);




        tLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
               // final String email= tEmail.getText().toString().trim();
                final String email= tEmail.getText().toString().trim();
                final String password=tPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(TeacherActivity.this, "Please Enter All required Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    tAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(TeacherActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(TeacherActivity.this, "Sign In Error", Toast.LENGTH_SHORT).show();
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
        tAuth.addAuthStateListener(tfirebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        tAuth.removeAuthStateListener(tfirebaseAuthListener);
    }

  /*  private void spinnerCheck() {

        TeachRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    user = ds.getValue(TeacherCheck.class);
                    assert user != null;

                    if (!listName.contains(user.getTeacher_Name())) {
                        listName.add(user.getTeacher_Name());
                    }

                    if (!listEmail.contains(user.getTeacher_Email_ID())) {
                        listEmail.add(user.getTeacher_Email_ID());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }  */

}
