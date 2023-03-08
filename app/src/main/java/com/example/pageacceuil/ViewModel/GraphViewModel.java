package com.example.pageacceuil.ViewModel;

import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.DataUpdate;
import com.example.pageacceuil.Model.ESP;
import com.example.pageacceuil.Model.FirebaseAccess;
import com.example.pageacceuil.R;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class GraphViewModel extends ViewModel implements DataUpdate {
    private FirebaseAccess acess;
    private ESP currentEsp;
    private ArrayList<Entry> A_temp = new ArrayList<>();
    private ArrayList<Entry> A_lux = new ArrayList<>();
    private ArrayList<Entry> A_CO2 = new ArrayList<>();
    private ArrayList<Entry> A_humi = new ArrayList<>();
    private ArrayList<Entry> A_O2 = new ArrayList<>();
    private boolean leftAxisUsed = false;
    private boolean rightAxisUsed = false;
    private String leftAxisName = "";
    private String rightAxisName = "";

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

    public GraphViewModel() {
        this.acess = FirebaseAccess.getInstance();
        this.currentEsp = ESP.getInstance();
        getData().observeForever(new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                System.out.println("vue modele graphviewmodel");
            }
        });
        acess.setGraphViewModel(this);
        acess.loadInData();
        acess.setRealtimeDataListener();
        acess.setEspTimeListener(currentEsp);
        listData = new ArrayList<>();

    }


    private final MutableLiveData<LineData> updateGraph = new MutableLiveData<>();

    public LiveData getUpdateGraph() {
        return updateGraph;
    }

    public void updateData(Data data) {
        if (data != null) {
            System.out.println("update data");
            listData.add(data);
            chargerDonner(data);
        }
    }

    public void updateMoments(){
        listenerTemps.postValue(currentEsp.getTauxRafrai());
    }


    public LiveData<String> getMoments() {
        return listenerTemps;
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

    @Override
    protected void onCleared() {
        super.onCleared();
        acess.deleteListener();
    }

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

    void chargerDonner(Data data) {
        System.out.println("charger donner");
        A_CO2.add(new Entry(A_CO2.size() - 1, data.getCO2()));
        A_temp.add(new Entry(A_temp.size() - 1, data.getTemperature()));
        A_humi.add(new Entry(A_CO2.size() - 1, data.getHumidite()));
        A_O2.add(new Entry(A_CO2.size() - 1, data.getO2()));
        A_lux.add(new Entry(A_CO2.size() - 1, data.getLight()));
        creaGraph();
    }

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




}
