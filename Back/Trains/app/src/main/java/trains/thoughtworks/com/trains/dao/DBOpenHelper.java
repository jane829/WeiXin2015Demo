package trains.thoughtworks.com.trains.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jane on 15-5-21.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String START = "start";
    private static final String END = "end";
    private static final String DISTANCE = "distance";




    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists route_vector_table" +
                "(id integer primary key autoincrement," +
                "start text not null,"+                 // start of vector
                "end text not null," +                      // end of vector
                "distance long);");                 //distance from start to end

        ContentValues val = new ContentValues();
        val.put("start", "X");
        val.put("end", "Y");
        val.put("distance", 100);
        sqLiteDatabase.insert("route_vector_table", null, val);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
