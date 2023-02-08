package com.example.pageacceuil;

import java.io.Serializable;
import java.util.ArrayList;


public class ListData {
    private static volatile ListData instance;
    private ArrayList<Data> listData;


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


    public ListData() {
        listData = new ArrayList<Data>(0);
    }

    public Data recup_data(int i) {
        return listData.get(i);
    }

    public void deleteAllData() {
        listData.clear();
    }

    public void list_add_data(Data a) {
        listData.add(a);
    }

    public void list_supAll_data() {
        listData.clear();
    }

    public int list_size() {
        return listData.size();
    }

    public void listsortCO2() {
        for (int i = 0; i < listData.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(i).getCO2() < listData.get(j).getCO2()) {
                    Data temp = listData.get(i);
                    listData.set(i, listData.get(j));
                    listData.set(j, temp);
                }
            }
        }
    }

    public void listsortTemp() {
        for (int i = 0; i < listData.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(i).getTemperature() < listData.get(j).getTemperature()) {
                    Data temp = listData.get(i);
                    listData.set(i, listData.get(j));
                    listData.set(j, temp);
                }
            }
        }
    }

    public void listsortHum() {
        for (int i = 0; i < listData.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(i).getHumidite() < listData.get(j).getHumidite()) {
                    Data temp = listData.get(i);
                    listData.set(i, listData.get(j));
                    listData.set(j, temp);
                }
            }
        }
    }

    public void listsortO2() {
        for (int i = 0; i < listData.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(i).getO2() < listData.get(j).getO2()) {
                    Data temp = listData.get(i);
                    listData.set(i, listData.get(j));
                    listData.set(j, temp);
                }
            }
        }
    }

    public void listsortLux() {
        for (int i = 0; i < listData.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(i).getLight() < listData.get(j).getLight()) {
                    Data temp = listData.get(i);
                    listData.set(i, listData.get(j));
                    listData.set(j, temp);
                }
            }
        }
    }

    public void listsortDate() {
        for (int i = 0; i < listData.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(i).getTemps().compareTo(listData.get(j).getTemps()) < 0) {
                    Data temp = listData.get(i);
                    listData.set(i, listData.get(j));
                    listData.set(j, temp);
                }
            }
        }
    }

    public void listsortDateDesc() {
        for (int i = 0; i < listData.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(i).getTemps().compareTo(listData.get(j).getTemps()) > 0) {
                    Data temp = listData.get(i);
                    listData.set(i, listData.get(j));
                    listData.set(j, temp);
                }
            }
        }
    }

    public void listsortCO2Desc() {
        for (int i = 0; i < listData.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(i).getCO2() > listData.get(j).getCO2()) {
                    Data temp = listData.get(i);
                    listData.set(i, listData.get(j));
                    listData.set(j, temp);
                }
            }
        }
    }

    public void listsortTempDesc() {
        for (int i = 0; i < listData.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(i).getTemperature() > listData.get(j).getTemperature()) {
                    Data temp = listData.get(i);
                    listData.set(i, listData.get(j));
                    listData.set(j, temp);
                }
            }
        }
    }

    public void listsortHumDesc() {
        for (int i = 0; i < listData.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(i).getHumidite() > listData.get(j).getHumidite()) {
                    Data temp = listData.get(i);
                    listData.set(i, listData.get(j));
                    listData.set(j, temp);
                }
            }
        }
    }

    public void listsortO2Desc() {
        for (int i = 0; i < listData.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(i).getO2() > listData.get(j).getO2()) {
                    Data temp = listData.get(i);
                    listData.set(i, listData.get(j));
                    listData.set(j, temp);
                }
            }
        }
    }

    public void listsortLuxDesc() {
        for (int i = 0; i < listData.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(i).getLight() > listData.get(j).getLight()) {
                    Data temp = listData.get(i);
                    listData.set(i, listData.get(j));
                    listData.set(j, temp);
                }
            }
        }
    }
}