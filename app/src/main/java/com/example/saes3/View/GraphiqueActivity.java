package com.example.saes3.View;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.saes3.R;
import com.example.saes3.Model.Axe;
import com.example.saes3.Model.Data;


import com.example.saes3.ViewModel.GraphViewModel;
import com.example.saes3.Util.XAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

import java.text.DecimalFormat;

public class GraphiqueActivity extends AppCompatActivity implements View.OnClickListener, OnNavigationItemSelectedListener {

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

    private Boolean actif=false;
    /**
     * Object axis on the graph
     */
    private Axe Axis;

    private XAxis xl;
    /**
     * The ViewModel of this view
     */
    ActivityResultLauncher<Intent> mStartForResult;
    private GraphViewModel graphViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_page);

        graphViewModel = new ViewModelProvider(this).get(GraphViewModel.class);


        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                emptyESP();
            }
        };
/**
 * Observer of new LineData object available to refrest current graph
 */
        graphViewModel.getUpdateGraph().observe(this, (Observer<LineData>) linedata -> {
            graph.setData(linedata);
            graph.invalidate();

           // if (actif) {
                handler.removeCallbacks(runnable);
            //}

            //if(Boolean.FALSE.equals(actif)) {
              //  handler.postDelayed(runnable, 7000);
//
  //          }
        });

        /**
         * Observer of new refresh rate of current ESP
         */
        graphViewModel.getMoments().observe(this, s -> valTemp.setText(s));

        graphViewModel.getData().observe(this, (Observer<Data>) newData-> {
            actuValues(newData);
         //   notif.getInstance().creaNotif(newData);
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
         * add formatter of value on the top X axis
         */
        xl.setValueFormatter(new XAxisValueFormatter(graphViewModel.getListData()));

        Axis=Axe.getInstance();
        //Création Axe Y droit
        Axis.setRightAxis(graph.getAxisRight());
        Axis.getRightAxis().setEnabled(true);
        Axis.getRightAxis().setTextColor(Color.BLACK);
        Axis.getRightAxis().setDrawGridLines(true);


        Axis.setLeftAxis(graph.getAxisLeft());
        Axis.getLeftAxis().setEnabled(true);
        Axis.getLeftAxis().setTextColor(Color.BLACK);
        Axis.getLeftAxis().setDrawGridLines(true);

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
                // TODO document why this method is empty
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
                Toast.makeText(getApplicationContext(), "Heure = " + graphViewModel.returnValue().recup_data((int) h.getX()+1 ).getTemps()+ ", " + label + h.getY(), Toast.LENGTH_SHORT).show();
            }
        });

        mStartForResult =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                               graph.notifyDataSetChanged();
                               graph.invalidate();
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
                };
                startActivity(openSetting);
                break;
            case R.id.btnExport:
                try {
                    Toast.makeText(getApplicationContext(), "Export excel commencé ", Toast.LENGTH_SHORT).show();
                    String response=graphViewModel.exportFile();
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "échec", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return false;
    }

    public void emptyESP(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(GraphiqueActivity.this);
        alertDialog.setMessage("Il semblerait que l'ESP ne contienne aucune données, l'avez vous brancher?");
        alertDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    public void lostESP(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(GraphiqueActivity.this);
        alertDialog.setMessage("Il semblerait que l'ESP que le signal de l'ESP ai été perdu, vérifié son alimentation électrique");
        alertDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }



    /**
     * send CheckBox clicked to the ViewModel
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        try {


            switch (v.getId()) {
                case R.id.boxCO2:
                case R.id.boxTemp:
                case R.id.boxO2:
                case R.id.boxHumi:
                case R.id.boxLux:
                    graphViewModel.notifyCheck(v.getId());
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Aucune données reçu pour le moment", Toast.LENGTH_SHORT).show();

        } catch (IndexOutOfBoundsException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Erreur");
            builder.setMessage("VOus avez une erreur de données dans la base de données\n" +
                    "Veuillez identifier l'erreur et la corriger");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Erreur inconnue", Toast.LENGTH_SHORT).show();
        }


    }
}