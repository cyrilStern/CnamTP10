package fr.cyrilstern.cnam.cnamtp10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ServiceSensor.MyService;

public class BatteryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        startService(new Intent(this, MyService.class));
    }
    @Override
    protected void onPause(){
        super.onPause();
        stopService(new Intent(this,MyService.class));
    }
}
