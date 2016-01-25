package com.kisese.brayo.wazonote.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kisese.brayo.wazonote.R;

/**
 * Created by Brayo on 8/2/2014.
 */
public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] headers;

    SharedPreferences notes;

    public CustomList(Activity context, String[] headers) {
        super(context, R.layout.row, headers);
        this.context = context;
        this.headers = headers;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        notes = context.getSharedPreferences("secure", Context.MODE_PRIVATE);

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.row_c, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.headeline);
        txtTitle.setText(headers[position]);

        TextView txtDescription = (TextView) rowView.findViewById(R.id.linksy);
        txtDescription.setText(notes.getString(headers[position], null));


            return rowView;
        }

    }
