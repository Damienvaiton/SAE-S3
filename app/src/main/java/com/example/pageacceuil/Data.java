package com.example.pageacceuil;

import java.text.DecimalFormat;


public class Data {

    private float d_humi;
    private float d_temperature;
    private float d_lux;
    private float d_O2;
    private float d_CO2;
    private float d_heure;

    public Data() {
    }

    public Data(float d_humidite, float d_temperature) {
        DecimalFormat cast=new DecimalFormat("#.##");
        this.d_humi = Float.parseFloat(cast.format(d_humidite));
        this.d_temperature = Float.parseFloat(cast.format(d_temperature));
    }

    public Data(float d_humidite, float d_temperature, float d_O2, float d_CO2, float d_heure) {
        DecimalFormat cast=new DecimalFormat("#.##");
        this.d_humi = Float.parseFloat(cast.format(d_humidite));
        this.d_temperature = Float.parseFloat(cast.format(d_temperature));
        this.d_O2 = d_O2;
        this.d_CO2 = d_CO2;
        this.d_heure = d_heure;
    }

    public float getD_humidite() {return d_humi;}

    public void setHumidite(float d_humidite) {
        this.d_humi = d_humidite;
    }

    public float getTemperature() {
        return d_temperature;
    }

    public void setTemperature(float d_temperature) {
        this.d_temperature = d_temperature;
    }

    public float getLux() {
        return d_lux;
    }

    public void setLux(float d_lux) {
        this.d_lux = d_lux;
    }

    public float getO2() {
        return d_O2;
    }

    public void setO2(float d_O2) {
        this.d_O2 = d_O2;
    }

    public float getCO2() {
        return d_CO2;
    }

    public void setCO2(float d_CO2) {
        this.d_CO2 = d_CO2;
    }

    public float getHeure() {
        return d_heure;
    }

    public void setHeure(float d_heure) {
        this.d_heure = d_heure;
    }
}
