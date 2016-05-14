package fr.cyrilstern.cnam.cnamtp10;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.zip.Inflater;

import sensorpackage.MySensors;

public class MainActivity extends AppCompatActivity {
    private List<Sensor> listeSensorOnDevice;
    private Sensor temperature;
    private Sensor oriantation;
    private Sensor light;
    private Sensor magnetic;
    private Sensor humidity;
    private Sensor barometer;
    private Sensor stepCounter;
    private Sensor proximity;
    private SensorManager sensorManager;
    private LinearLayout linearLayout;
    private  TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater layoutInflaterParent = getLayoutInflater();
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        View view = layoutInflaterParent.inflate(R.layout.activity_main, null);

        init();
    }

    private Messenger messenger;
    private Intent intent;

    private void init() {
        messenger = new Messenger(handler);
        intent = new Intent(this, MySensors.class);
        intent.putExtra("messager", messenger);
        startService(intent);
        sensorManager = (SensorManager) getApplicationContext().getSystemService(SENSOR_SERVICE);

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            Bundle extras = message.getData();
            if (extras != null) {
                Toast.makeText(getApplicationContext(), extras.get("sensor").toString(), Toast.LENGTH_LONG).show();
                listeSensorOnDevice = (List<Sensor>) extras.get("sensor");
                if (listeSensorOnDevice != null) asyncTask.execute(listeSensorOnDevice);


            } else {
                Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private final SensorEventListener temperatureListener =new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            textView.setText("TEMPERATURE: " + event.values[2]);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void activeLayout() {
        Inflater inflater = new Inflater();
        TextView textView1 = new TextView(getApplicationContext());
        if(temperature!=null){
        textView1.setText(String.valueOf(temperature.getPower()));
            textView = new TextView(getApplicationContext());
        textView1.setText(((temperature.getName())));
        textView.setText(String.valueOf(temperature.getPower()));
        textView.setTextColor(Color.BLACK);
        linearLayout.addView(textView);
        linearLayout.addView(textView1);

        }

    }

    AsyncTask asyncTask = new AsyncTask() {
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            activeLayout();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            for (int i = 0; i < listeSensorOnDevice.size(); i++) {
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("temperature")) {

                Log.i("sensorattach",listeSensorOnDevice.get(i).toString());
                    temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                    sensorManager.registerListener(temperatureListener,temperature,sensorManager.SENSOR_DELAY_NORMAL);
                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("light")) {

                    light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("magnetic sensor")) {

                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("accelerometer")) {

                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("gyroscope")) {

                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("barometer")) {

                }
                if (listeSensorOnDevice.get(i).toString().contains("3-axis acceleromter")) {

                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("step counter sensor")) {

                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("screen orientation sensor")) {

                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("orientation sensor")) {

                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("humidity sensor")) {

                }
                if (listeSensorOnDevice.get(i).toString().contains("proximity sensor")) {

                }

            }
            return null;

        }


    };
}
