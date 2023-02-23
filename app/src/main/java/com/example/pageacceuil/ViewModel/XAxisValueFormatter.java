package com.example.pageacceuil.ViewModel;


import com.example.pageacceuil.Model.Data;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class XAxisValueFormatter extends IndexAxisValueFormatter {

    private ArrayList<Data> datas;


    public XAxisValueFormatter(ArrayList<Data> datas) {
        this.datas = datas;
    }

    @Override

    public String getFormattedValue(float value) {
        if ((int) value == 0) {
            return "";
        }
        return datas.get((int) value+1 ).getTemps();
    }//A revoir
}

