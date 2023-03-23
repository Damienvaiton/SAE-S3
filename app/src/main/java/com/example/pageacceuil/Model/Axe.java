package com.example.pageacceuil.Model;

import com.github.mikephil.charting.components.YAxis;

public class Axe {
    private  YAxis leftAxis;
    private  YAxis rightAxis;
    private static volatile Axe instance;

    public YAxis getLeftAxis() {
        return leftAxis;
    }

    public void setLeftAxis(YAxis leftAxis) {
        this.leftAxis = leftAxis;
    }

    public YAxis getRightAxis() {
        return rightAxis;
    }

    public void setRightAxis(YAxis rightAxis) {
        this.rightAxis = rightAxis;
    }

    public Axe() {
        this.leftAxis = new YAxis();
        this.rightAxis = new YAxis();
    }

    public static Axe getInstance() {

        Axe result = instance;
        if (result != null) {
            return result;
        }
        synchronized (Axe.class) {
            if (instance == null) {
                instance = new Axe();
            }
            return instance;
        }
    }
}
