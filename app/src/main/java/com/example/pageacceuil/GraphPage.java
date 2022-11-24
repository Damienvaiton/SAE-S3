package com.example.pageacceuil;

import static java.lang.Thread.sleep;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class GraphPage extends AppCompatActivity implements View.OnClickListener  {

    private LineChart graph;
    private CheckBox boxTemp;
    private CheckBox boxO2;
    private CheckBox boxLux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph_page);


        boxTemp=(CheckBox)findViewById(R.id.boxTemp);
        boxTemp.setOnClickListener(this);

        boxO2=(CheckBox)findViewById(R.id.boxO2);
        boxO2.setOnClickListener(this);

        boxLux=(CheckBox)findViewById(R.id.boxLux);
        boxLux.setOnClickListener(this);

        graph = (LineChart) findViewById(R.id.lineChart);


        graph.setDrawGridBackground(false);
        graph.getDescription().setEnabled(false);
        graph.setDrawBorders(false);

        graph.getAxisLeft().setEnabled(false);
        graph.getAxisRight().setDrawAxisLine(false);
        graph.getAxisRight().setDrawGridLines(false);
        graph.getXAxis().setDrawAxisLine(false);
        graph.getXAxis().setDrawGridLines(false);

        // enable touch gestures
        graph.setTouchEnabled(true);

        // enable scaling and dragging
        graph.setDragEnabled(true);
        graph.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        graph.setPinchZoom(false);



        creaGraph();


    }



    void creaGraph() {

        graph.resetTracking();
        graph.clear();


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        if(boxO2.isChecked()) {

            ArrayList<Entry> O2 = new ArrayList<>();

            O2.add(new Entry(0, (float) (Math.random() * (40 - 25))));
            O2.add(new Entry(1, (float) (Math.random() * (40 - 25))));
            O2.add(new Entry(2, (float) (Math.random() * (40 - 25))));
            O2.add(new Entry(3, (float) (Math.random() * (40 - 25))));

            LineDataSet set = new LineDataSet(O2, "yo");
            dataSets.add(set);
        }

        if(boxLux.isChecked()) {

            ArrayList<Entry> O2 = new ArrayList<>();

            O2.add(new Entry(0, (float) (Math.random() * (40 - 25))));
            O2.add(new Entry(1, (float) (Math.random() * (40 - 25))));
            O2.add(new Entry(2, (float) (Math.random() * (40 - 25))));
            O2.add(new Entry(3, (float) (Math.random() * (40 - 25))));

            LineDataSet set = new LineDataSet(O2, "yo");
            dataSets.add(set);
        }

      if(boxTemp.isChecked()) {

          ArrayList<Entry> O2 = new ArrayList<>();

          O2.add(new Entry(0, (float) (Math.random() * (40 - 25))));
          O2.add(new Entry(1, (float) (Math.random() * (40 - 25))));
          O2.add(new Entry(2, (float) (Math.random() * (40 - 25))));
          O2.add(new Entry(3, (float) (Math.random() * (40 - 25))));

          LineDataSet set = new LineDataSet(O2, "yo");
          dataSets.add(set);
      }



        LineData data = new LineData(dataSets);

        graph.setData(data);

    }


    @Override

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.boxTemp:
                System.out.println("oe");
                creaGraph();
                break;
            case R.id.boxO2:
                System.out.println("oe");
                creaGraph();
                break;
            case R.id.boxLux:
                System.out.println("dld");
                creaGraph();
                break;
            default:
                break;

        }
    }

    ;
}