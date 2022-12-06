package com.example.pageacceuil;

import java.text.DecimalFormat;


public class Data {

    private float humidite;
    private float temperature;
    private float lux;
    private float O2;
    private float CO2;
    private String temps;

    public Data() {
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

    public float getLux() {
        return lux;
    }

    public void setLux(float lux) {
        this.lux = lux;
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
}
