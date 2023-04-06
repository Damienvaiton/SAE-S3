package com.example.saes3.Model;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

import org.junit.Test;

public class FirebaseAccessTest extends TestCase {
    @Test
    public void testLoadInData() {
            assertEquals(FirebaseAccess.getInstance().testFirebase(), true);
        }
    }
