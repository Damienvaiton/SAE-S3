package com.example.pageacceuil;

import java.text.DecimalFormat;


public class Data {

    private float humidite;
    private float temperature;
    private float d_lux;
    private float d_O2;
    private float d_CO2;
    private String temps;

    public Data() {
    }

    public Data(float humidite, float temperature) {
        DecimalFormat cast=new DecimalFormat("#.##");
        this.humidite = humidite;
        this.temperature =temperature;
        System.out.println("yo");
    }

    public Data(float humidite, float temperature, float O2, float CO2, String heure) {
        DecimalFormat cast=new DecimalFormat("#.##");
        this.humidite = humidite;
        this.temperature = temperature;
        this.d_O2 = O2;
        this.d_CO2 = CO2;
        this.temps = heure;
    }

    public float getHumidite() {
        System.out.println("yo");
        System.out.println(humidite);
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

    public String getHeure() {
        return temps;
    }

    public String setHeure(String d_heure) {
        return this.temps = d_heure;
    }
}
