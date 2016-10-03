package com.meizu.voiceassistant.commandType.weather;

import java.util.List;

/**
 * Created by weichangtan on 16/9/23.
 */

public class WeatherBean {
    //basic 城市基本信息
    //城市数据
    private String city;
    //城市ID
    private String id;
    //国家名称
    private String cnty;
    //纬度
    private String lat;
    //经度
    private String lon;
    //update 数据更新时间,24小时制
    //数据更新的当地时间
    private String loc;
    //数据更新的UTC时间
    private String utc;


    //aqi 空气质量指数*
    private String aqi;
    //city

    //PM10 1小时平均值(ug/m³)
    private String pm10;
    //PM2.5 1小时平均值(ug/m³)
    private String pm25;
    //二氧化硫1小时平均值(ug/m³)
    private String so2;
    //二氧化氮1小时平均值(ug/m³)
    private String no2;
    //一氧化碳1小时平均值(ug/m³)
    private String co;
    //臭氧1小时平均值(ug/m³)
    private String o3;
    //空气质量类别
    private String qlty;


    //now 实况天气
    //cond	天气状况
    //天气代码
    private String cond_code;
    //天气描述
    private String cond_txt;
    //体感温度
    private String fl;
    //湿度(%)
    private String hum;
    //降雨量(mm)
    private String pcpn;
    //气压
    private String pres;
    //tmp 实况天气
    private String tmp;
    //能见度(km)
    private String vis;
    //wind 	风力状况
    //风向(角度)
    private String wind_deg;
    //风向(方向)
    private String wind_dir;
    //风力等级
    private String wind_sc;
    //风速(Kmph)
    private String wind_spd;


    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getCond_code() {
        return cond_code;
    }

    public void setCond_code(String cond_code) {
        this.cond_code = cond_code;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public List<OneDayWeatherInfs> getmOneDayWeatherInfs() {
        return mOneDayWeatherInfs;
    }

    public void setmOneDayWeatherInfs(List<OneDayWeatherInfs> mOneDayWeatherInfs) {
        this.mOneDayWeatherInfs = mOneDayWeatherInfs;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(String wind_deg) {
        this.wind_deg = wind_deg;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getWind_sc() {
        return wind_sc;
    }

    public void setWind_sc(String wind_sc) {
        this.wind_sc = wind_sc;
    }

    public String getWind_spd() {
        return wind_spd;
    }

    public void setWind_spd(String wind_spd) {
        this.wind_spd = wind_spd;
    }

    //suggestion 生活指数*
    //drsg brf	穿衣指数 简介
    private String drsgBrf;
    //drsg txt 	穿衣指数 详情
    private String drsgTxt;

    //comf 	brg 舒适度 简介
    private String comfBrf;
    //comf 	txt 舒适度 详情
    private String comfTxt;

    //cw brf洗车指数 简介
    private String cwBrf;
    //cw txt洗车指数 详情
    private String cwTxt;

    //flu brf 感冒指数 简介
    private String fluBrf;
    //flu txt 感冒指数 详情
    private String fluTxt;

    //sport brf运动指数 简介
    private String sportBrf;
    //sport txt运动指数 详情
    private String sportTxt;

    //trav brf旅游指数 简介
    private String travBrf;
    //trav txt旅游指数 详情
    private String travTxt;

    //uv brf紫外线指数 简介
    private String uvBrf;
    //uv txt紫外线指数 详情
    private String uvTxt;

    private List<OneDayWeatherInfs> mOneDayWeatherInfs;

    public WeatherBean() {
    }

    public WeatherBean(String aqi, String city, String cnty, String comfBrf, String comfTxt, String cond_txt, String cwBrf, String cwTxt, String drsgBrf, String drsgTxt, String fluBrf, String fluTxt, List<OneDayWeatherInfs> oneDayWeatherInfs, String now, String pm10, String pm25, String sportBrf, String sportTxt, String travBrf, String travTxt, String uvBrf, String uvTxt) {
        this.aqi = aqi;
        this.city = city;
        this.cnty = cnty;
        this.comfBrf = comfBrf;
        this.comfTxt = comfTxt;
        this.cond_txt = cond_txt;
        this.cwBrf = cwBrf;
        this.cwTxt = cwTxt;
        this.drsgBrf = drsgBrf;
        this.drsgTxt = drsgTxt;
        this.fluBrf = fluBrf;
        this.fluTxt = fluTxt;
        mOneDayWeatherInfs = oneDayWeatherInfs;
        this.tmp = now;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.sportBrf = sportBrf;
        this.sportTxt = sportTxt;
        this.travBrf = travBrf;
        this.travTxt = travTxt;
        this.uvBrf = uvBrf;
        this.uvTxt = uvTxt;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getComfBrf() {
        return comfBrf;
    }

    public void setComfBrf(String comfBrf) {
        this.comfBrf = comfBrf;
    }

    public String getComfTxt() {
        return comfTxt;
    }

    public void setComfTxt(String comfTxt) {
        this.comfTxt = comfTxt;
    }

    public String getCond_txt() {
        return cond_txt;
    }

    public void setCond_txt(String cond_txt) {
        this.cond_txt = cond_txt;
    }

    public String getCwBrf() {
        return cwBrf;
    }

    public void setCwBrf(String cwBrf) {
        this.cwBrf = cwBrf;
    }

    public String getCwTxt() {
        return cwTxt;
    }

    public void setCwTxt(String cwTxt) {
        this.cwTxt = cwTxt;
    }

    public String getDrsgBrf() {
        return drsgBrf;
    }

    public void setDrsgBrf(String drsgBrf) {
        this.drsgBrf = drsgBrf;
    }

    public String getDrsgTxt() {
        return drsgTxt;
    }

    public void setDrsgTxt(String drsgTxt) {
        this.drsgTxt = drsgTxt;
    }

    public String getFluBrf() {
        return fluBrf;
    }

    public void setFluBrf(String fluBrf) {
        this.fluBrf = fluBrf;
    }

    public String getFluTxt() {
        return fluTxt;
    }

    public void setFluTxt(String fluTxt) {
        this.fluTxt = fluTxt;
    }

    public List<OneDayWeatherInfs> getOneDayWeatherInfs() {
        return mOneDayWeatherInfs;
    }

    public void setOneDayWeatherInfs(List<OneDayWeatherInfs> oneDayWeatherInfs) {
        mOneDayWeatherInfs = oneDayWeatherInfs;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getSportBrf() {
        return sportBrf;
    }

    public void setSportBrf(String sportBrf) {
        this.sportBrf = sportBrf;
    }

    public String getSportTxt() {
        return sportTxt;
    }

    public void setSportTxt(String sportTxt) {
        this.sportTxt = sportTxt;
    }

    public String getTravBrf() {
        return travBrf;
    }

    public void setTravBrf(String travBrf) {
        this.travBrf = travBrf;
    }

    public String getTravTxt() {
        return travTxt;
    }

    public void setTravTxt(String travTxt) {
        this.travTxt = travTxt;
    }

    public String getUvBrf() {
        return uvBrf;
    }

    public void setUvBrf(String uvBrf) {
        this.uvBrf = uvBrf;
    }

    public String getUvTxt() {
        return uvTxt;
    }

    public void setUvTxt(String uvTxt) {
        this.uvTxt = uvTxt;
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "aqi=" + aqi +
//                ", mOneDayWeatherInfs=" + mOneDayWeatherInfs +
                ", 温度tmp=" + tmp +
                ", pm10=" + pm10 +
                ", pm25=" + pm25 +
                ", 城市city='" + city + '\'' +
                ", cond_txt='" + cond_txt + '\'' +
                ", cnty='" + cnty + '\'' +
                ", comfBrf='" + comfBrf + '\'' +
                ", comfTxt='" + comfTxt + '\'' +
                ", cwBrf='" + cwBrf + '\'' +
                ", cwTxt='" + cwTxt + '\'' +
                ", drsgBrf='" + drsgBrf + '\'' +
                ", drsgTxt='" + drsgTxt + '\'' +
                ", fluBrf='" + fluBrf + '\'' +
                ", fluTxt='" + fluTxt + '\'' +
                ", sportBrf='" + sportBrf + '\'' +
                ", sportTxt='" + sportTxt + '\'' +
                ", travBrf='" + travBrf + '\'' +
                ", travTxt='" + travTxt + '\'' +
                ", uvBrf='" + uvBrf + '\'' +
                ", uvTxt='" + uvTxt + '\'' +
                '}';
    }

}
