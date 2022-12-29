package com.example.pageacceuil;

import java.io.Serializable;
import java.util.ArrayList;


public class ListData implements Serializable {

    public transient ArrayList<Data> olistData;

    public ListData(){
       olistData = new ArrayList<Data>(1);
    }


    public Data recup_data(int i){
        return olistData.get(i);
    }

    public void list_add_data(Data a){
        olistData.add(a);
    }

    public ArrayList<Data> getOlistData() {
        return olistData;
    }

    public void list_supAll_data(){
        olistData.clear();
    }

    public int list_size() {
        return olistData.size();
    }
}

