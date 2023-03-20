package com.example.sulav.attendanceapp.admin.DeleteClassTeacherStudent;

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

import com.example.sulav.attendanceapp.Classrecord;
import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.listviewhelpers.DeleteCheck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteStudent extends AppCompatActivity {

    DatabaseReference Student_reference;
    DatabaseReference attend_reference;
    DatabaseReference AddStudent_reference;
    DatabaseReference ClassRecord;

    private Button Deletebutton;

    Classrecord user;
    String classname;

    private EditText Semail,Spassword,Cname;
    Spinner spinnerclass;
    ArrayList<String> listclass;



    private ProgressBar Progressbar;

    private FirebaseAuth tAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        listclass= new ArrayList<>();
        listclass.add("Select Class");
        user= new Classrecord();
        spinnerclass = (Spinner) findViewById(R.id.spinnerClass);

        tAuth = FirebaseAuth.getInstance();

        Student_reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Student");
        AddStudent_reference = FirebaseDatabase.getInstance().getReference("AddStudent");
        attend_reference = FirebaseDatabase.getInstance().getReference("ATTENDREFERENCE");
        ClassRecord = FirebaseDatabase.getInstance().getReference("ClassRecord");



        Semail = (EditText) findViewById(R.id.semail);
       // Cname = (EditText) findViewById(R.id.Classname);
        Spassword = (EditText) findViewById(R.id.spassword);

        Deletebutton = (Button) findViewById(R.id.sdelete);

        Progressbar = findViewById(R.id.progressbar);
        Progressbar.setVisibility(View.GONE);

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


        Deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent();
            }
        });

    }

    private void deleteStudent(){
        Progressbar.setVisibility(View.VISIBLE);
        final String TeacherEmailD , TeacherPasswordD , CnameD;

        TeacherEmailD = Semail.getText().toString().trim();
       // CnameD = Cname.getText().toString().trim();
        TeacherPasswordD = Spassword.getText().toString().trim();

        if (classname.equals("Select Class")) {
            Progressbar.setVisibility(View.GONE);
            Toast.makeText(DeleteStudent.this, "SELECT CLASS NAME", Toast.LENGTH_SHORT).show();

        }


       else if(TextUtils.isEmpty(TeacherEmailD) || TextUtils.isEmpty(TeacherPasswordD)){

            Progressbar.setVisibility(View.GONE);
            Toast.makeText(DeleteStudent.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();

        }
        else{
            tAuth.signInWithEmailAndPassword(TeacherEmailD,TeacherPasswordD).addOnCompleteListener(DeleteStudent.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Progressbar.setVisibility(View.GONE);
                        Toast.makeText(DeleteStudent.this, "Invalid Student Email or Password",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String user_id = tAuth.getCurrentUser().getUid();

                        AddStudent_reference.child(user_id).removeValue();
                        attend_reference.child(classname).child(user_id).removeValue();
                        Student_reference.child(user_id).removeValue();
                        ClassRecord.child(user_id).removeValue();
                        Progressbar.setVisibility(View.GONE);
                        toast();
                        finish();
                    }
                }
            });


        }

    }

    private void spinnerCheck() {

        ClassRecord.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    user = ds.getValue(Classrecord.class);
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

    private void toast() {
        Toast.makeText(this," Deleted Successfully",Toast.LENGTH_LONG).show();
    }
}
