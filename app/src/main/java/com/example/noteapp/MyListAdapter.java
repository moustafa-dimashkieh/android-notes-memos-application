package com.example.noteapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.noteapp.R;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] titles;
    private final String[] dates;
    private final String[] previews;

    public MyListAdapter(Activity context, String[] titles, String[] dates, String[] previews) {
        super(context, R.layout.layout_list, titles);

        this.context=context;
        this.titles=titles;
        this.dates=dates;
        this.previews=previews;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.layout_list, null,true);

        TextView titleText = rowView.findViewById(R.id.title);
        TextView dateText = rowView.findViewById(R.id.date);
        TextView previewText = rowView.findViewById(R.id.preview);

        titleText.setText(titles[position]);
        dateText.setText(dates[position]);
        previewText.setText(previews[position]);

        return rowView;
    };
}
