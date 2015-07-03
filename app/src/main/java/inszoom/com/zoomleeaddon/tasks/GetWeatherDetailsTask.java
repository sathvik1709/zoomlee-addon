package inszoom.com.zoomleeaddon.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sathvik on 02/07/15.
 */
public class GetWeatherDetailsTask extends AsyncTask<String,String,List<String>> {

    private final WeakReference<TextView> tempTextViewReference;
    private final WeakReference<TextView> summaryTextViewReference;

    public GetWeatherDetailsTask(TextView tempTextView,TextView summaryTextView) {
        tempTextViewReference = new WeakReference<TextView>(tempTextView);
        summaryTextViewReference = new WeakReference<TextView>(summaryTextView);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(String... params) {

        List<String> resList = new ArrayList<String>();

        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet("https://api.forecast.io/forecast/78b85733d202d012dbf8513150f28a0e/" +
                params[0] +
                ","+params[1]+"T12:00:00-0400");
        Log.d("inputs",params[0]+","+params[1]);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                //json parsing
                JSONObject jsonObject = new JSONObject(builder.toString());

                //Log.d("temp",String.valueOf(jsonObject.getJSONObject("currently").getDouble("temperature")));

                resList.add(String.valueOf(jsonObject.getJSONObject("currently").getDouble("temperature")));
                resList.add(String.valueOf(jsonObject.getJSONObject("currently").getString("summary")));


                return resList;
            } else {
                Log.d("Error", "error");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        resList.add("no info available");
        resList.add("no info available");
        return resList;
    }

    @Override
    protected void onPostExecute(List<String> s) {
        super.onPostExecute(s);

        if (tempTextViewReference != null && summaryTextViewReference != null) {
            final TextView tempTextView = tempTextViewReference.get();
            final TextView summaryTextView = summaryTextViewReference.get();

            if(tempTextView != null && summaryTextView != null){
                tempTextView.setText(s.get(0));
                summaryTextView.setText(s.get(1));
            }
        }

    }
}
