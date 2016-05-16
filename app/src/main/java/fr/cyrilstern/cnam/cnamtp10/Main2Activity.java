package fr.cyrilstern.cnam.cnamtp10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {
    private TextView textTechnology,textHealth,textLevel,textPlugged,textCapacity,textStatus,textTemperature,textVoltage;
    private Messenger messenger;
    private Bundle bundle;
    private Message message;
    private HashMap hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
    }
    protected void init(){
        textHealth = (TextView) findViewById(R.id.health);
        textLevel = (TextView) findViewById(R.id.level);
        textPlugged = (TextView) findViewById(R.id.plugged);
        textCapacity = (TextView) findViewById(R.id.present);
        textStatus = (TextView) findViewById(R.id.status);
        textTechnology = (TextView) findViewById(R.id.texttechnologie);
        textTemperature = (TextView) findViewById(R.id.temperature);
        textVoltage = (TextView) findViewById(R.id.voltage);
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiverBattery,ifilter);


    }

    private Handler handler =  new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle extras = message.getData();

        }
    };

    public void batteriecheck(){
        textLevel.setText("Level : " + hashMap.get("level").toString());
        textVoltage.setText("Voltage :" + hashMap.get("voltage").toString());
        textTemperature.setText("temperature : " + hashMap.get("temperature").toString() +"°c");
        textTechnology.setText("Technologie :" + hashMap.get("technologie").toString());
        textHealth.setText("Santé :" + hashMap.get("health").toString());
        textPlugged.setText("Plugged : " + hashMap.get("plugged").toString());
        textStatus.setText("Status :" + hashMap.get("status").toString());
        textCapacity.setText("Présence : " + hashMap.get("presence").toString());

    }

    public final BroadcastReceiver broadcastReceiverBattery = new BroadcastReceiver() {
        String thealth;
        String tPlugged;
        String tStatus;
        String tPresent;
        @Override
        public void onReceive(Context context, Intent intent) {
            hashMap = new HashMap();
            int iLevel =
                    intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

            boolean  iPresent = intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
            if (iPresent == true) {
                tPresent = "present";

            } else if (iPresent == false) {
                tPresent = "pas de batterie";

            }
            String iTechnology =
                    intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            int iHealth =
                    intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
            switch (iHealth){
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    thealth = "GOOD";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                     thealth = "MORTE";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                     thealth = "SURCHAUFFE";
            }
            int iPlugged =
                    intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            switch (iPlugged){
                case BatteryManager.BATTERY_PLUGGED_AC:
                    tPlugged = "SECTEUR";
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    tPlugged = "USB";
                    break;
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    tPlugged = "INDUCTION";
                    break;
                default:
                    tPlugged = "PAS BRANCHER";
            }
            int iStatus =
                    intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            switch (iStatus) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    tStatus = "EN CHARGE";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    tStatus = "PAS EN CHARGEMENT";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    tStatus = "PLEIN";
            }
            int iTemperature =
                    intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            int iVoltage =
                    intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            hashMap.put("technologie", iTechnology);
            hashMap.put("health", thealth);
            hashMap.put("level", iLevel);
            hashMap.put("plugged", tPlugged);
            hashMap.put("status", iStatus);
            hashMap.put("presence", tPresent);
            hashMap.put("temperature", iTemperature/10);
            hashMap.put("voltage", iVoltage);
            batteriecheck();

        }

    };
}
