package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import android.graphics.drawable.GradientDrawable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private static final String LOCATION_SEPARATOR = " of ";
    public EarthquakeAdapter(Context context,List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView==null){
               listItemView = LayoutInflater.from(getContext()).inflate(
                       R.layout.earthquake_list_item,parent,false);
        }
        Earthquake currentEarthquake = getItem(position);

        String original = currentEarthquake.getLocation();
        String location, distance;
        if(original.contains(LOCATION_SEPARATOR)){
            String[] parts = original.split(LOCATION_SEPARATOR);
            distance = parts[0] + LOCATION_SEPARATOR;
            location = parts[1];
        }
        else{
            distance = getContext().getString(R.string.near_the);
            location = original;
        }

        TextView magnitudeView =(TextView) listItemView.findViewById(R.id.magnitude);
        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(currentEarthquake.getMagnitude());
        magnitudeView.setText(output);

        TextView distanceView =(TextView) listItemView.findViewById(R.id.distance);
        distanceView.setText(distance);

        TextView locationView =(TextView) listItemView.findViewById(R.id.location);
        locationView.setText(location);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        Date dateObject = new Date(currentEarthquake.getTimeInMilliSeconds());

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = currentEarthquake.formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = currentEarthquake.formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        return listItemView;
    }

    public int getMagnitudeColor(double mag){
        switch ((int)mag){
            case 1:  return ContextCompat.getColor(getContext(), R.color.magnitude1);
            case 2:  return ContextCompat.getColor(getContext(), R.color.magnitude2);
            case 3:  return ContextCompat.getColor(getContext(), R.color.magnitude3);
            case 4:  return ContextCompat.getColor(getContext(), R.color.magnitude4);
            case 5:  return ContextCompat.getColor(getContext(), R.color.magnitude5);
            case 6:  return ContextCompat.getColor(getContext(), R.color.magnitude6);
            case 7:  return ContextCompat.getColor(getContext(), R.color.magnitude7);
            case 8:  return ContextCompat.getColor(getContext(), R.color.magnitude8);
            case 9:  return ContextCompat.getColor(getContext(), R.color.magnitude9);
        }
        return ContextCompat.getColor(getContext(), R.color.magnitude10plus);
    }
}
