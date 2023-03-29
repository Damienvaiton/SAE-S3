package com.example.saes3.Model;

import java.io.Serializable;
import java.util.ArrayList;


public class ListData implements Serializable {
    private static ListData instance;
    private ArrayList<Data> listAllData;

    public ListData() {
        listData = new ArrayList<Data>(0);
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

<<<<<<< HEAD:app/src/main/java/com/example/pageacceuil/Model/ListData.java
    public ListData() {
        listAllData = new ArrayList<>(0);
    }

=======
>>>>>>> Tests_unitaires:app/src/main/java/com/example/saes3/Model/ListData.java
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

    public void listsortCO2() {
        for (int i = 0; i < listAllData.size(); i++) {
            for (int j = 0; j < listAllData.size(); j++) {
                if (listAllData.get(i).getCO2() < listAllData.get(j).getCO2()) {
                    Data temp = listAllData.get(i);
                    listAllData.set(i, listAllData.get(j));
                    listAllData.set(j, temp);
                }
            }
        }
    }

    public void listsortTemp() {
        for (int i = 0; i < listAllData.size(); i++) {
            for (int j = 0; j < listAllData.size(); j++) {
                if (listAllData.get(i).getTemperature() < listAllData.get(j).getTemperature()) {
                    Data temp = listAllData.get(i);
                    listAllData.set(i, listAllData.get(j));
                    listAllData.set(j, temp);
                }
            }
        }
    }

    public void listsortHum() {
        for (int i = 0; i < listAllData.size(); i++) {
            for (int j = 0; j < listAllData.size(); j++) {
                if (listAllData.get(i).getHumidite() < listAllData.get(j).getHumidite()) {
                    Data temp = listAllData.get(i);
                    listAllData.set(i, listAllData.get(j));
                    listAllData.set(j, temp);
                }
            }
        }
    }

    public void listsortO2() {
        for (int i = 0; i < listAllData.size(); i++) {
            for (int j = 0; j < listAllData.size(); j++) {
                if (listAllData.get(i).getO2() < listAllData.get(j).getO2()) {
                    Data temp = listAllData.get(i);
                    listAllData.set(i, listAllData.get(j));
                    listAllData.set(j, temp);
                }
            }
        }
    }

    public void listsortLux() {
        for (int i = 0; i < listAllData.size(); i++) {
            for (int j = 0; j < listAllData.size(); j++) {
                if (listAllData.get(i).getLight() < listAllData.get(j).getLight()) {
                    Data temp = listAllData.get(i);
                    listAllData.set(i, listAllData.get(j));
                    listAllData.set(j, temp);
                }
            }
        }
    }

    public void listsortDate() {
        for (int i = 0; i < listAllData.size(); i++) {
            for (int j = 0; j < listAllData.size(); j++) {
                if (listAllData.get(i).getTemps().compareTo(listAllData.get(j).getTemps()) < 0) {
                    Data temp = listAllData.get(i);
                    listAllData.set(i, listAllData.get(j));
                    listAllData.set(j, temp);
                }
            }
        }
    }

    public void listsortDateDesc() {
        for (int i = 0; i < listAllData.size(); i++) {
            for (int j = 0; j < listAllData.size(); j++) {
                if (listAllData.get(i).getTemps().compareTo(listAllData.get(j).getTemps()) > 0) {
                    Data temp = listAllData.get(i);
                    listAllData.set(i, listAllData.get(j));
                    listAllData.set(j, temp);
                }
            }
        }
    }

    public void listsortCO2Desc() {
        for (int i = 0; i < listAllData.size(); i++) {
            for (int j = 0; j < listAllData.size(); j++) {
                if (listAllData.get(i).getCO2() > listAllData.get(j).getCO2()) {
                    Data temp = listAllData.get(i);
                    listAllData.set(i, listAllData.get(j));
                    listAllData.set(j, temp);
                }
            }
        }
    }

    public void listsortTempDesc() {
        for (int i = 0; i < listAllData.size(); i++) {
            for (int j = 0; j < listAllData.size(); j++) {
                if (listAllData.get(i).getTemperature() > listAllData.get(j).getTemperature()) {
                    Data temp = listAllData.get(i);
                    listAllData.set(i, listAllData.get(j));
                    listAllData.set(j, temp);
                }
            }
        }
    }

    public void listsortHumDesc() {
        for (int i = 0; i < listAllData.size(); i++) {
            for (int j = 0; j < listAllData.size(); j++) {
                if (listAllData.get(i).getHumidite() > listAllData.get(j).getHumidite()) {
                    Data temp = listAllData.get(i);
                    listAllData.set(i, listAllData.get(j));
                    listAllData.set(j, temp);
                }
            }
        }
    }

    public void listsortO2Desc() {
        for (int i = 0; i < listAllData.size(); i++) {
            for (int j = 0; j < listAllData.size(); j++) {
                if (listAllData.get(i).getO2() > listAllData.get(j).getO2()) {
                    Data temp = listAllData.get(i);
                    listAllData.set(i, listAllData.get(j));
                    listAllData.set(j, temp);
                }
            }
        }
    }

    public void listsortLuxDesc() {
        for (int i = 0; i < listAllData.size(); i++) {
            for (int j = 0; j < listAllData.size(); j++) {
                if (listAllData.get(i).getLight() > listAllData.get(j).getLight()) {
                    Data temp = listAllData.get(i);
                    listAllData.set(i, listAllData.get(j));
                    listAllData.set(j, temp);
                }
            }
        }
    }
}