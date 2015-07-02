package inszoom.com.zoomleeaddon.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import inszoom.com.zoomleeaddon.R;
import inszoom.com.zoomleeaddon.database.SQLiteHelper;


public class MainActivity extends ActionBarActivity {

    FloatingActionButton floatingActionButton;
    LinearLayout no_trips_layout;
    ListView trips_list;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    List<String> userTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the views
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        no_trips_layout = (LinearLayout) findViewById(R.id.no_trips_layout);
        trips_list = (ListView) findViewById(R.id.trips_list);

        //initialize database classes
        sqLiteHelper = new SQLiteHelper(this);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();

        //initialize variables
        userTrips = new ArrayList<String>();

        //
        getUserTripList();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddTripsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void getUserTripList(){

        String groupByTrips = "select * from " + SQLiteHelper.TABLE_NAME + " group by "+ SQLiteHelper.TRIP_NAME;
        Cursor c = sqLiteDatabase.rawQuery(groupByTrips, null);

        if(c.moveToFirst()){
            userTrips.add(c.getString(c.getColumnIndex(SQLiteHelper.TRIP_NAME)));
        }else{
            no_trips_layout.setVisibility(View.VISIBLE);
        }
        while(c.moveToNext()){
            userTrips.add(c.getString(c.getColumnIndex(SQLiteHelper.TRIP_NAME)));
        }

        if(no_trips_layout.getVisibility() != View.GONE){
            ArrayAdapter tripsListAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, userTrips);
            trips_list.setAdapter(tripsListAdapter);
        }

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
