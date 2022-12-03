package com.example.pageacceuil;

public class Data {

    private float humidite;
    private float temperature;

    public Data() {
    }

    public Data(float humidite, float temperature) {
        this.humidite = humidite;
        this.temperature = temperature;
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
}
