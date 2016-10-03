package com.meizu.voiceassistant.commandType.weather;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by weichangtan on 16/9/23.
 */

public class heweather extends AsyncTask<String, Void, String> {
    private String httpArg;
    private WeatherGet weather;

    public heweather(String httpArg, WeatherGet weather) {
        this.httpArg = httpArg;
        this.weather=weather;
    }

    @Override
    protected String doInBackground(String... params) {
        String httpUrl = "http://apis.baidu.com/heweather/weather/free";

        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "0fae3dfc07157836aae3c8cefdc2777e");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(result);

        return result;


    }

    @Override
    protected void onPostExecute(String s) {
        weather.getweather(s);
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
