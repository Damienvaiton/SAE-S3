package com.example.saes3.Model;

import java.io.Serializable;
import java.util.ArrayList;


public class ListData implements Serializable {
    private static ListData instance;
    private ArrayList<Data> listAllData;

    public ListData() {
        listAllData = new ArrayList<Data>(0);
    }

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

    public ArrayList<Data> getListAllData() {
        return listAllData;
    }

    public Data recup_data(int i) {
        return listAllData.get(i);
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