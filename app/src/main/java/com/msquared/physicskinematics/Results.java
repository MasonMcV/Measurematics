package com.msquared.physicskinematics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent2 = getIntent();
    }

    public void onButtonRecalculate(View view) {
        Intent myIntent3 = new Intent(this, GatheringData.class);
        startActivity(myIntent3);
    }

    public void onButtonReturnHome(View view) {
        Intent myIntent4 = new Intent(this, MainActivity.class);
        startActivity(myIntent4);
    }
}
