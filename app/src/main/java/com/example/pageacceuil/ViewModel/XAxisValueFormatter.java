package com.example.pageacceuil.ViewModel;


import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.ListData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class XAxisValueFormatter extends IndexAxisValueFormatter {

    private ListData listData;


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

