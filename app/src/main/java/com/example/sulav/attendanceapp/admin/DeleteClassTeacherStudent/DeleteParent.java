package com.example.sulav.attendanceapp.admin.DeleteClassTeacherStudent;

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

import com.example.sulav.attendanceapp.ParentCheck;
import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.TeacherCheck;
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

public class DeleteParent extends AppCompatActivity {

    DatabaseReference Parent_reference;
    DatabaseReference AddParent_reference;

    private Button Deletebutton;

    private EditText PEmail , PPassword;

   /* AutoCompleteTextView PEmail;
    ArrayList autoparentlist = new ArrayList<>();
    ParentCheck autoparentuser = new ParentCheck();
    String autoParentResult;  */

    private android.widget.ProgressBar ProgressBar;

    private FirebaseAuth tAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_parent);

        tAuth = FirebaseAuth.getInstance();

        Parent_reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Parent");
        AddParent_reference = FirebaseDatabase.getInstance().getReference("AddParent");


        PEmail = (EditText) findViewById(R.id.pemail);
       // PEmail = (AutoCompleteTextView) findViewById(R.id.autoparentemail);
        PPassword = (EditText) findViewById(R.id.ppassword);

        Deletebutton = (Button) findViewById(R.id.pdelete);

        ProgressBar = findViewById(R.id.progressbar);
        ProgressBar.setVisibility(View.GONE);

       /* AutoCheck();
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,autoparentlist);
        PEmail.setAdapter(arrayAdapter4);
        PEmail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoParentResult = (String) parent.getItemAtPosition(position);
                // here is your selected item
            }
        });  */


        Deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteParent();
            }
        });
    }

    private void deleteParent(){
        ProgressBar.setVisibility(View.VISIBLE);
        final String ParentEmailD , ParentPasswordD;

        ParentEmailD = PEmail.getText().toString().trim();
       // ParentEmailD = autoParentResult.trim();
        ParentPasswordD = PPassword.getText().toString().trim();

        if(TextUtils.isEmpty(ParentEmailD) || TextUtils.isEmpty(ParentPasswordD)){

            ProgressBar.setVisibility(View.GONE);
            Toast.makeText(DeleteParent.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();

        }
        else{
            tAuth.signInWithEmailAndPassword(ParentEmailD,ParentPasswordD).addOnCompleteListener(DeleteParent.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        ProgressBar.setVisibility(View.GONE);
                        Toast.makeText(DeleteParent.this, "Invalid Parent Email or Password",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String user_id = tAuth.getCurrentUser().getUid();

                        AddParent_reference.child(user_id).removeValue();
                        Parent_reference.child(user_id).removeValue();
                        ProgressBar.setVisibility(View.GONE);
                        toast();
                        finish();
                    }
                }
            });


        }

    }

  /*  private void AutoCheck() {

        Parent_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    autoparentuser = ds.getValue(ParentCheck.class);
                    assert autoparentuser != null;

                    if (!autoparentlist.contains(autoparentuser.getParent_Email_ID())) {
                        autoparentlist.add(autoparentuser.getParent_Email_ID());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }  */

    private void toast() {
        Toast.makeText(this," Deleted Successfully",Toast.LENGTH_LONG).show();
    }
}
