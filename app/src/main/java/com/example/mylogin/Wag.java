package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Wag extends AppCompatActivity implements View.OnClickListener {
    private CardView l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wag);
        findViewById(R.id.levelone).setOnClickListener(this);
        findViewById(R.id.waglevel2).setOnClickListener(this);
        findViewById(R.id.wagsection_wise).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.wagsection_wise:
                i=new Intent(this, wag_section_wise.class);
                startActivity(i);
                break;
            case R.id.levelone:
                i=new Intent(this, level1_QB.class);
                i.putExtra("Level", 1);  // Adding the level filter
                i.putExtra("QuestionType", "3 Phase / G9");  // Adding the question type filter
                startActivity(i);
                break;
            case R.id.waglevel2:
                i=new Intent(this, level1_QB.class);
                i.putExtra("Level", 2);  // Adding the level filter
                i.putExtra("QuestionType", "3 Phase / G9");  // Adding the question type filter
                startActivity(i);
                break;
        }
    }


}