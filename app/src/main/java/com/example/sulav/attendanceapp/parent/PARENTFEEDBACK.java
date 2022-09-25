package com.example.sulav.attendanceapp.parent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sulav.attendanceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PARENTFEEDBACK extends AppCompatActivity {

    public static final String message = " com.example.sulav.attendanceapp.parent.MESSAGE";

    Button feedback_send;

    private DatabaseReference feedbackData;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedback_send = (Button) findViewById(R.id.feedback_send);

        feedbackData = FirebaseDatabase.getInstance().getReference("PARENTFEEDBACK");




        feedback_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editText  = (EditText) findViewById(R.id.parentfeedbacktext);
                String message = editText .getText().toString().trim();

                if(TextUtils.isEmpty(message)) {
                    Toast.makeText(PARENTFEEDBACK.this, "  Please Enter The FeedBack", Toast.LENGTH_SHORT).show();
                }
                else{
                    String id = feedbackData.push().getKey();

                    feedbackData.child(id).child("PARENTFEEDBACK").setValue(message.toString());
                   // feedbackData.child(id).child("PARENTFEEDBACK").setValue(message.toString());

                    Toast.makeText(getApplicationContext(),"Feedback Sent", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        });

    }
}

