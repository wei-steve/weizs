package com.meizu.voiceassistant.commandType.stock;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by weichangtan on 16/1/13.
 */
public class HttpGetStock extends AsyncTask<String, Void, Bundle> {
    private HttpGetStockDateListener getstocklistener;
    private int refreshNum;
    private boolean refresh;
    private String stockcode;
    private Handler handler;
    public HttpGetStock(String stockcode,int refreshNum,boolean refresh,HttpGetStockDateListener getstocklistener,Handler handler){
        this.stockcode=stockcode;
        this.refreshNum=refreshNum;
        this.refresh=refresh;
        this.getstocklistener=getstocklistener;
        this.handler=handler;
    }

    @Override
    protected Bundle doInBackground(String... params) {
        String TAG = HttpGetStock.class.getSimpleName();
        Bundle bundle =new Bundle();
//        bundle.putParcelable();
        URL url= null;
        try {
            url = new URL("http://hq.sinajs.cn/list="+stockcode);
            URLConnection connection=url.openConnection();
            InputStream is=connection.getInputStream();
            InputStreamReader isr= new InputStreamReader(new BufferedInputStream(is), Charset.forName("gbk"));
            BufferedReader br=new BufferedReader(isr);
            String line;
            StringBuffer sb=new StringBuffer();
            while ((line=br.readLine())!=null){
                sb.append(line);
                Log.d(TAG, "股票获取结果: "+line);

            }
            br.close();
            isr.close();
            is.close();
            bundle.putString("voicecommand",sb.toString());
//            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            url = new URL("http://image.sinajs.cn/newchart/min/n/"+stockcode+".gif");
            URLConnection connection=url.openConnection();
            InputStream is=connection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();

            is.close();
            bundle.putParcelable("bitmap",bm );
            return bundle;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Bundle s) {
//        if(refresh=false){
            getstocklistener.getStockDateUrl(s,refresh,refreshNum,stockcode);
//        }else{

//        }
//        getstocklistener.getStockDateUrl(s,refresh,refreshNum);




        super.onPostExecute(s);
    }
}
