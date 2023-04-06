package com.example.saes3.Model;


import java.io.Serializable;
import java.text.DecimalFormat;


public class Data implements Serializable {
    private float humidite;
    private float temperature;
    private float light;
    private float o2;
    private float co2;
    private String temps;

    public Data() {
    }

    public Data(float humidite, float temperature, float light, float o2, float co2, String temps) {
        this.humidite = humidite;
        this.temperature = temperature;
        this.light = light;
        this.o2 = o2;
        this.co2 = co2;
        this.temps = temps;
    }

    public float getHumidite() {
        return humidite;
    }

    public void setHumidite(float humidite) {
        this.humidite = humidite;
    }

    public float getTemperature() {
        return temperature;
    }


    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getLight() {
        return light;
    }

    public void setLight(float light) {
        this.light = light;
    }

    public float getO2() {
        return o2;
    }

    public void setO2(float o2) {
        this.o2 = o2;
    }

    public float getCo2() {
        return co2;
    }

    public void setCo2(float co2) {
        this.co2 = co2;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }


    @Override
    public String toString() {
        DecimalFormat a = new DecimalFormat("##.###");
        String message="";
        message+=(a.format(getTemperature()) + "° | ");
        message+=a.format(getHumidite()) + "% | ";
        message+=a.format(getCo2()) + "% | ";
        message+=a.format(getO2()) + "% | ";
        message+=a.format(getLight()) + "L";
        message+=getTemps();
        return message;

    }
}