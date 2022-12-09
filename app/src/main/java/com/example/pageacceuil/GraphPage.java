package com.example.pageacceuil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GraphPage extends AppCompatActivity implements View.OnClickListener,BottomNavigationView.OnNavigationItemSelectedListener  {

    private LineChart graph;
    public int indice=0;
    public int pos=0;

    public ListData listData;


    private TextView val4;
    private TextView val3;
    private TextView val2;
    private TextView val1;

    private EditText valTemp;


    private CheckBox boxTemp;
    private CheckBox boxHumi;
    private CheckBox boxLux;

    private Button btnAjout;

    private int valeurTempo=2000;

    private BottomAppBar bottomNav;
    private BottomNavigationView bottomNavigationView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    ArrayList<Entry> A_temp = new ArrayList<>();
    ArrayList<Entry> A_lux = new ArrayList<>();
    ArrayList<Entry> A_humi = new ArrayList<>();

    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph_page);


        DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32/A8:03:2A:EA:EE:CC/Mesure");



        listData=new ListData();

         /* myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            int i = 0;
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                i++;
                                                Data a = dataSnapshot.getValue(Data.class);
                                                listData.list_add_data(a);
                                                System.out.println(i + " ; " + a.getHumidite() + "/" + a.getTemperature());
                                                listData.list_add_data(a);
                                                listData.list_add_data(a);
                                                val2.setText("" + a.getTemperature());
                                                val2.setTextSize(18);
                                                val3.setText("" + a.getHumidite());
                                                val3.setTextSize(18);
                                                creaGraph();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
        });*/
        myRef.addChildEventListener(new ChildEventListener() {
            @Override

            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if(snapshot.getChildrenCount()==3){
                    Data a = snapshot.getValue(Data.class);
                    listData.list_add_data(a);
                    System.out.println(pos + " ; " + listData.recup_data(pos).getHumidite() + "/" + listData.recup_data(pos).getTemperature() + listData.recup_data(pos).getTemps());
                    pos++;
                    creaGraph();
                    actuValues();

                }
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

        //Textview pour affichage données en haut
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

        //boxTemp
        boxTemp=(CheckBox)findViewById(R.id.boxTemp);
        boxTemp.setOnClickListener(this);

        //boxHumi
        boxHumi =(CheckBox)findViewById(R.id.boxHumi);
        boxHumi.setOnClickListener(this);

        //boxLux
        boxLux=(CheckBox)findViewById(R.id.boxLux);
        boxLux.setOnClickListener(this);


        //Constru graph
        graph = (LineChart) findViewById(R.id.lineChart);

        //Création Axe X
        XAxis xl = graph.getXAxis();
        xl.setTextColor(Color.BLACK);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        //Création Axe Y gauche
        YAxis leftAxis = graph.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisMaximum(30f);
        leftAxis.setAxisMinimum(20f);
        leftAxis.setDrawGridLines(true);

        //Création Axe Y droit
        YAxis rightAxis = graph.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setTextColor(Color.BLACK);
        rightAxis.setAxisMaximum(60f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setDrawGridLines(true);

        //Set paramètre du graph
        paramGraph();

    }



    void creaGraph() {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        if(boxTemp.isChecked()) {
            A_temp.add(new Entry(indice, (float) listData.recup_data(listData.list_size()-1).getTemperature()));
            LineDataSet setTemp = new LineDataSet(A_temp, "Température");
            setTemp.setAxisDependency(YAxis.AxisDependency.LEFT);
            paramSet(setTemp);
            setTemp.setColor(Color.BLUE);
            setTemp.setCircleColor(Color.BLUE);
            dataSets.add(setTemp);
        }
        if (boxLux.isChecked()) {
            A_lux.add(new Entry  (indice, (float)listData.recup_data(listData.list_size()-1).getHumidite()));
            //       A_lux.add(new Entry(listData.recup_data(listData.list_size() - 1).getHeure(), listData.recup_data(listData.list_size() - 1).getD_humidite()));
            LineDataSet set02 = new LineDataSet(A_lux, "Lux");
            paramSet(set02);
            set02.setColor(Color.YELLOW);
            set02.setCircleColor(Color.YELLOW);
            dataSets.add(set02);
        }

        if (boxHumi.isChecked()) {
            A_humi.add(new Entry(indice, (float)listData.recup_data(listData.list_size()-1).getHumidite()));
            LineDataSet setHumi = new LineDataSet(A_humi, "Humidité");
            setHumi.setAxisDependency(YAxis.AxisDependency.RIGHT);
            paramSet(setHumi);
            setHumi.setColor(Color.RED);
            setHumi.setCircleColor(Color.RED);

            dataSets.add(setHumi);
        }

        LineData data = new LineData(dataSets);
        graph.setData(data);
        indice++;
        data.notifyDataChanged();
        graph.notifyDataSetChanged();
        graph.invalidate();

    }

    private void checkchkBox(){
        /*int checkActive=0;
        for (CheckBox vb: ){ //Faire un tab de checkbox pour voir coombien sont check
            if (vb.isChecked() && checkActive>2){
                Toast.makeText(getApplicationContext(),"Il ne peut y avoir plus de 2 valeurs dans le graphique", Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    private void paramSet(LineDataSet set) {

        set.setFillAlpha(120);
        set.setLineWidth(2.5f);
        set.setCircleRadius(3.5f);
        set.setCircleHoleRadius(1f);
        set.setValueTextSize(0f);
        set.setValueTextColor(Color.BLACK);

        /*  set.setValueTextSize(10f);*/
        set.setDrawValues(true);

    }

    void actuValues(){
        int y=(listData.list_size())-1;
        val1.setText( listData.recup_data(y).getTemperature()+"°");
        val1.setTextSize(18);
        val2.setText(listData.recup_data(y).getTemps());
        val2.setTextSize(18);

        val3.setText(listData.recup_data(y).getHumidite()+"%");
        val3.setTextSize(18);
        val4.setText(listData.recup_data(y).getTemperature()+"°");
        val4.setTextSize(18);
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


        /* graph.setVisibleYRangeMaximum(120);
         graph.setVisibleYRange(30, YAxis.AxisDependency.LEFT);
        graph.getAxisLeft().setSpaceTop(10000000);
        graph.getAxisRight().setSpaceTop(1000);
        graph.getAxisLeft().setSpaceBottom(400);
        Contrôle des échelle */

    }


    public void editTemps() {
        DatabaseReference varTemps = database.getReference("SAE_S3_BD/ESP32/A8:03:2A:EA:EE:CC");
        varTemps.child("TauxRafraichissement").setValue(valeurTempo);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.boxTemp:
            case R.id.boxHumi:
            case R.id.boxLux:
                break;
            case R.id.btnAdd:
                Pop_up customPopup = new Pop_up(this);
                customPopup.build();
                customPopup.getYesButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"Valeur ajoutée", Toast.LENGTH_SHORT).show();
                        DatabaseReference AjoutO2 = database.getReference("SAE_S3_BD/ESP32/A8:03:2A:EA:EE:CC/Mesure");
                        //     AjoutO2.child("humidite").equalTo(0).on("child", functon(snapshot){
                        //  System.out.println("yo"););

                        AjoutO2.child("333").child("humidite").setValue(customPopup.getValue());
                        customPopup.dismiss();
                    }
                });
                customPopup.getNoButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customPopup.dismiss();
                    }
                });
                break;
            case R.id.rotate:
                System.out.println("Rotation écran paysage");
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {

            case R.id.retourArr:
                System.out.println("Retour écran titre ");
                break;
            case R.id.setting:
                System.out.println("Parametre");
                Intent openSetting;
                openSetting = new Intent(GraphPage.this, SettingPage.class);
                startActivity(openSetting);
                break;
            case R.id.btnExport:
                System.out.println("dld");
        }
        return false;
    }
    /*@Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());

    }
*/

}