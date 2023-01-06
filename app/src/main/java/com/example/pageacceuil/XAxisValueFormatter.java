package com.example.pageacceuil;


import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class XAxisValueFormatter extends IndexAxisValueFormatter {

    private final ListData listData;


    public XAxisValueFormatter(ListData listData) {
        this.listData = listData;
    }

 /*
     @Override
      public String getAxisLabel(float value, AxisBase axis) {
          return listData.recup_data(GraphPage.indice-1).getTemps();
      }





           public String getAxisLabel(float value, AxisBase axis) {
          return super.getAxisLabel(listData.recup_data(GraphPage.indice-1).getTemps(),1);
      }
  }*/@Override
    public String getFormattedValue(float value) {
     System.out.println(value);
        return listData.recup_data((int) value-1).getTemps();
    }
}

