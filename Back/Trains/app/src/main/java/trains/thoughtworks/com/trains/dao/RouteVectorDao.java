package trains.thoughtworks.com.trains.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import trains.thoughtworks.com.trains.entity.RouteVector;

/**
 * Created by jane on 15-5-21.
 */
public class RouteVectorDao {
    private static final int VERSION = 1;
    private static final String START = "start";
    private static final String END = "end";
    private static final String DISTANCE = "distance";
    private static final String DATABASE_NAME = "routes.db";
    private static final String TABLE_NAME = "route_vector_table";

    private Context mContext;
    private SQLiteOpenHelper mHelper;
    private SQLiteDatabase mDataBase;

    public RouteVectorDao(Context context) {
        this.mContext = context;
        mHelper = new DBOpenHelper(context, DATABASE_NAME, null, VERSION);
    }

    public long insert(RouteVector vector) {
        long result ;
        mDataBase = mHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(START, vector.getStart());
        cv.put(END, vector.getEnd());
        cv.put(DISTANCE, vector.getDistance());
        result = mDataBase.insert(TABLE_NAME, null, cv);
        return result;
    }

    public int delete() {
        mDataBase = mHelper.getReadableDatabase();
//        mDataBase.delete(TABLE_NAME,null,cusor);
        return 0;
    }

    public int update() {
        return 0;
    }

    /** query all the basic route vector & saved in list*/
    public List<RouteVector> query() {
        List<RouteVector> routeVectors = new ArrayList<RouteVector>();
        Cursor cusor = null;
        mDataBase = mHelper.getReadableDatabase();

        String sql = "select * from "+TABLE_NAME;
        cusor = mDataBase.rawQuery(sql,null);
        if(cusor != null) {
            while(cusor.moveToNext()) {
                RouteVector vector = new RouteVector();
                String[] colums = cusor.getColumnNames();
                for(String columName : colums) {
                    int index = cusor.getColumnIndex(columName);
                    if(START.equals(columName)) {
                        vector.setStart(cusor.getString(index));
                    } else if(END.equals(columName)) {
                        vector.setEnd(cusor.getString(index));
                    } else if(DISTANCE.equals(columName)) {
                        vector.setDistance(cusor.getLong(index));
                    }
                    routeVectors.add(vector);
                }
            }
        }
        return routeVectors;
    }
}
