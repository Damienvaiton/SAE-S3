package com.example.pageacceuil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GraphPage extends AppCompatActivity implements View.OnClickListener,BottomNavigationView.OnNavigationItemSelectedListener  {

    private LineChart graph;

    private ListData listData;


    private TextView val4;
    private TextView val3;
    private TextView val2;
    private TextView val1;


    private CheckBox boxTemp;
    private CheckBox boxO2;
    private CheckBox boxLux;

    private Button btnAjout;


    private BottomAppBar bottomNav;
    private BottomNavigationView bottomNavigationView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph_page);
        ListData listData=new ListData();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32/A8:03:2A:EA:EE:CC");
      /*   DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().child("A8:03:2A:EA:EE:CC");*/


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    i++;
                    Data a=dataSnapshot.getValue(Data.class);
                    System.out.println(i+" ; "+a.getHumidite()+"/"+a.getTemperature());
                    listData.list_add_data(a);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: ");
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
        refreshRate();
       /* try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


    }

    void refreshRate(){
        creaGraph();
        actuValues();
    }

    //Faire un systeme de min mSax
    void creaGraph() {

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

       /* graph.getAxisLeft().setSpaceTop(10000000);
        graph.getAxisRight().setSpaceTop(1000);
        graph.getAxisLeft().setSpaceBottom(400);
        Contrôle des échelle */


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        if(boxO2.isChecked()) {

            ArrayList<Entry> O2 = new ArrayList<>();

            for (int i=0;i<783;i++){
            O2.add(new Entry(i,listData.recup_data(i).getTemperature()));
            }

            LineDataSet set = new LineDataSet(O2, "O2");

            set.setLineWidth(2.5f);
            set.setCircleRadius(5f);
            set.setCircleHoleRadius(2.5f);


            set.setValueTextColor(Color.BLACK);
            set.setValueTextSize(12f);
            set.setDrawValues(true);

            set.setColor(Color.BLUE) ;
            set.setCircleColor(Color.BLUE);



            dataSets.add(set);
        }

        if(boxLux.isChecked()) {

            ArrayList<Entry> O2 = new ArrayList<>();

            O2.add(new Entry(0, (float) (Math.random() * (180 - 250))));
            O2.add(new Entry(1, (float) (Math.random() * (180 - 250))));
            O2.add(new Entry(2, (float) (Math.random() * (180 - 250))));
            O2.add(new Entry(3, (float) (Math.random() * (180 - 250))));

            LineDataSet set = new LineDataSet(O2, "Lux");

            set.setColor(Color.YELLOW);
            set.setCircleColor(Color.YELLOW);

            set.setLineWidth(2.5f);
            set.setCircleRadius(5f);
            set.setCircleHoleRadius(2.5f);


            set.setValueTextColor(Color.BLACK);
            set.setValueTextSize(12f);
            set.setDrawValues(true);


            dataSets.add(set);
        }

        if(boxTemp.isChecked()) {

            ArrayList<Entry> O2 = new ArrayList<>();

            O2.add(new Entry(0, (float) (Math.random() * (360 - 25))));
            O2.add(new Entry(1, (float) (Math.random() * (360 - 25))));
            O2.add(new Entry(2, (float) (Math.random() * (360 - 25))));
            O2.add(new Entry(3, (float) (Math.random() * (360 - 25))));

            LineDataSet set = new LineDataSet(O2, "Température");


            set.setColor(Color.RED);
            set.setCircleColor(Color.RED);


            set.setLineWidth(2.5f);
            set.setCircleRadius(5f);
            set.setCircleHoleRadius(2.5f);


            set.setValueTextColor(Color.BLACK);
            set.setValueTextSize(12f);
            set.setDrawValues(true);

            dataSets.add(set);
        }




        LineData data = new LineData(dataSets);

        graph.setData(data);

    }

    void actuValues(){
        DecimalFormat a=new DecimalFormat("#.##");


        val1.setText(a.format((Math.random() * (40 - 25))));
        val2.setText(a.format((Math.random() * (40 - 25))));
        val3.setText(a.format((Math.random() * (40 - 25))));
        val4.setText(a.format((Math.random() * (40 - 25))));
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.export,menu);
        return true;
    }*/



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.boxTemp:
            case R.id.boxO2:
            case R.id.boxLux:
                System.out.println("oe");
                creaGraph();
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

    ;
}