package com.example.pageacceuil.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pageacceuil.Model.Data;
//import com.example.pageacceuil.Model.DataUpdate;
import com.example.pageacceuil.R;
import com.example.pageacceuil.ViewModel.GraphViewModel;
import com.example.pageacceuil.ViewModel.XAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;

//, DataUpdate
public class GraphiqueActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private LineChart graph;
    /**
     * TextView to display real time value at the top of the screen
     */
    private TextView viewO2;
    private TextView viewCO2;
    private TextView viewLux;
    private TextView viewTemp;
    private TextView viewHumi;
    private TextView valTemp;
    /**
     * CheckBox for the choice of which value must be in the graph
     */
    private CheckBox boxO2;
    private CheckBox boxCO2;
    private CheckBox boxTemp;
    private CheckBox boxHumi;
    private CheckBox boxLux;
    /**
     * Object for the control of the NavBar
     */
    private BottomAppBar bottomNav;
    private BottomNavigationView bottomNavigationView;
    /**
     * Object axis on the graph
     */
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xl;
    /**
     * The ViewModel of this view
     */
    private GraphViewModel graphViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_page);

        graphViewModel = new ViewModelProvider(this).get(GraphViewModel.class);


/**
 * Observer of new LineData object available to refrest current graph
 */
        graphViewModel.getUpdateGraph().observe(this, new Observer<LineData>() {
            @Override
            public void onChanged(LineData linedata) {
                graph.setData(linedata);
                graph.invalidate();
            }
        });

        /**
         * Observer of new refresh rate of current ESP
         */
        graphViewModel.getMoments().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                valTemp.setText(s);

            }
        });

        graphViewModel.getData().observe(this, new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                actuValues(data);
            }
        });


        valTemp = findViewById(R.id.viewTime);



        viewTemp = findViewById(R.id.viewTemp);
        viewLux = findViewById(R.id.viewLux);
        viewCO2 = findViewById(R.id.viewCO2);
        viewO2 = findViewById(R.id.viewO2);
        viewHumi = findViewById(R.id.viewHumi);


        bottomNav = findViewById(R.id.bottomNav);
        setSupportActionBar(bottomNav);

        bottomNavigationView = findViewById(R.id.bottomNavMenuView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //boxTemp
        boxTemp = findViewById(R.id.boxTemp);
        boxTemp.setOnClickListener(this);

        //boxHumi
        boxHumi = findViewById(R.id.boxHumi);
        boxHumi.setOnClickListener(this);

        boxCO2 = findViewById(R.id.boxCO2);
        boxCO2.setOnClickListener(this);

        boxO2 = findViewById(R.id.boxO2);
        boxO2.setOnClickListener(this);


        //boxLux
        boxLux = findViewById(R.id.boxLux);
        boxLux.setOnClickListener(this);


        //Constru graph
        graph = findViewById(R.id.lineChart);
        graph.clear();
        //Création Axe X


        xl = graph.getXAxis();
        xl.setTextColor(Color.BLACK);
        xl.setDrawGridLines(true);
        xl.setEnabled(true);
        xl.setAvoidFirstLastClipping(false);
        /**
         * add formatter of value in the top X axis
         */
        xl.setValueFormatter(new XAxisValueFormatter(graphViewModel.getListData()));


        //Création Axe Y droit
        rightAxis = graph.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setTextColor(Color.BLACK);
        rightAxis.setDrawGridLines(true);

        leftAxis = graph.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);

        /**
         * Add preference to the graph
         */
        paramGraph();

        /**
         * add listener in the graph to show a Toast of value hit in the graph
         */
        graph.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onNothingSelected() {

            }

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String label;
                if (h.getAxis().name().equals("LEFT")) {
                    label = graphViewModel.getLeftAxisName() + " = ";
                } else if (h.getAxis().name().equals("RIGHT")) {
                    label = graphViewModel.getRightAxisName() + " = ";
                } else {
                    label = "X =";
                }
                //Toast.makeText(getApplicationContext(), "Heure = " + graphViewModel.getDatas().get((int) h.getX() - 1).get+ ", " + label + h.getY(), Toast.LENGTH_SHORT).show();
            }

        });

    }

    /**
     * Set new Data values to the textView in the top
     * @param data lastest Data value available
     */
    void actuValues(Data data) {
        DecimalFormat a = new DecimalFormat("##.###");
        if (data.getTemperature() != 0) {
            viewTemp.setText(a.format(data.getTemperature()) + "°");
        }
        if (data.getLight() != 0) {
            viewLux.setText(a.format(data.getLight()) + "l");
        }
        if (data.getCO2() != 0) {
            viewCO2.setText(a.format(data.getCO2()) + "%");
        }
        if (data.getO2() != 0) {
            viewO2.setText(a.format(data.getO2()) + "%");
        }
        if (data.getHumidite() != 0) {
            viewHumi.setText(a.format(data.getHumidite()) + "%");
        }
    }

    /**
     * Configure the parameters of the graph
     */
    void paramGraph() {
        graph.setNoDataText("Aucune données reçu pour le moment");
        graph.setNoDataTextColor(Color.BLACK);
        graph.setTouchEnabled(true);
        graph.setDragEnabled(true);
        graph.setScaleEnabled(true);
        graph.setPinchZoom(false);
        graph.setDrawGridBackground(true);
        graph.getDescription().setEnabled(true);
        graph.getDescription().setText("UnivLyon1");
        graph.setDrawBorders(true);
        graph.getAxisLeft().setEnabled(true);
        graph.getXAxis().setDrawAxisLine(true);
        graph.getXAxis().setDrawGridLines(true);
        graph.getAxisRight().setDrawAxisLine(true);
        graph.getAxisRight().setDrawGridLines(true);
    }


    /**
     * Delete listener on FirebaseAccess if we left this view
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        graphViewModel.onClose();
    }

    /**
     * @param item The selected item
     * @return
     * performs different action depending on the returned id, return false if failed
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewData:
                Intent openViewData;
                openViewData = new Intent(GraphiqueActivity.this, VueDataActivity.class);
                startActivity(openViewData);
                break;
            case R.id.setting:
                Intent openSetting;
                openSetting = new Intent(GraphiqueActivity.this, SettingsEtuActivity.class);
                if (!graphViewModel.getLeftAxisName().equals("")) {
                    openSetting.putExtra("leftAxisName", graphViewModel.getLeftAxisName());
                }
                if (!graphViewModel.getRightAxisName().equals("")) {
                    openSetting.putExtra("rightAxisName", graphViewModel.getRightAxisName());
                }
                startActivity(openSetting);
                break;
            case R.id.btnExport:
                try {
                    Toast.makeText(getApplicationContext(), "Export excel commencé ", Toast.LENGTH_SHORT).show();
                    //exportFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return false;
    }

  /*
    */

    /**
     * send CheckBox clicked to the ViewModel
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boxCO2:
            case R.id.boxTemp:
            case R.id.boxO2:
            case R.id.boxHumi:
            case R.id.boxLux:
                graphViewModel.notifyCheck(v.getId());
                break;
        }
    }
}