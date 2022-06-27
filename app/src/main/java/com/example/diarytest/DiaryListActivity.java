package com.example.diarytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiaryListActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    ListView listview;
    Long tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy_list);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listview = findViewById(R.id.rview);
        ArrayList<Diary> listViewData = new ArrayList<>();
        Intent intent = getIntent();
        String UID = intent.getStringExtra("UID");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tmp = dataSnapshot.child("account").child(UID).child("diary").getChildrenCount();
                String tmp1 = dataSnapshot.child("account").child(UID).child("diary").getValue().toString();
                String result = tmp1.substring(1,tmp1.length()-1);
                String[] tmptext = result.split(",");

                for(int i=0;i<tmp;i++){
                    Diary listData = new Diary();
                    listData.wrtieTime = tmptext[i].split("=")[0];
                    listData.diaryText = tmptext[i].split("=")[1];

                    listViewData.add(listData);
                }
                Collections.sort(listViewData); //정렬
                ListAdapter oAdapter = new SimpleTextAdapter(listViewData);
                listview.setAdapter(oAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }});
    }
}