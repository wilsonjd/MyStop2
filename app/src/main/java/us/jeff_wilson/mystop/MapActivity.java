package us.jeff_wilson.mystop;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Jeff on 3/29/2016.
 */
public class MapActivity extends Activity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient indexClient;
    private GoogleApiClient locationClient;
    private GoogleMap googleMap;
    static LatLng myPos;

    private void moveToCurrentLocation(LatLng currentLocation) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        indexClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        locationClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).build();

        try {
            if (googleMap == null) {
                MapFragment mf = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map));

                mf.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap gm) {

                        if (ActivityCompat.checkSelfPermission(MapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }

                        googleMap = gm;

                        // Show a satellite map with roads
            /* MAP_TYPE_NORMAL: Basic map with roads.
            MAP_TYPE_SATELLITE: Satellite view with roads.
            MAP_TYPE_TERRAIN: Terrain view without roads.
            */
                        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                        // Place dot on current location

                        googleMap.setMyLocationEnabled(true);

                        // Enables indoor maps
                        googleMap.setIndoorEnabled(true);

                        // Show Zoom buttons
                        googleMap.getUiSettings().setZoomControlsEnabled(true);


                        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                locationClient);
                        if (mLastLocation != null) {
                            Marker myLocMarker = googleMap.addMarker(new MarkerOptions().
                                    position(new LatLng(mLastLocation.getLatitude(),
                                            mLastLocation.getLongitude())).title("My Location"));
                        }

                        String stopName = MainActivity.activity.selectedStop.getName();
                        // Create a marker in the map at a given position with a title
                        Marker marker = googleMap.addMarker(new MarkerOptions().
                                position(myPos).title(stopName));
                        moveToCurrentLocation(myPos);

                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onStart() {
        super.onStart();

        locationClient.connect();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        indexClient.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Map Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://us.jeff_wilson.mystop/http/host/path")
        );
        AppIndex.AppIndexApi.start(indexClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Map Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://us.jeff_wilson.mystop/http/host/path")
        );
        AppIndex.AppIndexApi.end(indexClient, viewAction);
        indexClient.disconnect();

        locationClient.disconnect();

    }
}
