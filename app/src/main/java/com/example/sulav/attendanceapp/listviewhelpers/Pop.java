package com.example.sulav.attendanceapp.listviewhelpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sulav.attendanceapp.R;
import com.example.sulav.attendanceapp.listviewhelpers.IndividualList;

import java.util.ArrayList;

public class Pop extends Activity {

    ListView popupview;
    //TextView test;
   // String SELDATE;
    //String SELCLASS;
    //String SELCOURSE;
    //String SELROLLNO;

    ArrayAdapter<String> adapter;
    ArrayList<String> list;


    IndividualList user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);

        list= new ArrayList<>();


        Intent intent = getIntent();
        list = intent.getStringArrayListExtra("TodayList");
       // SELDATE = intent.getExtras().getString("SELDATE");
        //SELCLASS = intent.getExtras().getString("SELCLASS");
        //SELCOURSE = intent.getExtras().getString("SELCOURSE");
        //SELROLLNO = intent.getExtras().getString("SELROLLNO");

        adapter=new ArrayAdapter<String>(this,R.layout.forpopup,R.id.popuplist , list);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        popupview= (ListView) findViewById(R.id.popupview);

        int width = dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.6));

        //popupview.setAdapter(null);


       popupview.setAdapter(adapter);

       /* for (int i=0; i<list.size();i++) {
            test.append(list.get(i));
            test.append("\n");
        }  */




    }
}
