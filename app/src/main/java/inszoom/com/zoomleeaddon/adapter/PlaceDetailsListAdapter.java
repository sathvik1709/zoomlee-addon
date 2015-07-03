package inszoom.com.zoomleeaddon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import inszoom.com.zoomleeaddon.R;
import inszoom.com.zoomleeaddon.tasks.GetWeatherDetailsTask;

/**
 * Created by Sathvik on 02/07/15.
 */
public class PlaceDetailsListAdapter extends BaseAdapter {

    List<PlaceDetails> placeDetailsList;
    Context context;

    public PlaceDetailsListAdapter(List<PlaceDetails> placeDetailsList, Context context) {
        this.placeDetailsList = placeDetailsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return placeDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return placeDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.place_details_list_item,null);
        TextView place_details_list_item_place_name = (TextView) convertView.findViewById(R.id.place_details_list_item_place_name);
        place_details_list_item_place_name.setMaxLines(1);
        TextView place_details_list_item_visit_date = (TextView) convertView.findViewById(R.id.place_details_list_item_visit_date);

        TextView place_details_list_item_temperature = (TextView) convertView.findViewById(R.id.place_details_list_item_temperature);
        TextView place_details_list_item_weather_summary = (TextView) convertView.findViewById(R.id.place_details_list_item_weather_summary);

        GetWeatherDetailsTask getWeatherDetailsTask =
                new GetWeatherDetailsTask(place_details_list_item_temperature,place_details_list_item_weather_summary);
        getWeatherDetailsTask.execute(placeDetailsList.get(position).getLatLng(),placeDetailsList.get(position).getDate());

        place_details_list_item_place_name.setText(placeDetailsList.get(position).getPlaceName());
        place_details_list_item_visit_date.setText(placeDetailsList.get(position).getDate());

        return convertView;
    }
}
