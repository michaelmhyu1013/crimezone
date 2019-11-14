package com.example.crimehelp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CrimeEventListAdapter extends ArrayAdapter<CrimeEventMarker> {
    private Activity context;
    private List<CrimeEventMarker> crimeEventsList;

    public CrimeEventListAdapter(Activity context, List<CrimeEventMarker> crimeEventsList) {
        super(context, R.layout.bottom_sheet_crime_list_layout, crimeEventsList);
        this.context = context;
        this.crimeEventsList = crimeEventsList;
    }

    public CrimeEventListAdapter(Context context, int resource, List<CrimeEventMarker> objects, Activity context1, List<CrimeEventMarker> crimeEventsList) {
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
        TextView crimeEventTime = listViewItem.findViewById(R.id.crimeEventTime);
        try {
        CrimeEventMarker crimeEvent = crimeEventsList.get(position);

            crimeEventType.setText(crimeEvent.getTYPE());

            Date tvDate = new GregorianCalendar(Integer.parseInt(crimeEvent.getYEAR()),
                    Integer.parseInt(crimeEvent.getMONTH()), Integer.parseInt(crimeEvent.getDAY()),
                    Integer.parseInt(crimeEvent.getHOUR()), Integer.parseInt(crimeEvent.getMINUTE())).getTime();
            String date = "" + tvDate;
            crimeEventTime.setText(date);

        } catch (Exception e) {
            Log.e("ADAPTER ERROR", e.toString());
            e.printStackTrace();
        }

        return listViewItem;
    }

    public CrimeEventMarker getItem(int position){
        return crimeEventsList.get(position);
    }

}
