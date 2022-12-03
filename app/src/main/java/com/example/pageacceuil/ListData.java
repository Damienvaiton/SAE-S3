package com.example.pageacceuil;

import java.util.ArrayList;


public class ListData {

    public ArrayList<Data> listData;

    public ListData(){
       listData = new ArrayList<Data>(1);
    }


    public Data recup_data(int i){
        return listData.get(i);
    }

    public void list_add_data(Data a){listData.add(a);
    }

    public void list_supAll_data(){
        listData.clear();
    }

    public int list_size() {
        return listData.size();
    }
}

