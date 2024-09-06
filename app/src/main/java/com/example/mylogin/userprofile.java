package com.example.mylogin;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userprofile extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String empId;

    private TextView empIdTextView, passwordTextView, logoutButton, attemptsTextView, averageTextView, scoreTextView, nameTextView, mobileNoTextView, designationTextView, sectionTextView, changePasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        // Initialize views
        empIdTextView = findViewById(R.id.empId);
        passwordTextView = findViewById(R.id.password);
        attemptsTextView = findViewById(R.id.currentAttempts);
        averageTextView = findViewById(R.id.average);
        scoreTextView = findViewById(R.id.score);
        nameTextView = findViewById(R.id.name);
        mobileNoTextView = findViewById(R.id.mobileno);
        designationTextView = findViewById(R.id.designation);
        sectionTextView = findViewById(R.id.sec);
        changePasswordTextView = findViewById(R.id.changepass);
        logoutButton = findViewById(R.id.logout);

        // Get empId from the intent
        empId = getIntent().getStringExtra("empId");

        if (empId == null || empId.isEmpty()) {
            Toast.makeText(this, "EmpID is null or empty", Toast.LENGTH_SHORT).show();
            Log.e("UserProfile", "EmpID is null or empty");
            return;
        }

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("1pDK-bQKBrZS1xNzWjv5uOR2gm9cnlhM9qjCMPSFZN2g/Sheet1");

        // Fetch data from the database
        fetchDataFromDatabase(empId);

        // Set the onClickListener for the changePasswordTextView
        changePasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog(empId);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void fetchDataFromDatabase(String empId) {
        databaseReference.child(empId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Handle empId as a Number
                    String empIdValue = getStringValue(snapshot.child("EmpID"));
                    String password = getStringValue(snapshot.child("Password"));
                    Long attempts = snapshot.child("Attempts").getValue(Long.class);
                    Double average = snapshot.child("Average").getValue(Double.class);
                    Long score = snapshot.child("Score").getValue(Long.class);
                    String name = getStringValue(snapshot.child("Name"));
                    String mobileNo = getStringValue(snapshot.child("MobileNo"));
                    String designation = getStringValue(snapshot.child("Designation"));
                    String section = getStringValue(snapshot.child("Section"));

                    // Check if TextViews are not null before setting text
                    if (empIdTextView != null) empIdTextView.setText(empIdValue != null ? empIdValue : "N/A");
                    if (passwordTextView != null) passwordTextView.setText(password != null ? password : "N/A");
                    if (attemptsTextView != null) attemptsTextView.setText(attempts != null ? String.valueOf(attempts) : "N/A");
                    if (averageTextView != null) averageTextView.setText(average != null ? String.valueOf(average) : "N/A");
                    if (scoreTextView != null) scoreTextView.setText(score != null ? String.valueOf(score) : "N/A");
                    if (nameTextView != null) nameTextView.setText(name != null ? name : "N/A");
                    if (mobileNoTextView != null) mobileNoTextView.setText(mobileNo != null ? mobileNo : "N/A");
                    if (designationTextView != null) designationTextView.setText(designation != null ? designation : "N/A");
                    if (sectionTextView != null) sectionTextView.setText(section != null ? section : "N/A");
                } else {
                    Toast.makeText(userprofile.this, "Employee not found", Toast.LENGTH_SHORT).show();
                    Log.e("UserProfile", "Employee not found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("UserProfile", "Database error: " + error.getMessage());
                Toast.makeText(userprofile.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getStringValue(DataSnapshot snapshot) {
        Object value = snapshot.getValue();
        if (value != null) {
            if (value instanceof String) {
                return (String) value;
            } else if (value instanceof Number) {
                return String.valueOf(value);
            } else {
                Log.e("UserProfile", "Unsupported type for value: " + value.getClass().getName());
            }
        }
        return null;
    }

    private void showChangePasswordDialog(String empId) {
        Dialog changePasswordDialog = new Dialog(this, R.style.RoundedDialog); // Apply custom style
        changePasswordDialog.setContentView(R.layout.activity_update_pass);
        changePasswordDialog.setCancelable(true);

        EditText newPassword = changePasswordDialog.findViewById(R.id.newPassword);
        EditText reenterPassword = changePasswordDialog.findViewById(R.id.reenterPassword);
        Button updateButton = changePasswordDialog.findViewById(R.id.updateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPass = newPassword.getText().toString();
                String reenterPass = reenterPassword.getText().toString();

                if (newPass.equals(reenterPass)) {
                    updatePassword(empId, newPass);
                    changePasswordDialog.dismiss();
                } else {
                    Toast.makeText(userprofile.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        changePasswordDialog.show();
    }

    private void updatePassword(String empId, String newPassword) {
        databaseReference.child(empId).child("Password").setValue(newPassword)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    // Update passwordTextView immediately after successful update
                    passwordTextView.setText(newPassword);
                })
                .addOnFailureListener(e -> {
                    Log.e("UserProfile", "Failed to update password: " + e.getMessage());
                    Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show();
                });
    }

    private void logout() {
        // Clear SharedPreferences if you are using it to store user session data
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirect to MainActivity
        Intent intent = new Intent(userprofile.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}