package com.example.crimehelp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    public static final String radius = "radius";
    public static final String items = "items";
    private Spinner etMaxRadius;
    private Spinner etMaxItems;
    private static final int defaultMaxItems = 9;
    private static final int defaultMaxRadius = 6;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        etMaxItems =  findViewById(R.id.edit_max_marker_count);
//        ArrayAdapter countAdapter = (ArrayAdapter) etMaxItems.getAdapter();
//        int countSpinnerPosition = countAdapter.getPosition(defaultMaxItems);
//
        etMaxRadius = findViewById(R.id.edit_max_radius);
//        ArrayAdapter radiusAdapter = (ArrayAdapter) etMaxRadius.getAdapter();
//        int radiusSpinnerPosition = radiusAdapter.getPosition(defaultMaxRadius);
//
//        etMaxItems.setSelection(countSpinnerPosition);
//        etMaxRadius.setSelection(radiusSpinnerPosition);
        writeToMaxCount(items, defaultMaxItems);
        writeToMaxRadius(radius, defaultMaxRadius);
        etMaxItems.setSelection(getCountPreferenceValue(items));
        etMaxRadius.setSelection(getRadiusPreferenceValue(radius));
    }

    @Override
    public void onPause() {
        super.onPause();
        writeToMaxCount(items, defaultMaxItems);
        writeToMaxRadius(radius, defaultMaxItems);
    }

    public int getCountPreferenceValue(String value) {
        SharedPreferences sp = getSharedPreferences(value, 0);
        return sp.getInt(getString(R.string.max_marker_count_50), defaultMaxItems);
    }

    public int getRadiusPreferenceValue(String value) {
        SharedPreferences sp = getSharedPreferences(value, 0);
        return sp.getInt(getString(R.string.max_marker_count_50), defaultMaxRadius);
    }

    public void writeToMaxCount(String name, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(name, 0).edit();
        editor.putInt(getString(R.string.max_marker_count_50), value);
        editor.commit();
    }

    public void writeToMaxRadius(String name, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(name, 0).edit();
        editor.putInt(getString(R.string._175), value);
        editor.commit();
    }
}

