package com.example.sulav.attendanceapp.teacher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.listviewhelpers.StudentLeaveRequestList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LEAVE_REQUESTS extends AppCompatActivity {

    DatabaseReference reference;

    private Button viewbutton;
    private Button clearbutton;
    ListView listViewSL;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    StudentLeaveRequestList user;

    String Tname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave__requests);

        user= new StudentLeaveRequestList();
        list= new ArrayList<>();

        adapter=new ArrayAdapter<String>(this,R.layout.list,R.id.feedbacklist , list);

        listViewSL= (ListView) findViewById(R.id.listViewSL);

        Intent intent = getIntent();
        Tname = intent.getExtras().getString("TName");


        viewbutton = (Button)findViewById(R.id.button);
        clearbutton = (Button)findViewById(R.id.clearbutton);


        reference = FirebaseDatabase.getInstance().getReference("STUDENT LEAVE REQUEST").child(Tname);

        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            user= ds.getValue(StudentLeaveRequestList.class);
                            if(user != null){
                                list.add(user.getMESSAGE());
                            }
                            else {
                                String s = "NO LEAVE REQUEST" ;
                                list.add(s);
                            }

                        }
                        listViewSL.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        clearbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue();
                list.clear();
                finish();
              /*  reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            user= ds.getValue(UserFeedbackParent.class);
                            assert user != null;
                            list.add(user.getPARENTFEEDBACK());

                        }
                        listViewPF.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }); */
            }

        });
    }
}
