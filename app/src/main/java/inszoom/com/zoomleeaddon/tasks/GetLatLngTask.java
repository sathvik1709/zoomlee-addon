package inszoom.com.zoomleeaddon.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import inszoom.com.zoomleeaddon.taskResponce.GetLatLngTaskResponce;

/**
 * Created by Sathvik on 02/07/15.
 */
public class GetLatLngTask extends AsyncTask<String,String,String> {

    public GetLatLngTaskResponce responce = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        String url = "http://maps.google.com/maps/api/geocode/json?address="+params[0].replaceAll("\\s+","")+"&sensor=false";
        Log.d("ss",url);
        HttpGet httpGet = new HttpGet(url);
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

                if(jsonObject.getString("status").equalsIgnoreCase("OK")){
                    Double longitute = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                            .getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lng");

                    Double latitude = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                            .getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lat");

                    return String.valueOf(latitude)+","+String.valueOf(longitute);
                }
                //Log.d("ss",String.valueOf(latitude)+String.valueOf(longitute));

                return "no";
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

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("ss",s);
        responce.processResult(s);
        super.onPostExecute(s);
    }



}
