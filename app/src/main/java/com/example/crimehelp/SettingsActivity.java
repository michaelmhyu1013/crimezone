package com.example.crimehelp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    public static SharedPreferences sp;
    public static final String radius = "radius";
    public static final String items = "items";
    private Spinner etMaxRadius;
    private Spinner etMaxItems;
    private static final int defaultMaxItems = 9;
    private static final int defaultMaxRadius = 6;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sp = getSharedPreferences("Settings", 0);


        // set Defaults
        etMaxItems = findViewById(R.id.edit_max_marker_count);
        etMaxRadius = findViewById(R.id.edit_max_radius);

        etMaxItems.setSelection(getCountPreferenceValue(items));
        etMaxRadius.setSelection(getRadiusPreferenceValue(radius));
    }

    @Override
    protected void onStart() {
        super.onStart();
        etMaxItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                writeToMaxCount(items, position);
                Toast.makeText(SettingsActivity.this, "Max items updated!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                writeToMaxCount(items, defaultMaxItems);
            }

        });

        etMaxRadius.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                writeToMaxRadius(items, position);
                Toast.makeText(SettingsActivity.this, "Radius updated!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                writeToMaxRadius(items, defaultMaxRadius);

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        etMaxItems.setSelection(getCountPreferenceValue(items));
        etMaxRadius.setSelection(getRadiusPreferenceValue(radius));
        System.out.println("Debug line");
    }

    public int getCountPreferenceValue(String value) {
        SharedPreferences sp = getSharedPreferences("Settings", 0);
        int val = sp.getInt(items, defaultMaxItems);
        return val;
    }

    public int getRadiusPreferenceValue(String value) {
        SharedPreferences sp = getSharedPreferences("Settings", 0);
        int val = sp.getInt(radius, defaultMaxRadius);
        return val;
    }

    public void writeToMaxCount(String name, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(name, 0).edit();
        editor.putInt(items, value);
        Log.e("Commited max count", "e");
        editor.commit();
    }

    public void writeToMaxRadius(String name, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(name, 0).edit();
        editor.putInt(radius, value);
        Log.e("Commited max radius", "e");
        editor.commit();
    }
}

