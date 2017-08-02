package com.example.elmira.sci;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProfileCustomList extends ArrayAdapter<String> {
    private String[] names;
    private String[] values;
    private Activity context;

    public ProfileCustomList(Activity context, String[] names, String[] values) {
        super(context, R.layout.profile_row, names);
        this.context = context;
        this.names = names;
        this.values = values;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.profile_row, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.titleTextView);
        TextView textViewValue = (TextView) listViewItem.findViewById(R.id.valueTextView);

        textViewName.setText(names[position]);
        textViewValue.setText(values[position]);

        return  listViewItem;
    }
}
