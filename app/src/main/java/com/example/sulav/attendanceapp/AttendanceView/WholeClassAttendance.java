package com.example.sulav.attendanceapp.AttendanceView;

import android.app.DatePickerDialog;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.listviewhelpers.Sub_list;
import com.example.sulav.attendanceapp.listviewhelpers.WholeClassList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class WholeClassAttendance extends AppCompatActivity {

    private EditText  selDate , Date;

    String SelectDate, SelectClass, SelectCourse;
    ListView listview;

    private Button viewButton;

    DatabaseReference Add_Class_CourseReference;
    AutoCompleteTextView spinnercourse;
    Spinner spinnerclass;
    ArrayList<String> listclass;
    ArrayList<String> listcourse;
    String classname;
    String coursename;
    Sub_list infolist;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    WholeClassList user;

    DatabaseReference ref;

    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole_class);

        Add_Class_CourseReference = FirebaseDatabase.getInstance().getReference("Class_Course_Record");
        infolist = new Sub_list();
        listclass= new ArrayList<>();
        listclass.add("Select Class");
        listcourse= new ArrayList<>();

        user = new WholeClassList();
        list = new ArrayList<>();

        adapter=new ArrayAdapter<String>(this,R.layout.wholeclassmapping,R.id.wholelist , list);


        selDate = (EditText) findViewById(R.id.selDate);
        spinnercourse = (AutoCompleteTextView) findViewById(R.id.spinnercourse);
        spinnerclass = (Spinner) findViewById(R.id.selClass);

        Date = (EditText) findViewById(R.id.selDate);

        listview = (ListView) findViewById(R.id.listview);
        listview.setVisibility(View.GONE);

        viewButton = (Button) findViewById(R.id.viewButton);

        spinnerAutoTextCheck();

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


        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(WholeClassAttendance.this, new DatePickerDialog.OnDateSetListener() {
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
        list.clear();
        listview.setVisibility(View.GONE);
        //SelectDate = selDate.getText().toString().trim();
        SelectClass = classname.trim();
        SelectCourse = coursename.trim();
        SelectDate = Date.getText().toString().trim();


        ref = FirebaseDatabase.getInstance().getReference("Attendance_Class").child(SelectClass).child(SelectCourse).child(SelectDate);

        if (TextUtils.isEmpty(SelectDate) || TextUtils.isEmpty(SelectClass) || TextUtils.isEmpty(SelectCourse)) {
            Toast.makeText(WholeClassAttendance.this, "Empty Input", Toast.LENGTH_SHORT).show();
        }
        else if (SelectClass.equals("Select Class")) {

            Toast.makeText(WholeClassAttendance.this, "WRONG CLASS NAME", Toast.LENGTH_SHORT).show();

        }
        else
            {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        user = ds.getValue(WholeClassList.class);
                        if (user != null) {

                            list.add("Student Roll No"+" "+" "+user.getROLL_NO() + "\n" + "Student Name"+" "+" "+" "+user.getSTUDENT_NAME() + "\n" +"Student Status"+" "+" "+" "+  user.getSTATUS());

                            if (list.contains("null")) {
                                Toast.makeText(WholeClassAttendance.this, "Wrong Data", Toast.LENGTH_SHORT).show();

                            } else {
                                /*Intent intent = new Intent(WholeClassAttendance.this, Pop.class);
                                intent.putExtra("SELDATE", SelectDate);
                                intent.putExtra("SELCLASS", SelectClass);
                                intent.putExtra("SELCOURSE", SelectCourse);
                                intent.putExtra("SELROLLNO", SelectRollNo);

                                intent.putStringArrayListExtra("TodayList", list);

                                startActivity(intent);  */
                                listview.setVisibility(View.VISIBLE);
                                listview.setAdapter(adapter);

                            }
                        }
                        else {
                            Toast.makeText(WholeClassAttendance.this, "Wrong Data", Toast.LENGTH_SHORT).show();

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

                list.clear();
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
}



