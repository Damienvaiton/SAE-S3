package com.example.saes3.Model;

import androidx.databinding.ObservableArrayList;

import java.io.Serializable;


public class ListData implements Serializable {
    private static ListData instance;
    private final ObservableArrayList listAllData;


    public static ListData getInstance() {

        ListData result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ListData.class) {
            if (instance == null) {
                instance = new ListData();
            }
            return instance;
        }
    }

    public ObservableArrayList<Data> getListAllData() {
        return listAllData;
    }

    private ListData() {
        listAllData=new ObservableArrayList<>();
    }

    public Data recupData(int position) {
        return (Data) listAllData.get(position);
    }


    public void listAddData(Data data) {
        listAllData.add(data);
    }

    public void listSupAllData() {
        listAllData.clear();
    }

    public int listSize() {
        return listAllData.size();
    }


}