package com.example.sulav.attendanceapp.admin.ADMINFEEDBACK;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.listviewhelpers.UserFeedbackStudent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ADMIN_STUDENTFEEDBACK extends AppCompatActivity {

    DatabaseReference reference;

    private Button viewbutton;
    private Button clearbutton;
    ListView listViewPF;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    UserFeedbackStudent user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__studentfeedback);

        user = new UserFeedbackStudent();
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list, R.id.feedbacklist, list);


        listViewPF = (ListView) findViewById(R.id.listViewPF);
        listViewPF.setVisibility(View.GONE);


        viewbutton = (Button) findViewById(R.id.button);
        clearbutton = (Button) findViewById(R.id.clearbutton);


        reference = FirebaseDatabase.getInstance().getReference("STUDENTFEEDBACK");

        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            user = ds.getValue(UserFeedbackStudent.class);
                            if (user != null) {
                                list.add(user.getSTUDENTFEEDBACK());
                            } else {
                                String s = "NO FEEDBACK";
                                list.add(s);
                            }

                        }
                        listViewPF.setVisibility(View.VISIBLE);
                        listViewPF.setAdapter(adapter);

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
                listViewPF.setVisibility(View.VISIBLE);
                listViewPF.setAdapter(adapter);
            }

        });


    }
}
