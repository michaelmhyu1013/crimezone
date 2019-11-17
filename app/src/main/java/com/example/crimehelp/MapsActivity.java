package com.example.crimehelp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
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
    private List<Marker> currentLocationMarkers;
    private List<Circle> currentLocationRadius;
    private List<Marker> searchMarkers;
    private LocationRequest mLocationRequest;
    private Marker mCurrLocationMarker;
    private Location mLastLocation;
    private List<Circle> searchRadius;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    public HashMap<String,Boolean> filter;


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
        currentLocationMarkers = new ArrayList<>();
        currentLocationRadius = new ArrayList<>();
        filter = new HashMap<String,Boolean>();
        filter.put("Break and Enter", false);
        filter.put("Mischief", false);
        filter.put("Offence", false);
        filter.put("Other Theft", false);
        filter.put("Theft from Vehicle", false);
        filter.put("Theft Of Vehicle", false);
        filter.put("Theft Of Bicycle", false);
        filter.put("Vehicle Collision", false);


        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);

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
                LatLng latLng = getSearchResultLocation();
                if(latLng == null) {
                    return false;
                }
                generateCrimeEventMarkers(latLng, searchMarkers,searchRadius);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                createCrimeEventSlideUp(searchMarkers);
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
                            mLastLocation = location;
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

            LocationCallback mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    try{
                        List<Location> locationList = locationResult.getLocations();
                        if (locationList.size() > 0) {
                            //The last location in the list is the newest
                            Location location = locationList.get(locationList.size() - 1);
                            Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                            if (LatLongDistance.distance(location.getLatitude(), mLastLocation.getLongitude(), location.getLongitude(), mLastLocation.getLongitude()) < 75) {
                                return;
                            }else {
                                mLastLocation = location;
                            }
                            if (mCurrLocationMarker != null) {
                                mCurrLocationMarker.remove();
                            }

                            //Place current location marker
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);
                            markerOptions.title("Current Position");
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                            mCurrLocationMarker = mMap.addMarker(markerOptions);
                            generateCrimeEventMarkers(latLng,currentLocationMarkers,currentLocationRadius);
                            //move map camera (testing)
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                        }
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            //TODO: maybe we can get current location and plot crime points for current location
            //fusedLocationClient
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000); // 10 seconds to test intervals
            mLocationRequest.setFastestInterval(10000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(MapsActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //Location Permission already granted
                    fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    mMap.setMyLocationEnabled(true);
                } else {
                    //Request Location Permission
                    checkLocationPermission();
                }
            }
            else {
                fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mMap.setMyLocationEnabled(true);
            }
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
    private void clearMarkersAndCircles(List<Marker> markers,List<Circle> radius) {
        for (Marker marker : markers) {

            marker.remove();
        }
        markers.clear();
        for (Circle circle : radius) {
            circle.remove();
        }
        radius.clear();
    }
    public int getMarkerColor(String crimeType) {
        if (crimeType.contains("Break and Enter"))
            return getResources().getColor(R.color.Break);
        if (crimeType.contains("Mischief"))
            return getResources().getColor(R.color.Mischief);
        if (crimeType.contains("Offence Against a Person"))
            return getResources().getColor(R.color.Offense);
        if (crimeType.contains("Other Theft"))
            return getResources().getColor(R.color.OtherTheft);
        if (crimeType.contains("Theft from Vehicle"))
            return getResources().getColor(R.color.TheftFromVehicle);
        if (crimeType.contains("Theft of Bicycle"))
            return getResources().getColor(R.color.TheftOfBicycle);
        if (crimeType.contains("Theft of Vehicle"))
            return getResources().getColor(R.color.TheftOfVehicle);
        if (crimeType.contains("Vehicle Collision"))
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
        if(dataString[0].contains(crimeType)) {
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
        if(dataString[0].contains(crimeType)) {
            marker.setVisible(true);
        }
        for (Circle circle : searchRadius) {
            String strColor = String.format("#%08X", 0x27FFFFFF & getMarkerColor(crimeType));
            if (circle.getFillColor() == Color.parseColor(strColor)) {
                circle.setVisible(true);
            }
        }
    }

    public void setVisibilityOfCurrentMarkerandCircle(String crimeType,List<Marker> markers,List<Circle> radius) {
        if (crimeType.contains("Break and Enter")) {
            if (filter.get("Break and Enter")!= null && filter.get("Break and Enter")) {
                markers.get(markers.size()-1).setVisible(true);
                radius.get(radius.size()-1).setVisible(true);
            }
            else {
                markers.get(markers.size()-1).setVisible(false);
                radius.get(radius.size()-1).setVisible(false);
                Log.e("temp", "false");
            }
        }
        if (crimeType.contains("Mischief")) {
            if (filter.get("Mischief")!= null && filter.get("Mischief")) {
                markers.get(markers.size()-1).setVisible(true);
                radius.get(radius.size()-1).setVisible(true);
            }
            else {
                markers.get(markers.size()-1).setVisible(false);
                radius.get(radius.size()-1).setVisible(false);
            }
        }
        if (crimeType.contains("Offense")) {
            if (filter.get("Offense")!= null && filter.get("Offense")) {
                markers.get(markers.size()-1).setVisible(true);
                radius.get(radius.size()-1).setVisible(true);
            }
            else {
                markers.get(markers.size()-1).setVisible(false);
                radius.get(radius.size()-1).setVisible(false);
            }
        }
        if (crimeType.contains("Offense")) {
            if (filter.get("Offense")!= null && filter.get("Offense")) {
                markers.get(markers.size()-1).setVisible(true);
                radius.get(radius.size()-1).setVisible(true);
            }
            else {
                markers.get(markers.size()-1).setVisible(false);
                radius.get(radius.size()-1).setVisible(false);
            }
        }
        if (crimeType.contains("Theft from Vehicle")) {
            if (filter.get("Theft from Vehicle")!= null && filter.get("Theft from Vehicle")) {
                markers.get(markers.size()-1).setVisible(true);
                radius.get(radius.size()-1).setVisible(true);
            }
            else {
                markers.get(markers.size()-1).setVisible(false);
                radius.get(radius.size()-1).setVisible(false);
            }
        }
        if (crimeType.contains("Theft of Bicycle")) {
            if (filter.get("Theft Of Bicycle")!= null && filter.get("Theft Of Bicycle")) {
                markers.get(markers.size()-1).setVisible(true);
                radius.get(radius.size()-1).setVisible(true);
            }
            else {
                markers.get(markers.size()-1).setVisible(false);
                radius.get(radius.size()-1).setVisible(false);
            }
        }
        if (crimeType.contains("Theft of Vehicle")) {
            if (filter.get("Theft Of Vehicle")!= null && filter.get("Theft Of Vehicle")) {
                markers.get(markers.size()-1).setVisible(true);
                radius.get(radius.size()-1).setVisible(true);
            }
            else {
                markers.get(markers.size()-1).setVisible(false);
                radius.get(radius.size()-1).setVisible(false);
            }
        }
        if (crimeType.contains("Vehicle Collision")) {
            if (filter.get("Vehicle Collision")!= null && filter.get("Vehicle Collision")) {
                markers.get(markers.size()-1).setVisible(true);
                radius.get(radius.size()-1).setVisible(true);
            }
            else {
                markers.get(markers.size()-1).setVisible(false);
                radius.get(radius.size()-1).setVisible(false);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (abdt.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
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


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }


    private LatLng getSearchResultLocation() {
        String location = searchView.getQuery().toString();
        List<Address> addressList = new ArrayList<>();
        LatLng latLng = null;
        if (!location.equals("")) {
            Geocoder geocoder = new Geocoder(MapsActivity.this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressList.size() < 1) {
                Toast.makeText(MapsActivity.this, "No search results.", Toast.LENGTH_LONG).show();
            }
//                    searchView.clearFocus();
            Address address = addressList.get(0);
            latLng = new LatLng(address.getLatitude(), address.getLongitude());
        }
        return latLng;
    }
    private boolean generateCrimeEventMarkers(LatLng latLng,List<Marker> markers,List<Circle> radius) {
            int count = 50;
            clearMarkersAndCircles(markers,radius);
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
                        markers.add(mMap.addMarker(new MarkerOptions().position(marker).icon(getMarkerIcon(strColor)).snippet(crimeEvent.toString())));
                        radius.add(mMap.addCircle(new CircleOptions()
                                .center(marker)
                                .radius(15)
                                .strokeWidth(0.5f)
                                .fillColor(Color.parseColor(strColor))));
                        count--;
                        setVisibilityOfCurrentMarkerandCircle(crimeEvent.getTYPE(),markers,radius);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            markers.add(mMap.addMarker(new MarkerOptions().position(latLng)));
            return true;
        }

        private void createCrimeEventSlideUp(List<Marker> markers) {
            List<Marker> list = new ArrayList<>(markers);
            list = sortListIntoBuckets(list);
            CrimeEventMarkerListAdapter adapter = new CrimeEventMarkerListAdapter(MapsActivity.this, list);
            lvCrimeEventsSlideUp.setAdapter(adapter);
        }
}
