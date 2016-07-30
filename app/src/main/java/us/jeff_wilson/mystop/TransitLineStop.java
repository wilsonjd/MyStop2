package us.jeff_wilson.mystop;

import android.location.Location;

/**
 * Created by Jeff on 3/22/2016.
 */
public class TransitLineStop {
    String _name;
    Location _stopLocation;
    TransitLine _theLine;

    TransitLineStop(TransitLine line, String n, Location l) {
        _theLine = line;
        _name = n;
        _stopLocation = l;
    }

    public String getName() { return _name;}
    public Location getLocation() { return _stopLocation;}

    public double distanceTo(Location location) { return _stopLocation.distanceTo(location);}

}
