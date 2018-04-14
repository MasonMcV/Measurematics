package com.msquared.physicskinematics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Orientation.Listener
{
    
    //TextView tv= findViewById(R.id.rotationVector);
    //TextView gameDataRotation = findViewById(R.id.gameRotation);
    //TextView gyroText;
    
    private Orientation mOrientation;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    
        mOrientation = new Orientation(this);
        
        //mOrientation.startListening(this);
        
    }
    
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
}
