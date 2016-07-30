package us.jeff_wilson.mystop;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jeff on 3/22/2016.
 */
public class TransitSystem {
    String _name;
    private Map<String, TransitLine> _lineMap;
    MetroArea _theMetroArea;

    TransitSystem(MetroArea ma, String n) {
        _theMetroArea = ma;
        _name = n;
        _lineMap = new HashMap<>();
    }
    public String getName() { return _name;}

    public TransitLine findLine(String n) {
        return _lineMap.get(n);
    }

    public TransitLine createLine(String n) {
        TransitLine s = new TransitLine(this, n);
        _lineMap.put(n, s);
        return s;
    }

    public Set<String> getLineNames() {
        Set<String> names = _lineMap.keySet();
        return names;
    }
}
