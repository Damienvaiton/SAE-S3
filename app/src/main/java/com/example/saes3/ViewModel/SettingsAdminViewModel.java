
package com.example.saes3.ViewModel;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saes3.Model.Data;
import com.example.saes3.Model.ESP;
import com.example.saes3.Model.FirebaseAccess;
import com.example.saes3.Model.ListData;
import com.example.saes3.Util.ClassTransitoireViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SettingsAdminViewModel extends ViewModel {
    private HashMap<String, String> hashmapESP;
    private FirebaseAccess database;

    private ClassTransitoireViewModel transit;

    private MutableLiveData<ArrayList<String>> listener = new MutableLiveData<>();

    private MutableLiveData<Data> listenerData = new MutableLiveData<>();
    private ArrayList<String> tabESP = new ArrayList<>();


    public SettingsAdminViewModel() {
        database = FirebaseAccess.getInstance();
        transit=ClassTransitoireViewModel.getInstance();
        transit.setSettingsAdminViewModel(this);
        database.getAllESP();
        hashmapESP = new HashMap<>();

        ListData.getInstance().getListAllData().addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                // TODO document why this method is empty
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
// TODO document why this method is empty
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                listenerData.postValue((Data) sender.get(positionStart));
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
// TODO document why this method is empty
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
// TODO document why this method is empty
            }
        });
    }
    public void setListenerESP() {

        database.setRealtimeDataListener();
        database.loadInData();
        database.setEspTimeListener();
    }
    private final MutableLiveData<String> liveDataRefresh = new MutableLiveData<>();

    public LiveData<Data> getListenerData(){return listenerData;}
    public void updateRefresh(String refresh){
        liveDataRefresh.postValue(refresh);
    }

    public LiveData<String> getTempsAdmin() {
        return liveDataRefresh;
    }

    public void clearTab(){
        ListData.getInstance().listSupAllData();


    }


    public LiveData<ArrayList<String>> getHashmapESP() {
        return listener;
    }


    public void addESP(String esp, @Nullable String nom) {
        if (nom == null) {
            hashmapESP.putIfAbsent(esp, null);
            tabESP.add(esp);
        } else {
            hashmapESP.put(esp, nom);
            tabESP.add(nom);
        }
        listener.postValue(tabESP);
    }

    /**nameES
     *      * Function trigger if a ESP has been delete to the databse
     *      * Remove the ESP name of tabESP if @param P has been find in tabESP
     */
    public void deleteESP(String nameESP) {
        Iterator<String> iterator = tabESP.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.equals(nameESP)) {
                iterator.remove(); // Utiliser l'itérateur pour supprimer l'élément de la liste
            }
        }
        listener.postValue(tabESP);
    }

    /**
     * Define the selected ESP to FirebaseAcess
     */
    public void creaESP(String nom) {
        for (Map.Entry<String, String> entry : hashmapESP.entrySet()) {
            if (entry.getValue()==nom || entry.getKey()==nom) {
                if (entry.getValue() == null) {
                    database.setEsp(new ESP(entry.getKey(), null));
                    return;
                }
                database.setEsp(new ESP(entry.getKey(), null));
                return;
            }
        }


    }


    /**
     * Define the selected ESP to FirebaseAcess
     */


    public void renameESP(String nickname){
        database.setNicknameEsp(nickname);
    }
    public void suppESP(){
        database.deleteEsp();
    }

    public void resetESP(){
        database.resetValueFirebase();
        ListData.getInstance().listSupAllData();
    }
    public void setESPrefresh(String temps){
        database.setEspRefreshRate(Integer.parseInt(temps));
    }


}