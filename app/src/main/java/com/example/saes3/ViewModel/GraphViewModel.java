package com.example.saes3.ViewModel;

import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saes3.AppApplication;
import com.example.saes3.Model.Data;
import com.example.saes3.Model.FirebaseAccess;
import com.example.saes3.Model.ListData;
import com.example.saes3.R;
import com.example.saes3.Util.AlertDialog;
import com.example.saes3.Util.ClassTransitoireViewModel;
import com.example.saes3.View.GraphiqueActivity;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class GraphViewModel extends ViewModel {
    /**
     * LiveData taking care of transiting the object of the new graph from the ViewModel to the View     */
    private final MutableLiveData<LineData> updateGraph = new MutableLiveData<>();
    private final MutableLiveData<String> updateTemps = new MutableLiveData<>();
    private final MutableLiveData<Data> updateData = new MutableLiveData<>();
    private FirebaseAccess acess;
    private boolean leftAxisUsed = false;
    private boolean rightAxisUsed = false;
    private String leftAxisName = "";
    private String rightAxisName = "";
    /**
     * Arrays of each LineDataSet entry
     */
    private ArrayList<Entry> A_temp = new ArrayList<>();
    private ArrayList<Entry> A_lux = new ArrayList<>();
    private ArrayList<Entry> A_CO2 = new ArrayList<>();
    private ArrayList<Entry> A_humi = new ArrayList<>();
    private ArrayList<Entry> A_O2 = new ArrayList<>();
    /**
     * Tables grouping the ArrayList of entry and the name for the construction of the graph     */
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

    /**
     * Handler to observe if ESP stop respond
     */
    Handler handlerLostESP = new Handler();
    Runnable runnableLostESP;
    /**
     * Constructor of ViewModel
     */

    public GraphViewModel() {
        this.acess = FirebaseAccess.getInstance();
        ClassTransitoireViewModel.getInstance().setGraphViewModel(this);
        acess.loadInData();
        acess.setRealtimeDataListener();
        acess.setEspTimeListener();

        handlerLostESP = new Handler();

        runnableLostESP = () -> {
            if(AppApplication.getCurrentActivity() != null && AppApplication.getCurrentActivity() instanceof GraphiqueActivity) {
                AlertDialog.getInstance().lostESP();
            }
        };
    }


    /**
     * LiveData taking care of passing ESP refresh rate from ViewModel to View     */
    public void updateRefresh(String refresh) {
        updateTemps.postValue(refresh);
        handlerLostESP.postDelayed(runnableLostESP, FirebaseAccess.refresh*2);

    }

    /**
     * Getter LiveData of graphique
     */
    public LiveData getUpdateGraph() {
        return updateGraph;
    }

    /**
     * Getter LiveData of refresh time ESP
     */
    public LiveData<String> getMoments() {
        return updateTemps;
    }

    /**
     * update Function of data LiveData
     *
     * @param data Objet Data récupéré de Firebase
     */
    public void updateData(Data data) {
        if (data != null) {

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





    /**
     *if this instance was destruct, listener of Firebase was also destruct
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        acess.deleteListener();
    }

    /**
     * Find which checkButton is checked
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
            default:
                Log.w("Switch checkbox gra","ID inconnu");
        }

    }

    /**
     * Charge les données dans leurs ArrayList respective
     *
     * @param data
     */
    void chargerDonner(Data data) {
        A_CO2.add(new Entry(A_CO2.size() - 1, data.getCo2()));
        A_temp.add(new Entry(A_temp.size() - 1, data.getTemperature()));
        A_humi.add(new Entry(A_CO2.size() - 1, data.getHumidite()));
        A_O2.add(new Entry(A_CO2.size() - 1, data.getO2()));
        A_lux.add(new Entry(A_CO2.size() - 1, data.getLight()));
        creaGraph();
    }

    public ListData returnValue() {
        return ListData.getInstance();
    }

    /**
     * Fonction who create the graphic objet
     *
     * @param
     */


    void creaGraph() {

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
        ListData instanceListData = ListData.getInstance();
        if (instanceListData.recupData(cptLignes).getCo2() == 0) {
            return false;
        }
        if (instanceListData.recupData(cptLignes).getTemperature() == 0) {
            return false;
        }
        if (instanceListData.recupData(cptLignes).getHumidite() == 0) {
            return false;
        }
        if (instanceListData.recupData(cptLignes).getO2() == 0) {
            return false;
        }
        if (instanceListData.recupData(cptLignes).getLight() == 0) {
            return false;
        }
        return instanceListData.recupData(cptLignes).getTemps() != "";
    }

    public String exportFile() {
        ListData instanceListData = ListData.getInstance();
        int cptLignes = instanceListData.listSize()-1;
        if (cptLignes <= 1) {
            return "Export excel annulé, pas assez de valeurs";
        }
        if (!isDataValid(cptLignes)) {
            cptLignes--;
        }
        BufferedWriter writer;
        File csvFile;
        String titre = "Numero Mesure;Humidite;Temerature;CO2;O2;Lux;Heure";

        try {
            csvFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Mesure.csv");
            if (!csvFile.exists()) {
                csvFile.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(csvFile));

            writer.write(titre);
            for (int i = 0; i < cptLignes; i++) {
                String resultat = Integer.toString(i);
                resultat += ";" + Math.round(instanceListData.recupData(i).getHumidite() * 1000.0) / 1000.0;
                resultat += ";" + Math.round(instanceListData.recupData(i).getTemperature() * 100.0) / 100.0;
                resultat += ";" + Math.round(instanceListData.recupData(i).getCo2() * 1000.0) / 1000.0;
                resultat += ";" + Math.round(instanceListData.recupData(i).getO2() * 1000.0) / 1000.0;
                resultat += ";" + Math.round(instanceListData.recupData(i).getLight() * 1000.0) / 1000.0;
                resultat += ";" + instanceListData.recupData(i).getTemps();
                writer.newLine();
                writer.write(resultat);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de l'export, annulation";
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
