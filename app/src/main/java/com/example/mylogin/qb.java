package com.example.mylogin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class qb extends AppCompatActivity {
     MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qb);
        myAdapter.clearSelections();

    }
}