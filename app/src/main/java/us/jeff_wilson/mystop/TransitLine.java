package us.jeff_wilson.mystop;

import android.location.Location;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jeff on 3/22/2016.
 */
public class TransitLine {
    String _name;
    LinkedHashMap<String, TransitLineStop> _stopMap;
    Map<Location, TransitLineStop> _locationStops;
    TransitSystem _theSystem;

    TransitLine(TransitSystem system, String n) {
        _theSystem = system;
        _name = n;
        _stopMap = new LinkedHashMap<>();
        _locationStops = new HashMap<>();
    }

    public String getName() { return _name;}

    TransitLineStop createStop(String n, Location l) {
        TransitLineStop s = new TransitLineStop(this, n, l);
        _stopMap.put(n, s);
        _locationStops.put(l, s);
        return s;
    }

    public Set<String> getStopNames() {
        Set<String> names = _stopMap.keySet();
        return names;
    }

    TransitLineStop findStop(String n) {
        return _stopMap.get(n);
    }

    TransitLineStop findStop(Location l) {
        return _locationStops.get(l);
    }
}
