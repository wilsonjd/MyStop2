package us.jeff_wilson.mystop;

import android.location.Location;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

// import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Jeff on 3/22/2016.
 */
public class TransitDatabase {
    LinkedHashMap<String, MetroArea> _metroAreas;

    TransitLineStop[] hudsonLineStops;
    TransitLineStop[] orangeLineStops;


    String[] hudsonLineStopNames = {
            "Jeff House",
             "Poughkeepsie",
             "New Hamburgh",
             "Beacon",
             "Cold Spring",
             "Garrison",
             "Peekskill",
             "Cortlandt",
             "Croton-Harmon",
             "Ossining",
             "Tarrytown",
             "Yonkers",
             "Marble Hill",
             "Harlem-125th Street",
             "Grand Central Terminal",
    };
    double[] hudsonLineLatLng = {
            41.872735, -73.896301,
            41.706641, -73.938001,
            41.586692, -73.947494,
            41.506443, -73.984787,
            41.415232, -73.958063,
            41.380994, -73.947398,
            41.284894, -73.931391,
            41.247025, -73.923111,
            41.189820, -73.882692,
            41.157602, -73.869118,
            41.075528, -73.865103,
            40.936769, -73.901762,
            40.874943, -73.91206,
            40.805082, -73.939018,
            40.752838, -73.977139,
    };

    String[] chicagoOrangeLineStopNames = {
            "Midway",
            "Pulaski",
            "Kedzie",
            "Western",
            "35/Archer",
            "Ashland",
            "Halsted",
            "Roosevelt",
            "Adams/Wabash",
            "Randolph/Wabash",
            "State/Lake",
            "Clark/Lake",
            "Washington/Wells",
            "Quincy/Wells",
            "LaSalle/Van Buren",
            "State/Van Buren",
    };

    double[] chicagoOrangeLineLatLng = {
            41.786786, -87.737983,
            41.800028, -87.724091,
            41.804238, -87.704382,
            41.804665, -87.684039,
            41.829349, -87.680868,
            41.839264, -87.665427,
            41.846878, -87.648212,
            41.867360, -87.626667,
            41.879497, -87.626068,
            41.884423, -87.626183,
            41.885781, -87.627949,
            41.885730, -87.631531,
            41.882569, -87.633825,
            41.878736, -87.633767,
            41.876904, -87.631809,
            41.876957, -87.628727,
    };
    /*
        StopInfo hudsonLineStops[] = {
                { "Poughkeepsie", 41.706641, -73.938001},
                { "New Hamburgh", 41.586692, -73.947494},
                { "Beacon", 41.506443, -73.984787},
                { "Cold Spring", 41.415232, -73.958063},
                { "Garrison", 41.380994, -73.947398},
                { "Peekskill", 41.284894, -73.931391},
                { "Cortlandt", 41.247025, -73.923111},
                { "Croton-Harmon", 41.189820, -73.882692},
                { "Ossining", 41.157602, -73.869118},
                { "Tarrytown", 41.075528, -73.865103},
                { "Yonkers", 40.936769, -73.901762},
                { "Marble Hill", 40.874943, -73.91206},
                { "Harlem-125th Street", 40.805082, -73.939018},
                { "Grand Central Terminal", 40.752838, -73.977139},
        };
        */


    /*
    StopInfo harlemLineStops[] = {
            { "Wassaic", 41.814786, -73.562328},
            { "Dover Plains", 41.742395, -73.576232},
            { "Wingdale", 41.637419, -73.571653},
            { "Pawling", 41.564667, -73.600348},
            { "Patterson", 41.511802, -73.604308},
            { "Southeast", 41.413789, -73.623226},
            { "Brewster", 41.394546, -73.619880},
            { "Croton Falls", 41.348107, -73.662206},
            { "Purdy's", 41.325692, -73.658935},
            { "Goldens Bridge", 41.294206, -73.677649},
            { "Katonah", 41.259870, -73.684060},
            { "Bedford Hills", 41.237240, -73.700098},
            { "Mt Kisco", 41.208162, -73.729860},
            { "Chappaqua", 41.158023, -73.774967 },
            { "Pleasantville", 41.135398, -73.792551 },
            { "Hawthorne", 41.109577, -73.795925 },
            { "Valhalla", 41.073070, -73.772817 },
            { "North White Plains", 41.051143, -73.772550 },
            { "White Plains", 41.033113, -73.775247 },
            { "Hartsdale", 41.010840, -73.795901 },
            { "Scarsdale", 40.989559, -73.808575 },
            { "Crestwood", 40.959191, -73.820781 },
            { "Tuckahoe", 40.950288, -73.828917 },
            { "Bronxville", 40.940327, -73.835045 },
            { "Fleetwood", 40.926884, -73.839974 },
            { "Mt Vernon West", 40.912332, -73.850840 },
            { "Wakefield", 40.906381, -73.855386 },
            { "Woodlawn", 40.895552, -73.862595 },
            { "Williams Bridge", 40.878133, -73.871093 },
            { "Botanical Garden", 40.867201, -73.881902 },
            { "Fordham", 40.861869, -73.8902210 },

            { "Grand Central Terminal", 40.752838, -73.977139},
    };
    */

    TransitDatabase() { _metroAreas = new LinkedHashMap<>(); }

    public MetroArea findMetroArea(String name) {
        return _metroAreas.get(name);
    }

    public MetroArea createMetroArea(String n) {
        MetroArea m = new MetroArea(this, n);
        _metroAreas.put(n, m);
        return m;
    }

    public Set<String> getMetroAreaNames() {
        Set<String> names = _metroAreas.keySet();
        return names;
    }

    public void loadData() {

        // test data
        MetroArea boston = createMetroArea("Boston");

        MetroArea chicago = createMetroArea("Chicago");
        TransitSystem ctaL = chicago.createSystem("CTA L");
        TransitLine ctaLRed = ctaL.createLine("Red");
        TransitLine ctaLBlue = ctaL.createLine("Blue");
        TransitLine ctaLBrown = ctaL.createLine("Brown");
        TransitLine ctaLGreen = ctaL.createLine("Green");
        TransitLine ctaLOrange = ctaL.createLine("Orange");

        orangeLineStops = new TransitLineStop[chicagoOrangeLineStopNames.length];
        for(int i = 0; i < chicagoOrangeLineStopNames.length; ++i) {
            Location testLoc = new Location("test");
            testLoc.setLatitude(chicagoOrangeLineLatLng[i*2]);  // myLoc.lattitude
            testLoc.setLongitude(chicagoOrangeLineLatLng[i*2 + 1]); // myLoc.longitude
            orangeLineStops[i] = ctaLOrange.createStop(chicagoOrangeLineStopNames[i], testLoc);
        }


        TransitLine ctaLPurple = ctaL.createLine("Purple");
        TransitLine ctaLPink = ctaL.createLine("Pink");
        TransitLine ctaLYellow = ctaL.createLine("Yellow");

        TransitSystem cteBus = chicago.createSystem("CTA Bus");
        TransitSystem chicagoMetra = chicago.createSystem("Metra");
        TransitLine metraMDN = chicagoMetra.createLine("Milwaukee District North");
        TransitLine metraNCS = chicagoMetra.createLine("North Central Service");
        TransitLine metraUPN = chicagoMetra.createLine("Union Pacific North");
        TransitLine metraUPNW = chicagoMetra.createLine("Union Pacific Northwest");
        TransitLine metraHC = chicagoMetra.createLine("Heritage Corridor");
        TransitLine metraMED = chicagoMetra.createLine("Metra Electric District");
        TransitLine metraRI = chicagoMetra.createLine("Rock Island");
        TransitLine metraSWS = chicagoMetra.createLine("SouthWest Service");
        TransitLine metraBNSF = chicagoMetra.createLine("BNSF Railway");
        TransitLine metraMD = chicagoMetra.createLine("Milwaukee District");
        TransitLine metraUPW = chicagoMetra.createLine("Union Pacific West");

        MetroArea nyc = createMetroArea("New York City");
        TransitSystem metroNorth = nyc.createSystem("Metro North");
        TransitLine hudsonLine = metroNorth.createLine("Hudson");
        TransitLine harlemLine = metroNorth.createLine("Harlem");
        TransitLine newHavenLine = metroNorth.createLine("New Haven");

        // LatLng myLoc = new LatLng();
        hudsonLineStops = new TransitLineStop[hudsonLineStopNames.length];
        for(int i = 0; i < hudsonLineStopNames.length; ++i) {
            Location testLoc = new Location("test");
            testLoc.setLatitude(hudsonLineLatLng[i*2]);  // myLoc.lattitude
            testLoc.setLongitude(hudsonLineLatLng[i*2 + 1]); // myLoc.longitude
            hudsonLineStops[i] = hudsonLine.createStop(hudsonLineStopNames[i], testLoc);
        }

        TransitSystem LIRR = nyc.createSystem("Long Island Rail Road");
        TransitLine babalonLine = LIRR.createLine("Babalon");
        TransitLine hempteadLine = LIRR.createLine("Hempstead");
        TransitLine ronkLine = LIRR.createLine("Ronkonkoma");

        TransitSystem NYCSubway = nyc.createSystem("Subway");

        MetroArea wash = createMetroArea("Washington");

    }
}
