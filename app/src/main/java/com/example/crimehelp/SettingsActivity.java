package com.example.crimehelp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    public static final String radius = "radius";
    public static final String items = "items";
    private EditText etMaxRadius;
    private EditText etMaxItems;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        etMaxItems = findViewById(R.id.edit_max_marker_count);
        etMaxRadius = findViewById(R.id.edit_max_radius);
        etMaxItems.setText(getPreferenceValue(items));
        etMaxRadius.setText(getPreferenceValue(radius));
    }

    public String getPreferenceValue(String value)
    {
        SharedPreferences sp = getSharedPreferences(value,0);
        String str = sp.getString("myStore","TheDefaultValueIfNoValueFoundOfThisKey");
        return str;
    }

    public void writeToPreference(String thePreference)
    {
        SharedPreferences.Editor editor = getSharedPreferences(thePreference,0).edit();
        editor.putString("myStore", thePreference);
        editor.commit();
    }
}

