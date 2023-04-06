package com.example.saes3.Model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DataTest {
    private Data data;

    @Before
    public void setUp() throws Exception {
        data = new Data(10.0f, 11.0f, 12.0f, 13.0f, 14.00f, "16:16:16");
    }

    @Test
    public void getHumidite() {
        assertEquals(10.0f, data.getHumidite(), 0.0);
    }

    @Test
    public void setHumidite() {
        data.setHumidite(9.0f);
        assertEquals(9.0f, data.getHumidite(), 0.0);
    }

    @Test
    public void getTemperature() {
        assertEquals(11.0f, data.getTemperature(), 0.0);
    }

    @Test
    public void setTemperature() {
        data.setTemperature(8.0f);
        assertEquals(8.0f, data.getTemperature(), 0.0);
    }

    @Test
    public void getLight() {
        assertEquals(12.0f, data.getLight(), 0.0);
    }

    @Test
    public void setLight() {
        data.setLight(7.0f);
        assertEquals(7.0f, data.getLight(), 0.0);
    }

    @Test
    public void getO2() {
        assertEquals(13.0f, data.getO2(), 0.0);
    }

    @Test
    public void setO2() {
        data.setO2(6.0f);
        assertEquals(6.0f, data.getO2(), 0.0);
    }

    @Test
    public void getCO2() {
        assertEquals(14.0f, data.getCo2(), 0.0);
    }

    @Test
    public void setCO2() {
        data.setCo2(5.0f);
        assertEquals(5.0f, data.getCo2(), 0.0);
    }

    @Test
    public void getTemps() {
        assertEquals(data.getTemps(), "16:16:16");
    }

    @Test
    public void setTemps() {
        data.setTemps("15:15:15");
        assertEquals(data.getTemps(), "15:15:15");
    }
}