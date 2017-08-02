package com.example.elmira.sci;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> {
    private String[] names;
    private Integer[] imageId;
    private Activity context;

    public CustomList(Activity context, String[] names, Integer[] imageId) {
        super(context, R.layout.main_list_row, names);
        this.context = context;
        this.names = names;
        this.imageId = imageId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.main_list_row, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.list_item_text);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.list_item_icon);

        textViewName.setText(names[position]);
        image.setImageResource(imageId[position]);
        return  listViewItem;
    }
}
