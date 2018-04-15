package com.msquared.physicskinematics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.round;
import static java.lang.Math.tan;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

public class Results extends AppCompatActivity {

    double _distance;
    String _unit;
    ImageView drawingImageView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent2 = getIntent();
        Bundle c = this.getIntent().getExtras();
        double[] array=c.getDoubleArray("key");
        _unit = c.getString("string");
        _distance = array[0];
        
        double height = round(getHeight(array[1], array[2])*10)/10.0;
        double groundTheta = round(getGroundTheta(height)*10)/10.0;
    
        TextView heightView = (TextView) findViewById(R.id.heightText);
        TextView distanceView = (TextView) findViewById(R.id.distanceText);
        TextView thetaView = (TextView) findViewById(R.id.thetaText);
        
        heightView.setText(height + " " + _unit);
        distanceView.setText(_distance + " " + _unit);
        thetaView.setText(groundTheta + "°");
        
        displayScreen();
    }
    
    public void onButtonRecalculate(View view) {
        Bundle d = new Bundle();
        d.putDoubleArray("key", new double[]{_distance});
        d.putString("string", _unit);
        Intent myIntent3 = new Intent(this, GatheringData.class);
        myIntent3.putExtras(d);
        startActivity(myIntent3);
    }

    public void onButtonReturnHome(View view) {
        Intent myIntent4 = new Intent(this, MainActivity.class);
        startActivity(myIntent4);
    }
    
    private double getHeight(double theta1, double theta2)
    {
        double height1 = tan(toRadians(abs(90-abs(theta1))))*_distance;
        double height2 = tan(toRadians(abs(90-abs(theta2))))*_distance;
        return height1 + height2;
    }
    
    private double getGroundTheta(double height)
    {
        double theta = toDegrees(atan((height/_distance)));
        return theta;
    }
    
    public void displayScreen() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
    
        drawingImageView = (ImageView) this.findViewById(R.id.triangleImage);
        Bitmap bitmap = Bitmap.createBitmap((int) screenWidth, (int) screenHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawingImageView.setImageBitmap(bitmap);
    
        TextView heightView = (TextView) findViewById(R.id.heightText);
        TextView distanceView = (TextView) findViewById(R.id.distanceText);
        TextView thetaView = (TextView) findViewById(R.id.thetaText);
        
        Point point1 = new Point(screenWidth/10, (int)round(screenHeight*5.75/10));
        Point point2 = new Point((int)round(screenWidth*8.5/10), (int)round(screenHeight*5.75/10));
        Point point3 = new Point((int)round(screenWidth*8.5/10), screenHeight*3/10);
    
    
        // Line
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);
        canvas.drawLine(point1.x, point1.y, point2.x, point2.y, paint);
        canvas.drawLine(point2.x, point2.y, point3.x, point3.y, paint);
        canvas.drawLine(point3.x, point3.y, point1.x, point1.y, paint);
        
    }
}
