package com.example.pageacceuil;


import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class XAxisValueFormatter extends IndexAxisValueFormatter {

    private ListData listData;



    public XAxisValueFormatter(ListData listData) {
        this.listData = listData;
    }

    @Override
        public String getFormattedValue(float value) {
        System.out.println(1);
            return listData.recup_data(GraphPage.indice-1).getTemps();

        }
    }

