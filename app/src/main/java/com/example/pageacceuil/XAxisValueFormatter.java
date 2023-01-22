package com.example.pageacceuil;


import static com.example.pageacceuil.GraphPage.graph;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class XAxisValueFormatter extends IndexAxisValueFormatter {

    private final ListData listData;
    private String previousValue="";


    public XAxisValueFormatter(ListData listData) {
        this.listData = listData;
    }
@Override

    public String getFormattedValue(float value) {
        if ((int)value==0 ){
            return "";
        }

    //System.out.println(graph.getXAxis().getFormattedLabel(2));

      return listData.recup_data((int) value-1).getTemps();
    }
}

