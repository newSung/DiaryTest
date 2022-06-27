package com.example.diarytest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    EditText Nid;
    EditText Npw;
    Button bt;
    String id;
    String pw;
    long tmp;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Nid = findViewById(R.id.textView);
        Npw = findViewById(R.id.textView2);
        bt = findViewById(R.id.button3);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = Nid.getText().toString();
                pw = Npw.getText().toString();

                if(Nid.length()==0)
                    Toast.makeText(getApplicationContext(), "id를 입력해 주세요", Toast.LENGTH_SHORT).show();
                else if(Npw.length()==0)
                    Toast.makeText(getApplicationContext(), "pw를 입력해 주세요", Toast.LENGTH_SHORT).show();
                else {
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int count = 0;
                            tmp = dataSnapshot.child("IdList").getChildrenCount();
                            for (int i = 1; i <= tmp; i++) {
                                if (dataSnapshot.child("IdList").child(Long.toString(i)).getValue().equals(id) == true) {
                                    Toast.makeText(getApplicationContext(), "이미 존재하는 id입니다", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                else
                                    count++;
                            }
                            if(count == tmp){
                                Register(tmp,id,pw);
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }});
                }
            }
        });
    }
    public void Register(Long tmp, String id, String pw) {
        mDatabase.child("IdList").child(Long.toString(tmp+1)).setValue(id);
        mDatabase.child("account").child(id).child("pw").setValue(pw);
    }
}