package com.example.crimehelp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MainActivity";
    private GoogleMap mMap;
    private SearchView searchView;
    private SupportMapFragment mapFragment;
    private List<CrimeEventMarker> crimeEventsList;
    ListView lvCrimeEventsSlideUp;
    private BottomSheetBehavior sheetBehavior;
    private FusedLocationProviderClient fusedLocationClient;
    private ArrayList<Marker> searchMarkers;
    private List<Circle> searchRadius;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    public HashMap<String,Boolean> filter;
    private Switch breakAndEnter;
    private Switch mischief;
    private Switch offense;
    private Switch otherTheft;
    private Switch theftFromVehicle;
    private Switch theftOfVehicle;
    private Switch theftOfBicycle;
    private Switch vehicleCollision;

    //private Marker currentLocationMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        crimeEventsList = new ArrayList<>();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        searchMarkers = new ArrayList<>();
        searchRadius = new ArrayList<>();
        filter = new HashMap<String,Boolean>();
        filter.put("Break and Enter", true);
        filter.put("Mischief", true);
        filter.put("Offence", true);
        filter.put("Other Theft", true);
        filter.put("Theft from Vehicle", true);
        filter.put("Theft Of Vehicle", true);
        filter.put("Theft Of Bicycle", true);
        filter.put("Vehicle Collision", true);


        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
//
        breakAndEnter = ((Switch) nav_view.getMenu().findItem(R.id.switchBreakandEnter).getActionView());
        breakAndEnter.setChecked(true);
        mischief = ((Switch) nav_view.getMenu().findItem(R.id.switchMischief).getActionView());
        mischief.setChecked(true);
        offense = ((Switch) nav_view.getMenu().findItem(R.id.switchOffense).getActionView());
        offense.setChecked(true);
        otherTheft = ((Switch) nav_view.getMenu().findItem(R.id.switchOtherTheft).getActionView());
        otherTheft.setChecked(true);
        theftFromVehicle = ((Switch) nav_view.getMenu().findItem(R.id.switchTheftFromVehicle).getActionView());
        theftFromVehicle.setChecked(true);
        theftOfVehicle =((Switch) nav_view.getMenu().findItem(R.id.switchTheftOfVehicle).getActionView());
        theftOfVehicle.setChecked(true);
        theftOfBicycle =((Switch) nav_view.getMenu().findItem(R.id.switchTheftOfBicycle).getActionView());
        theftOfBicycle.setChecked(true);
        vehicleCollision = ((Switch) nav_view.getMenu().findItem(R.id.switchVehicleCollision).getActionView());
        vehicleCollision.setChecked(true);

        breakAndEnter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filter.put("Break and Enter", true);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityTrue(marker, "Break and Enter");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                } else {
                    filter.put("Break and Enter", false);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityFalse(marker, "Break and Enter");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                }
            }
        });

        mischief.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filter.put("Mischief", true);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityTrue(marker, "Mischief");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                } else {
                    filter.put("Mischief", false);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityFalse(marker, "Mischief");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                }
            }
        });
        offense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filter.put("Offense", true);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityTrue(marker, "Offense");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                } else {
                    filter.put("Offense", false);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityFalse(marker, "Offense");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                }
            }
        });
        otherTheft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filter.put("Other Theft", true);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityTrue(marker, "Other Theft");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                } else {
                    filter.put("Other Theft", false);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityFalse(marker, "Other Theft");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                }
            }
        });
        theftFromVehicle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filter.put("Theft from Vehicle", true);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityTrue(marker, "Theft from Vehicle");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                } else {
                    filter.put("Theft from Vehicle", false);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityFalse(marker, "Theft from Vehicle");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                }
            }
        });
        theftOfVehicle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filter.put("Theft Of Vehicle", true);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityTrue(marker, "Theft of Vehicle");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                } else {
                    filter.put("Theft Of Vehicle", false);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityFalse(marker, "Theft of Vehicle");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                }
            }
        });
        theftOfBicycle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filter.put("Theft Of Bicycle", true);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityTrue(marker, "Theft of Bicycle");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                } else {
                    filter.put("Theft Of Bicycle", false);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityFalse(marker, "Theft of Bicycle");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                }
            }
        });
        vehicleCollision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filter.put("Vehicle Collision", true);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityTrue(marker, "Vehicle Collision");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                } else {
                    filter.put("Vehicle Collision", false);
                    try {
                        for (Marker marker : searchMarkers) {
                            if (marker != null)
                            {
                                setVisibilityFalse(marker, "Vehicle Collision");

                            }
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.print("NullPointerException caught");
                    }
                }
            }
        });

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Switch s;
                switch (item.getItemId()) {
                    case R.id.switchBreakandEnter:
                        s = (Switch)findViewById(R.id.switchBreakandEnter);
                        if (!s.isChecked()) {
                            s.setChecked(true);
                            filter.put("Break and Enter", true);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityTrue(marker, "Break and Enter");

                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        else {
                            s.setChecked(false);
                                        filter.put("Break and Enter", false);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityFalse(marker, "Break and Enter");

                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                     break;
                    case R.id.switchMischief:
                        s = (Switch)findViewById(R.id.switchMischief);
                        if (!s.isChecked()) {
                            s.setChecked(true);
                                        filter.put("Mischief", true);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityTrue(marker, "Mischief");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        else {
                            s.setChecked(false);
                                        filter.put("Mischief", false);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityFalse(marker, "Mischief");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        break;
                    case R.id.switchOffense:
                        s = (Switch)findViewById(R.id.switchOffense);
                        if (!s.isChecked()) {
                            s.setChecked(true);
                                        filter.put("Offense", true);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityTrue(marker, "Offense");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        else {
                            s.setChecked(false);
                                        filter.put("Offense", false);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityFalse(marker, "Offense");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        break;
                    case R.id.switchOtherTheft:
                        s = (Switch)findViewById(R.id.switchOtherTheft);
                        if (!s.isChecked()) {
                            s.setChecked(true);
                                        filter.put("Other Theft", true);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityTrue(marker, "Other Theft");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        else {
                            s.setChecked(false);
                            filter.put("Other Theft", false);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityFalse(marker, "Other Theft");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        break;
                    case R.id.switchTheftFromVehicle:
                        s = (Switch)findViewById(R.id.switchTheftFromVehicle);
                        if (!s.isChecked()) {
                            s.setChecked(true);
                                        filter.put("Theft from Vehicle", true);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityTrue(marker, "Theft from Vehicle");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        else {
                            s.setChecked(false);
                                        filter.put("Theft from Vehicle", false);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityFalse(marker, "Theft from Vehicle");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        break;
                    case R.id.switchTheftOfBicycle:
                        s = (Switch)findViewById(R.id.switchTheftOfBicycle);
                        if (!s.isChecked()) {
                            s.setChecked(true);
                                        filter.put("Theft of Bicycle", true);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityTrue(marker, "Theft of Bicycle");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        else {
                            s.setChecked(false);
                                        filter.put("Theft of Bicycle", false);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityFalse(marker, "Theft of Bicycle");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        break;
                    case R.id.switchTheftOfVehicle:
                        s = (Switch)findViewById(R.id.switchTheftOfVehicle);
                                        filter.put("Theft of Vehicle", true);
                        if (!s.isChecked()) {
                            s.setChecked(true);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityTrue(marker, "Theft of Vehicle");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        else {
                            s.setChecked(false);
                                        filter.put("Theft of Vehicle", false);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityFalse(marker, "Theft of Vehicle");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        break;
                    case R.id.switchVehicleCollision:
                        s = (Switch)findViewById(R.id.switchVehicleCollision);
                        if (!s.isChecked()) {
                            s.setChecked(true);
                                        filter.put("Vehicle Collision", true);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityTrue(marker, "Vehicle Collision");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        else {
                            s.setChecked(false);
                                        filter.put("Vehicle Collision", false);
                            try {
                                for (Marker marker : searchMarkers) {
                                    if (marker != null)
                                    {
                                        setVisibilityFalse(marker, "Vehicle Collision");
                                    }
                                }
                            }
                            catch(NullPointerException e)
                            {
                                System.out.print("NullPointerException caught");
                            }
                        }
                        break;
                }
                return true;
            }
        });



        //bottom-sheet init
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.maps_activity);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);

        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(200);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
                Log.e("onStateChanged", "onStateChanged:" + newState);
//                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
////                    fab.setVisibility(View.GONE);
//                } else {
////                    fab.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
                Log.e("onSlide", "onSlide");
            }
        });

        lvCrimeEventsSlideUp = bottomSheet.findViewById(R.id.search_list);
        lvCrimeEventsSlideUp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                // create temp arraylist of selected marker just to move map to selected list item
                Marker crimeEventMarker = (Marker) adapter.getItemAtPosition(position);
                behavior.setPeekHeight(200);
                LatLng selectedListItem = crimeEventMarker.getPosition();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedListItem, 16));
                crimeEventMarker.showInfoWindow();
            }
        });


        searchView = findViewById(R.id.sv_location);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = new ArrayList<>();

                if (!location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList.size() < 1) {
                        Toast.makeText(MapsActivity.this, "No search results.", Toast.LENGTH_LONG).show();
                        return false;
                    }
//                    searchView.clearFocus();
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    int count = 50;
                    clearSearchMarkersAndCircles();
                    for (CrimeEventMarker crimeEvent : crimeEventsList) {
                        if (count <= 0) {
                            break;
                        }
                        try {
                            double latitude = Double.parseDouble(crimeEvent.getX());
                            double longitude = Double.parseDouble(crimeEvent.getY());
                            if (LatLongDistance.distance(latitude, latLng.latitude, longitude, latLng.longitude) < 175) {
                                LatLng marker = new LatLng(latitude, longitude);
                                int color = getMarkerColor(crimeEvent.getTYPE());
                                String strColor = String.format("#%08X", 0x27FFFFFF & color);
                                searchMarkers.add(mMap.addMarker(new MarkerOptions().position(marker).icon(getMarkerIcon(strColor)).snippet(crimeEvent.toString())));
                                searchRadius.add(mMap.addCircle(new CircleOptions()
                                        .center(marker)
                                        .radius(15)
                                        .strokeWidth(0.5f)
                                        .fillColor(Color.parseColor(strColor))));
                                count--;
                                setVisibilityOfCurrentMarkerandCircle(crimeEvent.getTYPE());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    List<Marker> list = new ArrayList<>(searchMarkers);
                    list = sortListIntoBuckets(list);
                    CrimeEventMarkerListAdapter adapter = new CrimeEventMarkerListAdapter(MapsActivity.this, list);
                    lvCrimeEventsSlideUp.setAdapter(adapter);
                    searchMarkers.add(mMap.addMarker(new MarkerOptions().position(latLng).title(location)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(latLng));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        }
                    }
                });


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.setInfoWindowAdapter(new CrimeEventInfoWindowAdapter(getLayoutInflater()));
        } else {
            Log.d(TAG, "Location permissions denied.");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        new readAllCrimeDataTask().execute();
    }

    private class readAllCrimeDataTask extends AsyncTask<Void, Void, List<CrimeEventMarker>> {

        protected void onPostExecute(List<CrimeEventMarker> result) {
            //TODO: maybe we can get current location and plot crime points for current location

            Log.d(TAG, "Completed async task");
        }

        @Override
        protected List<CrimeEventMarker> doInBackground(Void... voids) {
            BufferedReader reader;
            try {
                final InputStream file = getAssets().open("crimeData.txt");
                reader = new BufferedReader(new InputStreamReader(file));
                Gson gson = new Gson();
                Type type = new TypeToken<List<CrimeEventMarker>>() {
                }.getType();
                String line = reader.readLine();
                crimeEventsList = gson.fromJson(line, type);
                for (CrimeEventMarker crimeEvent : crimeEventsList) {
                    try {
                        UTM2Deg deg = new UTM2Deg(Double.parseDouble(crimeEvent.getX()), Double.parseDouble(crimeEvent.getY()));
                        crimeEvent.setY(deg.getLongitude() + "");
                        crimeEvent.setX(deg.getLatitude() + "");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return crimeEventsList;
        }
    }


    public void openSettingsActivity(View v) {
        Intent openSettings = new Intent(this, SettingsActivity.class);
        startActivity(openSettings);
    }

    /**
     * We need to remove every marker from the map before we do a search.
     */
    private void clearSearchMarkersAndCircles() {
        for (Marker marker : searchMarkers) {

            marker.remove();
        }
        searchMarkers.clear();
        for (Circle circle : searchRadius) {
            circle.remove();
        }
        searchRadius.clear();
    }
    public int getMarkerColor(String crimeType) {
        if (crimeType.contains("Break and Enter"))
            return getResources().getColor(R.color.Break);
        else if (crimeType.contains("Mischief"))
            return getResources().getColor(R.color.Mischief);
        else if (crimeType.contains("Offence Against a Person"))
            return getResources().getColor(R.color.Offense);
        else if (crimeType.contains("Other Theft"))
            return getResources().getColor(R.color.OtherTheft);
        else if (crimeType.contains("Theft from Vehicle"))
            return getResources().getColor(R.color.TheftFromVehicle);
        else if (crimeType.contains("Theft of Bicycle"))
            return getResources().getColor(R.color.TheftOfBicycle);
        else if (crimeType.contains("Theft of Vehicle"))
            return getResources().getColor(R.color.TheftOfVehicle);
        else if (crimeType.contains("Vehicle Collision"))
            return getResources().getColor(R.color.VehicleCollision);
        return Color.WHITE;
    }

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    public void setVisibilityFalse(Marker marker, String crimeType) {
        String[] dataString = marker.getSnippet().split("~");
        if(dataString[0].toLowerCase().contains(crimeType.toLowerCase())) {
            marker.setVisible(false);
        }
        for (Circle circle : searchRadius) {
            String strColor = String.format("#%08X", 0x27FFFFFF & getMarkerColor(crimeType));
            if (circle.getFillColor() == Color.parseColor(strColor)) {
                 circle.setVisible(false);
            }
        }
    }

    public void setVisibilityTrue(Marker marker, String crimeType) {
        String[] dataString = marker.getSnippet().split("~");
        if(dataString[0].toLowerCase().contains(crimeType.toLowerCase())) {
            marker.setVisible(true);
        }
        for (Circle circle : searchRadius) {
            String strColor = String.format("#%08X", 0x27FFFFFF & getMarkerColor(crimeType));
            if (circle.getFillColor() == Color.parseColor(strColor)) {
                circle.setVisible(true);
            }
        }
    }

    public void setVisibilityOfCurrentMarkerandCircle(String crimeType) {
        if (crimeType.contains("Break and Enter")) {
            if (filter.get("Break and Enter")!= null && filter.get("Break and Enter")) {
                searchMarkers.get(searchMarkers.size()-1).setVisible(true);
                searchRadius.get(searchRadius.size()-1).setVisible(true);
            }
            else {
                searchMarkers.get(searchMarkers.size()-1).setVisible(false);
                searchRadius.get(searchRadius.size()-1).setVisible(false);
                Log.e("temp", "false");
            }
        }
        else if (crimeType.contains("Mischief")) {
            if (filter.get("Mischief")!= null && filter.get("Mischief")) {
                searchMarkers.get(searchMarkers.size()-1).setVisible(true);
                searchRadius.get(searchRadius.size()-1).setVisible(true);
            }
            else {
                searchMarkers.get(searchMarkers.size()-1).setVisible(false);
                searchRadius.get(searchRadius.size()-1).setVisible(false);
            }
        }
        else if (crimeType.contains("Offense")) {
            if (filter.get("Offense")!= null && filter.get("Offense")) {
                searchMarkers.get(searchMarkers.size()-1).setVisible(true);
                searchRadius.get(searchRadius.size()-1).setVisible(true);
            }
            else {
                searchMarkers.get(searchMarkers.size()-1).setVisible(false);
                searchRadius.get(searchRadius.size()-1).setVisible(false);
            }
        }
        else if (crimeType.contains("Other Theft")) {
            if (filter.get("Other Theft")!= null && filter.get("Other Theft")) {
                searchMarkers.get(searchMarkers.size()-1).setVisible(true);
                searchRadius.get(searchRadius.size()-1).setVisible(true);
            }
            else {
                searchMarkers.get(searchMarkers.size()-1).setVisible(false);
                searchRadius.get(searchRadius.size()-1).setVisible(false);
            }
        }
        else if (crimeType.contains("Theft from Vehicle")) {
            if (filter.get("Theft from Vehicle")!= null && filter.get("Theft from Vehicle")) {
                searchMarkers.get(searchMarkers.size()-1).setVisible(true);
                searchRadius.get(searchRadius.size()-1).setVisible(true);
            }
            else {
                searchMarkers.get(searchMarkers.size()-1).setVisible(false);
                searchRadius.get(searchRadius.size()-1).setVisible(false);
            }
        }
        else if (crimeType.contains("Theft of Bicycle")) {
            if (filter.get("Theft Of Bicycle")!= null && filter.get("Theft Of Bicycle")) {
                searchMarkers.get(searchMarkers.size()-1).setVisible(true);
                searchRadius.get(searchRadius.size()-1).setVisible(true);
            }
            else {
                searchMarkers.get(searchMarkers.size()-1).setVisible(false);
                searchRadius.get(searchRadius.size()-1).setVisible(false);
            }
        }
        else if (crimeType.contains("Theft of Vehicle")) {
            if (filter.get("Theft Of Vehicle")!= null && filter.get("Theft Of Vehicle")) {
                searchMarkers.get(searchMarkers.size()-1).setVisible(true);
                searchRadius.get(searchRadius.size()-1).setVisible(true);
            }
            else {
                searchMarkers.get(searchMarkers.size()-1).setVisible(false);
                searchRadius.get(searchRadius.size()-1).setVisible(false);
            }
        }
        else if (crimeType.contains("Vehicle Collision")) {
            if (filter.get("Vehicle Collision")!= null && filter.get("Vehicle Collision")) {
                searchMarkers.get(searchMarkers.size()-1).setVisible(true);
                searchRadius.get(searchRadius.size()-1).setVisible(true);
            }
            else {
                searchMarkers.get(searchMarkers.size()-1).setVisible(false);
                searchRadius.get(searchRadius.size()-1).setVisible(false);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (abdt.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    private List<Marker> sortListIntoBuckets(List<Marker> list) {
        Map<String, List<Marker>> cumulativeMap = new TreeMap<>();
        List<Marker> returnList = new ArrayList<>();
        for (Marker marker : list) {
            try {
                String[] crimeData = marker.getSnippet().split("~");
                if (!cumulativeMap.containsKey(crimeData[0])) {
                    cumulativeMap.put(crimeData[0], new ArrayList<Marker>());
                }
                cumulativeMap.get(crimeData[0]).add(marker);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        for (List<Marker> typeList : cumulativeMap.values()) {
            returnList.addAll(typeList);
        }
        return returnList;
    }
}
