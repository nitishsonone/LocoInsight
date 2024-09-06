package com.example.mylogin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mcq extends AppCompatActivity {

    private static final String TAG = "mcq";

    private TextView questionNumber, remainingTime, questionHindi, questionEnglish;
    private RadioGroup optionsRadioGroup;
    private RadioButton option1, option2, option3, option4, markForReviewRadioButton;
    private Button previousButton, nextButton, submitButton;
    private List<Question> questionList = new ArrayList<>();
    private Map<Integer, Boolean> markedForReview = new HashMap<>();
    private Map<Integer, String> selectedOptions = new HashMap<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private int skippedQuestions = 0;
    private CountDownTimer timer;
    private static final long TIMER_DURATION = 120 * 60 * 1000;

    // Drawer components
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private LinearLayout navButtonsContainer;
    private boolean isTimerEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcql2);
        String empId = getIntent().getStringExtra("empId");
        isTimerEnabled = getIntent().getBooleanExtra("timer", true);
        initializeUIComponents();
        fetchQuestionsFromFirebase();
        setupButtonListeners();
        if (isTimerEnabled) {
            startTimer();
        } else {
            remainingTime.setVisibility(View.GONE); // Hide the timer TextView
        }
    }


    private void initializeUIComponents() {
        questionNumber = findViewById(R.id.question_number);
        remainingTime = findViewById(R.id.remaining_time);
        questionHindi = findViewById(R.id.question_hindi);
        questionEnglish = findViewById(R.id.question_english);
        optionsRadioGroup = findViewById(R.id.options_radio_group);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        previousButton = findViewById(R.id.previous_button);
        nextButton = findViewById(R.id.next_button);
        submitButton = findViewById(R.id.submit_button);
        markForReviewRadioButton = findViewById(R.id.mark_for_review_radio_button);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navButtonsContainer = findViewById(R.id.nav_buttons_container);

        Button openDrawerButton = findViewById(R.id.open_drawer_button);
        openDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView);
                populateNavigationButtons();
            }
        });
        populateNavigationButtons();

        // Check if any view is null after initialization
        if (questionNumber == null) Log.e(TAG, "questionNumber is null");
        if (remainingTime == null) Log.e(TAG, "remainingTime is null");
        if (questionHindi == null) Log.e(TAG, "questionHindi is null");
        if (questionEnglish == null) Log.e(TAG, "questionEnglish is null");
        if (optionsRadioGroup == null) Log.e(TAG, "optionsRadioGroup is null");
        if (option1 == null) Log.e(TAG, "option1 is null");
        if (option2 == null) Log.e(TAG, "option2 is null");
        if (option3 == null) Log.e(TAG, "option3 is null");
        if (option4 == null) Log.e(TAG, "option4 is null");
        if (previousButton == null) Log.e(TAG, "previousButton is null");
        if (nextButton == null) Log.e(TAG, "nextButton is null");
        if (submitButton == null) Log.e(TAG, "submitButton is null");

        if (questionNumber == null || remainingTime == null || questionHindi == null ||
                questionEnglish == null || optionsRadioGroup == null || option1 == null ||
                option2 == null || option3 == null || option4 == null || previousButton == null ||
                nextButton == null || submitButton == null) {
            Log.e(TAG, "One or more views are null after initialization");
            Toast.makeText(this, "Failed to initialize UI components", Toast.LENGTH_SHORT).show();
            finish(); // Close activity to prevent further issues
        }
    }

    //    private void populateNavigationButtons() {
//        navButtonsContainer.removeAllViews(); // Clear existing views before adding new ones
//
//        for (int i = 0; i < questionList.size(); i++) {
//            Button questionButton = new Button(this);
//            questionButton.setText("" + (i + 1));
//            questionButton.setTag(i);
//            questionButton.setOnClickListener(v -> {
//                int index = (int) v.getTag();
//                currentQuestionIndex = index;
//                displayQuestion();
//                drawerLayout.closeDrawer(navigationView);
//            });
//            navButtonsContainer.addView(questionButton);
//        }
//    }
    private void populateNavigationButtons() {
        navButtonsContainer.removeAllViews(); // Clear existing views before adding new ones

        // Calculate number of rows required
        int numRows = (int) Math.ceil(questionList.size() / 3.0);
        TextView legendSolved = new TextView(this);
        legendSolved.setText("Solved");
        legendSolved.setTextSize(18);
        legendSolved.setGravity(Gravity.CENTER);
        legendSolved.setBackgroundResource(R.drawable.legent__blue_background);
        navButtonsContainer.addView(legendSolved);

        View space = new View(this);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                16 // Adjust spacing as needed
        ));
        navButtonsContainer.addView(space);

        TextView legendUnsolved = new TextView(this);
        legendUnsolved.setText("Unsolved");
        legendUnsolved.setTextSize(18);
        legendUnsolved.setGravity(Gravity.CENTER);
        legendUnsolved.setBackgroundColor(Color.GRAY);
        navButtonsContainer.addView(legendUnsolved);

        space = new View(this);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                16 // Adjust spacing as needed
        ));
        navButtonsContainer.addView(space);

        TextView legendMarkedForReview = new TextView(this);
        legendMarkedForReview.setText("Marked for Review");
        legendMarkedForReview.setTextSize(18);
        legendMarkedForReview.setGravity(Gravity.CENTER);
        legendMarkedForReview.setBackgroundResource(R.drawable.legend_green_background);
        navButtonsContainer.addView(legendMarkedForReview);

        // Add spacing between legend and buttons
        space = new View(this);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                16 // Adjust spacing as needed
        ));
        navButtonsContainer.addView(space);

        for (int i = 0; i < numRows; i++) {
            // Create a new horizontal LinearLayout for each row
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            // Add 3 buttons to the current row, or fewer if it's the last row
            int buttonsInThisRow = Math.min(3, questionList.size() - i * 3);

            for (int j = 0; j < buttonsInThisRow; j++) {
                int index = i * 3 + j;
                Button questionButton = createNavigationButton(index);
                rowLayout.addView(questionButton);
            }

            // Add the current row to the container
            navButtonsContainer.addView(rowLayout);

            // Add spacing between rows if needed
            if (i < numRows - 1) {
                space = new View(this);
                space.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        16 // Adjust spacing as needed
                ));
                navButtonsContainer.addView(space);
            }
        }
    }

    private Button createNavigationButton(int index) {
        Button questionButton = new Button(this);
        questionButton.setText("" + (index + 1));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, // Width is 0 initially
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f // Weight 1 for equal distribution
        );
        params.setMarginEnd(8); // Margin between buttons
        questionButton.setLayoutParams(params);
        if(markedForReview.containsKey(index) && markedForReview.get(index)){
            questionButton.setBackgroundResource(R.drawable.legend_green_background);
        }else if(selectedOptions.containsKey(index)){
            questionButton.setBackgroundResource(R.drawable.legent__blue_background);
        }
        questionButton.setOnClickListener(v -> {
            currentQuestionIndex = index;
            displayQuestion();
            drawerLayout.closeDrawer(navigationView);
        });
        // Determine button background based on question status
        return questionButton;
    }



    private void setupButtonListeners() {
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex > 0) {
                    saveSelectedOption();
                    currentQuestionIndex--;
                    displayQuestion();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex < questionList.size() - 1) {
                    saveSelectedOption();
                    currentQuestionIndex++;
                    displayQuestion();
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSelectedOption();
                showSubmitConfirmationDialog();
            }
        });

        markForReviewRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the mark for review state
                boolean isMarked = !markedForReview.getOrDefault(currentQuestionIndex, false);
                markedForReview.put(currentQuestionIndex, isMarked);
                markForReviewRadioButton.setChecked(isMarked);

            }
        });
    }
    private void showSubmitConfirmationDialog() {
        // Create a dialog instance
        Dialog dialog = new Dialog(mcq.this);
        dialog.setContentView(R.layout.dialog_confirm_submit);
        dialog.setCancelable(true);

        // Initialize dialog views
        TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
        Button yesButton = dialog.findViewById(R.id.yes_button);
        Button noButton = dialog.findViewById(R.id.no_button);

        // Get user's name from intent extras
        String empName = getIntent().getStringExtra("empName");

        // Set dialog message with user's name
        if (empName != null && !empName.isEmpty()) {
            dialogMessage.setText(empName + ", Do you really want to submit?");
        } else {
            dialogMessage.setText("Do you really want to submit?");
        }

        // Set onClickListener for Yes button
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // Proceed to result activity
                calculateScore();
                Intent intent = new Intent(mcq.this, Result.class);
                intent.putExtra("score", score);
                intent.putExtra("totalQuestions", questionList.size());
                intent.putExtra("correctAnswers", correctAnswers);
                intent.putExtra("wrongAnswers", wrongAnswers);
                intent.putExtra("skippedQuestions", skippedQuestions);
                String [] currTime = remainingTime.getText().toString().split(":");
                long hours  = Long.parseLong(currTime[0]);
                long minutes  = Long.parseLong(currTime[1]);
                long seconds  = Long.parseLong(currTime[2]);
                long secondsRemaining =  (hours * 3600) + (minutes * 60) + seconds;
                long timeTakenSeconds = TIMER_DURATION-secondsRemaining;
                hours = timeTakenSeconds / (1000 * 60 * 60);
                minutes = (timeTakenSeconds / (1000 * 60)) % 60;
                seconds = (timeTakenSeconds / 1000) % 60;

                intent.putExtra("timeTaken", remainingTime.getText().toString());
                intent.putExtra("empId", getIntent().getStringExtra("empId")); // pass empId
                startActivity(intent);
            }
        });

        // Set onClickListener for No button
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // Stay on current activity
            }
        });

        // Show dialog
        dialog.show();
    }

    private void startTimer() {
        timer = new CountDownTimer(TIMER_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hours = millisUntilFinished / (1000 * 60 * 60);
                long minutes = (millisUntilFinished / (1000 * 60)) % 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                remainingTime.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            }

            @Override
            public void onFinish() {
                submitButton.performClick(); // Automatically submit when timer finishes
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel(); // Cancel timer to avoid memory leaks
        }
    }

    private void fetchQuestionsFromFirebase() {
        ProgressDialog progressDialog = new ProgressDialog(mcq.this);
        progressDialog.setMessage("Loading questions...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.d(TAG, "Starting to fetch questions from Firebase");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("1pDK-bQKBrZS1xNzWjv5uOR2gm9cnlhM9qjCMPSFZN2g/Sheet2");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "DataSnapshot received");

                List<Question> level1Questions = new ArrayList<>();
                List<Question> level3Questions = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = deserializeQuestion(snapshot);
                    if (question != null) {
                        Log.d(TAG, "Fetched question: " + question.getQuestion());

                        if (question.getLevel() == 1) {
                            level1Questions.add(question);
                            Log.d(TAG, "Added to level 2 questions: " + question.getQuestion());
                        } else if (question.getLevel() == 3) {
                            level3Questions.add(question);
                            Log.d(TAG, "Added to level 3 questions: " + question.getQuestion());
                        }
                    } else {
                        Log.e(TAG, "Failed to deserialize question from snapshot");
                    }
                }

                Log.d(TAG, "Shuffling and trimming questions");

                Collections.shuffle(level1Questions);
                if (level1Questions.size() > 100) {
                    level1Questions = level1Questions.subList(0, 100);
                }

                Collections.shuffle(level3Questions);
                if (level3Questions.size() > 10) {
                    level3Questions = level3Questions.subList(0, 10);
                }

                questionList.clear();
                questionList.addAll(level1Questions);
                questionList.addAll(level3Questions);

                Log.d(TAG, "Final question list size: " + questionList.size());

                displayQuestion();
                progressDialog.dismiss();

                populateNavigationButtons();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mcq.this, "Failed to fetch questions", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }


//    private void fetchQuestionsFromFirebase() {
//        ProgressDialog progressDialog = new ProgressDialog(mcql2.this);
//        progressDialog.setMessage("Loading questions...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        Log.d(TAG, "Starting to fetch questions from Firebase");
//
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("1ArgO93DDKRv6osLsGyLUS0CdE-XAqDcm0m7L93Do2zc/Sheet2");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d(TAG, "DataSnapshot received");
//
//                List<Question> level2Questions = new ArrayList<>();
//                List<Question> level3Questions = new ArrayList<>();
//                int counter = 0;
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Question question = deserializeQuestion(snapshot);
//                    if (question != null) {
//                        Log.d(TAG, "Fetched question: " + question.getQuestion());
//                        counter++;
//
//                        if (counter >= 1 && counter <= 100 && question.getLevel() == 2) {
//                            level2Questions.add(question);
//                            Log.d(TAG, "Added to level 2 questions: " + question.getQuestion());
//                        } else if (counter >= 101 && counter <= 110 && question.getLevel() == 3) {
//                            level3Questions.add(question);
//                            Log.d(TAG, "Added to level 3 questions: " + question.getQuestion());
//                        }
//                    } else {
//                        Log.e(TAG, "Failed to deserialize question from snapshot");
//                    }
//                }
//
//                Log.d(TAG, "Shuffling and trimming questions");
//
//                Collections.shuffle(level2Questions);
//                if (level2Questions.size() > 100) {
//                    level2Questions = level2Questions.subList(0, 100);
//                }
//
//                Collections.shuffle(level3Questions);
//                if (level3Questions.size() > 10) {
//                    level3Questions = level3Questions.subList(0, 10);
//                }
//
//                questionList.clear();
//                questionList.addAll(level2Questions);
//                questionList.addAll(level3Questions);
//
//                Log.d(TAG, "Final question list size: " + questionList.size());
//
//                displayQuestion();
//                progressDialog.dismiss();
//
//                populateNavigationButtons();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(mcql2.this, "Failed to fetch questions", Toast.LENGTH_SHORT).show();
//                Log.e(TAG, "Database error: " + databaseError.getMessage());
//            }
//        });
//    }





    private void displayQuestion() {
        if (questionList.isEmpty() || currentQuestionIndex >= questionList.size()) {
            Log.e(TAG, "No questions to display or current index out of bounds");
            return;
        }
        Question question = questionList.get(currentQuestionIndex);
        Log.d(TAG, "Displaying question: " + question.getQuestion());
        questionNumber.setText((currentQuestionIndex + 1) + "/110");
        String serialNumber = (currentQuestionIndex + 1) + ". ";
        questionHindi.setText(serialNumber + question.getQuestion());
        questionEnglish.setText(serialNumber + question.getQuestionEnglish());
        option1.setText(question.getA());
        option2.setText(question.getB());
        option3.setText(question.getC());
        option4.setText(question.getD());
        optionsRadioGroup.clearCheck();

        if (selectedOptions.containsKey(currentQuestionIndex)) {
            String selectedOption = selectedOptions.get(currentQuestionIndex);
            switch (selectedOption) {
                case "A":
                    option1.setChecked(true);
                    break;
                case "B":
                    option2.setChecked(true);
                    break;
                case "C":
                    option3.setChecked(true);
                    break;
                case "D":
                    option4.setChecked(true);
                    break;
                default:
                    Log.e(TAG, "Invalid selected option: " + selectedOption);
            }
            Log.d(TAG, "Restored selected option for question " + currentQuestionIndex + ": " + selectedOption);
        }
        boolean isMarkedForReview = markedForReview.getOrDefault(currentQuestionIndex, false);
        markForReviewRadioButton.setChecked(isMarkedForReview);
    }

    private void saveSelectedOption() {
        int selectedId = optionsRadioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            String selectedOption = null;
            switch (selectedId) {
                case R.id.option_1:
                    selectedOption = "A";
                    break;
                case R.id.option_2:
                    selectedOption = "B";
                    break;
                case R.id.option_3:
                    selectedOption = "C";
                    break;
                case R.id.option_4:
                    selectedOption = "D";
                    break;
                default:
                    Log.e(TAG, "Invalid selected radio button ID: " + selectedId);
            }
            if (selectedOption != null) {
                selectedOptions.put(currentQuestionIndex, selectedOption);
                Log.d(TAG, "Saved selected option for question " + currentQuestionIndex + ": " + selectedOption);
            }
        }

    }


    private void calculateScore() {
        score = 0;
        correctAnswers = 0;
        wrongAnswers = 0;
        skippedQuestions = 0;

        for (int i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);
            if (selectedOptions.containsKey(i)) {
                String selectedOption = selectedOptions.get(i);
                String correctAnswer = question.getCorrect();

                Log.d(TAG, "Question " + (i + 1) + ": Selected: " + selectedOption + ", Correct: " + correctAnswer);

                if (correctAnswer != null && selectedOption != null && selectedOption.equals(correctAnswer)) {
                    score++;
                    correctAnswers++;
                } else {
                    wrongAnswers++;
                }
            } else {
                skippedQuestions++;
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private Question deserializeQuestion(DataSnapshot snapshot) {
        try {
            Map<String, Object> data = snapshot.getValue(new GenericTypeIndicator<Map<String, Object>>() {});
            if (data == null) {
                Log.e(TAG, "Snapshot data is null");
                return null;
            }

            Question question = new Question();
            question.setA((String) data.get("A"));
            question.setB((String) data.get("B"));
            question.setC((String) data.get("C"));
            question.setCorrect((String) data.get("Correct"));
            question.setD((String) data.get("D"));

            Object levelObj = data.get("Level");
            question.setLevel(levelObj != null ? ((Number) levelObj).intValue() : 0);

            question.setQuestion((String) data.get("Question"));
            question.setQuestionTypeConventional3PhaseG9((String) data.get("QuestionType"));
            question.setQuestionEnglish((String) data.get("QuestionEnglish"));
            question.setSection((String) data.get("Section"));

            Object srNoObj = data.get("Sr No");
            question.setSrNo(srNoObj != null ? ((Number) srNoObj).intValue() : 0);

            question.setTopic((String) data.get("Topic"));

            return question;
        } catch (Exception e) {
            Log.e(TAG, "Failed to deserialize Question: " + e.getMessage());
            return null;
        }

    }


    @Override
    public void onBackPressed() {        // Override onBackPressed to handle back navigation

        Dialog dialog = new Dialog(mcq.this);
        dialog.setContentView(R.layout.dialog_confirm_submit);
        dialog.setCancelable(true);

        // Initialize dialog views
        TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
        Button yesButton = dialog.findViewById(R.id.yes_button);
        Button noButton = dialog.findViewById(R.id.no_button);

        // Get user's name from intent extras
        String empName = getIntent().getStringExtra("empName");
        String empId = getIntent().getStringExtra("empId");

        // Set dialog message with user's name
        if (empName != null && !empName.isEmpty()) {
            dialogMessage.setText(empName + ", Do you really want to Exam?");
        } else {
            dialogMessage.setText("Do you really want to Quit Exam?");
        }

        // Set onClickListener for Yes button
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcq.this, HomeActivity.class);
                i.putExtra("empId", empId);
                startActivity(i);
            }
        });

        // Set onClickListener for No button
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // Stay on current activity
            }
        });

        // Show dialog
        dialog.show();
    }
}