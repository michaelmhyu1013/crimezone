package com.example.crimehelp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Date;
import java.util.GregorianCalendar;

public class CrimeEventInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater inflater = null;
    Context context;


    public CrimeEventInfoWindowAdapter(Context context, LayoutInflater inflater) {
        this.inflater = inflater;
        this.context = context;
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

            ImageView infoWindowImage = windowLayoutView.findViewById(R.id.infoWindowCrimeImage);
            infoWindowImage.setImageDrawable(generateDrawableId(dataString[0]));

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

    public Drawable generateDrawableId(String crimeType) {
        if (crimeType.contains("Break and Enter")) {
            return context.getResources().getDrawable(R.drawable.thiefblack, null);
        } else if (crimeType.contains("Mischief")) {
            return context.getResources().getDrawable(R.drawable.mischieficonblack, null);

        } else if (crimeType.contains("Offence Against a Person")) {
            return context.getResources().getDrawable(R.drawable.offenseblack, null);

        } else if (crimeType.contains("Other Theft")) {
            return context.getResources().getDrawable(R.drawable.theftblack, null);

        } else if (crimeType.contains("Theft from Vehicle")) {
            return context.getResources().getDrawable(R.drawable.theftfromvehicleiconblack, null);

        } else if (crimeType.contains("Theft of Bicycle")){
            return context.getResources().getDrawable(R.drawable.bicycleblack, null);

        } else if (crimeType.contains("Theft of Vehicle")) {
            return context.getResources().getDrawable(R.drawable.robbingcariconblack, null);

        } else {
            return context.getResources().getDrawable(R.drawable.vehiclecollisionblack, null);
        }
    }
}
