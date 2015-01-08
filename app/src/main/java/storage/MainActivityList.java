package storage;

/**
 * Created by Brayo on 7/23/2014.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.kisese.brayo.wazonote.MainActivity;
import com.kisese.brayo.wazonote.R;

public class MainActivityList  extends ActionBarActivity {
    RegistrationAdapter adapter_ob;
    RegistrationOpenHelper helper_ob;
    SQLiteDatabase db_ob;
    ListView nameList;
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_main);
        nameList = (ListView) findViewById(R.id.lv_name);
        adapter_ob = new RegistrationAdapter(this);



        String[] from = { helper_ob.LINK, helper_ob.HEADLINE};
        int[] to = { R.id.linksy, R.id.headeline};

        cursor = adapter_ob.queryName();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                R.layout.row, cursor, from, to);
        nameList.setAdapter(cursorAdapter);
        nameList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Bundle passdata = new Bundle();
                Cursor listCursor = (Cursor) arg0.getItemAtPosition(arg2);
                int nameId = listCursor.getInt(listCursor
                        .getColumnIndex(helper_ob.KEY_ID));
                // Toast.makeText(getApplicationContext(),
                // Integer.toString(nameId), 500).show();
                passdata.putInt("keyid", nameId);
                Intent passIntent = new Intent(MainActivityList.this,
                        EditActivity.class);
                passIntent.putExtras(passdata);
                startActivity(passIntent);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.notifyAll();
        //Inflate the ActionBar with this menu layout
        inflater.inflate(R.menu.restore_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //on actionBar item clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_restore:
                Toast.makeText(this, "Preparing to Restore from SD Card", Toast.LENGTH_SHORT).show();
                //add a function t create the dialog
                Intent intent = new Intent(MainActivityList.this, AndroidExplorer.class);
               // intent.putExtra("headline", headline.getText());
               // intent.putExtra("description", description.getText());
                MainActivityList.this.startActivity(intent);
                //regadapter.deletOneRecord(rowId);
                finish();
                break;




            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onResume() {
        super.onResume();
        cursor.requery();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivityList.this,MainActivity.class);
        MainActivityList.this.startActivity(intent);
        super.onBackPressed();
    }


    @SuppressWarnings("deprecation")
    private void alertYaEmpty(){
        final AlertDialog alertView = new AlertDialog.Builder(this).create();
        alertView.setTitle("     Oops!!");
        alertView.setMessage("Looks like you have no saved notes present");
        alertView.setButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                alertView.dismiss();
            }
        });
        alertView.show();
    }

}

