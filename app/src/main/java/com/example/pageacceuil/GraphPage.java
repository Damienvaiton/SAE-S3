package com.example.pageacceuil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GraphPage extends AppCompatActivity implements View.OnClickListener  {

    private LineChart graph;
    private CheckBox boxTemp;
    private CheckBox boxO2;
    private CheckBox boxLux;
    private Button btnAjout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph_page);

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

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

       /* graph.setDrawGridBackground(true);
        graph.getDescription().setEnabled(true);
        graph.setDrawBorders(true);*/

        graph.getAxisLeft().setEnabled(true);
        graph.getAxisRight().setDrawAxisLine(true);
        graph.getAxisRight().setDrawGridLines(true);
        graph.getXAxis().setDrawAxisLine(true);
        graph.getXAxis().setDrawGridLines(true);



        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        if(boxO2.isChecked()) {

            ArrayList<Entry> O2 = new ArrayList<>();

            O2.add(new Entry(0, (float) (Math.random() * (40 - 25))));
            O2.add(new Entry(1, (float) (Math.random() * (40 - 25))));
            O2.add(new Entry(2, (float) (Math.random() * (40 - 25))));
            O2.add(new Entry(3, (float) (Math.random() * (40 - 25))));

            LineDataSet set = new LineDataSet(O2, "O2");

            set.setLineWidth(2.5f);
            set.setCircleRadius(4f);


            set.setColor(R.color.teal_200);
            set.setCircleColor(R.color.orange);


            dataSets.add(set);
        }

        if(boxLux.isChecked()) {

            ArrayList<Entry> O2 = new ArrayList<>();

            O2.add(new Entry(0, (float) (Math.random() * (40 - 25))));
            O2.add(new Entry(1, (float) (Math.random() * (40 - 25))));
            O2.add(new Entry(2, (float) (Math.random() * (40 - 25))));
            O2.add(new Entry(3, (float) (Math.random() * (40 - 25))));

            LineDataSet set = new LineDataSet(O2, "Lux");

            set.setColor(R.color.jaunorange);
            set.setCircleColor(R.color.black);

            set.setLineWidth(2.5f);
            set.setCircleRadius(4f);

            dataSets.add(set);
        }

      if(boxTemp.isChecked()) {

          ArrayList<Entry> O2 = new ArrayList<>();

          O2.add(new Entry(0, (float) (Math.random() * (40 - 25))));
          O2.add(new Entry(1, (float) (Math.random() * (40 - 25))));
          O2.add(new Entry(2, (float) (Math.random() * (40 - 25))));
          O2.add(new Entry(3, (float) (Math.random() * (40 - 25))));

          LineDataSet set = new LineDataSet(O2, "Température");

          set.setColor(R.color.orange);
          set.setCircleColor(R.color.orange);

          set.setLineWidth(2.5f);
          set.setCircleRadius(4f);

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
            case R.id.btnAdd:
                Intent addData;
                addData = new Intent(GraphPage.this, connectetu.class);
                startActivity(addData);
            default:
                break;

        }
    }

    ;
}