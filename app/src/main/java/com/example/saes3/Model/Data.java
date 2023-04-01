package com.example.saes3.Model;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Data extends ArrayList<Data> implements Serializable {
    private float humidite;
    private float temperature;
    private float light;
    private float O2;
    private float CO2;
    private String temps;

    public Data() {
    }

    public Data(float humidite, float temperature, float light, float o2, float CO2, String temps) {
        this.humidite = humidite;
        this.temperature = temperature;
        this.light = light;
        this.O2 = o2;
        this.CO2 = CO2;
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
        return O2;
    }

    public void setO2(float o2) {
        O2 = o2;
    }

    public float getCO2() {
        return CO2;
    }

    public void setCO2(float CO2) {
        this.CO2 = CO2;
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
        message+=a.format(getCO2()) + "% | ";
        message+=a.format(getO2()) + "% | ";
        message+=a.format(getLight()) + "L";
        return message;
    }
}