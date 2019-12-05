package com.Planted.growaway;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Weather_icon extends AsyncTask<Void,Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(Void... voids) {
        URL url = null;
        try {
            url = new URL("http://openweathermap.org/img/wn/"+Weather_data.icon+"@2x.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpURLConnection.setReadTimeout(500);
            httpURLConnection.setConnectTimeout(500);
        try {
            httpURLConnection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        try {
            httpURLConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*InputStream is = null;
        try {
            is = httpURLConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }*/

        try (InputStream is = httpURLConnection.getInputStream()) {
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    } catch (IOException e) {
        return null;
        }

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        MainActivity.icon_img.setImageBitmap(bitmap);
    }
}
