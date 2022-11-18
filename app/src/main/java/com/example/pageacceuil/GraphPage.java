package com.example.pageacceuil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class GraphPage extends AppCompatActivity {

    private LineChart graph;
    private Spinner refreshRate;
    private Spinner valuesNeeded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_page);
        refreshRate=findViewById(R.id.refreshRate);
        valuesNeeded=findViewById(R.id.valuesNeeded);

        creaGraph();


        };




    //region Description
    //void creaSpinner(){





       void creaGraph() {

           graph = (LineChart) findViewById(R.id.lineChart);
           refreshRate = (Spinner) findViewById(R.id.refreshRate);
           valuesNeeded = (Spinner) findViewById(R.id.valuesNeeded);

           ArrayList<Entry> Yvalues = new ArrayList<>();

           Yvalues.add(new Entry(0, (float) (Math.random() * (40 - 25))));
           Yvalues.add(new Entry(1, (float) (Math.random() * (40 - 25))));
           Yvalues.add(new Entry(2, (float) (Math.random() * (40 - 25))));
           Yvalues.add(new Entry(3, (float) (Math.random() * (40 - 25))));

           LineDataSet set = new LineDataSet(Yvalues, "yo");
           set.setFillAlpha(110);
           ArrayList<ILineDataSet> dataSets = new ArrayList<>();
           dataSets.add(set);

           LineData data = new LineData(dataSets);

           graph.setData(data);

       }


    }
