//package com.example.mylogin;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class MainActivity2 extends AppCompatActivity {
//
//    private TextView mockTestWithTimerTextView, mockTestWithoutTimerTextView, mockTestWithTimerTextView2, mockTestWithoutTimerTextView2;
//    private String empId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//
//        // Retrieve empId from the Intent
//        empId = getIntent().getStringExtra("empId");
//
//        mockTestWithTimerTextView = findViewById(R.id.timemock1);
//        mockTestWithoutTimerTextView = findViewById(R.id.withouttimemock1);
//
//        mockTestWithTimerTextView2 = findViewById(R.id.timemock2);
//        mockTestWithoutTimerTextView2 = findViewById(R.id.withouttimemock2);
//
//        mockTestWithTimerTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity2.this, mcqinstructions.class);
//                intent.putExtra("timer", true);
//                intent.putExtra("empId", empId); // Pass empId to mcq activity
//                startActivity(intent);
//            }
//        });
//
//        mockTestWithoutTimerTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity2.this, mcqinstructions.class);
//                intent.putExtra("timer", false);
//                intent.putExtra("empId", empId); // Pass empId to mcq activity
//                startActivity(intent);
//            }
//        });
//
//        mockTestWithTimerTextView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity2.this, mcqinstructions.class);
//                intent.putExtra("timer", true);
//                intent.putExtra("empId", empId); // Pass empId to mcq activity
//                startActivity(intent);
//            }
//        });
//
//        mockTestWithoutTimerTextView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity2.this, mcqinstructions.class);
//                intent.putExtra("timer", false);
//                intent.putExtra("empId", empId); // Pass empId to mcq activity
//                startActivity(intent);
//            }
//        });
//    }
//}

package com.example.mylogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private TextView mockTestWithTimerTextView, mockTestWithoutTimerTextView, mockTestWithTimerTextView2, mockTestWithoutTimerTextView2;
    private String empId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Retrieve empId from the Intent
        empId = getIntent().getStringExtra("empId");

        mockTestWithTimerTextView = findViewById(R.id.timemock1);
        mockTestWithoutTimerTextView = findViewById(R.id.withouttimemock1);

        mockTestWithTimerTextView2 = findViewById(R.id.timemock2);
        mockTestWithoutTimerTextView2 = findViewById(R.id.withouttimemock2);

        mockTestWithTimerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, mcqinstructions.class);
                intent.putExtra("timer", true);
                intent.putExtra("empId", empId); // Pass empId to mcq activity
                intent.putExtra("role", "Technician"); // Pass role
                startActivity(intent);
            }
        });

        mockTestWithoutTimerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, mcqinstructions.class);
                intent.putExtra("timer", false);
                intent.putExtra("empId", empId); // Pass empId to mcq activity
                intent.putExtra("role", "Technician"); // Pass role
                startActivity(intent);
            }
        });

        mockTestWithTimerTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, mcqinstructions.class);
                intent.putExtra("timer", true);
                intent.putExtra("empId", empId); // Pass empId to mcq activity
                intent.putExtra("role", "Junior Engineer"); // Pass role
                startActivity(intent);
            }
        });

        mockTestWithoutTimerTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, mcqinstructions.class);
                intent.putExtra("timer", false);
                intent.putExtra("empId", empId); // Pass empId to mcq activity
                intent.putExtra("role", "Junior Engineer"); // Pass role
                startActivity(intent);
            }
        });
    }
}