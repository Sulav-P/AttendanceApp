package com.example.sulav.attendanceapp.teacher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.listviewhelpers.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class STUDENT_ATTENDANCE_SHEET extends AppCompatActivity {

    Calendar calendar;
    RadioGroup radioGroup;
    RadioButton radioButton;
    SimpleDateFormat simpleDateFormat;
    String Date;
    String classname;
    String coursename;
   // String key;

    ListView listView;

    FirebaseDatabase database;
    DatabaseReference ref;
    DatabaseReference refattend;
    DatabaseReference refAttendClass;

    ArrayList<String> list;
    ArrayList<String> listRoll;
    ArrayList<String> listName;
    ArrayList<String> selection= new ArrayList<String>();
    ArrayAdapter<String> adapter;

    User user;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__attendance__sheet);

        user= new User();

         Intent intent = getIntent();
         classname = intent.getExtras().getString("Classname");
         coursename = intent.getExtras().getString("Coursename");

        radioGroup = findViewById(R.id.radioGroup);
        listView= (ListView) findViewById(R.id.listView);

        database= FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("ATTENDREFERENCE").child(classname);
        refattend= database.getReference("Attendance");
        refAttendClass= database.getReference("Attendance_Class");

        list= new ArrayList<>();
        listRoll= new ArrayList<>();
        listName= new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.user_info,R.id.userInfo , list);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date = simpleDateFormat.format(calendar.getTime());


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    user= ds.getValue(User.class);
                    assert user != null;
                    list.add(user.getStudent_RollNo()+"\n" +user.getStudent_Name());
                    listRoll.add(user.getStudent_RollNo());
                    listName.add(user.getStudent_Name());
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void selectItem(View view)
    {
        boolean checked = ((RadioButton)view).isChecked() ;

       switch (view.getId())
        {
            case R.id.presentchk:
                if(checked)
                { selection.add("P");
                }
               else{
                    selection.remove("P");
                }
                break;

            case R.id.absentchk:
                if(checked)
                { selection.add("A");
                }
               else{
                    selection.remove("A");
                }
                break;
        }


    }



    public void attendsave(View view) {

        String todaydate = Date;
        String Roll_No;
        String ClassName = classname;
        String CourseName = coursename;


        if (ClassName.equals("Select Class") || CourseName.equals("Select Course")) {

            Toast.makeText(STUDENT_ATTENDANCE_SHEET.this, "SELECT CLASS AND COURSE", Toast.LENGTH_SHORT).show();
            finish();

        }
        else {
             /* Date =todaydate;
              classname =ClassName;
              coursename =CourseName;   */
            if (selection.isEmpty()) {

                Toast.makeText(STUDENT_ATTENDANCE_SHEET.this, "Please take Attendance First!!", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(STUDENT_ATTENDANCE_SHEET.this, "I am saving to database", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < list.size(); i++) {

                    String key = refAttendClass.push().getKey();
                    Roll_No = listRoll.get(i);
                    String Student_Name = listName.get(i);

                    String status = selection.get(i);

                    refattend.child(ClassName).child(todaydate).child(CourseName).child(String.valueOf(Roll_No)).child("STATUS").setValue(status);

                    refAttendClass.child(ClassName).child(CourseName).child(todaydate).child(key).child("STATUS").setValue(status);

                    refattend.child(ClassName).child(todaydate).child(CourseName).child(String.valueOf(Roll_No)).child("ROLL_NO").setValue(Roll_No);
                    refattend.child(ClassName).child(todaydate).child(CourseName).child(String.valueOf(Roll_No)).child("STUDENT_NAME").setValue(Student_Name);

                    refAttendClass.child(ClassName).child(CourseName).child(todaydate).child(key).child("ROLL_NO").setValue(Roll_No);
                    refAttendClass.child(ClassName).child(CourseName).child(todaydate).child(key).child("STUDENT_NAME").setValue(Student_Name);

                    // selection(key);

                }


                finish();

            }
        }
    }

    /*public void selection(String key){
        for (int i = 0; i < selection.size(); i++) {

            String status = selection.get(i);
            String Roll_No = listRoll.get(i);

            refattend.child(classname).child(Date).child(coursename).child(String.valueOf(Roll_No)).child("STATUS").setValue(status);

            refAttendClass.child(classname).child(coursename).child(Date).child(key).child("STATUS").setValue(status);

        }
    } */

}

