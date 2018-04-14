package com.msquared.physicskinematics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    private Orientation mOrientation;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent3 = getIntent();
        
        mOrientation = new Orientation(this);
        
        //mOrientation.startListening(this);
    }
    
    
    public void onButtonClick(View view){
        Intent myIntent = new Intent(this, GatheringData.class);
        startActivity(myIntent);
    }
}
