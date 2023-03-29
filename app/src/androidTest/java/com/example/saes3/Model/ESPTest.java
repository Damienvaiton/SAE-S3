package com.example.saes3.Model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ESPTest {
    ESP esp;

    @Before
    public void setUp() throws Exception {
        esp = new ESP("A1:A1:A1:A1", "Test");
    }

    @Test
    public void getMacEsp() {
        assertEquals(esp.getMacEsp(), "A1:A1:A1:A1");
    }

    @Test
    public void setMacEsp() {
        esp.setMacEsp("B2:B2:B2:B2");
        assertEquals(esp.getMacEsp(), "B2:B2:B2:B2");
    }

    @Test
    public void getNomEsp() {
        assertEquals(esp.getNomEsp(), "Test");
    }

    @Test
    public void setNomEsp() {
        esp.setNomEsp("Test2");
        assertEquals(esp.getNomEsp(), "Test2");
    }
}