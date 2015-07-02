package inszoom.com.zoomleeaddon.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import inszoom.com.zoomleeaddon.R;
import inszoom.com.zoomleeaddon.adapter.PlaceDetails;
import inszoom.com.zoomleeaddon.adapter.PlaceDetailsListAdapter;
import inszoom.com.zoomleeaddon.database.SQLiteHelper;

public class PlaceWeatherDetailsActivity extends ActionBarActivity {

    ListView place_weather_details_activity_list;

    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_weather_details);

        Intent intent = getIntent();
        String placeFilter = intent.getStringExtra("trip_name");

        //initialize views
        place_weather_details_activity_list = (ListView) findViewById(R.id.place_weather_details_activity_list);

        //initialize database classes
        sqLiteHelper = new SQLiteHelper(this);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();



        //populate list
        populateList(placeFilter);


    }

    private  void populateList(String placeFilter){

        List<PlaceDetails> placeDetailsList = new ArrayList<PlaceDetails>();

        String getPlaces = "select * from " + SQLiteHelper.TABLE_NAME + " where "+ SQLiteHelper.TRIP_NAME+" = 'Trip1'";
        Cursor c = sqLiteDatabase.rawQuery(getPlaces, null);

        if(c.moveToFirst()){
            //placeDetailsList
            PlaceDetails placeDetails =new PlaceDetails();
            placeDetails.setDate(c.getString(c.getColumnIndex(SQLiteHelper.DATE)));
            placeDetails.setPlaceName(c.getString(c.getColumnIndex(SQLiteHelper.PLACE_NAME)));
            placeDetails.setLatLng(c.getString(c.getColumnIndex(SQLiteHelper.LAT))
                    + "," + c.getString(c.getColumnIndex(SQLiteHelper.LNG)));

            placeDetailsList.add(placeDetails);
        }

        while(c.moveToNext()){
            //placeDetailsList
            PlaceDetails placeDetails =new PlaceDetails();
            placeDetails.setDate(c.getString(c.getColumnIndex(SQLiteHelper.DATE)));
            placeDetails.setPlaceName(c.getString(c.getColumnIndex(SQLiteHelper.PLACE_NAME)));
            placeDetails.setLatLng(c.getString(c.getColumnIndex(SQLiteHelper.LAT))
                    + "," + c.getString(c.getColumnIndex(SQLiteHelper.LNG)));

            placeDetailsList.add(placeDetails);
        }

        for(PlaceDetails p : placeDetailsList){
            Log.d("places",p.getPlaceName()+" -- "+p.getLatLng()+ " -- "+p.getDate());
        }


        PlaceDetailsListAdapter adapter = new PlaceDetailsListAdapter(placeDetailsList,this);
        place_weather_details_activity_list.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_weather_details, menu);
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
