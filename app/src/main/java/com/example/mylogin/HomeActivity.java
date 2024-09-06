package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView full_mock, con_loco, wag_9,hindi,video,profile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Initialize CardViews
        full_mock = findViewById(R.id.full_mock);
        con_loco = findViewById(R.id.con_loco);
        wag_9 = findViewById(R.id.wag9);
        hindi = findViewById(R.id.hindi);
        video = findViewById(R.id.video);
        profile=findViewById(R.id.userprofile);

        // Set OnClickListener for CardViews
        full_mock.setOnClickListener(this);
        con_loco.setOnClickListener(this);
        wag_9.setOnClickListener(this);
        hindi.setOnClickListener(this);
        video.setOnClickListener(this);
        profile.setOnClickListener(this);

        // Set OnClickListener for userprofile ImageView
        CardView userprofileImageView = findViewById(R.id.userprofile);
        userprofileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, userprofile.class);
                String empId = getIntent().getStringExtra("empId");
                intent.putExtra("empId", empId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.full_mock:
                intent = new Intent(this, MainActivity2.class);
                intent.putExtra("empId", getIntent().getStringExtra("empId"));
                startActivity(intent);
                break;
            case R.id.con_loco:
                intent = new Intent(this, Con_Loco.class);
                startActivity(intent);
                break;
            case R.id.wag9:
                intent = new Intent(this, Wag.class);
                startActivity(intent);
                break;

            case R.id.video:
                intent = new Intent(this, video.class);
                startActivity(intent);
                break;

            case R.id.hindi:
                intent = new Intent(this, level1_QB.class);
                intent.putExtra("Section","hindi");
                intent.putExtra("QuestionType", "hindi");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();  // This ensures all activities are finished and the app exits
    }
}
