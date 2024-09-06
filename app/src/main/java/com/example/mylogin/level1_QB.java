package com.example.mylogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class level1_QB extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Question> questions;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_qb);

        recyclerView = findViewById(R.id.ques_list);
        textView = findViewById(R.id.textView2);
        database = FirebaseDatabase.getInstance().getReference("1pDK-bQKBrZS1xNzWjv5uOR2gm9cnlhM9qjCMPSFZN2g/Sheet2");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        questions = new ArrayList<>();
        myAdapter = new MyAdapter(this, questions);
        myAdapter.clearSelections();

        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questions.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    try {
                        if (snapshot1.exists()) {
                            Question q = deserializeQuestion(snapshot1);
                            questions.add(q);
                            Log.d("TAG", "Question fetched: " + q.getQuestion());
                        }
                    } catch (Exception e) {
                        Log.e("TAG", "Failed to deserialize question: " + e.getMessage());
                    }
                }
                myAdapter.notifyDataSetChanged();
                Intent intent = getIntent();
                if (intent != null && intent.hasExtra("Section") && intent.hasExtra("QuestionType")) {
                    String section = intent.getStringExtra("Section");
                    String questionType = intent.getStringExtra("QuestionType");
                    if(questionType.equals("Conventional")){
                        textView.setText(section+" SECTION QUESTION BANK \n( Conventional Loco)");
                    }else if (questionType.equals("hindi")) {
                        textView.setText("राजभाषा हिंदी प्रश्न संचय ");
                    }
                    else{
                        textView.setText(section+" SECTION QUESTION BANK\n ( 3 PHASE / WAG 9 Loco)");
                    }
                    myAdapter.filterBySection(section,questionType);
                }
                Intent intent1 = getIntent();
                if (intent1 != null && intent1.hasExtra("Level") && intent1.hasExtra("QuestionType")) {
                    int level = intent1.getIntExtra("Level", 1);
                    String questionType = intent1.getStringExtra("QuestionType");

                    if(questionType.equals("Conventional")){
                        textView.setText("CONVENTIONAL LOCO \nQUESTION BANK (LEVEL "+level+")");
                    }else{
                        textView.setText("3 PHASE / WAG 9 \nLOCO QUESTION BANK (LEVEL "+level+")");
                    }
                    myAdapter.filterByLevelAndQuestionType(level, questionType);
                } else {
                    myAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Database error: " + error.getMessage());
            }
        });

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.setShowAnswer(true);
                myAdapter.notifyDataSetChanged();
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.setShowAnswer(false);
                myAdapter.notifyDataSetChanged();
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
            question.setCorrect(getStringFromObject(data.get("Correct")));
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

    private String getStringFromObject(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }
}
