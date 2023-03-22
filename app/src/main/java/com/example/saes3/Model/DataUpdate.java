

package com.example.saes3.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public interface DataUpdate {
      MutableLiveData<Data> listenerDonnées= new MutableLiveData<>();
      MutableLiveData<String> listenerTemps = new MutableLiveData<>();
    MutableLiveData<ArrayList<Data>> listenerTabData = new MutableLiveData<>();


      public default  LiveData<Data> getData() {
         return listenerDonnées;
     }

    public default  LiveData<ArrayList<Data>> getTabData() {
        return listenerTabData;
    }

     public default LiveData<String> getMoments(){
         return  listenerTemps;
     }

     public default void updateLiveData(Data data) {
         if (data != null) {
             listenerDonnées.postValue(data);
             System.out.println("sdnjuivrefvn<");
         }
     }

         public default void updateMoments (String temps){
             if (temps != null){
                 listenerTemps.postValue(temps);
             }
         }
    public default void updateLiveTabData(ArrayList<Data> arrayList) {
        if (arrayList != null) {
            listenerTabData.postValue(arrayList);
            System.out.println("sdnjuivrefvn<");
        }
    }

    //faire get et update, mais comment signiflier?

}