package com.example.pageacceuil;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class GraphPage extends AppCompatActivity {

    private LineChart graph;
    private CheckBox boxTemp;
    private CheckBox boxO2;
    private CheckBox boxLux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_page);
        boxTemp=(CheckBox)findViewById(R.id.boxTemp);
        boxTemp=(CheckBox)findViewById(R.id.boxO2);
        boxTemp=(CheckBox)findViewById(R.id.boxLux);

        creaGraph();


    }

    ;


    //region Description
    //void creaSpinner(){


    void creaGraph() {

        graph = (LineChart) findViewById(R.id.lineChart);

        ArrayList<Entry> O2 = new ArrayList<>();

        O2.add(new Entry(0, (float) (Math.random() * (40 - 25))));
        O2.add(new Entry(1, (float) (Math.random() * (40 - 25))));
        O2.add(new Entry(2, (float) (Math.random() * (40 - 25))));
        O2.add(new Entry(3, (float) (Math.random() * (40 - 25))));

        ArrayList<Entry> Lux = new ArrayList<>();

        Lux.add(new Entry(0, (float) (Math.random() * (40 - 25))));
        Lux.add(new Entry(1, (float) (Math.random() * (40 - 25))));
        Lux.add(new Entry(2, (float) (Math.random() * (40 - 25))));
        Lux.add(new Entry(3, (float) (Math.random() * (40 - 25))));


        LineDataSet set = new LineDataSet(O2, "yo");
        LineDataSet set1 = new LineDataSet(Lux, "yo");
        set.setFillAlpha(110);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        graph.setData(data);

    }

    boxTemp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)  {
                // Your code
            } else {
                // Your code
            }
        }
    });
}