package com.example.pageacceuil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class GraphPage extends AppCompatActivity {

    private LineChart graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_page);
        creaGraph();


    }
       void creaGraph(){

           graph= (LineChart) findViewById(R.id.lineChart);

           ArrayList<Entry> Yvalues = new ArrayList<>();

           Yvalues.add(new Entry(0,(float)5.5));
           Yvalues.add(new Entry(1,(float)6.4));
           Yvalues.add(new Entry(2,(float)7.6));
           Yvalues.add(new Entry(3,(float)8.3));

           LineDataSet set= new LineDataSet(Yvalues,"yo");

           set.setFillAlpha(110);

           ArrayList<ILineDataSet> dataSets = new ArrayList<>();
           dataSets.add(set);

           LineData data =new LineData(dataSets);

           graph.setData(data);
       }

}