package inszoom.com.zoomleeaddon.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sathvik on 02/07/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbo";
    public static final String TABLE_NAME = "trips_table";
    public static final String TRIP_NAME = "trip_name";
    public static final String PLACE_NAME = "place_name";
    public static final String DATE = "date";
    public static final String LAT = "latitude";
    public static final String LNG = "longitude";


    private final String createdb = "create table if not exists " + TABLE_NAME + " ( "
            + TRIP_NAME + " varchar(255), "
            + PLACE_NAME + " varchar(255), "
            + DATE + " datetime, "
            + LAT + " varchar(255), "
            + LNG + " varchar(255));";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(createdb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
        db.execSQL("drop table "+TABLE_NAME);
    }

}
