package com.example.saes3.Model;

import androidx.databinding.ObservableArrayList;

import java.io.Serializable;


public class ListData implements Serializable {
    private static ListData instance;
    private ObservableArrayList listAllData;


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

    public ListData() {
        listAllData=new ObservableArrayList<>();
    }

    public Data recup_data(int i) {
        return (Data) listAllData.get(i);
    }

    public void deleteAllData() {
        listAllData.clear();
    }

    public void list_add_data(Data a) {
        listAllData.add(a);
    }

    public void list_supAll_data() {
        listAllData.clear();
    }

    public int list_size() {
        return listAllData.size();
    }


}