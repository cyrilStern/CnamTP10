package ServiceSensor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.List;

public class MyService extends Service {
    public MyService() {
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
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        Sensor Temperature = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        List <Sensor> sensorsList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(int i = 0; i<sensorsList.size();i++) Log.i("sensor",sensorsList.get(i).toString());
        Log.i("vendeur",Temperature.getVendor());

        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        // Cancel the persistent notification.

        // Tell the user we stopped.
       // Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }


}
