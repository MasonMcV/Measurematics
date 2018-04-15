package com.msquared.physicskinematics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Results extends AppCompatActivity {

    double _distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent2 = getIntent();
        Bundle c = this.getIntent().getExtras();
        double [] array=c.getDoubleArray("key");
        _distance = array[0];
    }

    public void onButtonRecalculate(View view) {
        Bundle d = new Bundle();
        d.putDoubleArray("key", new double[]{_distance});
        Intent myIntent3 = new Intent(this, GatheringData.class);
        myIntent3.putExtras(d);
        startActivity(myIntent3);
    }

    public void onButtonReturnHome(View view) {
        Intent myIntent4 = new Intent(this, MainActivity.class);
        startActivity(myIntent4);
    }
}
