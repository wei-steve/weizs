package com.meizu.voiceassistant.commandType.weather;

/**
 * Created by weichangtan on 16/9/23.
 */

public class OneDayWeatherInfs {

    private String condD;
    private String condN;
    private String date;
    private String windDir;
    private String windSc;
    private String windSpd;
    private int tmpMax;
    private int tmpMin;

    public OneDayWeatherInfs() {
    }

    public OneDayWeatherInfs(String condD, String condN, String date, int tmpMax, int tmpMin, String windDir, String windSc, String windSpd) {
        this.condD = condD;
        this.condN = condN;
        this.date = date;
        this.tmpMax = tmpMax;
        this.tmpMin = tmpMin;
        this.windDir = windDir;
        this.windSc = windSc;
        this.windSpd = windSpd;
    }

    public String getCondD() {
        return condD;
    }

    public void setCondD(String condD) {
        this.condD = condD;
    }

    public String getCondN() {
        return condN;
    }

    public void setCondN(String condN) {
        this.condN = condN;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTmpMax() {
        return tmpMax;
    }

    public void setTmpMax(int tmpMax) {
        this.tmpMax = tmpMax;
    }

    public int getTmpMin() {
        return tmpMin;
    }

    public void setTmpMin(int tmpMin) {
        this.tmpMin = tmpMin;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindSc() {
        return windSc;
    }

    public void setWindSc(String windSc) {
        this.windSc = windSc;
    }

    public String getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(String windSpd) {
        this.windSpd = windSpd;
    }

    @Override
    public String toString() {
        return "OneDayWeatherInfs{" +
                "condD='" + condD + '\'' +
                ", condN='" + condN + '\'' +
                ", date='" + date + '\'' +
                ", windDir='" + windDir + '\'' +
                ", windSc='" + windSc + '\'' +
                ", windSpd='" + windSpd + '\'' +
                ", tmpMax=" + tmpMax +
                ", tmpMin=" + tmpMin +
                '}';
    }
}
