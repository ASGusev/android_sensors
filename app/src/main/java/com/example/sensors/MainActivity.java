package com.example.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView[] textFields = new TextView[3];
    private DrawView drawView;

    private Sensor gravity;
    private Sensor magnetic;
    private SensorManager mSensorManager;

    float[] grav = new float[3];
    float[] magn = new float[3];
    float[] orientation = new float[3];
    float[] rotation = new float[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gravity = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        textFields[0] = (TextView) findViewById(R.id.textView3);
        textFields[1] = (TextView) findViewById(R.id.textView4);
        textFields[2] = (TextView) findViewById(R.id.textView5);
        drawView = (DrawView) findViewById(R.id.drawView1);

        for (int i = 0; i < 3; i++) {
            textFields[i].setText("0");
            orientation[i] = 0;
        }

        for (int i = 0; i < 3; i++) {
            grav[i] = 0;
            magn[i] = 0;
        }

        for (int i = 0; i < 9; i++) {
            rotation[i] = 0;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public final void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER: {
                for (int i = 0; i < 3; i++) {
                    grav[i] = event.values[i];
                }
            }
            case Sensor.TYPE_MAGNETIC_FIELD: {
                for (int i = 0; i < 3; i++) {
                    magn[i] = event.values[i];
                }
            }
        }

        SensorManager.getRotationMatrix(rotation, null, grav, magn);
        SensorManager.getOrientation(rotation, orientation);

        for (int i = 0; i < 3; i++) {
            textFields[i].setText(String.valueOf(orientation[i]));
        }

        drawView.setAngle(orientation[1]);
        drawView.invalidate();
    }
}