package com.example.crimehelp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.Marker;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CrimeEventMarkerListAdapter extends ArrayAdapter<Marker> {
    private Activity context;
    private List<Marker> crimeEventsList;

    public CrimeEventMarkerListAdapter(Activity context, List<Marker> crimeEventsList) {
        super(context, R.layout.bottom_sheet_crime_list_layout, crimeEventsList);
        this.context = context;
        this.crimeEventsList = crimeEventsList;
    }

    public CrimeEventMarkerListAdapter(Context context, int resource, List<Marker> objects, Activity context1, List<Marker> crimeEventsList) {
        super(context, resource, objects);
        this.context = context1;
        this.crimeEventsList = crimeEventsList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.bottom_sheet_crime_list_layout, null, true);

        TextView crimeEventType = listViewItem.findViewById(R.id.crimeEventType);
        crimeEventType.setTextColor(Color.WHITE);
        TextView crimeEventTime = listViewItem.findViewById(R.id.crimeEventTime);
        crimeEventTime.setTextColor(Color.WHITE);

        try {
            Marker crimeEvent = crimeEventsList.get(position);

            String[] crimeData = crimeEvent.getSnippet().split("~");

            crimeEventType.setText(crimeData[0]);
            crimeEventTime.setText(crimeData[3]);

        } catch (Exception e) {
            Log.e("ADAPTER ERROR", e.toString());
            e.printStackTrace();
        }

        return listViewItem;
    }

    public Marker getItem(int position) {
        return crimeEventsList.get(position);
    }

}
