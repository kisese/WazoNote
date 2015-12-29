package storage;

/**
 * Created by Brayo on 7/23/2014.
 */

import android.app.AlertDialog;


import android.content.Context;
import android.content.DialogInterface;

import android.content.SharedPreferences;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.kisese.brayo.wazonote.MainActivity;
import com.kisese.brayo.wazonote.R;

public class MainActivityList  extends ListFragment {
    RegistrationAdapter adapter_ob;
    RegistrationOpenHelper helper_ob;
    SQLiteDatabase db_ob;
    ListView nameList;
    Cursor cursor;
    private SharedPreferences notes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notes = getActivity().getSharedPreferences("notes_status", Context.MODE_PRIVATE);

        if(!notes.getString("notes_empty", "").equals("no")){
            return inflater.inflate(R.layout.empty_layout, container, false);
        }else {
            // nameList = (ListView) container.findViewById(R.id.lv_name);
            return inflater.inflate(R.layout.db_main, container, false);
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.db_main);

        adapter_ob = new RegistrationAdapter(getActivity());
        String[] from = { helper_ob.LINK, helper_ob.HEADLINE};
        int[] to = { R.id.linksy, R.id.headeline};

        cursor = adapter_ob.queryName();
        MyListAdapter materials = new MyListAdapter(getActivity(), R.layout.row, cursor, from, to);
        setListAdapter(materials);



    }





    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        // TODO Auto-generated method stub
        Bundle passdata = new Bundle();
        Cursor listCursor = (Cursor) l.getItemAtPosition(position);
        int nameId = listCursor.getInt(listCursor
                .getColumnIndex(helper_ob.KEY_ID));
        // Toast.makeText(getApplicationContext(),
        // Integer.toString(nameId), 500).show();
        passdata.putInt("keyid", nameId);
        Intent passIntent = new Intent(getActivity(),
                EditActivity.class);
        passIntent.putExtras(passdata);
        startActivity(passIntent);

        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onResume() {
        super.onResume();
        cursor.requery();
    }

}

