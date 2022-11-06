package com.example.fisac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity3 extends AppCompatActivity {
    private Button button;
    private TextView email;
    private TextView password;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mAuth = FirebaseAuth.getInstance();

        email = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        button = (Button) findViewById(R.id.submit_button);

        database = FirebaseDatabase.getInstance("https://console.firebase.google.com/");
        myRef = database.getReference().child("users");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertData();

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(MainActivity3.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    openActivity();
                                    Toast.makeText(MainActivity3.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity3.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void insertData() {
        User user = new User(email.getText().toString());

        myRef.push().setValue(user);
    }

    private void openActivity(){
        Intent intent =new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}