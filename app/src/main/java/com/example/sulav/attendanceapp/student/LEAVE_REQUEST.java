package com.example.sulav.attendanceapp.student;

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
import android.widget.Toast;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.TeacherCheck;
import com.example.sulav.attendanceapp.listviewhelpers.DeleteCheck;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LEAVE_REQUEST extends AppCompatActivity {

   // public static final String Lmessage = " com.example.sulav.attendanceapp.student.MESSAGE";


    Button LeaveReq_send;
    AutoCompleteTextView spinnername;
    ArrayList<String> listname;
    String teachname;
    String Sname;
    TeacherCheck user;
    DatabaseReference SpinnerReference;

    private DatabaseReference leaveData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave__request);

        Intent intent = getIntent();
        Sname = intent.getExtras().getString("SName");


        LeaveReq_send = (Button) findViewById(R.id.studentleaverequest_send);

        leaveData = FirebaseDatabase.getInstance().getReference("STUDENT LEAVE REQUEST");
        SpinnerReference = FirebaseDatabase.getInstance().getReference("Users").child("Teacher");
        listname= new ArrayList<>();
        user= new TeacherCheck();

        spinnername = (AutoCompleteTextView) findViewById(R.id.teachname);

        spinnerCheck();

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

        LeaveReq_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editText  = (EditText) findViewById(R.id.studentleaverequest_text);
                String Lmessage =editText .getText().toString().trim();
                String Lname = teachname.trim();

                if(TextUtils.isEmpty(Lmessage) || TextUtils.isEmpty(Lname) ) {
                    Toast.makeText(LEAVE_REQUEST.this, "  Please Enter ALL DETAILS", Toast.LENGTH_SHORT).show();
                }
                else{
                    String id = leaveData.push().getKey();
                    leaveData.child(Lname).child(id).child("MESSAGE").setValue(Lmessage);
                    Toast.makeText(getApplicationContext(),"LEAVE REQUEST SENT", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        });

    }

    private void spinnerCheck() {

        SpinnerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    user = ds.getValue(TeacherCheck.class);
                    assert user != null;

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

}
