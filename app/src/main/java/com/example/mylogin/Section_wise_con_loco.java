package com.example.mylogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Section_wise_con_loco extends AppCompatActivity implements View.OnClickListener {
    TextView TM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_wise_con_loco);
        findViewById(R.id.TM).setOnClickListener(this);
        findViewById(R.id.BR).setOnClickListener(this);
        findViewById(R.id.MAINT).setOnClickListener(this);
        findViewById(R.id.STATIC).setOnClickListener(this);
        findViewById(R.id.PPIO).setOnClickListener(this);
        findViewById(R.id.AUX).setOnClickListener(this);
        findViewById(R.id.GENL).setOnClickListener(this);
        findViewById(R.id.TR).setOnClickListener(this);
        findViewById(R.id.ELEX).setOnClickListener(this);
        findViewById(R.id.LAB).setOnClickListener(this);
        findViewById(R.id.PN).setOnClickListener(this);
        findViewById(R.id.HR).setOnClickListener(this);
        findViewById(R.id.MISC).setOnClickListener(this);
        findViewById(R.id.SE).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.TM:i=new Intent(this,level1_QB.class);
                         i.putExtra("Section","TM");
                i.putExtra("QuestionType", "Conventional");

                startActivity(i);
                         break;
            case R.id.BR:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","BR");
                i.putExtra("QuestionType", "Conventional");
                startActivity(i);
                break;
            case R.id.MAINT:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","MAINT");
                i.putExtra("QuestionType", "Conventional");
                startActivity(i);
                break;
            case R.id.STATIC:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","STATIC");
                i.putExtra("QuestionType", "Conventional");
                startActivity(i);
                break;
            case R.id.PPIO:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","PPIO");
                i.putExtra("QuestionType", "Conventional");
                startActivity(i);
                break;
            case R.id.AUX:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","AUX");
                i.putExtra("QuestionType", "Conventional");
                startActivity(i);
                break;
            case R.id.GENL:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","GENL");
                i.putExtra("QuestionType", "Conventional");
                startActivity(i);
                break;
            case R.id.TR:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","TR");
                i.putExtra("QuestionType", "Conventional");
                startActivity(i);
                break;
            case R.id.ELEX:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","ELEX");
                i.putExtra("QuestionType", "Conventional");
                startActivity(i);
                break;
            case R.id.PN:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","PN");
                i.putExtra("QuestionType", "Conventional");
                startActivity(i);
                break;
            case R.id.HR:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","HR");
                i.putExtra("QuestionType", "Conventional");
                startActivity(i);
                break;
            case R.id.SE:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","SE");
                i.putExtra("QuestionType", "Conventional");
                startActivity(i);
                break;
            case R.id.MISC:i=new Intent(this,level1_QB.class);
                i.putExtra("Section","MISC");
                i.putExtra("QuestionType", "Conventional");
                startActivity(i);
                break;


        }

    }
}