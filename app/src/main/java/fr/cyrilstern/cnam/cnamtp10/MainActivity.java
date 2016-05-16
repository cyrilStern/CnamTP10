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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.zip.Inflater;

import sensorpackage.MySensors;

public class MainActivity extends AppCompatActivity {
    private List<Sensor> listeSensorOnDevice;
    private Sensor temperature,oriantation,light,magnetic,humidity,barometer,gyroscope,stepCounter,proximity,accelerometer;
    private SensorManager sensorManager;
    private LinearLayout linearLayout;
    private TextView textViewlight,textView,textAccurancyGyroscope,textAccurancyLight,textAccurancyAccelerometer,textAccurancyTemperature,textAccurancyMagnetic,textMagneticX,textMagneticY,textMagneticZ,textViewAcceleromterX,textViewAcceleromterY,textViewAcceleromterZ,textGyroscopeX,textGyroscopeY,textGyroscopeZ;
    private View view;
    private LinearLayout.LayoutParams buttonLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater layoutInflaterParent = getLayoutInflater();
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        view = layoutInflaterParent.inflate(R.layout.activity_main, null);
        view.setBackgroundColor(Color.DKGRAY);

        init();
    }

    private Messenger messenger;
    private Intent intent;

    private void init() {
        textView = new TextView(this);
        textViewlight = new TextView(this);
        textViewAcceleromterX = new TextView(this);
        textViewAcceleromterY = new TextView(this);
        textViewAcceleromterZ = new TextView(this);
        textMagneticX = new TextView(this);
        textMagneticY = new TextView(this);
        textMagneticZ = new TextView(this);
        textGyroscopeX = new TextView(this);
        textGyroscopeY = new TextView(this);
        textGyroscopeZ = new TextView(this);
        textAccurancyTemperature = new TextView(this);
        textAccurancyMagnetic = new TextView(this);
        textAccurancyGyroscope = new TextView(this);
        textAccurancyAccelerometer = new TextView(this);
        messenger = new Messenger(handler);
        intent = new Intent(this, MySensors.class);
        intent.putExtra("messager", messenger);
        startService(intent);
        sensorManager = (SensorManager) getApplicationContext().getSystemService(SENSOR_SERVICE);
        buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, 50, 0, 0);

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

    private void noSensor(String string){

        LinearLayout linearLayouttemperature = new LinearLayout(getApplicationContext());
        TextView textView1 = new TextView(getApplicationContext());
        textView1.setText(string + "this sensor is not available : ");
        linearLayouttemperature.setOrientation(LinearLayout.VERTICAL);

    }



    private void activeLayoutTemperature() {
        LinearLayout linearLayouttemperature = new LinearLayout(getApplicationContext());
        TextView textView1 = new TextView(getApplicationContext());
        textView1.setText("TEMPERATURE : ");
        linearLayouttemperature.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(linearLayouttemperature);
        if(temperature!=null){
            textView = new TextView(getApplicationContext());
            textView.setTextColor(Color.RED);
            textView1.setTextColor(Color.BLACK);
            linearLayouttemperature.setBackgroundColor(Color.GRAY);
            linearLayouttemperature.addView(textView1);
            linearLayouttemperature.addView(textView);
            linearLayouttemperature.addView(textAccurancyTemperature);
        }

    }

    private final SensorEventListener temperatureListener =new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            textView.setText(event.values[0] + "°c");
            switch (event.accuracy){
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    textAccurancyTemperature = new TextView(getApplicationContext());
                    textAccurancyTemperature.setText("Precision : Très haute");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                    textAccurancyTemperature = new TextView(getApplicationContext());
                    textAccurancyTemperature.setText("Precision : Moyenne");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                    textAccurancyTemperature = new TextView(getApplicationContext());
                    textAccurancyTemperature.setText("Precision : Faible");
                    break;

            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {


        }
    };

    private void activeLayoutLight() {
        LinearLayout linearLayoutLight = new LinearLayout(getApplicationContext());
        linearLayoutLight.setLayoutParams(buttonLayoutParams);
        linearLayoutLight.setOrientation(LinearLayout.VERTICAL);
        TextView textView2 = new TextView(getApplicationContext());
        TextView textView3 = new TextView(getApplicationContext());

        textView2.setText("LIGTH  ");
        textView3.setText("light:  ");

        linearLayout.addView(linearLayoutLight);
        if(temperature!=null){
            textViewlight = new TextView(getApplicationContext());
            textViewlight.setTextColor(Color.RED);
            textView2.setTextColor(Color.BLACK);
            linearLayoutLight.setBackgroundColor(Color.GRAY);
            linearLayoutLight.addView(textView2);
            linearLayoutLight.addView(textView3);
            linearLayoutLight.addView(textViewlight);
            linearLayoutLight.addView(textAccurancyLight);
        }

    }
    private final SensorEventListener lightListener =new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            textViewlight.setText(String.valueOf(event.values[0]) + "lux");
            switch (event.accuracy) {
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    textAccurancyLight = new TextView(getApplicationContext());
                    textAccurancyLight.setText("Precision : Très haute");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                    textAccurancyLight = new TextView(getApplicationContext());
                    textAccurancyLight.setText("Precision : Moyenne");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                    textAccurancyLight = new TextView(getApplicationContext());
                    textAccurancyLight.setText("Precision : Faible");
                    break;


            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {


        }
    };

    private void activeLayoutAccelerometer() {
        LinearLayout linearLayoutLight = new LinearLayout(getApplicationContext());
        linearLayoutLight.setLayoutParams(buttonLayoutParams);
        linearLayoutLight.setOrientation(LinearLayout.VERTICAL);
        TextView textView2 = new TextView(getApplicationContext());
        textView2.setText("ACCELEROMETER : ");
        linearLayout.addView(linearLayoutLight);
        if(temperature!=null){
            textViewlight.setTextColor(Color.RED);
            textView2.setTextColor(Color.BLACK);
            linearLayoutLight.setBackgroundColor(Color.GRAY);
            linearLayoutLight.addView(textView2);
            linearLayoutLight.addView(textViewAcceleromterX);
            linearLayoutLight.addView(textViewAcceleromterY);
            linearLayoutLight.addView(textViewAcceleromterZ);
            linearLayoutLight.addView(textAccurancyAccelerometer);

        }

    }
    private final SensorEventListener accelerometerListener =new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            textViewAcceleromterX.setText(" X: " + String.valueOf(event.values[0])+"m/s^2");
            textViewAcceleromterY.setText(" Y: " + String.valueOf(event.values[1])+"m/s^2");
            textViewAcceleromterZ.setText(" Z: " + String.valueOf(event.values[2])+"m/s^2");

            switch (event.accuracy){
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    textAccurancyAccelerometer = new TextView(getApplicationContext());
                    textAccurancyAccelerometer.setText("Precision : Très haute");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                    textAccurancyAccelerometer = new TextView(getApplicationContext());
                    textAccurancyAccelerometer.setText("Precision : Moyenne");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                    textAccurancyAccelerometer = new TextView(getApplicationContext());
                    textAccurancyAccelerometer.setText("Precision : Faible");
                    break;

            }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {



        }
    };

    private void activeLayoutGyroscop() {
        LinearLayout linearLayoutLight = new LinearLayout(getApplicationContext());
        linearLayoutLight.setLayoutParams(buttonLayoutParams);
        linearLayoutLight.setOrientation(LinearLayout.VERTICAL);
        TextView textView2 = new TextView(getApplicationContext());
        textView2.setText("GYROSCOPE : ");
        linearLayout.addView(linearLayoutLight);
        if(temperature!=null){
            textViewlight.setTextColor(Color.RED);
            textView2.setTextColor(Color.BLACK);
            linearLayoutLight.setBackgroundColor(Color.GRAY);
            linearLayoutLight.addView(textView2);
            linearLayoutLight.addView(textGyroscopeX);
            linearLayoutLight.addView(textGyroscopeY);
            linearLayoutLight.addView(textGyroscopeZ);
            linearLayoutLight.addView(textAccurancyGyroscope);

        }

    }
    private final SensorEventListener gyroscopeListener =new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            textGyroscopeX.setText(" X: " + String.valueOf(event.values[0])+"radians/s^2");
            textGyroscopeY.setText(" Y: " + String.valueOf(event.values[1])+"radians/s^2");
            textGyroscopeZ.setText(" Z: " + String.valueOf(event.values[2])+"radians/s^2");
            switch (event.accuracy){
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    textAccurancyGyroscope = new TextView(getApplicationContext());
                    textAccurancyGyroscope.setText("Precision : Très haute");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                    textAccurancyGyroscope = new TextView(getApplicationContext());
                    textAccurancyGyroscope.setText("Precision : Moyenne");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                    textAccurancyGyroscope = new TextView(getApplicationContext());
                    textAccurancyGyroscope.setText("Precision : Faible");
                    break;

            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {



        }
    };

    private void activeLayoutMagnetic() {
        LinearLayout linearLayoutLight = new LinearLayout(getApplicationContext());
        linearLayoutLight.setLayoutParams(buttonLayoutParams);
        linearLayoutLight.setOrientation(LinearLayout.VERTICAL);
        TextView textView2 = new TextView(getApplicationContext());
        textView2.setText("MAGNETIC : ");
        linearLayout.addView(linearLayoutLight);
        if(temperature!=null){
            textViewlight.setTextColor(Color.RED);
            textView2.setTextColor(Color.BLACK);
            linearLayoutLight.setBackgroundColor(Color.GRAY);
            linearLayoutLight.addView(textView2);
            linearLayoutLight.addView(textMagneticX);
            linearLayoutLight.addView(textMagneticY);
            linearLayoutLight.addView(textMagneticZ);
            linearLayoutLight.addView(textAccurancyMagnetic);

        }

    }
    private final SensorEventListener magneticListener =new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            textMagneticX.setText(" X: " + String.valueOf(event.values[0])+"µT");
            textMagneticY.setText(" Y: " + String.valueOf(event.values[1])+"µT");
            textMagneticZ.setText(" Z: " + String.valueOf(event.values[2])+"µT");
            switch (event.accuracy){
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    textAccurancyMagnetic = new TextView(getApplicationContext());
                    textAccurancyMagnetic.setText("Precision : Très haute");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                    textAccurancyMagnetic = new TextView(getApplicationContext());
                    textAccurancyMagnetic.setText("Precision : Moyenne");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                    textAccurancyMagnetic = new TextView(getApplicationContext());
                    textAccurancyMagnetic.setText("Precision : Faible");
                    break;

            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {



        }
    };

    AsyncTask asyncTask = new AsyncTask() {
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(temperature != null) activeLayoutTemperature();
            if(light != null) activeLayoutLight();
            if(accelerometer != null) activeLayoutAccelerometer();
            if(gyroscope != null) activeLayoutGyroscop();
            if(magnetic != null) activeLayoutMagnetic();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            for (int i = 0; i < listeSensorOnDevice.size(); i++) {
                    Log.i("sensorattach",listeSensorOnDevice.get(i).toString());
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("temperature")) {

                    temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                    sensorManager.registerListener(temperatureListener,temperature,sensorManager.SENSOR_DELAY_NORMAL);
                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("rgb sensor")) {

                    light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                    sensorManager.registerListener(lightListener,light,sensorManager.SENSOR_DELAY_NORMAL);
                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("magnetic sensor")) {

                    magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                    sensorManager.registerListener(magneticListener,magnetic,sensorManager.SENSOR_DELAY_NORMAL);

                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("accelerometer")) {

                    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    sensorManager.registerListener(accelerometerListener,accelerometer,sensorManager.SENSOR_DELAY_NORMAL);

                }
                if (listeSensorOnDevice.get(i).toString().toLowerCase().contains("gyroscope")) {

                    gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                    sensorManager.registerListener(gyroscopeListener,gyroscope,sensorManager.SENSOR_DELAY_NORMAL);

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
