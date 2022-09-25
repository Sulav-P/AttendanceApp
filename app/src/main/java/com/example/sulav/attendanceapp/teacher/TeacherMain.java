package com.example.sulav.attendanceapp.teacher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sulav.attendanceapp.AttendanceView.AttenadanceViewMain;
import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.listviewhelpers.Email_list;
import com.example.sulav.attendanceapp.listviewhelpers.IndividualList;
import com.example.sulav.attendanceapp.listviewhelpers.Sub_list;
import com.example.sulav.attendanceapp.listviewhelpers.TeacherName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherMain extends AppCompatActivity {

    private Button ViewAttendance;
    private Button TakeAttendance;
    private Button ViewLeaveRequests;
    private Button TeacherLogout;
    private ProgressBar progressBar;

    private FirebaseAuth tlogoutAuth;
    FirebaseDatabase database;
    DatabaseReference ref;
    DatabaseReference ref2;
    private FirebaseAuth tAuth;

    ArrayList<String> filteredsub;
    ArrayList<String> listemail;
    ArrayList<String> listclassname;


    Spinner spinner;
    Spinner spinnerclass;

    Email_list user;
    Sub_list user2;


    String Name ;
    String classname ;
    String coursename;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

      /*  tlogoutAuth = FirebaseAuth.getInstance();
        tAuth = FirebaseAuth.getInstance();   */

        Intent intent = getIntent();
        Name = intent.getExtras().getString("KeyT");


        database= FirebaseDatabase.getInstance();
        ref= database.getReference("Users").child("Teacher");



        filteredsub= new ArrayList<>();
        filteredsub.add("Select Course");

        listemail= new ArrayList<>();

        listclassname= new ArrayList<>();
        listclassname.add("Select Class");


      //  listemail.add("Select Email");

        user= new Email_list();
        user2= new Sub_list();


       // TeacherLogout = (Button) findViewById(R.id.btntLOGOUT);

        ViewAttendance = (Button) findViewById(R.id.studentsattendanceview);
        TakeAttendance = (Button) findViewById(R.id.studentattendancetake);
        ViewLeaveRequests = (Button) findViewById(R.id.studentleaverequests);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerclass = (Spinner) findViewById(R.id.spinnerclass);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);



         spinnerCheck();

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listclassname);
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





        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,filteredsub);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
                coursename = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      /*  TeacherLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                tlogoutAuth.signOut();
                progressBar.setVisibility(View.GONE);
                Intent intent =  new Intent(TeacherMain.this,TeacherActivity.class);
                startActivity(intent);
                finish();
            }
        });   */

       ViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent =  new Intent(TeacherMain.this,AttenadanceViewMain.class);
                startActivity(intent);


            }
        });



       TakeAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ClassName = classname;
                    String CourseName = coursename;
                    if (ClassName.equals("Select Class") || CourseName.equals("Select Course")) {

                        Toast.makeText(TeacherMain.this, "SELECT CLASS AND COURSE", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(TeacherMain.this, STUDENT_ATTENDANCE_SHEET.class);
                        intent.putExtra("Classname", classname);
                        intent.putExtra("Coursename", coursename);
                        startActivity(intent);
                    }
                }
            });



        ViewLeaveRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(TeacherMain.this,LEAVE_REQUESTS.class);
                intent.putExtra("TName", Name);
                startActivity(intent);

            }
        });



    }



   private void spinnerCheck() {


       /* ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    user = ds.getValue(Email_list.class);
                    assert user != null;

                    if (!listemail.contains(user.getTeacher_Email_ID())) {
                        listemail.add(user.getTeacher_Email_ID());
                    }
                     listemail.add(user.getTeacher_Email_ID());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });     */


        //  String[ ] strings= listemail.toArray(new String[listemail.size()]);

       // for (int i = 0; i <listemail.size() ; i++) {

      // if (listemail.contains(email())) {

            ref2 = FirebaseDatabase.getInstance().getReference("SpinnerCourse").child(Name);

            ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        user2 = ds.getValue(Sub_list.class);
                        assert user2 != null;

                        if (!filteredsub.contains(user2.getCOURSE_NAME())) {
                            filteredsub.add(user2.getCOURSE_NAME());
                        }
                        //filteredsub.add(user2.getCOURSE_NAME());

                        if (!listclassname.contains(user2.getCLASS_NAME())) {
                            listclassname.add(user2.getCLASS_NAME());
                        }
                        // listclassname.add(user2.getCLASS_NAME());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
     //  }


      /*public String email(){
           String email = "abc@gmail.com";
          // String email = user_id;
           return (email);
       }   */

}



    //}

//}

