package com.example.sulav.attendanceapp.AttendanceView;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sulav.attendanceapp.Roll_list;
import com.example.sulav.attendanceapp.admin.DeleteClassTeacherStudent.DeleteClass;
import com.example.sulav.attendanceapp.listviewhelpers.DeleteCheck;
import com.example.sulav.attendanceapp.listviewhelpers.IndividualList;
import com.example.sulav.attendanceapp.listviewhelpers.Pop;
import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.listviewhelpers.Sub_list;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class IndividualAttendance extends AppCompatActivity {
    private EditText Date;
    String SelectDate,SelectClass,SelectCourse,SelectRollNo;

    DatabaseReference Add_Class_CourseReference;
    DatabaseReference AddRoll_reference;

    private Button viewButton;

    AutoCompleteTextView spinnercourse;
    AutoCompleteTextView selRollNo;
    Spinner spinnerclass;

    ArrayList<String> listclass;
    ArrayList<String> listcourse;
    ArrayList<String> listrollno;
    String classname;
    String coursename;
    String studentrollno;

    ArrayList<String> list;
    IndividualList user;

    Sub_list infolist;
    Roll_list roll_list;

    String info;

    DatabaseReference ref;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_attendance);

        Add_Class_CourseReference = FirebaseDatabase.getInstance().getReference("Class_Course_Record");
        AddRoll_reference = FirebaseDatabase.getInstance().getReference("RollRecord");

        user = new IndividualList();
        list = new ArrayList<>();

        infolist = new Sub_list();
        roll_list = new Roll_list();
        listclass= new ArrayList<>();
        listclass.add("Select Class");
        listcourse= new ArrayList<>();
        listrollno= new ArrayList<>();


       // selDate = (EditText) findViewById(R.id.selDate);

        spinnercourse = (AutoCompleteTextView) findViewById(R.id.spinnercourse);
        spinnerclass = (Spinner) findViewById(R.id.selClass);
        selRollNo = (AutoCompleteTextView) findViewById(R.id.selRollNo);

        Date = (EditText) findViewById(R.id.selDate);

        viewButton = (Button) findViewById(R.id.viewButton);

        spinnerAutoTextCheck();
        AutoRollCheck();

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

        coursename = " ";
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listcourse);
        spinnercourse.setAdapter(arrayAdapter2);
        spinnercourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                coursename = (String) parent.getItemAtPosition(position);
                // here is your selected item
            }
        });

        studentrollno = " ";
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listrollno);
        selRollNo.setAdapter(arrayAdapter3);
        selRollNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                studentrollno = (String) parent.getItemAtPosition(position);
                // here is your selected item
            }
        });

       Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(IndividualAttendance.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month + 1;
                        String formattedMonth = "" + month;
                        String formattedDayOfMonth = "" + dayOfMonth;
                        String formattedYear=String.valueOf(year);

                        if(month < 10){

                            formattedMonth = "0" + month;
                        }
                        if(dayOfMonth < 10){

                            formattedDayOfMonth = "0" + dayOfMonth;
                        }

                        //formattedYear=formattedYear.substring(formattedYear.length()-2);
                        String date=formattedDayOfMonth + "-" + formattedMonth + "-" + formattedYear;
                        Date.setText(date);
                    }
                },day,month,year);
                datePickerDialog.show();
            }
        });


        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAttendInfo();
            }
        });


    }

    private void viewAttendInfo() {

        //SelectDate = selDate.getText().toString().trim();
        SelectClass = classname.trim();
        SelectCourse = coursename.trim();
        SelectRollNo = studentrollno.trim();
        SelectDate = Date.getText().toString().trim();

        if (TextUtils.isEmpty(SelectDate) || TextUtils.isEmpty(SelectClass) || TextUtils.isEmpty(SelectCourse) || TextUtils.isEmpty(SelectRollNo)) {
            Toast.makeText(IndividualAttendance.this, "Empty Input", Toast.LENGTH_SHORT).show();
        }
        else if (SelectClass.equals("Select Class")) {

            Toast.makeText(IndividualAttendance.this, "WRONG CLASS NAME", Toast.LENGTH_SHORT).show();

        }
            else
         {
             ref = FirebaseDatabase.getInstance().getReference("Attendance").child(SelectClass).child(SelectDate).child(SelectCourse).child(SelectRollNo);

             ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    user = dataSnapshot.getValue(IndividualList.class);
                    if (user != null) {

                        list.add(user.getSTUDENT_NAME() + "\n" + user.getROLL_NO() + "\n" + user.getSTATUS());

                        if (list.contains("null")) {
                            Toast.makeText(IndividualAttendance.this, "Wrong Data", Toast.LENGTH_SHORT).show();

                        } else {
                            Intent intent = new Intent(IndividualAttendance.this, Pop.class);
                            //intent.putExtra("SELDATE", SelectDate);
                            //intent.putExtra("SELCLASS", SelectClass);
                            //intent.putExtra("SELCOURSE", SelectCourse);
                            //intent.putExtra("SELROLLNO", SelectRollNo);

                            intent.putStringArrayListExtra("TodayList", list);

                            startActivity(intent);

                            list.clear();
                        }

                    }
                     else {
                        Toast.makeText(IndividualAttendance.this, "Wrong Data", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {


                }
            });

      /*  for (int i=0; i<list.size();i++){
            view.append(list.get(i));
            view.append("\n");
        }   */


        }
    }

    private void spinnerAutoTextCheck() {

        Add_Class_CourseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    infolist = ds.getValue(Sub_list.class);
                    assert infolist != null;

                    if (!listcourse.contains(infolist.getCOURSE_NAME())) {
                        listcourse.add(infolist.getCOURSE_NAME());
                    }

                    if (!listclass.contains(infolist.getCLASS_NAME())) {
                        listclass.add(infolist.getCLASS_NAME());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void AutoRollCheck() {

        AddRoll_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    roll_list = ds.getValue(Roll_list.class);
                    assert roll_list != null;

                    if (!listrollno.contains(roll_list.getStudent_RollNo())) {
                        listrollno.add(roll_list.getStudent_RollNo());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
