package com.example.crimehelp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

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

        CrimeEventMarker crimeEvent = crimeEventsList.get(position);

        //System.out.println(toDoList);

        crimeEventType.setText(crimeEvent.getTYPE());
        crimeEventTime.setText(crimeEvent.getDAY() + " " + crimeEvent.getHOUR() + " " + crimeEvent.getMINUTE());
        return listViewItem;
    }


}
