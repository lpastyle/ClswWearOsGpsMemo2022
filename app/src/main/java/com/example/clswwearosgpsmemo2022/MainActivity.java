package com.example.clswwearosgpsmemo2022;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import com.example.clswwearosgpsmemo2022.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {
    private static final String LOG_TAG=MainActivity.class.getSimpleName();
    private static final String LOC_PROVIDER = "WATCH";

    // Recycler view data model
    ArrayList<Location> locations = new ArrayList<>();
    GpsLocationAdapter adapter;

    // keep view binding
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG,"onCreate()");

        // Init view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // dummy init for test purpose only
        locations.add(makeLoc(42.12812921d, 6.22987212d));
        locations.add(makeLoc(43.24648328d, 7.53783836d));
        locations.add(makeLoc(44.39800122d, 8.40192765d));
        locations.add(makeLoc(45.83232627d, 9.12763238d));

        // Wearable recycler view init
        binding.gpsLocationWrv.setEdgeItemsCenteringEnabled(true);
        binding.gpsLocationWrv.setLayoutManager(new WearableLinearLayoutManager(this));
        adapter = new GpsLocationAdapter(locations,this);
        binding.gpsLocationWrv.setAdapter(adapter);

    }

    /* *********************************************************************************************
     * HELPER METHODS
     * *********************************************************************************************
     */
    private boolean hasGps() {
        Log.d(LOG_TAG, "hasGps()");
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }

    private Location makeLoc(double lat, double lon) {
        Location location = new Location(LOC_PROVIDER);
        Date now = new Date();
        location.setLatitude(lat);
        location.setLongitude(lon);
        location.setTime(now.getTime());
        return location;
    }
}