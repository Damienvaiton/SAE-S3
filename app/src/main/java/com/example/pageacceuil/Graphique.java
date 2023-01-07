package com.example.pageacceuil;

import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Graphique {
       private Database data;
       private ListData listData;
       private LineChart graph;

        public static int indice = 0;

        ArrayList<Entry> A_temp = new ArrayList<>();
        ArrayList<Entry> A_lux = new ArrayList<>();
        ArrayList<Entry> A_CO2 = new ArrayList<>();
        ArrayList<Entry> A_humi = new ArrayList<>();
        ArrayList<Entry> A_O2 = new ArrayList<>();

        public static YAxis leftAxis;
        public static YAxis rightAxis;
        private XAxis xl;




            DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32/A8:03:2A:EA:EE:CC/Mesure");

            listData = new ListData();

            myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot tab = task.getResult();
                    for (int i = 0; i < tab.getChildrenCount(); i++) {
                        Data a = tab.child(i + "").getValue(Data.class);
                        indice++;
                        listData.list_add_data(a);
                    }
                }

            });

            myRef.addChildEventListener(new ChildEventListener() {
                @Override

                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if (snapshot.getChildrenCount() == 3) {
                        Data a = snapshot.getValue(Data.class);
                        indice++;
                        listData.list_add_data(a);
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


            // A terminer
            DatabaseReference varTemps = database.getReference("SAE_S3_BD/ESP32/" + test[cho] + "/TauxRafraichissement");
            varTemps.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue(Integer.class) > 36000000) {
                        valTemp.setText(snapshot.getValue(Integer.class) / 1000 + " h");
                    }
                    if (snapshot.getValue(Integer.class) > 60000) {
                        valTemp.setText(snapshot.getValue(Integer.class) / 1000 + " m");
                    }
                    if (snapshot.getValue(Integer.class) > 1000) {
                        valTemp.setText(snapshot.getValue(Integer.class) / 1000 + " s");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            //Création Axe X

            //  XAxisRenderer mXAxisRenderer = new XAxisRenderer(ViewPortHandler, mXAxis, mLeftAxisTransformer);

            xl = graph.getXAxis();
            xl.setTextColor(Color.BLACK);
            xl.setDrawGridLines(true);
            xl.setEnabled(true);
            xl.setLabelCount(5); //Marche pas
            xl.setAvoidFirstLastClipping(false);
            xl.setValueFormatter(new XAxisValueFormatter(listData));//Nombre max de poin



            //Création Axe Y gauche
            leftAxis = graph.getAxisLeft();
            leftAxis.setTextColor(Color.BLACK);
            // leftAxis.setAxisMaximum(30f);
            // leftAxis.setAxisMinimum(20f);
            leftAxis.setDrawGridLines(true);
            // Ajout couleur axe  leftAxis.setAxisLineColor(Color.RED);


            //Création Axe Y droit
            rightAxis = graph.getAxisRight();
            rightAxis.setEnabled(true);
            rightAxis.setTextColor(Color.BLACK);
            //rightAxis.setAxisMaximum(60f);
            //rightAxis.setAxisMinimum(0f);
            rightAxis.setDrawGridLines(true);

            //Set paramètre du graph
            paramGraph();

            graph.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onNothingSelected() {

                }

                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    Toast.makeText(getApplicationContext(), "Heure = " +listData.recup_data((int)h.getX()-1).getTemps()  + ", X : " + h.getY(), Toast.LENGTH_SHORT).show();

                }
            });

        }


        void creaGraph() {
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            if (GraphPage.boxO2.isChecked()) {
                A_CO2.add(new Entry(indice, listData.recup_data(indice - 1).getCO2()));
                LineDataSet setCO2 = new LineDataSet(A_CO2, "CO2");
                setCO2.setAxisDependency(YAxis.AxisDependency.LEFT);
                paramSet(setCO2);

                setCO2.setColor(Color.RED);
                setCO2.setCircleColor(Color.RED);
                dataSets.add(setCO2);
            }
            if (boxTemp.isChecked()) {
                A_temp.add(new Entry(indice, listData.recup_data(indice - 1).getTemperature()));
                LineDataSet setTemp = new LineDataSet(A_temp, "Température");

                setTemp.setAxisDependency(YAxis.AxisDependency.LEFT);
                paramSet(setTemp);
                setTemp.setColor(Color.BLUE);
                setTemp.setCircleColor(Color.BLUE);
                dataSets.add(setTemp);
            }
            if (boxLux.isChecked()) {
                A_lux.add(new Entry(indice, listData.recup_data(listData.list_size() - 1).getLux()));
                LineDataSet setLux = new LineDataSet(A_lux, "Lux");
                paramSet(setLux);
                setLux.setColor(Color.YELLOW);
                setLux.setCircleColor(Color.YELLOW);
                dataSets.add(setLux);
            }
            if (boxHumi.isChecked()) {
                A_humi.add(new Entry(indice, listData.recup_data(indice - 1).getHumidite()));
                LineDataSet setHumi = new LineDataSet(A_humi, "Humidité");
                setHumi.setAxisDependency(YAxis.AxisDependency.RIGHT);
                paramSet(setHumi);
                setHumi.setColor(Color.RED);
                setHumi.setCircleColor(Color.RED);

                dataSets.add(setHumi);
            }
            if (boxO2.isChecked()) {
                A_O2.add(new Entry(indice, listData.recup_data(listData.list_size() - 1).getO2()));
                LineDataSet setO2 = new LineDataSet(A_O2, "O2");
                setO2.setAxisDependency(YAxis.AxisDependency.RIGHT);
                paramSet(setO2);
                setO2.setColor(Color.BLACK);
                setO2.setCircleColor(Color.BLACK);

                dataSets.add(setO2);
            }

            LineData data = new LineData(dataSets);
            graph.setData(data);
            data.notifyDataChanged();
            graph.notifyDataSetChanged();
//        graph.moveViewTo(2,2,xl); Défilement
            graph.invalidate();

        }

        private void checkchkBox() {
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
            set.setValueTextColor(Color.BLACK);
            // set.setValueTextSize(10f); Oui ou non?
            set.setDrawValues(false);

        }


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


        /* graph.setVisibleYRangeMaximum(120);
         graph.setVisibleYRange(30, YAxis.AxisDependency.LEFT);
        graph.getAxisLeft().setSpaceTop(10000000);
        graph.getAxisRight().setSpaceTop(1000);
        graph.getAxisLeft().setSpaceBottom(400);
        Contrôle des échelle */

        }


}