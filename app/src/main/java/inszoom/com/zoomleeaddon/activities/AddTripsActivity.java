package inszoom.com.zoomleeaddon.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import inszoom.com.zoomleeaddon.R;
import inszoom.com.zoomleeaddon.database.SQLiteHelper;


public class AddTripsActivity extends ActionBarActivity {

    EditText add_trips_activity_place_name;
    Button add_trips_activity_place_date;
    Button add_trips_activity_add_btn;
    ListView add_trips_activity_temp_list_places;

    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trips);

        // initializing views
        add_trips_activity_place_name = (EditText) findViewById(R.id.add_trips_activity_place_name);
        add_trips_activity_place_date = (Button) findViewById(R.id.add_trips_activity_place_date);
        add_trips_activity_add_btn = (Button) findViewById(R.id.add_trips_activity_add_btn);
        add_trips_activity_temp_list_places = (ListView) findViewById(R.id.add_trips_activity_temp_list_places);

        //initialize database classes
        sqLiteHelper = new SQLiteHelper(this);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        add_trips_activity_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv =  new ContentValues();

                cv.put(SQLiteHelper.TRIP_NAME, "");
                cv.put(SQLiteHelper.PLACE_NAME,add_trips_activity_place_name.getText().toString());
                cv.put(SQLiteHelper.DATE, "");
                cv.put(SQLiteHelper.LAT, "");
                cv.put(SQLiteHelper.LNG,"");

                sqLiteDatabase.insert(SQLiteHelper.TABLE_NAME, null, cv);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_trips, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
