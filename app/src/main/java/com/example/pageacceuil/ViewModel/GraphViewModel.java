package com.example.pageacceuil.ViewModel;

import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.FirebaseAccess;
import com.example.pageacceuil.R;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class GraphViewModel extends ViewModel  {
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
    /**
     * LiveData s'occupant de transiter le taux de raffraichissemnt de l'ESP du ViewModel à la Vue
     */
    public void updateRefresh(String refresh){
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
     * @param data Objet Data récupéré de Firebase
     */
    public void updateData(Data data) {
        if (data != null) {
            System.out.println("update data");
            listData.add(data);
            chargerDonner(data);
        }
    }


    public String getLeftAxisName(){
        return leftAxisName;
    }
    public String getRightAxisName(){
        return rightAxisName;
    }
    public ArrayList getListData(){
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

    /**
     * Fonction qui crée l'objet du graphique
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
     *     Choice of axe for the LineDataSet in param
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
     *     Delete listener when the activity is close
     */


    public void onClose(){
        acess.deleteListener();
    }




}
