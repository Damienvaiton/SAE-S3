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

    public void listsortCO2 () {
        for (int i = 0; i < olistData.size(); i++) {
            for (int j = 0; j < olistData.size(); j++) {
                if (olistData.get(i).getCO2() < olistData.get(j).getCO2()) {
                    Data temp = olistData.get(i);
                    olistData.set(i, olistData.get(j));
                    olistData.set(j, temp);
                }
            }
        }
    }

    public void listsortTemp () {
        for (int i = 0; i < olistData.size(); i++) {
            for (int j = 0; j < olistData.size(); j++) {
                if (olistData.get(i).getTemperature() < olistData.get(j).getTemperature()) {
                    Data temp = olistData.get(i);
                    olistData.set(i, olistData.get(j));
                    olistData.set(j, temp);
                }
            }
        }




    }

    public void listsortHum () {
        for (int i = 0; i < olistData.size(); i++) {
            for (int j = 0; j < olistData.size(); j++) {
                if (olistData.get(i).getHumidite() < olistData.get(j).getHumidite()) {
                    Data temp = olistData.get(i);
                    olistData.set(i, olistData.get(j));
                    olistData.set(j, temp);
                }
            }
        }
    }

    public void listsortO2 () {
        for (int i = 0; i < olistData.size(); i++) {
            for (int j = 0; j < olistData.size(); j++) {
                if (olistData.get(i).getO2() < olistData.get(j).getO2()) {
                    Data temp = olistData.get(i);
                    olistData.set(i, olistData.get(j));
                    olistData.set(j, temp);
                }
            }
        }
    }

    public void listsortLux () {
        for (int i = 0; i < olistData.size(); i++) {
            for (int j = 0; j < olistData.size(); j++) {
                if (olistData.get(i).getLux() < olistData.get(j).getLux()) {
                    Data temp = olistData.get(i);
                    olistData.set(i, olistData.get(j));
                    olistData.set(j, temp);
                }
            }
        }
    }

    public void listsortDate () {
        for (int i = 0; i < olistData.size(); i++) {
            for (int j = 0; j < olistData.size(); j++) {
                if (olistData.get(i).getTemps().compareTo(olistData.get(j).getTemps()) < 0) {
                    Data temp = olistData.get(i);
                    olistData.set(i, olistData.get(j));
                    olistData.set(j, temp);
                }
            }
        }
    }

    public void listsortDateDesc () {
        for (int i = 0; i < olistData.size(); i++) {
            for (int j = 0; j < olistData.size(); j++) {
                if (olistData.get(i).getTemps().compareTo(olistData.get(j).getTemps()) > 0) {
                    Data temp = olistData.get(i);
                    olistData.set(i, olistData.get(j));
                    olistData.set(j, temp);
                }
            }
        }
    }

    public void listsortCO2Desc () {
        for (int i = 0; i < olistData.size(); i++) {
            for (int j = 0; j < olistData.size(); j++) {
                if (olistData.get(i).getCO2() > olistData.get(j).getCO2()) {
                    Data temp = olistData.get(i);
                    olistData.set(i, olistData.get(j));
                    olistData.set(j, temp);
                }
            }
        }
    }

    public void listsortTempDesc () {
        for (int i = 0; i < olistData.size(); i++) {
            for (int j = 0; j < olistData.size(); j++) {
                if (olistData.get(i).getTemperature() > olistData.get(j).getTemperature()) {
                    Data temp = olistData.get(i);
                    olistData.set(i, olistData.get(j));
                    olistData.set(j, temp);
                }
            }
        }
    }

    public void listsortHumDesc () {
        for (int i = 0; i < olistData.size(); i++) {
            for (int j = 0; j < olistData.size(); j++) {
                if (olistData.get(i).getHumidite() > olistData.get(j).getHumidite()) {
                    Data temp = olistData.get(i);
                    olistData.set(i, olistData.get(j));
                    olistData.set(j, temp);
                }
            }
        }
    }

    public void listsortO2Desc () {
        for (int i = 0; i < olistData.size(); i++) {
            for (int j = 0; j < olistData.size(); j++) {
                if (olistData.get(i).getO2() > olistData.get(j).getO2()) {
                    Data temp = olistData.get(i);
                    olistData.set(i, olistData.get(j));
                    olistData.set(j, temp);
                }
            }
        }
    }

    public void listsortLuxDesc () {
        for (int i = 0; i < olistData.size(); i++) {
            for (int j = 0; j < olistData.size(); j++) {
                if (olistData.get(i).getLux() > olistData.get(j).getLux()) {
                    Data temp = olistData.get(i);
                    olistData.set(i, olistData.get(j));
                    olistData.set(j, temp);
                }
            }
        }
    }





}

