package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Con_Loco extends AppCompatActivity implements View.OnClickListener {
    private CardView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_loco);

        findViewById(R.id.level1).setOnClickListener(this);
        findViewById(R.id.level2).setOnClickListener(this);
        findViewById(R.id.section_wise).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.section_wise:
                i=new Intent(this, Section_wise_con_loco.class);
                startActivity(i);
                break;
            case R.id.level1:
                i=new Intent(this, level1_QB.class);
                i.putExtra("Level", 1);  // Adding the level filter
                i.putExtra("QuestionType", "Conventional");  // Adding the question type filter
                startActivity(i);
                break;
            case R.id.level2:
                i=new Intent(this, level1_QB.class);
                i.putExtra("Level", 2);  // Adding the level filter
                i.putExtra("QuestionType", "Conventional");  // Adding the question type filter
                startActivity(i);
                break;
        }
    }
}
