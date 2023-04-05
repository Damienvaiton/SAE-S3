package com.example.saes3.Util;


import com.example.saes3.Model.Data;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class XAxisValueFormatter extends IndexAxisValueFormatter {

    private ArrayList<Data> datas;

    /**
     * Default constructor
     *
     * @param datas ArrayList of Data
     */
    public XAxisValueFormatter(ArrayList<Data> datas) {
        this.datas = datas;
    }



    /**
     * Format the top X axe with the "Temps" fields
     */
    @Override
    public String getFormattedValue(float value) {
        if ((int) value == 0) {
            return "";
        }
        return datas.get((int) value).getTemps();
    }
}

