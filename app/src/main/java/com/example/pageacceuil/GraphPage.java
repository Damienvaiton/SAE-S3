package com.example.pageacceuil;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GraphPage extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private final int valeurTempo = 2000;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public int indice = 0;
    public ListData listData;
    ArrayList<Entry> A_temp = new ArrayList<>();
    ArrayList<Entry> A_lux = new ArrayList<>();
    ArrayList<Entry> A_humi = new ArrayList<>();
    private LineChart graph;
    private TextView val4;
    private TextView val3;
    private TextView val2;
    private TextView val1;
    private EditText valTemp;
    private CheckBox boxTemp;
    private CheckBox boxHumi;
    private CheckBox boxLux;
    private Button btnAjout;
    private   String choixESP="";
    private BottomAppBar bottomNav;
    private BottomNavigationView bottomNavigationView;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xl;


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph_page);

        String temp;
        int cho;

        choixESP = MainActivity.ChoixEspTransfert;
        cho = parseInt(choixESP);
        cho = cho+1;


        Resources res = getResources();
        String[] test = res.getStringArray(R.array.ChoixESP);


        temp = "SAE_S3_BD/ESP32/"+test[cho]+"/Mesure";



        DatabaseReference myRef = database.getReference(temp);


        listData = new ListData();
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

        valTemp = findViewById(R.id.setTime);
        valTemp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(getApplicationContext(), "Refresh :" + valTemp.getText(), Toast.LENGTH_SHORT).show();
                    editTemps();
                }
                return false;
            }
        });


        //Textview pour affichage données en haut
        val1 = findViewById(R.id.barVu1);
        val1.setText("T°");
        val2 = findViewById(R.id.barVu2);
        val2.setText("Heure");
        val3 = findViewById(R.id.barVu3);
        val3.setText("Humidité");
        val4 = findViewById(R.id.barVu4);
        val4.setText("T°");

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

        //boxLux
        boxLux = findViewById(R.id.boxLux);
        boxLux.setOnClickListener(this);


        //Constru graph
        graph = findViewById(R.id.lineChart);
        graph.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onNothingSelected() {

            }

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(getApplicationContext(),"X "+h.getX()+" : Y "+h.getY(),Toast.LENGTH_SHORT).show();

            }
        });

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
        leftAxis.setAxisLineColor(Color.RED);


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
        if (boxTemp.isChecked()) {
            A_temp.add(new Entry(indice, (float) listData.recup_data(indice-1).getTemperature()));
            LineDataSet setTemp = new LineDataSet(A_temp, "Température");
            setTemp.setAxisDependency(YAxis.AxisDependency.LEFT);
            paramSet(setTemp);
            setTemp.setColor(Color.BLUE);
            setTemp.setCircleColor(Color.BLUE);
            dataSets.add(setTemp);
        }
        if (boxLux.isChecked()) {
            A_lux.add(new Entry(indice, (float) listData.recup_data(listData.list_size() - 1).getHumidite()));
            //       A_lux.add(new Entry(listData.recup_data(listData.list_size() - 1).getHeure(), listData.recup_data(listData.list_size() - 1).getD_humidite()));
            LineDataSet set02 = new LineDataSet(A_lux, "Lux");
            paramSet(set02);
            set02.setColor(Color.YELLOW);
            set02.setCircleColor(Color.YELLOW);
            dataSets.add(set02);
        }

        if (boxHumi.isChecked()) {
            A_humi.add(new Entry(indice, (float) listData.recup_data(listData.list_size()-1 ).getHumidite()));
            LineDataSet setHumi = new LineDataSet(A_humi, "Humidité");
            setHumi.setAxisDependency(YAxis.AxisDependency.RIGHT);
            paramSet(setHumi);
            setHumi.setColor(Color.RED);
            setHumi.setCircleColor(Color.RED);

            dataSets.add(setHumi);
        }

        LineData data = new LineData(dataSets);
        graph.setData(data);
        data.notifyDataChanged();
        graph.notifyDataSetChanged();
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
        set.setValueTextSize(0f);
        set.setValueTextColor(Color.BLACK);

        /*  set.setValueTextSize(10f);*/
        set.setDrawValues(true);

    }

    void actuValues() {
        DecimalFormat a = new DecimalFormat("##.###");
        int y = (listData.list_size()) - 1;
        val1.setText(a.format(listData.recup_data(y).getTemperature()) + "°"); // Test si c'est possible d'y faire avec indice
        val1.setTextSize(18);
        val4.setText(listData.recup_data(y).getTemps());
        val2.setTextSize(18);

        val3.setText(a.format(listData.recup_data(y).getHumidite()) + "%");
        val3.setTextSize(18);
        val4.setText(a.format(listData.recup_data(y).getHumidite()) + "%");
        val4.setTextSize(18);
    }


    void paramGraph() {
        graph.setNoDataText("Aucune données reçu pour le moment");
//        graph.setNoDataTextColor(3);
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
        switch (view.getId()) {
            case R.id.btnAdd:
                Pop_up customPopup = new Pop_up(this);
                customPopup.build("Ajout O2","");
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
            case R.id.retourArr:
                Pop_up customPopup = new Pop_up(this);
                customPopup.build("Sûr?");
                System.out.println("Retour écran titre ");
                customPopup.getYesButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GraphPage.this.finish();
                    }
                });
                customPopup.getNoButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customPopup.dismiss();
                    }
                });
                break;
            case R.id.setting:
                System.out.println("Parametre");
                Intent openSetting;
                openSetting = new Intent(GraphPage.this, SettingPage.class);
                startActivity(openSetting);
                break;
            case R.id.btnExport:
                try{
                    Toast.makeText(getApplicationContext(),"Export excel commencé ",Toast.LENGTH_SHORT).show();
                    exportFile();
                    System.out.println("Export excel ?");

                }
                catch(Exception e){
                    e.printStackTrace();
                }

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return false;
    }
    private void exportFile() {
        File file = new File(Environment.getExternalStorageDirectory()+File.separator+"Download"+File.separator+"SAE MESURES","Mesure.xls");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Mesures");
        // Add value in the cell
        HSSFRow row0 = sheet.createRow(0);

        // Ajout d'une cellule
        HSSFRow titreRow = sheet.getRow(0);
        HSSFCell cellTitre0 = titreRow.createCell(0);
        cellTitre0.setCellValue("Numero mesure");

        HSSFCell cell0 = titreRow.getCell(0);
        HSSFCellStyle nomCell0 = cell0.getCellStyle();
        cell0.setCellStyle(nomCell0);

        row0.createCell(1).setCellValue("Humidite");
        row0.createCell(2).setCellValue("Temperature");
        row0.createCell(3).setCellValue("Heure");

        HSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("0");
        row1.createCell(1).setCellValue("41");
        row1.createCell(2).setCellValue("21.9");
        row1.createCell(3).setCellValue("16:20:24");
        try {
            if (file.exists()){
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast.makeText(this, "Export excel dans le fichier telechargements", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}