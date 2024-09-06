package com.example.mylogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Result extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String empId;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_result);
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("1ArgO93DDKRv6osLsGyLUS0CdE-XAqDcm0m7L93Do2zc/Sheet1");
//
//        empId = getIntent().getStringExtra("empId");
//        Log.d("ResultActivity", "empId from intent: " + empId);
//
//        if (empId == null || empId.isEmpty()) {
//            empId = getSharedPreferences("MyPrefs", MODE_PRIVATE).getString("empId", null);
//            Log.d("ResultActivity", "empId from SharedPreferences: " + empId);
//        }
//
//        if (empId == null || empId.isEmpty()) {
//            Toast.makeText(this, "EmpID is null or empty", Toast.LENGTH_SHORT).show();
//            Log.e("ResultActivity", "EmpID is null or empty");
//            return;
//        }
//
//        getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().putString("empId", empId).apply();
//
//        int totalQuestions = getIntent().getIntExtra("totalQuestions", 50);
//        int score = getIntent().getIntExtra("score", 0);
//        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
//        int wrongAnswers = getIntent().getIntExtra("wrongAnswers", 0);
//        int skippedQuestions = getIntent().getIntExtra("skippedQuestions", 0);
//        String timeTaken = getIntent().getStringExtra("timeTaken");
//
//        ProgressBar solvedProgress = findViewById(R.id.progress_solved);
//        ProgressBar skippedProgress = findViewById(R.id.progress_skipped);
//        ProgressBar correctProgress = findViewById(R.id.progress_correct);
//        ProgressBar wrongProgress = findViewById(R.id.progress_wrong);
//        TextView solvedValue = findViewById(R.id.text_solved_count);
//        TextView skippedValue = findViewById(R.id.text_skipped_count);
//        TextView correctValue = findViewById(R.id.text_correct_count);
//        TextView wrongValue = findViewById(R.id.text_wrong_count);
//        TextView totalScore = findViewById(R.id.total_score);
//        TextView timeTakenView = findViewById(R.id.time_taken);
//
//        int maxProgress = 50;
//        solvedProgress.setMax(maxProgress);
//        skippedProgress.setMax(maxProgress);
//        correctProgress.setMax(maxProgress);
//        wrongProgress.setMax(maxProgress);
//        solvedProgress.setProgress((correctAnswers + wrongAnswers) * maxProgress / totalQuestions);
//        skippedProgress.setProgress(skippedQuestions * maxProgress / totalQuestions);
//        correctProgress.setProgress(correctAnswers * maxProgress / totalQuestions);
//        wrongProgress.setProgress(wrongAnswers * maxProgress / totalQuestions);
//        solvedValue.setText(String.valueOf(correctAnswers + wrongAnswers));
//        skippedValue.setText(String.valueOf(skippedQuestions));
//        correctValue.setText(String.valueOf(correctAnswers));
//        wrongValue.setText(String.valueOf(wrongAnswers));
//        totalScore.setText("Total Score: " + score);
//
//        long totalMillis = 120 * 60;
//        String[] timeParts = timeTaken.split(":");
//        long hours  = Long.parseLong(timeParts[0]);
//        long minutes  = Long.parseLong(timeParts[1]);
//        long seconds  = Long.parseLong(timeParts[2]);
//        long timeMillis = (hours * 3600) + (minutes * 60) + seconds;;
//        long remainingMillis = totalMillis - timeMillis;
//        hours = remainingMillis / (60 * 60);
//        minutes = (remainingMillis / (60)) % 60;
//        seconds = remainingMillis% 60;
//        String remainingTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
//        timeTakenView.setText("Time Taken: " + remainingTime);
//
//        saveScoreToDatabase(empId, score);
//
//        OnBackPressedDispatcher onBackPressedDispatcher = this.getOnBackPressedDispatcher();
//        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                navigateToHome();
//            }
//        });
//
//        ImageView profileImageView = findViewById(R.id.profile);
//        profileImageView.setOnClickListener(v -> navigateToUserProfile());
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        databaseReference = FirebaseDatabase.getInstance().getReference("1pDK-bQKBrZS1xNzWjv5uOR2gm9cnlhM9qjCMPSFZN2g/Sheet1");

        empId = getIntent().getStringExtra("empId");
        Log.d("ResultActivity", "empId from intent: " + empId);

        if (empId == null || empId.isEmpty()) {
            empId = getSharedPreferences("MyPrefs", MODE_PRIVATE).getString("empId", null);
            Log.d("ResultActivity", "empId from SharedPreferences: " + empId);
        }

        if (empId == null || empId.isEmpty()) {
            Toast.makeText(this, "EmpID is null or empty", Toast.LENGTH_SHORT).show();
            Log.e("ResultActivity", "EmpID is null or empty");
            return;
        }

        getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().putString("empId", empId).apply();

        int totalQuestions = getIntent().getIntExtra("totalQuestions", 110);
        int score = getIntent().getIntExtra("score", 0);
        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        int wrongAnswers = getIntent().getIntExtra("wrongAnswers", 0);
        int skippedQuestions = getIntent().getIntExtra("skippedQuestions", 0);
        String timeTaken = getIntent().getStringExtra("timeTaken");

        ProgressBar solvedProgress = findViewById(R.id.progress_solved);
        ProgressBar skippedProgress = findViewById(R.id.progress_skipped);
        ProgressBar correctProgress = findViewById(R.id.progress_correct);
        ProgressBar wrongProgress = findViewById(R.id.progress_wrong);
        TextView solvedValue = findViewById(R.id.text_solved_count);
        TextView skippedValue = findViewById(R.id.text_skipped_count);
        TextView correctValue = findViewById(R.id.text_correct_count);
        TextView wrongValue = findViewById(R.id.text_wrong_count);
        TextView totalScore = findViewById(R.id.total_score);
        TextView timeTakenView = findViewById(R.id.time_taken);

        int maxProgress = 50;
        solvedProgress.setMax(maxProgress);
        skippedProgress.setMax(maxProgress);
        correctProgress.setMax(maxProgress);
        wrongProgress.setMax(maxProgress);
        solvedProgress.setProgress((correctAnswers + wrongAnswers) * maxProgress / totalQuestions);
        skippedProgress.setProgress(skippedQuestions * maxProgress / totalQuestions);
        correctProgress.setProgress(correctAnswers * maxProgress / totalQuestions);
        wrongProgress.setProgress(wrongAnswers * maxProgress / totalQuestions);
        solvedValue.setText(String.valueOf(correctAnswers + wrongAnswers));
        skippedValue.setText(String.valueOf(skippedQuestions));
        correctValue.setText(String.valueOf(correctAnswers));
        wrongValue.setText(String.valueOf(wrongAnswers));
        totalScore.setText("Total Score: " + score);

        // Handle timeTaken null case
        if (timeTaken != null && !timeTaken.isEmpty()) {
            long totalMillis = 120 * 60 * 1000;
            String[] timeParts = timeTaken.split(":");
            long hours = Long.parseLong(timeParts[0]);
            long minutes = Long.parseLong(timeParts[1]);
            long seconds = Long.parseLong(timeParts[2]);
            long timeMillis = (hours * 3600 * 1000) + (minutes * 60 * 1000) + (seconds * 1000);
            long remainingMillis = totalMillis - timeMillis;
            hours = remainingMillis / (60 * 60 * 1000);
            minutes = (remainingMillis / (60 * 1000)) % 60;
            seconds = (remainingMillis / 1000) % 60;
            String remainingTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
            timeTakenView.setText("Time Taken: " + remainingTime);
        } else {
            Log.e("ResultActivity", "timeTaken is null or empty");
            timeTakenView.setText("Time Taken: N/A");
        }

        saveScoreToDatabase(empId, score);

        OnBackPressedDispatcher onBackPressedDispatcher = this.getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateToHome();
            }
        });

        ImageView profileImageView = findViewById(R.id.profile);
        profileImageView.setOnClickListener(v -> navigateToUserProfile());
    }


    private void saveScoreToDatabase(String empId, int score) {
        if (empId != null && !empId.isEmpty()) {
            DatabaseReference empRef = databaseReference.child(empId);

            empRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long currentAttempts = 0;
                    long runningSum = 0;

                    if (snapshot.exists()) {
                        if (snapshot.hasChild("Attempts")) {
                            Long attemptsValue = snapshot.child("Attempts").getValue(Long.class);
                            if (attemptsValue != null) {
                                currentAttempts = attemptsValue;
                            }
                        }
                        if (snapshot.hasChild("RunningSum")) {
                            Long runningSumValue = snapshot.child("RunningSum").getValue(Long.class);
                            if (runningSumValue != null) {
                                runningSum = runningSumValue;
                            }
                        }
                    }

                    currentAttempts++;
                    runningSum += score;

                    double average = (double) runningSum / currentAttempts;
                    average = Math.round(average * 100.0) / 100.0; // Round to two decimal places

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("Score", score);
                    updates.put("Attempts", currentAttempts);
                    updates.put("RunningSum", runningSum);
                    updates.put("Average", average);

                    double finalAverage = average;
                    empRef.updateChildren(updates)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(Result.this, "Score and average updated", Toast.LENGTH_SHORT).show();
                                TextView averageScoreView = findViewById(R.id.average_score);
                                averageScoreView.setText(String.format(Locale.getDefault(), "Average Score: %.2f", finalAverage));
                            })
                            .addOnFailureListener(e -> {
                                Log.e("Firebase", "Error updating data: " + e.getMessage());
                                Toast.makeText(Result.this, "Failed to update score and average", Toast.LENGTH_SHORT).show();
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "Database error: " + error.getMessage());
                    Toast.makeText(Result.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(Result.this, "empId is null or empty", Toast.LENGTH_SHORT).show();
            Log.e("ResultActivity", "empId is null or empty in saveScoreToDatabase");
        }
    }

    private void navigateToHome() {
        Intent intent = new Intent(Result.this, HomeActivity.class);
        intent.putExtra("empId", empId);
        startActivity(intent);
        finish();
    }

    private void navigateToUserProfile() {
        Intent intent = new Intent(Result.this, userprofile.class);
        intent.putExtra("empId", empId);
        startActivity(intent);
    }
}



//package com.example.mylogin;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.OnBackPressedCallback;
//import androidx.activity.OnBackPressedDispatcher;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.HashMap;
//import java.util.Locale;
//import java.util.Map;
//
//public class Result extends AppCompatActivity {
//
//    private DatabaseReference databaseReference;
//    private String empId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_result);
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("1ArgO93DDKRv6osLsGyLUS0CdE-XAqDcm0m7L93Do2zc/Sheet1");
//
//        empId = getIntent().getStringExtra("empId");
//        Log.d("ResultActivity", "empId from intent: " + empId);
//
//
//        if (empId == null || empId.isEmpty()) {
//            empId = getSharedPreferences("MyPrefs", MODE_PRIVATE).getString("empId", null);
//            Log.d("ResultActivity", "empId from SharedPreferences: " + empId);
//        }
//
//        if (empId == null || empId.isEmpty()) {
//            Toast.makeText(this, "EmpID is null or empty", Toast.LENGTH_SHORT).show();
//            Log.e("ResultActivity", "EmpID is null or empty");
//            return;
//        }
//
//
//        getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().putString("empId", empId).apply();
//
//        int totalQuestions = getIntent().getIntExtra("totalQuestions", 50);
//        int score = getIntent().getIntExtra("score", 0);
//        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
//        int wrongAnswers = getIntent().getIntExtra("wrongAnswers", 0);
//        int skippedQuestions = getIntent().getIntExtra("skippedQuestions", 0);
//        String timeTaken = getIntent().getStringExtra("timeTaken");
//
//        ProgressBar solvedProgress = findViewById(R.id.progress_solved);
//        ProgressBar skippedProgress = findViewById(R.id.progress_skipped);
//        ProgressBar correctProgress = findViewById(R.id.progress_correct);
//        ProgressBar wrongProgress = findViewById(R.id.progress_wrong);
//        TextView solvedValue = findViewById(R.id.text_solved_count);
//        TextView skippedValue = findViewById(R.id.text_skipped_count);
//        TextView correctValue = findViewById(R.id.text_correct_count);
//        TextView wrongValue = findViewById(R.id.text_wrong_count);
//        TextView totalScore = findViewById(R.id.total_score);
//        TextView timeTakenView = findViewById(R.id.time_taken);
//
//        int maxProgress = 50;
//        solvedProgress.setMax(maxProgress);
//        skippedProgress.setMax(maxProgress);
//        correctProgress.setMax(maxProgress);
//        wrongProgress.setMax(maxProgress);
//        solvedProgress.setProgress((correctAnswers + wrongAnswers) * maxProgress / totalQuestions);
//        skippedProgress.setProgress(skippedQuestions * maxProgress / totalQuestions);
//        correctProgress.setProgress(correctAnswers * maxProgress / totalQuestions);
//        wrongProgress.setProgress(wrongAnswers * maxProgress / totalQuestions);
//        solvedValue.setText(String.valueOf(correctAnswers + wrongAnswers));
//        skippedValue.setText(String.valueOf(skippedQuestions));
//        correctValue.setText(String.valueOf(correctAnswers));
//        wrongValue.setText(String.valueOf(wrongAnswers));
//        totalScore.setText("Total Score: " + score);
//
//        long totalMillis = 20 * 60 * 1000;
//        String[] timeParts = timeTaken.split(":");
//        int minutes = Integer.parseInt(timeParts[0]);
//        int seconds = Integer.parseInt(timeParts[1]);
//        long timeMillis = (minutes * 60 + seconds) * 1000;
//        long remainingMillis = totalMillis - timeMillis;
//        long remainingSeconds = remainingMillis / 1000;
//        long remainingMinutes = remainingSeconds / 60;
//        remainingSeconds %= 60;
//        String remainingTime = String.format(Locale.getDefault(), "%02d:%02d", remainingMinutes, remainingSeconds);
//        timeTakenView.setText("Time Taken: " + remainingTime);
//
//        saveScoreToDatabase(empId, score);
//
//        OnBackPressedDispatcher onBackPressedDispatcher = this.getOnBackPressedDispatcher();
//        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                startActivity(new Intent(Result.this, HomeActivity.class));
//                finish();
//            }
//        });
//
//        ImageView backButton = findViewById(R.id.imageView);
//        TextView backText = findViewById(R.id.text_back);
//        backButton.setOnClickListener(v -> navigateToHome());
//        backText.setOnClickListener(v -> navigateToHome());
//    }
//
//    private void saveScoreToDatabase(String empId, int score) {
//        if (empId != null && !empId.isEmpty()) {
//            DatabaseReference empRef = databaseReference.child(empId);
//
//            empRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    long currentAttempts = 0;
//                    long runningSum = 0;
//
//                    if (snapshot.exists()) {
//                        if (snapshot.hasChild("Attempts")) {
//                            Long attemptsValue = snapshot.child("Attempts").getValue(Long.class);
//                            if (attemptsValue != null) {
//                                currentAttempts = attemptsValue;
//                            }
//                        }
//                        if (snapshot.hasChild("RunningSum")) {
//                            Long runningSumValue = snapshot.child("RunningSum").getValue(Long.class);
//                            if (runningSumValue != null) {
//                                runningSum = runningSumValue;
//                            }
//                        }
//                    }
//
//                    currentAttempts++;
//                    runningSum += score;
//
//                    double average = (double) runningSum / currentAttempts;
//
//                    Map<String, Object> updates = new HashMap<>();
//                    updates.put("Score", score);
//                    updates.put("Attempts", currentAttempts);
//                    updates.put("RunningSum", runningSum);
//                    updates.put("Average", average);
//
//                    empRef.updateChildren(updates)
//                            .addOnSuccessListener(aVoid -> {
//                                Toast.makeText(Result.this, "Score and average updated", Toast.LENGTH_SHORT).show();
//                                TextView averageScoreView = findViewById(R.id.average_score);
//                                averageScoreView.setText(String.format(Locale.getDefault(), "Average Score: %.2f", average));
//                            })
//                            .addOnFailureListener(e -> {
//                                Log.e("Firebase", "Error updating data: " + e.getMessage());
//                                Toast.makeText(Result.this, "Failed to update score and average", Toast.LENGTH_SHORT).show();
//                            });
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Log.e("Firebase", "Database error: " + error.getMessage());
//                    Toast.makeText(Result.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            Toast.makeText(Result.this, "empId is null or empty", Toast.LENGTH_SHORT).show();
//            Log.e("ResultActivity", "empId is null or empty in saveScoreToDatabase");
//        }
//    }
//
//    private void navigateToHome() {
//        Intent intent = new Intent(Result.this, HomeActivity.class);
//        intent.putExtra("empId", empId);
//        startActivity(intent);
//        finish();
//    }
//}