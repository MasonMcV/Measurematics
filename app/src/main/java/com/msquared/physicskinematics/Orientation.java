/*
Copyright (c) 2013 Keith Platfoot

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.msquared.physicskinematics;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.view.Surface;
import android.view.WindowManager;

public class Orientation implements SensorEventListener {
    
    public interface Listener {
        void onOrientationChanged(float pitch, float roll);
    }
    
    private static final int SENSOR_DELAY_MICROS = 16 * 1000; // 16ms
    
    private final WindowManager mWindowManager;
    
    private final SensorManager mSensorManager;
    
    @Nullable
    private final Sensor mRotationSensor;
    
    private int mLastAccuracy;
    private Listener mListener;
    
    public Orientation(Activity activity) {
        mWindowManager = activity.getWindow().getWindowManager();
        mSensorManager = (SensorManager) activity.getSystemService(Activity.SENSOR_SERVICE);
        
        // Can be null if the sensor hardware is not available
        mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }
    
    public void startListening(Listener listener) {
        if (mListener == listener) {
            return;
        }
        mListener = listener;
        if (mRotationSensor == null) {
            //LogUtil.w("Rotation vector sensor not available; will not provide orientation data.");
            return;
        }
        mSensorManager.registerListener(this, mRotationSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }
    
    public void stopListening() {
        mSensorManager.unregisterListener(this);
        mListener = null;
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (mLastAccuracy != accuracy) {
            mLastAccuracy = accuracy;
        }
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mListener == null) {
            return;
        }
        if (mLastAccuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            return;
        }
        if (event.sensor == mRotationSensor) {
            updateOrientation(event.values);
        }
    }
    
    @SuppressWarnings("SuspiciousNameCombination")
    private void updateOrientation(float[] rotationVector) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector);
        
        final int worldAxisForDeviceAxisX;
        final int worldAxisForDeviceAxisY;
        
        // Remap the axes as if the device screen was the instrument panel,
        // and adjust the rotation matrix for the device orientation.
        switch (mWindowManager.getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
            default:
                worldAxisForDeviceAxisX = SensorManager.AXIS_X;
                worldAxisForDeviceAxisY = SensorManager.AXIS_Z;
                break;
            case Surface.ROTATION_90:
                worldAxisForDeviceAxisX = SensorManager.AXIS_Z;
                worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_X;
                break;
            case Surface.ROTATION_180:
                worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_X;
                worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_Z;
                break;
            case Surface.ROTATION_270:
                worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_Z;
                worldAxisForDeviceAxisY = SensorManager.AXIS_X;
                break;
        }
        
        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisForDeviceAxisX,
                worldAxisForDeviceAxisY, adjustedRotationMatrix);
        
        // Transform rotation matrix into azimuth/pitch/roll
        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);
        
        // Convert radians to degrees
        float pitch = orientation[1] * -57;
        float roll = orientation[2] * -57;
        
        mListener.onOrientationChanged(pitch, roll);
    }
}
