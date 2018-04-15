package com.msquared.physicskinematics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class GatheringData extends AppCompatActivity implements Orientation.Listener
{
    
    private Orientation mOrientation;
    float[] dataMaxMin = {0,0};
    double _distance;
    String _string;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gathering_data);
    
        mOrientation = new Orientation(this);

        Intent intent = getIntent();
        Bundle b = this.getIntent().getExtras();
        double[] array = b.getDoubleArray("key");
        _string = b.getString("string");
        _distance = array[0];
        TextView distanceTextView = findViewById(R.id.textView4);
        distanceTextView.setText("Distance: " + _distance + " " + _string);
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
    }
    
    public void onBigButtonChanged(View view)
    {
        if(dataMaxMin[0] == 0)
        {
            dataMaxMin[0] = mOrientation.getRoll();
            Log.d("Data","Data = " + dataMaxMin[0] + " " + dataMaxMin[1]);
            return;
        }
        dataMaxMin[1] = mOrientation.getRoll();
        Log.d("Data","Data = " + dataMaxMin[0] + " " + dataMaxMin[1]);

        Intent myIntent2 = new Intent(this, Results.class);
        Bundle c = new Bundle();
        c.putDoubleArray("key", new double[]{_distance, dataMaxMin[0], dataMaxMin[1]});
        c.putString("string", _string);
        myIntent2.putExtras(c);
        startActivity(myIntent2);
        // TODO: Add the stuff that makes it go to the text activity and pass the dataMaxMin variable
    }
}
