package com.example.mylogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText empIdEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MyLogin);
        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(this);

        empIdEditText = findViewById(R.id.empId);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        databaseReference = FirebaseDatabase.getInstance().getReference("1pDK-bQKBrZS1xNzWjv5uOR2gm9cnlhM9qjCMPSFZN2g/Sheet1");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser();
            }
        });
    }

    private void authenticateUser() {
        final String empId = empIdEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        if (empId.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter both employee ID and password", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child(empId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Object passwordObject = dataSnapshot.child("Password").getValue();
                    String storedPassword = passwordObject != null ? passwordObject.toString() : null;

                    if (storedPassword != null && storedPassword.equals(password)) {
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                        finish();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("empId", empId); // Add this line to pass empId
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Employee ID not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onBackPressed() {
        // Do nothing (disable back button)
        // Alternatively, you can show a message or perform any other action you desire
    }
}
