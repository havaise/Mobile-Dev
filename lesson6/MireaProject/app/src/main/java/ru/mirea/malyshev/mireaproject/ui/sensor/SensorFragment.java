package ru.mirea.malyshev.mireaproject.ui.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.mirea.malyshev.mireaproject.R;

public class SensorFragment extends Fragment implements SensorEventListener {

    private TextView textViewX;
    private TextView textViewY;
    private TextView textViewZ;
    private TextView textDecision;

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);

        textViewX = view.findViewById(R.id.textViewX);
        textViewY = view.findViewById(R.id.textViewY);
        textViewZ = view.findViewById(R.id.textViewZ);
        textDecision = view.findViewById(R.id.textDecision);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null && accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            textViewX.setText("X: " + x);
            textViewY.setText("Y: " + y);
            textViewZ.setText("Z: " + z);

            if (x > 3) {
                textDecision.setText("Состояние: телефон наклонён влево");
            } else if (x < -3) {
                textDecision.setText("Состояние: телефон наклонён вправо");
            } else {
                textDecision.setText("Состояние: телефон расположен почти ровно");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}