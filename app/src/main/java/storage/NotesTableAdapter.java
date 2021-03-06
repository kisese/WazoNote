package storage;

/**
 * Created by Brayo on 7/23/2014.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotesTableAdapter {

    SQLiteDatabase database_ob;
    NotesDBHelper openHelper_ob;
    Context context;

    public NotesTableAdapter(Context c) {
        context = c;
    }

    public NotesTableAdapter opnToRead() {
        openHelper_ob = new NotesDBHelper(context,
                openHelper_ob.DATABASE_NAME, null, openHelper_ob.VERSION);
        database_ob = openHelper_ob.getReadableDatabase();
        return this;

    }

    public NotesTableAdapter opnToWrite() {
        openHelper_ob = new NotesDBHelper(context,
                openHelper_ob.DATABASE_NAME, null, openHelper_ob.VERSION);
        database_ob = openHelper_ob.getWritableDatabase();
        return this;

    }

    public void Close() {
        database_ob.close();
    }

    public long insertDetails(String link, String headline, String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(openHelper_ob.LINK, link);
        contentValues.put(openHelper_ob.HEADLINE, headline);
        contentValues.put(openHelper_ob.DESCRIPTION, description);
        opnToWrite();
        long val = database_ob.insert(openHelper_ob.TABLE_NAME, null,
                contentValues);
        Close();
        return val;

    }

    public Cursor queryName() {
        String[] cols = { openHelper_ob.KEY_ID, openHelper_ob.LINK,
                openHelper_ob.HEADLINE , openHelper_ob.DESCRIPTION};
        opnToWrite();
        Cursor c = database_ob.query(openHelper_ob.TABLE_NAME, cols, null,
                null, null, null,  openHelper_ob.LINK + " desc");

        return c;

    }

    public Cursor queryAll(int nameId) {
        String[] cols = { openHelper_ob.KEY_ID, openHelper_ob.LINK,
                openHelper_ob.HEADLINE , openHelper_ob.DESCRIPTION};
        opnToWrite();
        Cursor c = database_ob.query(openHelper_ob.TABLE_NAME, cols,
                openHelper_ob.KEY_ID + "=" + nameId, null, null, null,   openHelper_ob.LINK + " desc");

        return c;

    }

    public long updateldetail(int rowId, String link, String headline) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(openHelper_ob.LINK, link);
        contentValues.put(openHelper_ob.HEADLINE, headline);
        opnToWrite();
        long val = database_ob.update(openHelper_ob.TABLE_NAME, contentValues,
                openHelper_ob.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }

    public int deletOneRecord(int rowId) {
        // TODO Auto-generated method stub
        opnToWrite();
        int val = database_ob.delete(openHelper_ob.TABLE_NAME,
                openHelper_ob.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }


}
