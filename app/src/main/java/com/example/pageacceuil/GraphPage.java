package com.example.pageacceuil;

import static java.lang.Integer.valueOf;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GraphPage extends AppCompatActivity implements View.OnClickListener,BottomNavigationView.OnNavigationItemSelectedListener  {

    private LineChart graph;
    public int i=0;
    public int pos=0;

    public ListData listData;


    private TextView val4;
    private TextView val3;
    private TextView val2;
    private TextView val1;

    private EditText valTemp;


    private CheckBox boxTemp;
    private CheckBox boxO2;
    private CheckBox boxLux;

    private Button btnAjout;

private int valeurTempo;

    private BottomAppBar bottomNav;
    private BottomNavigationView bottomNavigationView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    ArrayList<Entry> A_O2 = new ArrayList<>();
    ArrayList<Entry> A_lux = new ArrayList<>();
    ArrayList<Entry> A_temp = new ArrayList<>();

    YAxis leftAxis;
    YAxis rightAxis;
    XAxis xl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph_page);


        DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32/A8:03:2A:EA:EE:CC/Mesure");


     listData=new ListData();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override

            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Data a=snapshot.getValue(Data.class);
                listData.list_add_data(a);
                System.out.println(a.getHumidite());
                System.out.println(a.getTemperature());
                System.out.println(i + " ; " + listData.recup_data(i).getHumidite() + "/" + listData.recup_data(i).getTemperature());
                pos++;
                creaGraph();
                actuValues();
                editTemps();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Impossible d'accéder au données");

            }
        });

        valTemp=(EditText)findViewById(R.id.setTime);
        valTemp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                valeurTempo=Integer.valueOf(valTemp.getText().toString());
                editTemps();


            }
        });
        val1=(TextView)findViewById(R.id.barVu1);
        val2=(TextView)findViewById(R.id.barVu2);
        val3=(TextView)findViewById(R.id.barVu3);
        val4=(TextView)findViewById(R.id.barVu4);

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        bottomNav=findViewById(R.id.bottomNav);
        setSupportActionBar(bottomNav);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavMenuView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);




        boxTemp=(CheckBox)findViewById(R.id.boxTemp);
        boxTemp.setOnClickListener(this);

        boxO2=(CheckBox)findViewById(R.id.boxO2);
        boxO2.setOnClickListener(this);

        boxLux=(CheckBox)findViewById(R.id.boxLux);
        boxLux.setOnClickListener(this);


        //Constru graph
        graph = (LineChart) findViewById(R.id.lineChart);

        YAxis leftAxis = graph.getAxisLeft();
        YAxis rightAxis = graph.getAxisRight();
        XAxis xl = graph.getXAxis();


        xl.setTextColor(Color.BLACK);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);


        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisMaximum(500f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);


        rightAxis.setEnabled(true);
        rightAxis.setTextColor(Color.BLACK);
        rightAxis.setAxisMaximum(500f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setDrawGridLines(true);

        paramGraph();

    }


    //Faire un systeme de min mSax
    void creaGraph() {
        System.out.println(listData.recup_data(0).getTemperature());
        System.out.println(listData.recup_data(0).getHumidite());
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        if(boxO2.isChecked()) {

                A_O2.add(new Entry  (i++, listData.recup_data(listData.list_size()-1).getTemperature()));
                LineDataSet set02 = new LineDataSet(A_O2, "O2");
                paramSet(set02);
                set02.setColor(Color.BLUE);
                set02.setCircleColor(Color.BLUE);

                dataSets.add(set02);
            }
        /*Proto*/
            if (boxLux.isChecked()) {
                A_lux.add(new Entry  (i++, listData.recup_data(listData.list_size()-1).getHumidite()));


                //       A_lux.add(new Entry(listData.recup_data(listData.list_size() - 1).getHeure(), listData.recup_data(listData.list_size() - 1).getD_humidite()));
                LineDataSet set02 = new LineDataSet(A_lux, "Lux");
                paramSet(set02);
                set02.setColor(Color.YELLOW);
                set02.setCircleColor(Color.YELLOW);

                dataSets.add(set02);
            }

            if (boxTemp.isChecked()) {
                A_temp.add(new Entry(pos, listData.recup_data(listData.list_size()-1).getHumidite()));
                LineDataSet set = new LineDataSet(A_temp, "Température");
                paramSet(set);
                set.setColor(Color.RED);
                set.setCircleColor(Color.RED);

                dataSets.add(set);
            }

            LineData data = new LineData(dataSets);
            graph.setData(data);
            data.notifyDataChanged();
            graph.notifyDataSetChanged();
            graph.invalidate();

    }


    private void paramSet(LineDataSet set) {

        set.setLineWidth(2.5f);
        set.setCircleRadius(5f);
        set.setCircleHoleRadius(2.5f);
        set.setValueTextSize(0f);
        set.setValueTextColor(Color.BLACK);
      /*  set.setValueTextSize(10f);*/
        set.setDrawValues(true);

    }

    void actuValues(){

      /* val2.setText(listData.recup_data(listData.list_size() - 1).getTemperature()));
        val3.setText(listData.recup_data(listData.list_size() - 1).getTemperature();
        val4.setText(listData.recup_data(listData.list_size() - 1).getTemperature();*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.boxTemp:
            case R.id.boxO2:
            case R.id.boxLux:
                System.out.println("oe");
                creaGraph();

             /*   if (!rightAxis.isEnabled()){
                    setRightAxis();
                } else if (!leftAxis.isEnabled()){
                    setLeftAxis();}
                    else{
                        boxTemp.clearFocus();
                        boxTemp.setChecked(false);
                    }
                        //Créée petit message pour ddire nombre max de graph puis décoche
*/

                break;
            case R.id.btnAdd:
                Pop_up customPopup = new Pop_up(this);
                customPopup.build();
                break;
            case R.id.btnExport:
                System.out.println("dld");

            default:
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.btnExport:
                System.out.println("Fonction export sur excel");
                break;
            case R.id.rotate:
                System.out.println("Rotation écran paysage");
                break;
            case R.id.retourArr:
                System.out.println("Retour écran titre ");
                break;
            case R.id.setting:
                System.out.println("Parametre");
                break;
        }
        return false;
    }

    void paramGraph(){
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

        graph.resetTracking();
        graph.clear();

        graph.setDrawGridBackground(true);
        graph.getDescription().setEnabled(true);
        graph.getDescription().setText("c les ratz");

        graph.setDrawBorders(true);

        graph.getAxisLeft().setEnabled(true);
        graph.getAxisRight().setDrawAxisLine(true);
        graph.getAxisRight().setDrawGridLines(true);
        graph.getXAxis().setDrawAxisLine(true);
        graph.getXAxis().setDrawGridLines(true);

        graph.setVisibleXRangeMaximum(5);
        /* graph.setVisibleYRangeMaximum(120);
         graph.setVisibleYRange(30, YAxis.AxisDependency.LEFT);
        graph.getAxisLeft().setSpaceTop(10000000);
        graph.getAxisRight().setSpaceTop(1000);
        graph.getAxisLeft().setSpaceBottom(400);
        Contrôle des échelle */

    }


public void editTemps(){


    DatabaseReference varTemps = database.getReference("SAE_S3_BD/ESP32/A8:03:2A:EA:EE:CC");
    varTemps.child("TauxRafraichissement").setValue(valeurTempo);

}
}