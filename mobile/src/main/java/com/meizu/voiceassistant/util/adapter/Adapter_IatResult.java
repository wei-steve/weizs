package com.meizu.voiceassistant.util.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meizu.voiceassistant.commandType.stock.SinaStockInfo;
import com.meizu.voiceassistant.util.ListData;
import com.meizu.voiceassistant.util.TTS;
import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.iatService.IntentService_TTS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weichangtan on 16/1/7.
 */
public class Adapter_IatResult extends BaseAdapter
{
    private static String TAG = Adapter_IatResult.class.getSimpleName();
    private List<ListData> lists;
    private Context mContext;
    private RelativeLayout layout;

    public Adapter_IatResult(List<ListData> lists, Context mContext)
	{
        this.lists = lists;
        this.mContext = mContext;
    }

    @Override
    public int getCount()
	{
        return lists.size();
    }

    @Override
    public Object getItem(int position)
	{
        return lists.get(position);
    }

    @Override
    public long getItemId(int position)
	{
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
	{
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //命令发送窗口
        if (lists.get(position).getFlag() == ListData.answer)
		{
            layout = (RelativeLayout) inflater.inflate(R.layout.item_left, null);
            TextView tv = (TextView) layout.findViewById(R.id.tv);
            tv.setText(lists.get(position).getContent());
            tv.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{
						Intent closstts = new Intent(mContext, IntentService_TTS.class);
						closstts.putExtra("closs", true);
						mContext.startService(closstts);
					}
				});
        }
        //回答结果窗口
        if (lists.get(position).getFlag() == ListData.voicecommand)
		{
            layout = (RelativeLayout) inflater.inflate(R.layout.item_right, null);
            TextView tv = (TextView) layout.findViewById(R.id.tv);
            tv.setText(lists.get(position).getContent());
            tv.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{
						Intent closstts = new Intent(mContext, IntentService_TTS.class);
						closstts.putExtra("closs", true);
						mContext.startService(closstts);
					}
				});
        }
        //英语窗口
        if (lists.get(position).getFlag() == ListData.english)
        {
            layout = (RelativeLayout) inflater.inflate(R.layout.item_english, null);
            Button btn = (Button) layout.findViewById(R.id.btn_english);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(mContext, IntentService_TTS.class);
                    intent.putExtra("text", lists.get(position).getContent());
                    intent.putExtra("language", TTS.zh);
                    mContext.startService(intent);
                }
            });
            TextView tv = (TextView) layout.findViewById(R.id.tv);
            tv.setText(lists.get(position).getContent());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent closstts = new Intent(mContext, IntentService_TTS.class);
                    closstts.putExtra("closs", true);
                    mContext.startService(closstts);
                }
            });
        }
        //搜索窗口
        if (lists.get(position).getFlag() == ListData.search)
        {
            layout = (RelativeLayout) inflater.inflate(R.layout.item_search, null);
            WebView webView = (WebView) layout.findViewById(R.id.webView);
            webView.loadUrl("http://m.baidu.com/s?word="+lists.get(position).getContent());
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // TODO Auto-generated method stub
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
            });

        }
        //股票窗口
        if (lists.get(position).getFlag() == ListData.stock)
		{
            layout = (RelativeLayout) inflater.inflate(R.layout.item_stock, null);
            String stockdate=lists.get(position).getContent();

            ArrayList<SinaStockInfo> list = new ArrayList<SinaStockInfo>(10);
            try {
                list.add(SinaStockInfo.parseStockInfo(stockdate));
            }catch (SinaStockInfo.ParseStockInfoException e){
                e.printStackTrace();
            }
            TextView tv1 = (TextView) layout.findViewById(R.id.tv1);
            TextView tv2 = (TextView) layout.findViewById(R.id.tv2);
            TextView tv3 = (TextView) layout.findViewById(R.id.tv3);
            ImageView iv1 = (ImageView) layout.findViewById(R.id.iv1);
            tv1.setText(list.get(0).getName());
            tv2.setText(list.get(0).getDate() + list.get(0).getTime());
            tv3.setText(list.get(0).getNowPrice() + "  " + list.get(0).getPercentPrice());
            iv1.setImageBitmap(lists.get(position).getBm());
//            ShowResultActivity.handler.sendEmptyMessageDelayed(ListData.stock,5000);
            Log.d(TAG, "股票窗口lists.get(position): "+position);
        }

        return layout;
    }
}
