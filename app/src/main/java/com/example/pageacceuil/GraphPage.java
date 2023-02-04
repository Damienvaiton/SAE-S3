package com.example.pageacceuil;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GraphPage extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {


    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ListData listData;
    ArrayList<Entry> A_temp = new ArrayList<>();
    ArrayList<Entry> A_lux = new ArrayList<>();
    ArrayList<Entry> A_CO2 = new ArrayList<>();
    ArrayList<Entry> A_humi = new ArrayList<>();
    ArrayList<Entry> A_O2 = new ArrayList<>();

    LineDataSet setHumi = new LineDataSet(A_humi, "Humidité");
    LineDataSet setCO2 = new LineDataSet(A_CO2, "CO2");
    LineDataSet setO2 = new LineDataSet(A_O2, "O2");
    LineDataSet setTemp = new LineDataSet(A_temp, "Température");
    LineDataSet setLux = new LineDataSet(A_lux, "Lux");
    public static LineChart graph;
    private TextView viewO2;
    private TextView viewCO2;
    private TextView viewLux;
    private TextView viewTemp;
    private TextView viewHumi;
    private TextView valTemp;


    private CheckBox boxO2;
    private CheckBox boxCO2;
    private CheckBox boxTemp;
    private CheckBox boxHumi;
    private CheckBox boxLux;

    private boolean leftAxisUsed = false;
    private boolean rightAxisUsed = false;

    private String leftAxisName = "";
    private String rightAxisName = "";
    private String choixESP = "";
    private String nomESP = "";
    private BottomAppBar bottomNav;
    private BottomNavigationView bottomNavigationView;
    public static YAxis leftAxis;
    public static YAxis rightAxis;
    private XAxis xl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_page);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("choixESP")) {
                this.choixESP = (String) intent.getSerializableExtra("choixESP");
            }
            if (intent.hasExtra("nomESP")) {
                this.nomESP = (String) intent.getSerializableExtra("nomESP");
            } else {
                System.out.println("Impossible de récup num ESP");
            }
        }
        DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32/" + choixESP);


        listData = ListData.getInstance();
FirebaseAcces bd= FirebaseAcces.getInstance();
bd.prechargebd(choixESP);
//        if(myRef.child("Mesure").
/*        myRef.child("Mesure").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot tab = task.getResult();
                if (tab.exists()) {
                    for (int i = 0; i < tab.getChildrenCount(); i++) {
                        Data a = tab.child(i + "").getValue(Data.class);
                        listData.list_add_data(a);
                        chargerDonner();
                    }

                } else {
                    AlertDialog.Builder pop = new AlertDialog.Builder(GraphPage.this);
                    pop.setMessage("ESP hors tension, merci de le brancher");
                    pop.setPositiveButton("ESP brancher", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Prêt", Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                        }
                    });

                    pop.show();

                }
            }
        });*/



        myRef.child("Mesure").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getChildrenCount() == 6) {
                    Data a = snapshot.getValue(Data.class);
                    listData.list_add_data(a);
                    chargerDonner();
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


        myRef.child("TauxRafraichissement").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String heure = "";
                String minute = "";
                String seconde = "";
                if (snapshot.getValue(Long.class) >= 3600000) {
                    heure = (snapshot.getValue(Long.class) / (1000 * 60 * 60) + "h");
                }
                if (snapshot.getValue(Long.class) >= 60000) {
                    minute = (snapshot.getValue(Long.class) % (1000 * 60 * 60)) / (1000 * 60) + "m";
                }
                if (snapshot.getValue(Long.class) >= 1000) {
                    seconde = (snapshot.getValue(Long.class) % (1000 * 60)) / 1000 + "s";
                }

                valTemp.setText(heure + minute + seconde);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        valTemp = findViewById(R.id.viewTime);


        //Textview pour affichage données en haut
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
        xl.setValueFormatter(new XAxisValueFormatter(listData));
        System.out.println(xl.mEntries.length);
        xl.mEntryCount = 0;


        //Création Axe Y droit
        rightAxis = graph.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setTextColor(Color.BLACK);
        rightAxis.setDrawGridLines(true);

        leftAxis = graph.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);

        //Set paramètre du graph
        paramGraph();

        graph.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onNothingSelected() {

            }

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String label;
                if (h.getAxis().name().equals("LEFT")) {
                    label = leftAxisName + " = ";
                } else if (h.getAxis().name().equals("RIGHT")) {
                    label = rightAxisName + " = ";
                } else {
                    label = "X =";
                }
                Toast.makeText(getApplicationContext(), "Heure = " + listData.recup_data((int) h.getX() - 1).getTemps() + ", " + label + h.getY(), Toast.LENGTH_SHORT).show();

            }

        });

    }

    void chargerDonner() {
        A_CO2.add(new Entry(listData.list_size(), listData.recup_data(listData.list_size() - 1).getCO2()));
        A_lux.add(new Entry(listData.list_size(), listData.recup_data(listData.list_size() - 1).getLight()));
        A_O2.add(new Entry(listData.list_size(), listData.recup_data(listData.list_size() - 1).getO2()));
        A_temp.add(new Entry(listData.list_size(), listData.recup_data(listData.list_size() - 1).getTemperature()));
        A_humi.add(new Entry(listData.list_size(), listData.recup_data(listData.list_size() - 1).getHumidite()));
        creaGraph();

    }


    void creaGraph() {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();


        if (boxCO2.isChecked()) {
            setCO2 = new LineDataSet(A_CO2, "CO2");
            paramSet(setCO2);
            choixAxe(setCO2);
            setCO2.setColor(Color.RED);
            setCO2.setCircleColor(Color.RED);
            dataSets.add(setCO2);
        }
        if (boxTemp.isChecked()) {
            setTemp = new LineDataSet(A_temp, "Température");
            paramSet(setTemp);
            choixAxe(setTemp);
            setTemp.setColor(Color.BLUE);
            setTemp.setCircleColor(Color.BLUE);
            dataSets.add(setTemp);
        }

        if (boxHumi.isChecked()) {
            setHumi = new LineDataSet(A_humi, "Humidité");
            paramSet(setHumi);
            choixAxe(setHumi);
            setHumi.setColor(Color.MAGENTA);
            setHumi.setCircleColor(Color.MAGENTA);
            dataSets.add(setHumi);
        }
        if (boxO2.isChecked()) {
            setO2 = new LineDataSet(A_O2, "O2");
            paramSet(setO2);
            choixAxe(setO2);
            setO2.setColor(Color.BLACK);
            setO2.setCircleColor(Color.BLACK);
            dataSets.add(setO2);
        }
        if (boxLux.isChecked()) {
            setLux = new LineDataSet(A_lux, "Lux");
            paramSet(setLux);
            choixAxe(setLux);
            setLux.setColor(Color.YELLOW);
            setLux.setCircleColor(Color.YELLOW);
            dataSets.add(setLux);

        }


        LineData data = new LineData(dataSets);
        graph.setData(data);
        data.notifyDataChanged();
        graph.notifyDataSetChanged();
        graph.invalidate();
        leftAxisUsed = false;
        rightAxisUsed = false;
    }

    void choixAxe(LineDataSet data) {
        if (!leftAxisUsed) {
            data.setAxisDependency(YAxis.AxisDependency.LEFT);
            leftAxisName = data.getLabel();
            leftAxisUsed = true;
            return;
        } else if (!rightAxisUsed) {
            data.setAxisDependency(YAxis.AxisDependency.RIGHT);
            rightAxisName = data.getLabel();
            rightAxisUsed = true;
            return;
        } else {
            data.setDrawValues(true);
            data.setValueTextSize(10);
        }
    }


    private void paramSet(LineDataSet set) {
        set.setFillAlpha(120);
        set.setLineWidth(2.5f);
        set.setCircleRadius(3.5f);
        set.setCircleHoleRadius(1f);
        set.setValueTextColor(Color.BLACK);
        set.setDrawValues(false);
    }

    void actuValues() {
        int pos = listData.list_size() - 1;
        DecimalFormat a = new DecimalFormat("##.###");
        if (listData.recup_data(pos).getTemperature() != 0) {
            viewTemp.setText(a.format(listData.recup_data(pos).getTemperature()) + "°");
        }
        if (listData.recup_data(pos).getLight() != 0) {
            viewLux.setText(a.format(listData.recup_data(pos).getLight()) + "l");
        }
        if (listData.recup_data(pos).getCO2() != 0) {
            viewCO2.setText(a.format(listData.recup_data(pos).getCO2()) + "%");
        }
        if (listData.recup_data(pos).getO2() != 0) {
            viewO2.setText(a.format(listData.recup_data(pos).getO2()) + "%");
        }
        if (listData.recup_data(pos).getHumidite() != 0) {
            viewHumi.setText(a.format(listData.recup_data(pos).getHumidite()) + "%");
        }
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
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewData:
                Intent openViewData;
                openViewData = new Intent(GraphPage.this, VueData.class);
                startActivity(openViewData);
                break;
            case R.id.setting:
                Intent openSetting;
                openSetting = new Intent(GraphPage.this, SettingPage.class);
                openSetting.putExtra("choixESP", choixESP);
                if (!nomESP.equals("")) {
                    openSetting.putExtra("nomESP", nomESP);
                }
                if (!leftAxisName.equals("")) {
                    openSetting.putExtra("leftAxisName", leftAxisName);
                }
                if (!rightAxisName.equals("")) {
                    openSetting.putExtra("rightAxisName", rightAxisName);
                }

                startActivity(openSetting);
                break;
            case R.id.btnExport:
                try {
                    Toast.makeText(getApplicationContext(), "Export excel commencé ", Toast.LENGTH_SHORT).show();
                    exportFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return false;
    }

    private void exportFile() {
        int cptLignes = listData.list_size() - 1;
        if (cptLignes < 1) {
            Toast.makeText(this, "Export excel annulé, pas de valeurs", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isDataValid(cptLignes)) {
            cptLignes--;
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Mesure.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Création de la feuille de calcul
        XSSFSheet sheet = workbook.createSheet("Mesures");

        // Création de la condition pour l'affichage en couleurs alternées
        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule("MOD(ROW(),2)");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
        // Plage des cellules affectées par la condition
        CellRangeAddress[] regions = {
                CellRangeAddress.valueOf("A1:G" + (cptLignes + 1))
        };
        sheetCF.addConditionalFormatting(regions, rule1);

        // Ajout de la 1ère ligne de titre
        XSSFRow row0 = sheet.createRow(0);
        // Cellule Numero
        XSSFCell cellNumero = row0.createCell(0);
        cellNumero.setCellValue("Numero mesure");
        // Cellule Humidite
        XSSFCell cellHum = row0.createCell(1);
        cellHum.setCellValue("Humidite");
        // Cellule Temperature
        XSSFCell cellTemp = row0.createCell(2);
        cellTemp.setCellValue("Temperature");
        // Cellule CO2
        XSSFCell cellCO2 = row0.createCell(3);
        cellCO2.setCellValue("CO2");
        // Cellule O2
        XSSFCell cellO2 = row0.createCell(4);
        cellO2.setCellValue("O2");
        // Cellule Lux
        XSSFCell cellLux = row0.createCell(5);
        cellLux.setCellValue("Lux");
        // Cellule Heure
        XSSFCell cellHeure = row0.createCell(6);
        cellHeure.setCellValue("Heure");

        // Ajout des lignes de mesures
        for (int i = 0; i < cptLignes; i++) {
            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(listData.recup_data(i).getHumidite());
            row.createCell(2).setCellValue(listData.recup_data(i).getTemperature());
            row.createCell(3).setCellValue(listData.recup_data(i).getCO2());
            row.createCell(4).setCellValue(listData.recup_data(i).getO2());
            row.createCell(5).setCellValue(listData.recup_data(i).getLight());
            row.createCell(6).setCellValue(listData.recup_data(i).getTemps());
        }

        // Création du fichier
        try {
            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast.makeText(this, "Export de " + cptLignes + " mesures dans le dossier téléchargements", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Export excel annulé, erreur", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isDataValid(int cptLignes) {
        if (listData.recup_data(cptLignes).getCO2() == 0) {
            return false;
        }
        if (listData.recup_data(cptLignes).getTemperature() == 0) {
            return false;
        }
        if (listData.recup_data(cptLignes).getHumidite() == 0) {
            return false;
        }
        if (listData.recup_data(cptLignes).getO2() == 0) {
            return false;
        }
        if (listData.recup_data(cptLignes).getLight() == 0) {
            return false;
        }
        return listData.recup_data(cptLignes).getTemps() != "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boxCO2:
               /* if (setCO2.getAxisDependency() != null) {
                    //Géré le fait qu'il y en ai plus de 2, genre boolean qui compte
                    System.out.println("yoc02");
                    desacAxe(setCO2.getAxisDependency());
                    setCO2.setAxisDependency(null);
                }*/
            case R.id.boxTemp:
               /* if (setTemp.getAxisDependency() != null) {
                    desacAxe(setTemp.getAxisDependency());
                    System.out.println("yotemp");
                    setTemp.setAxisDependency(null);
                }*/
            case R.id.boxO2:
               /* if (setO2.getAxisDependency() != null) {
                    desacAxe(setO2.getAxisDependency());
                    System.out.println("yo02");
                    setO2.setAxisDependency(null);
                }*/
            case R.id.boxHumi:
               /* if (setHumi.getAxisDependency() != null) {
                    desacAxe(setHumi.getAxisDependency());
                    System.out.println("yohumi");
                    setHumi.setAxisDependency(null);
                }*/
            case R.id.boxLux:
               /* if (setLux.getAxisDependency() != null) {
                    desacAxe(setLux.getAxisDependency());
                    System.out.println("yolux");
                    setLux.setAxisDependency(null);
                }*/
                creaGraph();
                break;
        }
    }
}
