package us.jeff_wilson.mystop;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jeff on 3/22/2016.
 */
public class MetroArea {
    String _name;
    Map<String, TransitSystem> _systems;
    TransitDatabase _theDatabase;

    MetroArea(TransitDatabase db, String n) {
        _theDatabase = db;
        _name = new String(n);
        _systems = new HashMap<>();
    }
    public String getName() { return _name;}

    TransitSystem findSystem(String n) {
        return _systems.get(n);
    }

    TransitSystem createSystem(String n) {
        TransitSystem s = new TransitSystem(this, n);
        _systems.put(n, s);
        return s;
    }

    Set<String> getSystemNames() {
        Set<String> names = _systems.keySet();
        return names;
    }
}
