package com.example.pageacceuil.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public interface DataUpdate {


      MutableLiveData<Data> listenerDonnées = new MutableLiveData<>();
      MutableLiveData<String> listenerTemps = new MutableLiveData<>();


      public default  LiveData<Data> getData() {
         return listenerDonnées;
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

    //faire get et update, mais comment signiflier?

}
