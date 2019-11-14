package com.example.crimehelp;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Date;
import java.util.GregorianCalendar;

public class CrimeEventInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater inflater = null;

    public CrimeEventInfoWindowAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View windowLayoutView = inflater.inflate(R.layout.crime_event_window_layout, null);
        try {
            //TODO: if we click on a current location or searched marker what should we do
            windowLayoutView.setLayoutParams(new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.WRAP_CONTENT));
            String[] dataString =marker.getSnippet().split("~");

            TextView tvCrimeType = windowLayoutView.findViewById(R.id.crimeTypeWindow);
            tvCrimeType.setText(dataString[0]);
            TextView tvCrimeHundredBlock = windowLayoutView.findViewById(R.id.hundredBlockWindow);
            tvCrimeHundredBlock.setText(dataString[1]);
            TextView tvCrimeNeighbourhood = windowLayoutView.findViewById(R.id.neighbourhoodWindow);
            tvCrimeNeighbourhood.setText(dataString[2]);
            TextView tvCrimeDate = windowLayoutView.findViewById(R.id.dateWindow);
            tvCrimeDate.setText(dataString[3]);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return windowLayoutView;
    }
}
