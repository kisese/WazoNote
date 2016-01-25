package com.kisese.brayo.wazonote.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.kisese.brayo.wazonote.R;

/**
 * Created by Brayo on 1/23/2015.
 */
public class MyListAdapter extends SimpleCursorAdapter {

    private int lastPosition = -1;
    Context context;
    private static LayoutInflater inflater=null;
    String[] from;
    int position;
    Cursor cursor;

    public MyListAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to) {
        super(context, layout, cursor, from, to);
        this.from = from;
        this.cursor = cursor;
        this.context = context;
        position = from.length;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        view.startAnimation(animation);
        lastPosition = position;
        // Create the idno textview with background image
        TextView idno = (TextView) view.findViewById(R.id.headeline);
        idno.setText(cursor.getString(2));

        // create the material textview
        TextView materials = (TextView) view.findViewById(R.id.linksy);
        materials.setText(cursor.getString(1));
    }


}
