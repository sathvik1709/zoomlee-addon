package inszoom.com.zoomleeaddon.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import inszoom.com.zoomleeaddon.R;
import inszoom.com.zoomleeaddon.database.SQLiteHelper;
import inszoom.com.zoomleeaddon.taskResponce.GetLatLngTaskResponce;
import inszoom.com.zoomleeaddon.tasks.GetLatLngTask;


public class AddTripsActivity extends ActionBarActivity implements GetLatLngTaskResponce {

    EditText add_trips_activity_place_name;
    static Button add_trips_activity_place_date;
    Button add_trips_activity_add_btn;
    ListView add_trips_activity_temp_list_places;

    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabaseReadable;
    SQLiteDatabase sqLiteDatabaseWritable;

    static String datePicked;

    GetLatLngTask getLatLngTask;


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
        sqLiteDatabaseWritable = sqLiteHelper.getWritableDatabase();
        sqLiteDatabaseReadable = sqLiteHelper.getReadableDatabase();


        add_trips_activity_place_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        add_trips_activity_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeGetLatLng();


            }
        });

    }

    private void executeGetLatLng(){
        getLatLngTask = new GetLatLngTask();
        getLatLngTask.responce = this;
        getLatLngTask.execute(add_trips_activity_place_name.getText().toString());
    }

    @Override
    public void processResult(String res) {
        getLatLngTask = null;

        if(datePicked != null && add_trips_activity_place_name.getText().toString().length() != 0
                && res != null && !res.equalsIgnoreCase("no")){

            ContentValues cv =  new ContentValues();
            cv.put(SQLiteHelper.TRIP_NAME, "Trip1");
            cv.put(SQLiteHelper.PLACE_NAME,add_trips_activity_place_name.getText().toString());
            cv.put(SQLiteHelper.DATE, datePicked);
            cv.put(SQLiteHelper.LAT, res.split(",")[0]);
            cv.put(SQLiteHelper.LNG,res.split(",")[1]);
            sqLiteDatabaseWritable.insert(SQLiteHelper.TABLE_NAME, null, cv);

            datePicked = null;
            add_trips_activity_place_name.setText("");
            add_trips_activity_place_date.setText("Select date");
            updateListView();

            Toast.makeText(this,"Place added",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Invalid inputs",Toast.LENGTH_SHORT).show();
        }



    }

    private void updateListView(){

        List<String> tripPlaces = new ArrayList<String>();

        String getPlaces = "select * from " + SQLiteHelper.TABLE_NAME + " where "+ SQLiteHelper.TRIP_NAME+" = 'Trip1'";
        Cursor c = sqLiteDatabaseReadable.rawQuery(getPlaces, null);



        if(c.moveToFirst()){
            tripPlaces.add(c.getString(c.getColumnIndex(SQLiteHelper.PLACE_NAME)));
        }

        while(c.moveToNext()){
            tripPlaces.add(c.getString(c.getColumnIndex(SQLiteHelper.PLACE_NAME)));
        }

        Log.d("db", tripPlaces.toString());

        ArrayAdapter tripsListAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, tripPlaces);
        add_trips_activity_temp_list_places.setAdapter(tripsListAdapter);
    }

    // Date picker dialog fragment
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            datePicked = String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day);
            add_trips_activity_place_date.setText(datePicked);
        }

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
