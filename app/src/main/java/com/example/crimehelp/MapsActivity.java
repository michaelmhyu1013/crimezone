package com.example.crimehelp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MainActivity";
    private GoogleMap mMap;
    SearchView searchView;
    SupportMapFragment mapFragment;
    private List<CrimeEventMarker> crimeEventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        crimeEventsList = new ArrayList<>();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        new DownloadFilesTask().execute();
    }

    private class DownloadFilesTask extends AsyncTask<Void, Void, List<CrimeEventMarker>> {

        protected void onPostExecute(List<CrimeEventMarker> result) {
            int num = 4000;
            for(CrimeEventMarker crimeEvent : result) {
                try {
                    if(num < 0){
                        return;
                    }
                    num--;
                    UTM2Deg deg = new UTM2Deg(Double.parseDouble(crimeEvent.getX()),Double.parseDouble(crimeEvent.getY()));
                    LatLng marker = new LatLng(deg.getLatitude(),deg.getLongitude());
                    //mMap.addMarker(new MarkerOptions().position(marker));
                    mMap.addCircle(new CircleOptions()
                            .center(marker)
                            .radius(250)
                            .strokeWidth(0f)
                            .fillColor(0x030000FF));
                }catch(Exception e) {
                    Log.d(TAG, e.toString());
                    continue;
                }

            }
            Log.d(TAG, "Completed async task");
        }

        @Override
        protected List<CrimeEventMarker> doInBackground(Void... voids) {
            BufferedReader reader;
            try{
                final InputStream file = getAssets().open("crimeData.txt");
                reader = new BufferedReader(new InputStreamReader(file));
                Gson gson = new Gson();
                Type type = new TypeToken<List<CrimeEventMarker>>(){}.getType();
                String line = reader.readLine();
                crimeEventsList = gson.fromJson(line, type);

                String x = "";
            } catch(IOException ioe){
                ioe.printStackTrace();
            }

            return crimeEventsList;
        }
    }


    public void openSettingsActivity(View v) {
        Intent openSettings = new Intent(this, SettingsActivity.class);
        startActivity(openSettings);
    }
}
