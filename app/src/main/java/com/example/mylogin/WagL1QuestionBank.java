package com.example.mylogin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class WagL1QuestionBank extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wag_l1_question_bank);
        recyclerView = findViewById(R.id.questionlist);
        databaseReference = FirebaseDatabase.getInstance().getReference("1pDK-bQKBrZS1xNzWjv5uOR2gm9cnlhM9qjCMPSFZN2g/Sheet2");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        questions = new ArrayList<>();
        myAdapter = new MyAdapter(this, questions);
        recyclerView.setAdapter(myAdapter);

        Button showAnswerButton = findViewById(R.id.showans);
        Button hideAnswerButton = findViewById(R.id.hideans);

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.setShowAnswer(true);
                myAdapter.notifyDataSetChanged();
            }
        });

        hideAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.setShowAnswer(false);
                myAdapter.notifyDataSetChanged();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questions.clear();  // Clear the list to avoid duplicates
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    try {
                        if (snapshot1.exists()) {
                            Question q = deserializeQuestion(snapshot1);
                            if(q.getLevel()==1 && !q.getQuestionTypeConventional3PhaseG9().equals("Conventional"))
                                questions.add(q);
                            Log.d("TAG", "Question fetched: " + q.getQuestion());
                        }
                    } catch (Exception e) {
                        Log.e("TAG", "Failed to deserialize question: " + e.getMessage());
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Database error: " + error.getMessage());
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private Question deserializeQuestion(DataSnapshot snapshot) throws DatabaseException {
        try {
            Map<String, Object> data = snapshot.getValue(new GenericTypeIndicator<Map<String, Object>>() {});
            if (data == null) {
                throw new DatabaseException("Snapshot is null");
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
            throw new DatabaseException("Failed to deserialize Question: " + e.getMessage());
        }
    }
}
