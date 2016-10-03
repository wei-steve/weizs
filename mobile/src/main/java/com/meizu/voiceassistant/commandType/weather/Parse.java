package com.meizu.voiceassistant.commandType.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weichangtan on 16/9/23.
 */

public class Parse {
    public Parse() {

    }

    public static WeatherBean resolveWeatherInf(String strPar) {
        WeatherBean weatherBean = new WeatherBean();
        try {
            JSONObject dataOfJson = new JSONObject(strPar);
            JSONArray jsonArray = dataOfJson.getJSONArray("HeWeather data service 3.0");

            JSONObject jsonArray0 = jsonArray.getJSONObject(0);

            //aqi 空气质量解析
            if (jsonArray0.has("aqi")) {    //只有国内才有aqi
                JSONObject aqi = jsonArray0.getJSONObject("aqi");
                JSONObject city = aqi.getJSONObject("city");
                weatherBean.setAqi(city.getString("aqi"));//aqi 空气质量指数*
                weatherBean.setPm10(city.getString("pm10"));//	PM10 1小时平均值(ug/m³)
                weatherBean.setPm25(city.getString("pm25"));//PM2.5 1小时平均值(ug/m³)
                weatherBean.setCo(city.getString("co"));//
                weatherBean.setNo2(city.getString("no2"));//
                weatherBean.setO3(city.getString("o3"));//
                weatherBean.setQlty(city.getString("qlty"));//
                weatherBean.setSo2(city.getString("so2"));//
            }

            //basic 城市基本信息
            JSONObject basic = jsonArray0.getJSONObject("basic");
            weatherBean.setCity(basic.getString("city"));//城市名称
            weatherBean.setId(basic.getString("id"));//城市ID
            weatherBean.setCnty(basic.getString("cnty"));//国家名称
            weatherBean.setLat(basic.getString("lat"));//纬度
            weatherBean.setLon(basic.getString("lon"));//经度
            if (basic.has("update")) {
                JSONObject update = basic.getJSONObject("update");
                weatherBean.setLoc(update.getString("loc"));//数据更新的当地时间
                weatherBean.setUtc(update.getString("utc"));//数据更新的UTC时间
            }

            //now 当前温度
            JSONObject now = jsonArray0.getJSONObject("now");//实况天气

            JSONObject now_cond = now.getJSONObject("cond");//天气状况
            weatherBean.setCond_code(now_cond.getString("code"));//天气代码
            weatherBean.setCond_txt(now_cond.getString("txt"));//天气描述

            weatherBean.setFl(now.getString("fl"));
            weatherBean.setHum(now.getString("hum"));
            weatherBean.setPcpn(now.getString("pcpn"));
            weatherBean.setPres(now.getString("pres"));
            weatherBean.setTmp(now.getString("tmp"));//当前温度(摄氏度)
            weatherBean.setVis(now.getString("vis"));

            JSONObject now_wind = now.getJSONObject("wind");
            weatherBean.setWind_deg(now_wind.getString("deg"));
            weatherBean.setWind_dir(now_wind.getString("dir"));
            weatherBean.setWind_sc(now_wind.getString("sc"));
            weatherBean.setWind_spd(now_wind.getString("spd"));

            //daily_forecast  连续七天的天气数据解析
            List<OneDayWeatherInfs> list = new ArrayList<>();
            JSONArray daily_forecast = jsonArray0.getJSONArray("daily_forecast");//天气预报
            for (int i = 0; i < 7; i++) {
                OneDayWeatherInfs oneDayWeatherInfs = new OneDayWeatherInfs();
                JSONObject oneDayWeather = daily_forecast.getJSONObject(i);
                JSONObject cond = oneDayWeather.getJSONObject("cond");//天气状况
                JSONObject temp = oneDayWeather.getJSONObject("tmp");//温度
                JSONObject wind = oneDayWeather.getJSONObject("wind");//风力状况
                oneDayWeatherInfs.setWindDir(wind.getString("dir"));//	风向(方向)
                oneDayWeatherInfs.setWindSc(wind.getString("sc"));//风力等级
                oneDayWeatherInfs.setWindSpd(wind.getString("spd"));//风速(Kmph)
                oneDayWeatherInfs.setTmpMax(temp.getInt("max"));//最高温度(摄氏度)
                oneDayWeatherInfs.setTmpMin(temp.getInt("min"));//最低温度(摄氏度)
                oneDayWeatherInfs.setCondD(cond.getString("txt_d"));//白天天气描述
                oneDayWeatherInfs.setCondN(cond.getString("txt_n"));//夜间天气描述
                oneDayWeatherInfs.setDate(oneDayWeather.getString("date"));//当地日期
                list.add(oneDayWeatherInfs);
            }
            weatherBean.setOneDayWeatherInfs(list);
            //suggestion 生活指数*
            if (jsonArray0.has("suggestion")) {
                JSONObject suggesttion = jsonArray0.getJSONObject("suggestion");
                JSONObject comf = suggesttion.getJSONObject("comf");
                weatherBean.setComfBrf(comf.getString("brf"));
                weatherBean.setComfTxt(comf.getString("txt"));
                JSONObject cw = suggesttion.getJSONObject("cw");
                weatherBean.setCwBrf(cw.getString("brf"));
                weatherBean.setCwTxt(cw.getString("txt"));
                JSONObject drsg = suggesttion.getJSONObject("drsg");
                weatherBean.setDrsgBrf(drsg.getString("brf"));
                weatherBean.setDrsgTxt(drsg.getString("txt"));
                JSONObject flu = suggesttion.getJSONObject("flu");
                weatherBean.setFluBrf(flu.getString("brf"));
                weatherBean.setFluTxt(flu.getString("txt"));
                JSONObject sport = suggesttion.getJSONObject("sport");
                weatherBean.setSportBrf(sport.getString("brf"));
                weatherBean.setSportTxt(sport.getString("txt"));
                JSONObject trav = suggesttion.getJSONObject("trav");
                weatherBean.setTravBrf(trav.getString("brf"));
                weatherBean.setTravTxt(trav.getString("txt"));
                JSONObject uv = suggesttion.getJSONObject("uv");
                weatherBean.setUvBrf(uv.getString("brf"));
                weatherBean.setUvTxt(uv.getString("txt"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherBean;
    }
}
