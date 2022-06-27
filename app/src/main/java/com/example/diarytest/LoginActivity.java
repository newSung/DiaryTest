    package com.example.diarytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


    public class LoginActivity extends AppCompatActivity {
        EditText Inputid;
        EditText InputPw;
        Button bt;
        Button bt2;
        String id;
        String pw;
        private DatabaseReference mDatabase;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Inputid = findViewById(R.id.InputID);
            InputPw = findViewById(R.id.InputPw);
            bt = findViewById(R.id.button);
            bt2 = findViewById(R.id.button4);
            mDatabase = FirebaseDatabase.getInstance().getReference();

            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = Inputid.getText().toString();
                    pw = InputPw.getText().toString();

                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(Inputid.length()==0)
                                Toast.makeText(getApplicationContext(), "id를 입력해 주세요", Toast.LENGTH_SHORT).show();
                            else if(InputPw.length()==0)
                                Toast.makeText(getApplicationContext(), "pw를 입력해 주세요", Toast.LENGTH_SHORT).show();
                            else if(dataSnapshot.child("account").child(id).getValue() == null){
                                Toast.makeText(getApplicationContext(), "id없음", Toast.LENGTH_SHORT).show();
                            }
                            else if(dataSnapshot.child("account").child(id).child("pw").getValue().toString().equals(pw) == false){
                                Toast.makeText(getApplicationContext(), "pw없음", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("UID", id);
                                startActivity(intent);
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }});
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }
        @Override
        protected void onStart() {
            super.onStart();
            Inputid = findViewById(R.id.InputID);
            InputPw = findViewById(R.id.InputPw);
            Inputid.setText("");
            InputPw.setText("");
        }
    }


