package com.msquared.physicskinematics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

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
        Bundle b = new Bundle();
        EditText editText = (EditText) findViewById(R.id.editText);
        Spinner mySpinner = findViewById(R.id.spinner);
        
        
        String message = editText.getText().toString();
        
        double distance = 1.0;
        if(!message.matches("")) distance = Double.parseDouble(message);
        b.putDoubleArray("key", new double[]{distance});
        b.putString("string",mySpinner.getSelectedItem().toString());
        Intent myIntent = new Intent(this, GatheringData.class);
        myIntent.putExtras(b);
        startActivity(myIntent);
    }
}
