package com.example.crimehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MainActivity";

    Location location;
    double longitude;
    double latitude;
    private GoogleMap mMap;
    DatabaseReference crimeEvents;
    SearchView searchView;
    SupportMapFragment mapFragment;
    private List<CrimeEventMarker> crimeEventsList;
    private BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        crimeEvents = FirebaseDatabase.getInstance().getReference("crimeEvents2");
        crimeEventsList = new ArrayList<>();
        // Obtain the crimeZoneDB
        //basicReadWrite();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //bottom-sheet init
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.maps_activity);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
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
        behavior.setPeekHeight(100);


        searchView = findViewById(R.id.sv_location);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (!location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(addressList.size() < 1) {
                        Toast.makeText(MapsActivity.this, "No search results.", Toast.LENGTH_LONG).show();
                        return false;
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

//        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//
//
//            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            longitude = location.getLongitude();
//            latitude = location.getLatitude();


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
        } else {
// Show rationale and request permission.
        }
        // Add a marker in Sydney and move the camera
//        LatLng test = new LatLng(longitude, latitude);
//        mMap.addMarker(new MarkerOptions().position(test).title("Marker in Current Location"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(test));
    }

    @Override
    protected void onStart() {
        super.onStart();
        crimeEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    CrimeEventMarker crimeEvent;
//                    if(snapshot.getValue() != null) {
//                        crimeEvent = snapshot.getValue(CrimeEventMarker.class);
//                        crimeEventsList.add(crimeEvent);
//                        Log.d(TAG, "Crime type is :" + crimeEvent.getTYPE());
//                        //CrimeEventMarker crimeEvent = dataSnapshot.getValue(CrimeEventMarker.class);
//                        UTM2Deg deg = new UTM2Deg(crimeEvent.getX(),crimeEvent.getY());
//                        LatLng marker = new LatLng(deg.getLatitude(),deg.getLongitude());
//                        mMap.addMarker(new MarkerOptions().position(marker));
//                        crimeEventsList.add(crimeEvent);
//                        Log.d(TAG, "Crime type is :" + crimeEvent.getTYPE());
//                    }
//                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // [END read_message]
    }

    public void openSettingsActivity(View v) {
        Intent openSettings = new Intent(this, SettingsActivity.class);
        startActivity(openSettings);
    }
}
