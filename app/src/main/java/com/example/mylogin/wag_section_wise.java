package com.example.mylogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class wag_section_wise extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wag_section_wise);
        findViewById(R.id.wag_TM).setOnClickListener(this);
        findViewById(R.id.wag_BR).setOnClickListener(this);
        findViewById(R.id.wag_MAINT).setOnClickListener(this);
        findViewById(R.id.wag_STATIC).setOnClickListener(this);
        findViewById(R.id.wag_PPIO).setOnClickListener(this);
        findViewById(R.id.wag_AUX).setOnClickListener(this);
        findViewById(R.id.wag_GENL).setOnClickListener(this);
        findViewById(R.id.wag_TR).setOnClickListener(this);
        findViewById(R.id.wag_ELEX).setOnClickListener(this);
        findViewById(R.id.wag_LAB).setOnClickListener(this);
        findViewById(R.id.wag_PN).setOnClickListener(this);
        findViewById(R.id.wag_HR).setOnClickListener(this);
        findViewById(R.id.wag_SE).setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.wag_TM:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","TM");
                i.putExtra("QuestionType", "3 Phase / G9");

                startActivity(i);
                break;
            case R.id.wag_BR:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","BR");
                i.putExtra("QuestionType", "3 Phase / G9");
                startActivity(i);
                break;
            case R.id.wag_MAINT:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","MAINT");
                i.putExtra("QuestionType", "3 Phase / G9");
                startActivity(i);
                break;
            case R.id.wag_STATIC:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","STATIC");
                i.putExtra("QuestionType", "3 Phase / G9");
                startActivity(i);
                break;
            case R.id.wag_PPIO:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","PPIO");
                i.putExtra("QuestionType", "3 Phase / G9");
                startActivity(i);
                break;
            case R.id.wag_AUX:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","AUX");
                i.putExtra("QuestionType", "3 Phase / G9");
                startActivity(i);
                break;
            case R.id.wag_GENL:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","GENL");
                i.putExtra("QuestionType", "3 Phase / G9");
                startActivity(i);
                break;
            case R.id.wag_TR:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","TR");
                i.putExtra("QuestionType", "3 Phase / G9");
                startActivity(i);
                break;
            case R.id.wag_ELEX:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","ELEX");
                i.putExtra("QuestionType", "3 Phase / G9");
                startActivity(i);
                break;
            case R.id.wag_PN:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","PN");
                i.putExtra("QuestionType", "3 Phase / G9");
                startActivity(i);
                break;
            case R.id.wag_HR:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","HR");
                i.putExtra("QuestionType", "3 Phase / G9");
                startActivity(i);
                break;
            case R.id.wag_SE:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","SE");
                i.putExtra("QuestionType", "3 Phase / G9");
                startActivity(i);
                break;


        }

    }
}