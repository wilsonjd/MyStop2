package us.jeff_wilson.mystop;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class MyStopBackgroundService extends IntentService {

    private static final String TAG = "us.jeff_wilson.mystop";
    public static final String NEAR_STOP = "NearMyStop";

    MyStopBackgroundService() {
        super(MyStopBackgroundService.class.getName());
    }

    boolean isNear(Location l1, Location l2) {

        double warnDistance = 2000;

        boolean rc = false;

        double dist = l1.distanceTo(l2);

	//        Log.d(TAG,"distance = " + Double.toString(dist));

        if (dist < warnDistance) {
            rc = true;
        }
        return rc;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String stopName = intent.getStringExtra("stop_name");
        String lineName = intent.getStringExtra("line_name");
        String systemName = intent.getStringExtra("system_name");
        String metroName = intent.getStringExtra("metro_name");

        TransitDatabase db = MainActivity._theDatabase;
        MetroArea ma = db.findMetroArea(metroName);
        TransitSystem sys = ma.findSystem(systemName);
        TransitLine line = sys.findLine(lineName);
        TransitLineStop stop = line.findStop(stopName);
        Location stopLocation = stop._stopLocation;

        // debug
        Log.d(TAG, "background service.  stopName: " + stopName);

        boolean done = false;

        GoogleApiClient locationClient = new GoogleApiClient.Builder(MainActivity.activity).addApi(LocationServices.API).build();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.activity,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MainActivity.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);


            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e(TAG, "location permission check failed.");
            return;
        }

        locationClient.connect();

        while (!done) {
            // get current location
	    //            Log.d(TAG,"getting myLocation");

            Location myLocation = LocationServices.FusedLocationApi.getLastLocation(
                    locationClient);
            //  compare to requested stop
            //  if "close" raise alarm

	    //            Log.d(TAG,"myLocation = " + myLocation + " stopLocation = " + stopLocation);

            if (myLocation != null && stopLocation != null) {
		//                Log.d(TAG, "myLocation = " + myLocation);

                if (isNear(myLocation, stopLocation)) {

                    // debug
		    Log.d(TAG, "my location is near stop.");

                    // raise alarm
                    done = true;

                    Intent i = new Intent(NEAR_STOP);
                    sendBroadcast(i);

                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        locationClient.disconnect();
    }
}
