package com.example.diarytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    EditText ipdia;
    Button bt;
    Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Intent intent = getIntent();
        String UID = intent.getStringExtra("UID");

        ipdia = findViewById(R.id.InputDia);
        bt = findViewById(R.id.SaveDia);
        bt2 = findViewById(R.id.button2);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ipdia.length()==0){
                    Toast.makeText(getApplicationContext(), "일기를 작성해 주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    String dia = ipdia.getText().toString();
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String getTime = sdf.format(date);
                    databaseReference.child("account").child(UID).child("diary").child(getTime).setValue(dia);
                    SaveDiary(UID,getTime,dia);
                }
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiaryListActivity.class);
                intent.putExtra("UID", UID);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ipdia = findViewById(R.id.InputDia);
        ipdia.setText("");
    }

    public void SaveDiary(String UID, String getTime,String diary) {
            databaseReference.child("account").child(UID).child("diary").child(getTime).setValue(diary)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(getApplicationContext(), DiaryListActivity.class);
                        intent.putExtra("UID", UID);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}