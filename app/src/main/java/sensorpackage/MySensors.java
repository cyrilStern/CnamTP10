package sensorpackage;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

public class MySensors extends Service {
    public MySensors() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private SensorManager sensorManager;
    @Override
    public void onCreate(){
        sensorManager=(SensorManager)
                getApplicationContext().getSystemService(SENSOR_SERVICE);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        Messenger messager = (Messenger) bundle.get("messager");
        Message msg = Message.obtain();
        Bundle sendBundle = new Bundle();
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        List<Sensor> sensorsList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sendBundle.putSerializable("sensor", (Serializable) sensorsList);
        msg.setData(sendBundle);
        try {
            messager.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {

        // Cancel the persistent notification.

        // Tell the user we stopped.
        // Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }
}
