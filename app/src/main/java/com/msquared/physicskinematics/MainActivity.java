package com.msquared.physicskinematics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Orientation.Listener
{
    private Orientation mOrientation;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        
        
        mOrientation = new Orientation(this);
        
        final RelativeLayout layout = findViewById(R.id.layout);
        layout.setOnTouchListener(touchListener);
        
        //mOrientation.startListening(this);
        
    }
    
    
    View.OnTouchListener touchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent)
        {
            TextView tappedView = (TextView) findViewById(R.id.tapped);
            tappedView.setText(String.format(Locale.ENGLISH,"Orientation at tap: \n%f",mOrientation.getRoll()));
            return false;
        }
    };
    
    @Override
    protected void onStart() {
        super.onStart();
        mOrientation.startListening(this);
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        mOrientation.stopListening();
    }
    
    @Override
    public void onOrientationChanged(float pitch, float roll)
    {
        TextView text = (TextView) findViewById(R.id.gameRotation);
        Log.d("Data",("New Data:"+ pitch + " " + roll));
        text.setText(String.format(Locale.ENGLISH,"New Data: \n%f\n%f", pitch,roll));
    }
    public void onButtonClick(View view){
    Intent myIntent = new Intent(this, GatheringData.class);
    startActivity(myIntent);
    }
}
