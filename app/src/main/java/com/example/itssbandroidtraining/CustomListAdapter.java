package com.example.itssbandroidtraining;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<MainActivity.Developer> {
    ArrayList<MainActivity.Developer> developers;
    Activity context;

    public CustomListAdapter(@NonNull Activity context, ArrayList<MainActivity.Developer> developers) {
        super(context, R.layout.customlistview,developers);
        this.context=context;
        this.developers=developers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View rowView=layoutInflater.inflate(R.layout.customlistview,null,true);
        MainActivity.Developer developer=getItem(position);
        TextView txtName= rowView.findViewById(R.id.txtListViewName);
        TextView txtDesg=rowView.findViewById(R.id.txtListViewDesg);
        rowView.findViewById(R.id.imageDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                developers.remove(position);
                notifyDataSetChanged();
            }
        });
        txtName.setText(developer.name);
        txtDesg.setText(developer.designation);
        return rowView;
    }
}
