package com.example.saes3.ViewModel;


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

    @Override

    /**
     * Format the top X axe with the "Temps" fields
     */
    public String getFormattedValue(float value) {
        if ((int) value == 0) {
            return "";
        }
        return datas.get((int) value + 1).getTemps();
    }
}

