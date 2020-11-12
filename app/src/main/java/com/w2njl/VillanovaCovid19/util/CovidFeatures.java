package com.w2njl.VillanovaCovid19.util;



public class CovidFeatures {
    private int id;
    private int RIS;
    private String danger;
   private int HR;
   private int SpO2;
   private double temp;
   private int TV;
   private int RR;
   private int MV;
    private String currentTime;

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public CovidFeatures(){

    }

    public CovidFeatures(String danger, int id, int RIS, int HR, int spO2, double temp, int TV, int RR, String currentTime) {
        this.danger = danger;
        this.id = id;
        this.RIS = RIS;
        this.HR = HR;
        this.SpO2 = spO2;
        this.temp = temp;
        this.TV = TV;
        this.RR = RR;
        this.currentTime = currentTime;



    }



    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRIS() {
        return RIS;
    }

    public void setRIS(int RIS) {
        this.RIS = RIS;
    }

    public int getHR() {
        return HR;
    }

    public void setHR(int HR) {
        this.HR = HR;
    }

    public int getSpO2() {
        return SpO2;
    }

    public void setSpO2(int spO2) {
        this.SpO2 = spO2;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getTV() {
        return TV;
    }

    public void setTV(int TV) {
        this.TV = TV;
    }

    public int getRR() {
        return RR;
    }

    public void setRR(int RR) {
        this.RR = RR;
    }



    @Override
    public String toString() {
        return "CovidFeatures{" +
                "id=" + id +
                ", RIS=" + RIS +
                ", HR=" + HR +
                ", SpO2=" + SpO2 +
                ", temp=" + temp +
                ", TV=" + TV +
                ", RR=" + RR +
                '}';
    }
}
