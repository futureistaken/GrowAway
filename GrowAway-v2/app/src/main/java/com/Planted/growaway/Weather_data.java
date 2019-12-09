package com.Planted.growaway;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.Http;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

public class Weather_data extends AsyncTask <Void,Void,Void> {
    String data = "";
    static String icon = "";
    // process the API in the background and reads the JASON string
    @Override
    protected Void doInBackground(Void... voids) {
        try{
            String url = "http://api.openweathermap.org/data/2.5/weather?q=Santa Cruz,US&" +
                    "APPID=e89e583c78c2e72afd9d59d443a1ecd0";
            JSONObject json = readJsonFromUrl(url);
            JSONArray JA = json.getJSONArray("weather");
            // extract the info from the internal array from the JSON string
            for (int i = 0;i<JA.length();i++){
                JSONObject JO = (JSONObject) JA.get(i);
                data = (String) JO.get("description");
                icon = (String) JO.get("icon");
            }
        }
        catch (MalformedURLException e){}

        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.test.setText(MainActivity.test.getText()+this.data);
        if (this.data.toLowerCase().contains("rain")){
            Toast.makeText(MainActivity.cx,"Keep your low water plants inside",
                    Toast.LENGTH_LONG).show();
        }
        Weather_icon process = new Weather_icon();
        process.execute();
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

}
