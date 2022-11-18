package com.example.pageacceuil;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        //ArrayAdapter adapter= ArrayAdapter(this, android.R.layout.simple_spinner_item,@array/setTime);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.android.R.layout.simple_spinner_item, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


// Apply the adapter to the spinner
        refreshRate.setAdapter(adapter);




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
       // new Thread(new Runnable() {

        //@Override
        //public void run() {
          //  for(int i = 0; i < 500; i++) {
            //    runOnUiThread(new Runnable() {
              //      @Override
                //    public void run() {
                  //      addEntry();
                   // }
                //});

                //try {
                  //  Thread.sleep(1000);
                //} catch (InterruptedException e) {
                //}
            //}
        //}
    //}).start();


    }
