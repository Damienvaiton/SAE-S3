package com.example.pageacceuil;


import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class XAxisValueFormatter extends IndexAxisValueFormatter {

    private final ListData listData;


    public XAxisValueFormatter(ListData listData) {
        this.listData = listData;
    }

    @Override

    public String getFormattedValue(float value) {
        if ((int) value == 0) {
            return "";
        }
        return listData.recup_data((int) value - 1).getTemps();
    }
}

