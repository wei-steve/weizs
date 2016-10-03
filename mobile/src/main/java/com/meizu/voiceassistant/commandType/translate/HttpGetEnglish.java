package com.meizu.voiceassistant.commandType.translate;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by weichangtan on 16/1/5.
 */
public class HttpGetEnglish extends AsyncTask<String ,Void, String> {

    private HttpGetEnglishDateListener getenglishlistener;
    private String aurl;
    public HttpGetEnglish(String aurl,HttpGetEnglishDateListener getenglishlistener){
        this.aurl=aurl;
        this.getenglishlistener=getenglishlistener;
    }

    @Override
    protected String doInBackground(String... params) {

        URL url= null;
        try {
            url = new URL("http://fanyi.youdao.com/openapi.do?keyfrom=voiceassistant&key=108279316&type=data&doctype=json&version=1.1&q="+aurl);
            URLConnection connection=url.openConnection();
            InputStream is=connection.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            String line;
            StringBuffer sb=new StringBuffer();
            while ((line=br.readLine())!=null){
                sb.append(line);
                Log.d("tag", line);

            }
            br.close();
            isr.close();
            is.close();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        getenglishlistener.getEnglishDateUrl(s);
        super.onPostExecute(s);
    }
}
