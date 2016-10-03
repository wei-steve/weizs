package com.meizu.voiceassistant.commandType.stock;

import android.os.Bundle;

/**
 * Created by weichangtan on 16/1/5.
 */
public interface HttpGetStockDateListener {
    void getStockDateUrl(Bundle date,boolean refresh,int refreshNum,String stockNum);
}
