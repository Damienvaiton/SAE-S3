package com.example.pageacceuil;

import android.content.Intent;
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
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GraphPage extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {


    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static int indice = 0;
    public static ListData listData;
    ArrayList<Entry> A_temp = new ArrayList<>();
    ArrayList<Entry> A_lux = new ArrayList<>();
    ArrayList<Entry> A_CO2 = new ArrayList<>();
    ArrayList<Entry> A_humi = new ArrayList<>();
    ArrayList<Entry> A_O2 = new ArrayList<>();
    private LineChart graph;
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
    private Button btnAjout;
    private String choixESP = "";
    private BottomAppBar bottomNav;
    private BottomNavigationView bottomNavigationView;
    public static YAxis leftAxis;
    public static YAxis rightAxis;
    private XAxis xl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_page);

        indice = 0;

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("ESP")) {

                this.choixESP = (String) intent.getSerializableExtra("ESP");
                System.out.println("ok");
            } else {
                System.out.println("erreur");
                System.out.println((String) intent.getSerializableExtra("ESP"));
            }
        }


        DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32/" + choixESP + "/Mesure");

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
        DatabaseReference varTemps = database.getReference("SAE_S3_BD/ESP32/" + choixESP + "/TauxRafraichissement");
        varTemps.addListenerForSingleValueEvent(new ValueEventListener() {
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
        viewTemp.setText("T°");
        viewLux = findViewById(R.id.viewLux);
        viewLux.setText("Lux");
        viewCO2 = findViewById(R.id.viewCO2);
        viewCO2.setText("CO2");
        viewO2 = findViewById(R.id.viewO2);
        viewO2.setText("O2");
        viewHumi = findViewById(R.id.viewHumi);
        viewHumi.setText("Humi");




        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

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

        //Création Axe X

        //  XAxisRenderer mXAxisRenderer = new XAxisRenderer(ViewPortHandler, mXAxis, mLeftAxisTransformer);

        xl = graph.getXAxis();
        xl.setTextColor(Color.BLACK);
        xl.setDrawGridLines(true);
        xl.setEnabled(true);
        xl.setAvoidFirstLastClipping(false);
        xl.setValueFormatter(new XAxisValueFormatter(listData));


        //Création Axe Y gauche
        leftAxis = graph.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);


        //Création Axe Y droit
        rightAxis = graph.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setTextColor(Color.BLACK);
        rightAxis.setDrawGridLines(true);

        //Set paramètre du graph
        paramGraph();
        chargerDonner();

        graph.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onNothingSelected() {

            }

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(getApplicationContext(), "Heure = " + listData.recup_data((int) h.getX() - 1).getTemps() + ", X : " + h.getY(), Toast.LENGTH_SHORT).show();

            }

        });

    }

    void chargerDonner() {
        for (int i = 0; i < listData.list_size(); i++) {
            System.out.println(listData.recup_data(i).getTemperature());
            A_CO2.add(new Entry(indice, listData.recup_data(i).getCO2()));
        }
    }

    void creaGraph() {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        if (boxCO2.isChecked()) {
            A_CO2.add(new Entry(indice, listData.recup_data(indice - 1).getCO2()));
            LineDataSet setCO2 = new LineDataSet(A_CO2, "CO2");
            setCO2.setAxisDependency(YAxis.AxisDependency.LEFT); // Faire un code de choix des axis?
            paramSet(setCO2);

            setCO2.setColor(Color.RED);
            setCO2.setCircleColor(Color.RED);
            dataSets.add(setCO2);
        }
        if (boxTemp.isChecked()) {
            A_temp.add(new Entry(indice, listData.recup_data(indice - 1).getTemperature()));
            LineDataSet setTemp = new LineDataSet(A_temp, "Température");

            setTemp.setAxisDependency(YAxis.AxisDependency.RIGHT);
            paramSet(setTemp);
            setTemp.setColor(Color.BLUE);
            setTemp.setCircleColor(Color.BLUE);
            dataSets.add(setTemp);
        }
        if (boxLux.isChecked()) {
            A_lux.add(new Entry(indice, listData.recup_data(indice - 1).getLux()));
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

    void actuValues() {
        DecimalFormat a = new DecimalFormat("##.###");
        if (listData.recup_data(indice - 1).getTemperature() != 0) {
            viewTemp.setText(a.format(listData.recup_data(indice - 1).getTemperature()) + "°");
        }
        if (listData.recup_data(indice - 1).getLux() != 0) {
            viewLux.setText(a.format(listData.recup_data(indice - 1).getLux()) + "Lux");
        }
        if (listData.recup_data(indice - 1).getCO2() != 0) {
            viewCO2.setText(a.format(listData.recup_data(indice - 1).getCO2()) + "%");
        }
        if (listData.recup_data(indice - 1).getO2() != 0) {
            viewO2.setText(a.format(listData.recup_data(indice - 1).getO2()) + "%");
        }
        if (listData.recup_data(indice - 1).getHumidite() != 0) {
            viewHumi.setText(a.format(listData.recup_data(indice - 1).getHumidite()) + "%");
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                Pop_up customPopup = new Pop_up(this);
                customPopup.build("Ajout O2", "",0);
                customPopup.getYesButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Valeur ajoutée", Toast.LENGTH_SHORT).show();
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

            default:
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rotate:
                System.out.println("Rotation écran paysage");
                // Surface s=new Surface();
                // s.ROTATION_90:
                // Surface.
                break;
            case R.id.viewData:
                Intent openViewData;
                openViewData = new Intent(GraphPage.this, VueData.class);
                openViewData.putExtra("listData", listData);
                startActivity(openViewData);

                break;
            case R.id.setting:
                System.out.println("Parametre");
                Intent openSetting;
                openSetting = new Intent(GraphPage.this, SettingPage.class);
                openSetting.putExtra("ESP",choixESP);
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
        int cptLignes=listData.list_size()-1;
        if(!isDataValid(cptLignes)){
            cptLignes--;
        }
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Download", "Mesure.xls");
        HSSFWorkbook workbook = new HSSFWorkbook();

        // Style border
        //HSSFCellStyle styleBorderTop=workbook.createCellStyle();
        //HSSFCellStyle styleBorderBottom=workbook.createCellStyle();
        //HSSFCellStyle styleBorderLeft=workbook.createCellStyle();
        //HSSFCellStyle styleBorderRight=workbook.createCellStyle();

        //styleBorderTop.setBorderTop(BorderStyle.THICK);
        //styleBorderBottom.setBorderBottom(BorderStyle.THICK);
        //styleBorderLeft.setBorderLeft(BorderStyle.THICK);
        //styleBorderRight.setBorderRight(BorderStyle.THICK);

        // Création de la feuille de calcul
        HSSFSheet sheet = workbook.createSheet("Mesures");

        // Création de la condition pour l'affichage en couleurs alternées
        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule("MOD(ROW(),2)");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
        // Plage des cellules affectées par la condition
        CellRangeAddress[] regions = {
                CellRangeAddress.valueOf("A1:D"+cptLignes+1)
        };
        sheetCF.addConditionalFormatting(regions, rule1);

        // Ajout de la 1ère ligne de titre
        HSSFRow row0 = sheet.createRow(0);
        // Cellule Numero
        HSSFCell cellNumero = row0.createCell(0);
        //cellNumero.setCellStyle(cellStyle);
        cellNumero.setCellValue("Numero mesure");
        /*
        cellNumero.setCellStyle(styleBorderTop);
        cellNumero.setCellStyle(styleBorderBottom);
        cellNumero.setCellStyle(styleBorderLeft);
        cellNumero.setCellStyle(styleBorderRight);
        */
        // Cellule Humidite
        HSSFCell cellHum = row0.createCell(1);
        cellHum.setCellValue("Humidite");
        /*
        cellHum.setCellStyle(styleBorderTop);
        cellHum.setCellStyle(styleBorderBottom);
        cellHum.setCellStyle(styleBorderLeft);
        cellHum.setCellStyle(styleBorderRight);
         */
        // Cellule Temperature
        HSSFCell cellTemp = row0.createCell(2);
        cellTemp.setCellValue("Temperature");
        /*
        cellTemp.setCellStyle(styleBorderTop);
        cellTemp.setCellStyle(styleBorderBottom);
        cellTemp.setCellStyle(styleBorderLeft);
        cellTemp.setCellStyle(styleBorderRight);
         */
        // Cellule CO2
        HSSFCell cellCO2 = row0.createCell(3);
        cellCO2.setCellValue("CO2");
        /*
        cellCO2.setCellStyle(styleBorderTop);
        cellCO2.setCellStyle(styleBorderBottom);
        cellCO2.setCellStyle(styleBorderLeft);
        cellCO2.setCellStyle(styleBorderRight);
         */
        // Cellule O2
        HSSFCell cellO2 = row0.createCell(4);
        cellO2.setCellValue("O2");
        /*
        cellO2.setCellStyle(styleBorderTop);
        cellO2.setCellStyle(styleBorderBottom);
        cellO2.setCellStyle(styleBorderLeft);
        cellO2.setCellStyle(styleBorderRight);
         */
        // Cellule Heure
        HSSFCell cellHeure = row0.createCell(5);
        cellHeure.setCellValue("Heure");
        /*
        cellHeure.setCellStyle(styleBorderTop);
        cellHeure.setCellStyle(styleBorderBottom);
        cellHeure.setCellStyle(styleBorderLeft);
        cellHeure.setCellStyle(styleBorderRight);
         */
        // Ajout des lignes de mesures
        for (int i=0;i<cptLignes;i++){
            HSSFRow row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(i);
            //row.getCell(0).setCellStyle(styleBorderLeft);
            row.createCell(1).setCellValue(listData.recup_data(i).getHumidite());
            row.createCell(2).setCellValue(listData.recup_data(i).getTemperature());
            row.createCell(2).setCellValue(listData.recup_data(i).getCO2());
            row.createCell(2).setCellValue(listData.recup_data(i).getO2());
            row.createCell(3).setCellValue(listData.recup_data(i).getTemps());
            //row.getCell(3).setCellStyle(styleBorderRight);
        }

        // Création du fichier
        try {
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast.makeText(this, "Export de "+cptLignes+" mesures dans le dossier téléchargements", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Export excel annulé, erreur", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isDataValid(int cptLignes){
        if (listData.recup_data(cptLignes).getCO2()==0){
            return false;
        }
        if (listData.recup_data(cptLignes).getTemperature()==0){
            return false;
        }
        if(listData.recup_data(cptLignes).getHumidite()==0){
            return false;
        }
        if(listData.recup_data(cptLignes).getTemps()==""){
            return false;
        }
        return true;
    }
}