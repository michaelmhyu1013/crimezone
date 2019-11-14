package com.example.crimehelp;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CrimeEventInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private CrimeEventMarker marker;
    private LayoutInflater inflater = null;

    public CrimeEventInfoWindowAdapter(CrimeEventMarker marker, LayoutInflater inflater) {
        this.marker = marker;
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View windowLayoutView = inflater.inflate(R.layout.crime_event_window_layout, null);

        TextView tvCrimeType = windowLayoutView.findViewById(R.id.crimeTypeWindow);
        tvCrimeType.setText(this.marker.getTYPE());
        TextView tvCrimeHundredBlock = windowLayoutView.findViewById(R.id.hundredBlockWindow);
        tvCrimeHundredBlock.setText(this.marker.getHUNDRED_BLOCK());
        TextView tvCrimeNeighbourhood = windowLayoutView.findViewById(R.id.neighbourhoodWindow);
        tvCrimeNeighbourhood.setText(this.marker.getNEIGHBOURHOOD());
        TextView tvCrimeDate = windowLayoutView.findViewById(R.id.dateWindow);
        String date = this.marker.getHOUR() + ":" + this.marker.getMINUTE() + " " + this.marker.getDAY()
                + " " + this.marker.getMONTH() + " " + this.marker.getYEAR();
        tvCrimeDate.setText(date);

        return windowLayoutView;
    }
}
