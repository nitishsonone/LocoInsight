//package com.example.mylogin;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class mcqinstructions extends AppCompatActivity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mcqinstructions);
//        Button cancel_button, start_exam_button;
//        start_exam_button = findViewById(R.id.start_exam_button);
//        cancel_button = findViewById(R.id.cancel_exam_button);
//
//        String empId = getIntent().getStringExtra("empId"); // receive empId
//
//        start_exam_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(mcqinstructions.this, mcq.class);
//                i.putExtra("empId", empId); // pass empId
//                startActivity(i);
//            }
//        });
//
//        cancel_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(mcqinstructions.this, HomeActivity.class);
//                i.putExtra("empId", empId); // pass empId
//                startActivity(i);
//            }
//        });
//    }
//
//}

package com.example.mylogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class mcqinstructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqinstructions);
        Button cancel_button, start_exam_button;
        start_exam_button = findViewById(R.id.start_exam_button);
        cancel_button = findViewById(R.id.cancel_exam_button);

        String empId = getIntent().getStringExtra("empId"); // receive empId
        boolean timer = getIntent().getBooleanExtra("timer", false); // receive timer info
        String role = getIntent().getStringExtra("role"); // receive role info

        start_exam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if ("Technician".equals(role)) {
                    i = new Intent(mcqinstructions.this, mcq.class);
                } else if ("Junior Engineer".equals(role)) {
                    i = new Intent(mcqinstructions.this, mcql2.class);
                } else {
                    return; // Invalid role, do nothing or show an error
                }
                i.putExtra("empId", empId); // pass empId
                i.putExtra("timer", timer); // pass timer info
                startActivity(i);
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mcqinstructions.this, HomeActivity.class);
                i.putExtra("empId", empId); // pass empId
                startActivity(i);
            }
        });
    }
}