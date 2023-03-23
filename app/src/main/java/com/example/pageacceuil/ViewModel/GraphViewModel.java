package com.example.pageacceuil.ViewModel;

import android.graphics.Color;
import android.os.Environment;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.FirebaseAccess;
import com.example.pageacceuil.Model.ListData;
import com.example.pageacceuil.R;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

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
import java.util.ArrayList;

public class GraphViewModel extends ViewModel {
    private FirebaseAccess acess;
    private boolean leftAxisUsed = false;
    private boolean rightAxisUsed = false;
    private String leftAxisName = "";
    private String rightAxisName = "";
    /**
     * Tableaux de chaque entrée des LineDataSet
     */
    private ArrayList<Entry> A_temp = new ArrayList<>();
    private ArrayList<Entry> A_lux = new ArrayList<>();
    private ArrayList<Entry> A_CO2 = new ArrayList<>();
    private ArrayList<Entry> A_humi = new ArrayList<>();
    private ArrayList<Entry> A_O2 = new ArrayList<>();

    /**
     * Tableaux des regroupant les ArrayList d'entry et le nom pour la construction du graphique
     */
    private LineDataSet setHumi = new LineDataSet(A_humi, "Humidité");
    private LineDataSet setCO2 = new LineDataSet(A_CO2, "CO2");
    private LineDataSet setO2 = new LineDataSet(A_O2, "O2");
    private LineDataSet setTemp = new LineDataSet(A_temp, "Température");
    private LineDataSet setLux = new LineDataSet(A_lux, "Lux");

    private boolean boxCO2 = false;
    private boolean boxHumi = false;
    private boolean boxO2 = false;
    private boolean boxTemp = false;
    private boolean boxLux = false;


    private ArrayList<Data> listData;

    /**
     * Constructeur du ViewModel
     */

    public GraphViewModel() {
        this.acess = FirebaseAccess.getInstance();
        ClassTransitoireViewModel.getInstance().setGraphViewModel(this);
        acess.setGraphViewModel(this);
        acess.loadInData();
        acess.setRealtimeDataListener();
        acess.setEspTimeListener();
        listData = new ArrayList<>();


    }

    /**
     * LiveData s'occupant de transiter l'objet du nouveau graphique du ViewModel à la Vue
     */
    private final MutableLiveData<LineData> updateGraph = new MutableLiveData<>();

    private final MutableLiveData<String> updateTemps = new MutableLiveData<>();
    private final MutableLiveData<Data> updateData = new MutableLiveData<>();

    /**
     * LiveData s'occupant de transiter le taux de raffraichissemnt de l'ESP du ViewModel à la Vue
     */
    public void updateRefresh(String refresh) {
        updateTemps.postValue(refresh);
    }

    /**
     * Getter du LiveData du graphique
     */
    public LiveData getUpdateGraph() {
        return updateGraph;
    }

    /**
     * Getter du LiveData du temps de raffraichissement ESP
     */
    public LiveData<String> getMoments() {
        return updateTemps;
    }

    /**
     * Fonction d'update du LiveData avec dernière valeur disponible
     *
     * @param data Objet Data récupéré de Firebase
     */
    public void updateData(Data data) {
        if (data != null) {
            System.out.println("update data");
            listData.add(data);
            chargerDonner(data);
            updateData.postValue(data);
        }
    }


    public String getLeftAxisName() {
        return leftAxisName;
    }

    public String getRightAxisName() {
        return rightAxisName;
    }

    public ArrayList getListData() {
        return listData;
    }

    /**
     * Si ce ViewModel est détruit alors les listeners de la class FirebaseAccess sont détruit
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        acess.deleteListener();
    }

    /**
     * Détermine quel checkButton à été coché
     */

    public void notifyCheck(int id) {
        switch (id) {
            case R.id.boxCO2:
                boxCO2 = !boxCO2;
                creaGraph();
                break;
            case R.id.boxTemp:
                boxTemp = !boxTemp;
                creaGraph();
                break;
            case R.id.boxO2:
                boxO2 = !boxO2;
                creaGraph();
                break;
            case R.id.boxHumi:
                boxHumi = !boxHumi;
                creaGraph();
                break;
            case R.id.boxLux:
                boxLux = !boxLux;
                creaGraph();
                break;
        }
    }

    /**
     * Charge les données dans leurs ArrayList respective
     *
     * @param data
     */
    void chargerDonner(Data data) {
        System.out.println("charger donner");
        A_CO2.add(new Entry(A_CO2.size() - 1, data.getCO2()));
        A_temp.add(new Entry(A_temp.size() - 1, data.getTemperature()));
        A_humi.add(new Entry(A_CO2.size() - 1, data.getHumidite()));
        A_O2.add(new Entry(A_CO2.size() - 1, data.getO2()));
        A_lux.add(new Entry(A_CO2.size() - 1, data.getLight()));
        creaGraph();
    }

    public ListData returnValue(){
        return ListData.getInstance();
    }

    /**
     * Fonction qui crée l'objet du graphique
     *
     * @param
     */


    void creaGraph() {
        System.out.println("créa graph");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();


        if (boxCO2) {
            setCO2 = new LineDataSet(A_CO2, "CO2");
            paramSet(setCO2);
            choixAxe(setCO2);
            setCO2.setColor(Color.RED);
            setCO2.setCircleColor(Color.RED);
            dataSets.add(setCO2);
        }
        if (boxTemp) {
            setTemp = new LineDataSet(A_temp, "Température");
            paramSet(setTemp);
            choixAxe(setTemp);
            setTemp.setColor(Color.BLUE);
            setTemp.setCircleColor(Color.BLUE);
            dataSets.add(setTemp);
        }

        if (boxHumi) {
            setHumi = new LineDataSet(A_humi, "Humidité");
            paramSet(setHumi);
            choixAxe(setHumi);
            setHumi.setColor(Color.MAGENTA);
            setHumi.setCircleColor(Color.MAGENTA);
            dataSets.add(setHumi);
        }
        if (boxO2) {
            setO2 = new LineDataSet(A_O2, "O2");
            paramSet(setO2);
            choixAxe(setO2);
            setO2.setColor(Color.BLACK);
            setO2.setCircleColor(Color.BLACK);
            dataSets.add(setO2);
        }
        if (boxLux) {
            setLux = new LineDataSet(A_lux, "Lux");
            paramSet(setLux);
            choixAxe(setLux);
            setLux.setColor(Color.YELLOW);
            setLux.setCircleColor(Color.YELLOW);
            dataSets.add(setLux);

        }
        updateGraph.postValue(new LineData(dataSets));
        leftAxisUsed = false;
        rightAxisUsed = false;
    }

    private void paramSet(LineDataSet set) {
        set.setFillAlpha(120);
        set.setLineWidth(2.5f);
        set.setCircleRadius(3.5f);
        set.setCircleHoleRadius(1f);
        set.setValueTextColor(Color.BLACK);
        set.setDrawValues(false);

    }

    /**
     * Choice of axe for the LineDataSet in param
     *
     * @param data LineDataSet eady to go in the graph
     */


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
            data.setValueTextSize(15);
        }
    }

    /**
     * Delete listener when the activity is close
     */

    private boolean isDataValid(int cptLignes) {
        ListData listData= ListData.getInstance();
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

    public String exportFile() {
        ListData listData = ListData.getInstance();
        int cptLignes = listData.list_size() - 1;
        if (cptLignes < 1) {
            return "Export excel annulé, pas de valeurs";
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
                return "Export de " + cptLignes + " mesures dans le dossier téléchargements";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Export terminé, disponible dans votre dossier téléchargement";
    }

    public void onClose() {
        acess.deleteListener();
    }


    public LiveData<Data> getData() {
        return updateData;
    }
}
