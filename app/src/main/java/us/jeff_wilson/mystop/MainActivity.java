package us.jeff_wilson.mystop;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    static TransitDatabase _theDatabase;
    Spinner MetroSpinner;
    Spinner SystemSpinner;
    Spinner LineSpinner;
    Spinner StopSpinner;
    Button SelectButton;
    public static MainActivity activity;
    public MetroArea selectedMetroArea;
    public TransitSystem selectedSystem;
    public TransitLine selectedLine;
    public TransitLineStop selectedStop;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _theDatabase = new TransitDatabase();
        _theDatabase.loadData();
        activity = this;

        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        MetroSpinner = (Spinner) findViewById(R.id.MetroSpinner);
        SystemSpinner = (Spinner) findViewById(R.id.SystemSpinner);
        LineSpinner = (Spinner) findViewById(R.id.LineSpinner);
        StopSpinner = (Spinner) findViewById(R.id.StopSpinner);
        Button SelectButton = (Button) findViewById(R.id.SelectButton);

        selectedMetroArea = null;
        selectedSystem = null;
        selectedLine = null;
        selectedStop = null;

        Set<String> manSet = _theDatabase.getMetroAreaNames();
        String[] manStrings = manSet.toArray(new String[manSet.size()]);
        ArrayAdapter<String> metroAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, manStrings);
        MetroSpinner.setAdapter(metroAdapter);

        MetroSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {

                String metroName = adapterView.getItemAtPosition(index).toString();
                MetroArea ma = _theDatabase.findMetroArea(metroName);
                MainActivity.activity.selectedMetroArea = ma;

                Set<String> sysNamesSet = ma.getSystemNames();
                String[] sysStrings = sysNamesSet.toArray(new String[sysNamesSet.size()]);
                ArrayAdapter<String> sysAdapter = new ArrayAdapter<>(MainActivity.activity,
                        R.layout.spinner_item, sysStrings);
                SystemSpinner.setAdapter(sysAdapter);
                LineSpinner.setAdapter(new ArrayAdapter<>(MainActivity.activity,
                        R.layout.spinner_item));
                StopSpinner.setAdapter(new ArrayAdapter<>(MainActivity.activity,
                        R.layout.spinner_item));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SystemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {

                // update for system name
                String systemName = adapterView.getItemAtPosition(index).toString();
                TransitSystem ts = MainActivity.activity.selectedMetroArea.findSystem(systemName);
                MainActivity.activity.selectedSystem = ts;
                Set<String> lineNamesSet = ts.getLineNames();
                String[] lineStrings = lineNamesSet.toArray(new String[lineNamesSet.size()]);
                ArrayAdapter<String> sysAdapter = new ArrayAdapter<>(MainActivity.activity,
                        R.layout.spinner_item, lineStrings);
                LineSpinner.setAdapter(sysAdapter);
                StopSpinner.setAdapter(new ArrayAdapter<>(MainActivity.activity,
                        R.layout.spinner_item));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {

                // update for system name
                String lineName = adapterView.getItemAtPosition(index).toString();
                TransitLine tl = MainActivity.activity.selectedSystem.findLine(lineName);
                MainActivity.activity.selectedLine = tl;
                Set<String> stopNamesSet = tl.getStopNames();
                String[] stopStrings = stopNamesSet.toArray(new String[stopNamesSet.size()]);
                ArrayAdapter<String> stopAdapter = new ArrayAdapter<>(MainActivity.activity,
                        R.layout.spinner_item, stopStrings);
                StopSpinner.setAdapter(stopAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        StopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {

                // update for system name
                String stopName = adapterView.getItemAtPosition(index).toString();
                MainActivity.activity.selectedStop = MainActivity.activity.selectedLine.findStop(stopName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    SelectButton.setOnClickListener(new AdapterView.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.out.println("button click");
            System.out.println("selected stop = " + selectedStop);

            if (selectedStop != null) {
                Location l = selectedStop.getLocation();

                MapActivity.myPos = new LatLng(l.getLatitude(), l.getLongitude());


                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);

                String stopName = selectedStop.getName();
                String lineName = selectedStop._theLine.getName();
                String systemName = selectedStop._theLine._theSystem.getName();
                String metroName = selectedStop._theLine._theSystem._theMetroArea.getName();
                Intent myIntent = new Intent(MainActivity.this, MyStopBackgroundService.class);
                myIntent.putExtra("stop_name", stopName);
                myIntent.putExtra("line_name", lineName);
                myIntent.putExtra("system_name", systemName);
                myIntent.putExtra("metro_name", metroName);

                startService(myIntent);

            }

        }
    });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
     //   if (id == R.id.action_settings) {
     //       return true;
     //   }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://us.jeff_wilson.mystop/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://us.jeff_wilson.mystop/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
