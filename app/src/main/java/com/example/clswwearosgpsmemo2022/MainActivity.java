package com.example.clswwearosgpsmemo2022;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.wear.activity.ConfirmationActivity;
import androidx.wear.widget.WearableLinearLayoutManager;

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

        // Watch must have embedded physical GPS for the application to work
        if (hasGps()) {
            Log.d(LOG_TAG, "Found standalone GPS hardware");
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

        } else {
            Log.e(LOG_TAG, "This hardware doesn't have GPS.");
            noGpsExitConfirmation();
        }
    }

    /* *********************************************************************************************
     * HELPER METHODS
     * *********************************************************************************************
     */
    private boolean hasGps() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }

    public void noGpsExitConfirmation() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.FAILURE_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_DURATION_MILLIS, 3000);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, getString(R.string.no_gps_message));
        startActivity(intent);
        finish();
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