package storage;

/**
 * Created by Brayo on 7/23/2014.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "NOTES_DB";
    public static final String TABLE_NAME = "notes";
    public static final int VERSION = 2;
    public static final String KEY_ID = "_id";
    public static final String LINK = "date";
    public static final String HEADLINE = "title";
    public static final String DESCRIPTION = "description";

    public static final String SCRIPT = "create table " + TABLE_NAME + " ("
            + KEY_ID + " integer primary key autoincrement, " +
            LINK+ " text not null, " +
            HEADLINE+ " text not null unique, " +
            DESCRIPTION + " text not null );";


    public NotesDBHelper(Context context, String name,
                         CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}
